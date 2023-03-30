package me.rafique.core.service;

import me.rafique.core.config.AppConfig;
import me.rafique.core.util.Cmn;
import me.rafique.core.util.FileExpert;
import me.rafique.core.util.ValidExpert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CustomerServiceFile {
    private Thread[] threadsSaveFiles;
    private Thread threadSaveFileValidCustomer;
    private Thread threadSaveFileInvalidCustomer;
    public HashMap<String,List<String>> validateCustomer(List<String> linesAll){


        //remove duplicate
        List<String> linesUnique=new ArrayList<String>();
        HashSet<String> linesHs=new HashSet<String>();
        for(String line:linesAll){
            linesHs.add(line);
        }

        for(String line:linesHs){
            linesUnique.add(line);
        }
        linesAll.clear();
        linesHs.clear();
        Cmn.prIn("Unique customer count : "+linesUnique.size());

        //validate customer (email,phone)
        // index 5-phone, 6-email
        List<String> linesValid=new ArrayList<String>();
        List<String> linesInvalid=new ArrayList<String>();

        for(String line:linesUnique){
            String[] lineItems=line.split(",");
            String phone=Cmn.strArrVal(lineItems,AppConfig.columnIndexToValidatePhone);
            String email=Cmn.strArrVal(lineItems,AppConfig.columnIndexToValidateEmail);
            if(ValidExpert.phoneAndEmailEx(phone,email)){
                linesValid.add(line);
            }else{
                linesInvalid.add(line);
            }
        }
        linesUnique.clear();

        HashMap<String,List<String>> linesCustomer=new HashMap<String,List<String>>();
        linesCustomer.put("valid",linesValid);
        linesCustomer.put("invalid",linesInvalid);
        return linesCustomer;
    }

    public void saveFile(HashMap<String,List<String>> linesCustomer){
        // save valid file
        List<String> linesValid=linesCustomer.get("valid");
        threadSaveFileValidCustomer=new Thread(new Runnable() {
            @Override
            public void run() {
                FileExpert.writeFile(linesValid, AppConfig.customerDataOutFileValid);
            }
        });


        // save invalid file
        List<String> linesInvalid=linesCustomer.get("invalid");
        threadSaveFileInvalidCustomer=new Thread(new Runnable() {
            @Override
            public void run() {
                FileExpert.writeFile(linesInvalid, AppConfig.customerDataOutFileInvalid);
            }
        });


    }
    public void saveFileThreadStart(){
        threadSaveFileValidCustomer.start();
        threadSaveFileInvalidCustomer.start();
    }
    public void saveFileThreadJoin(){
        try{
            threadSaveFileValidCustomer.join();
            threadSaveFileInvalidCustomer.join();
        }catch(InterruptedException ex){ex.printStackTrace();}
    }
//    public void

    public void saveFilesBatch(List<String> linesCustomer){
        int itemsCount=AppConfig.noOfMaxLinesPerBatchFile;
        String batchDir=AppConfig.customerDataOutBatch;
        List<List<String>> listLines=Cmn.splitListAsItemCount(linesCustomer,itemsCount);
        int intFileCount=listLines.size();
        threadsSaveFiles=new Thread[intFileCount];
        for(int i=0;i<intFileCount;i++){
            List<String> lines=listLines.get(i);
            String fileName=batchDir+"\\out.batch."+String.format("%03d",i+1)+".txt";
            String threadName="TH_"+String.format("%03d",i+1);
            threadsSaveFiles[i]=new Thread(new Runnable() {
                @Override
                public void run() {
                    FileExpert.writeFile(lines,fileName);
                }
            });
        }

    }
    public void saveFilesBatchThreadStart(){
        for(Thread thread:threadsSaveFiles){
            thread.start();
        }
    }
    public void saveFilesBatchThreadJoin(){
        try{
            for(Thread thread:threadsSaveFiles){
                thread.join();
            }
        }catch(InterruptedException ex){ex.printStackTrace();}
    }



}
