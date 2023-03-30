package me.rafique.core.service;

import me.rafique.core.config.AppConfig;
import me.rafique.core.data.DbAction;
import me.rafique.core.entity.Customer;
import me.rafique.core.repository.CustomerRepository;
import me.rafique.core.util.Cmn;
import me.rafique.core.util.FileExpert;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CustomerServiceDb {
    private String tableName="CustomerTemp";
    private String exportPathBatch ="batch007";
    private String exportPathOut ="data007";
    private String flag="valid";
    private final CustomerRepository repo;
    private int threadCount= AppConfig.threadCountMin;
    private Thread[] threads;
    private List<Future<String>> futuresExportBatch;
    private Future<String> futureExportAll;
    public CustomerServiceDb() {
        repo =new CustomerRepository();
    }


    private List<String>[] splitLines(List<String> lines){
        int mainIndex=0;
        List<String>[] arrLines=new List[threadCount];
        for(int i=0;i<threadCount;i++){
            arrLines[i]=new ArrayList<String>();
        }
        int linesSize=lines.size();
        int arrSizeTemp=(linesSize/threadCount);
        int arrSize=linesSize % threadCount==0?arrSizeTemp:arrSizeTemp+1;
        for(String line:lines){
            int index=mainIndex/arrSize;
            arrLines[index].add(line);
            mainIndex++;
        }
        return arrLines;
    }



    private void createAsync(List<String> linesAll){
        int linesAllItemCount=linesAll.size();
        if(linesAllItemCount<1){return;}
        threadCount=ServiceHelper.getThreadCount(linesAllItemCount);
        threads=new Thread[threadCount];
        Cmn.pr("-- Items splitting...");
        List<String>[] arrLines=splitLines(linesAll);
        Cmn.pr("-- Items splitted into " + threadCount + " subsets.");

        Cmn.pr("-- Total "+threadCount+" threads to insert into Customer ("+tableName+") table are creating...");
        for(int i=0;i<threadCount;i++){
            DbAction da=new DbAction();
            List<String> lines=arrLines[i];
            String threadName="TH_"+i;
            threads[i]=new Thread(new Runnable() {
                @Override
                public void run() {
                    da.openConnEx();
                    for(String line:lines){
                        Customer customer= ServiceHelper.customerFromLine(line);;
                        String[] row=line.split(",");
                        if(AppConfig.checkItemExistsBeforeInsert){
                            if(repo.existByDa(da,row)){continue;}
                        }
                        repo.createByDa(da,row);
                    }
                    da.closeConnEx();
                }
            });
        }
        Cmn.pr("-- Total "+threadCount+" threads to insert into "+tableName+" created.");

    }
    public void insertAsyncStart(){
        if(threads==null){return;}
        if(threads.length<1){return;}
        Cmn.pr("-- Threads to insert into Customer ("+tableName+") are starting...");
        for(Thread thread:threads){
            thread.start();
        }
        Cmn.pr("-- Threads to insert into Customer ("+tableName+") has been started.");
    }


    public void insertAsyncJoin(){
        if(threads==null){return;}
        if(threads.length<1){return;}
        try{
            for(Thread thread:threads){
                thread.join();
            }
        }catch(InterruptedException ex){ex.printStackTrace();}
    }
    public void setTableName(String tableName){
        this.tableName=tableName;
        repo.setTableName(this.tableName);
    }

    public void setExportPathBatch(String exportPathBatch) {
        this.exportPathBatch = exportPathBatch;
    }

    public void setExportPathOut(String exportPathOut) {
        this.exportPathOut = exportPathOut;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void insertAsync(List<String> lines){
        if(lines==null){return;}
        if(lines.size()<1){return;}
        Cmn.pr("-- Customer ("+tableName+") table is creating...");
        repo.createTable();
        Cmn.pr("-- Customer ("+tableName+") table created.");

        if(AppConfig.tableNeedToDeleteBeforeInsert){
            Cmn.pr("-- Customer ("+tableName+") table is deleting...");
            repo.deleteCustomersAll();
            Cmn.pr("-- Customer ("+tableName+") table deleted.");
        }

        Cmn.pr("-- Total "+lines.size()+" items need to insert.");

        createAsync(lines);
    }


    public void exportAllAsync(){
        if(AppConfig.exportFromDbRequired==false){return;}
        String threadId="th_export_all_"+flag;
        ExecutorService executorExportAll=Executors.newFixedThreadPool(1);
        futureExportAll=executorExportAll.submit(new Runnable() {
            @Override
            public void run() {
                DbAction da=new DbAction();
                da.openConnEx();
                ResultSet resultSet=repo.getResultSet(da);
                FileExpert.writeFile(resultSet, exportPathOut);
                da.closeConnEx();
            }
        },threadId);
        executorExportAll.shutdown();
    }
    public void exportAllWait(){
        if(futureExportAll==null){return;}
        try{
            futureExportAll.get();
            String threadId=futureExportAll.get();
        }
        catch(Exception ex){
            Cmn.prEx(ex);
        }
    }


    public void exportBatchAsync(){
        if(AppConfig.exportFromDbRequired==false){return;}
        int rowsCount=repo.rowsCount();
        if(rowsCount<1){return;}
        int linesInFile=AppConfig.noOfMaxLinesPerBatchFile;
        int threadCountTmp=rowsCount/linesInFile;
        int threadCountExport=rowsCount%linesInFile==0?threadCountTmp:threadCountTmp+1;

        ExecutorService executorExportBatch= Executors.newFixedThreadPool(threadCountExport);
        futuresExportBatch=new ArrayList<Future<String>>();
        for(int i=0;i<threadCountExport;i++){
            int ix=i;
            String threadId="TH_"+(ix+1)+"_"+flag;;
            Future<String> future=executorExportBatch.submit(new Runnable() {
                @Override
                public void run() {
                    String exportFilename=exportPathBatch+"/out.db."+flag+"."+String.format("%04d",(ix+1))+".txt";
                    DbAction da=new DbAction();
                    da.openConnEx();
                    int offset=(ix*linesInFile);
                    int rowCountPerPage=linesInFile;
                    ResultSet resultSet=repo.getResultSet(da,offset,rowCountPerPage);
                    FileExpert.writeFile(resultSet, exportFilename);
                    da.closeConnEx();
                }
            },threadId);
            futuresExportBatch.add(future);
        }
        executorExportBatch.shutdown();
    }
    public void exportBatchWait(){
                /*
        while (!executorExportBatch.isTerminated()) {
            Cmn.sleep(500);
        }
        */
        if(futuresExportBatch==null){return;}
        if(futuresExportBatch.size()<1){return;}
        try{
            for(Future<String> future:futuresExportBatch){
                String threadId=future.get();
            }
        }
        catch(Exception ex){Cmn.prEx(ex);}
    }


}
