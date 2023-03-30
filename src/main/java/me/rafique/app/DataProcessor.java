package me.rafique.app;

import me.rafique.core.config.AppConfig;
import me.rafique.core.data.DbAction;
import me.rafique.core.service.CustomerServiceDb;
import me.rafique.core.service.CustomerServiceFile;
import me.rafique.core.util.Cmn;
import me.rafique.core.util.FileExpert;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class DataProcessor {
    public static void processCustomer(){
        // getting all customer
        List<String> linesAll = FileExpert.readLines();
        Cmn.prIn("Total customer count: "+linesAll.size()+".");
        Cmn.look();
        // filter duplicate, validate phone, email
        CustomerServiceFile serviceFile=new CustomerServiceFile();
        HashMap<String, List<String>> linesCustomer=serviceFile.validateCustomer(linesAll);
        List<String> linesValid=linesCustomer.get("valid");
        List<String> linesInvalid=linesCustomer.get("invalid");
        Cmn.prIn("Valid customer count: "+linesValid.size());
        Cmn.prIn("Invalid customer count: "+linesInvalid.size());
        Cmn.look();



        // check db connection
        Cmn.prIn("Checking db connection...");
        boolean dbConnOk=DbAction.checkDbConnection();
        if(dbConnOk){
            Cmn.prIn("Db connected successfully.");
        }else{
            Cmn.prIn("Db was not connected.");
            Cmn.prIn("System is continuing with file operation only...");
        }
        Cmn.prIn("Db connection checked.");
        Cmn.look();

        // start timing
        LocalDateTime startTime=LocalDateTime.now();
        Cmn.pr(">> Process Start Time: "+startTime);

        // insert valid customer
        CustomerServiceDb serviceDbValid=new CustomerServiceDb();
        if(dbConnOk){
            serviceDbValid.setTableName(AppConfig.tableNameValidCustomer);
            serviceDbValid.insertAsync(linesValid);
        }

        // insert invalid customer
        CustomerServiceDb serviceDbInvalid=new CustomerServiceDb();
        if(dbConnOk){
            serviceDbInvalid.setTableName(AppConfig.tableNameInvalidCustomer);
            serviceDbInvalid.insertAsync(linesInvalid);
        }


        // save customer into file
        serviceFile.saveFile(linesCustomer);
        serviceFile.saveFilesBatch(linesValid);



        // thread start
        if(dbConnOk){
            serviceDbValid.insertAsyncStart();
            serviceDbInvalid.insertAsyncStart();
        }
        serviceFile.saveFileThreadStart();
        serviceFile.saveFilesBatchThreadStart();
        Cmn.prOk("Threads are running...");
        Cmn.prOk("Please wait...");

        // thread join
        if(dbConnOk){
            serviceDbValid.insertAsyncJoin();
            serviceDbInvalid.insertAsyncJoin();
        }
        Cmn.prOk("Customer (valid/invalid) data inserted into DB.");
        serviceFile.saveFileThreadJoin();
        Cmn.prOk("Customer (valid/invalid) data wrote into file.");
        serviceFile.saveFilesBatchThreadJoin();
        Cmn.prOk("Customer (valid) data wrote into segmented batch file.");
        Cmn.prOk("Threads have been completed works.");

        Cmn.pr("..");
        Cmn.pr("..");

        // export data from db into file
        if(dbConnOk){
            Cmn.prIn("Customer (valid) data exporting...");
            serviceDbValid.setExportPathOut(AppConfig.exportPathValidData);
            serviceDbValid.exportAllAsync();

            Cmn.prIn("Customer (invalid) data exporting...");
            serviceDbInvalid.setExportPathOut(AppConfig.exportPathInvalidData);
            serviceDbInvalid.exportAllAsync();

            Cmn.prIn("Customer (valid) batch exporting...");
            serviceDbValid.setExportPathBatch(AppConfig.exportPathBatch);
            serviceDbValid.exportBatchAsync();

            Cmn.prOk("Threads to export are running...");
            Cmn.prOk("Please wait...");

            // waiting to complete valid,invalid,valid batch data export
            serviceDbValid.exportAllWait();
            Cmn.prOk("Customer (valid) data exported.");
            serviceDbInvalid.exportAllWait();;
            Cmn.prOk("Customer (invalid) data exported.");
            serviceDbValid.exportBatchWait();
            Cmn.prOk("Customer (invalid) data batch exported.");
        }


        // end timing
        LocalDateTime endTime=LocalDateTime.now();
        String reqTime=Cmn.timeDuration(startTime,endTime);
        Cmn.pr(">> Process End Time: "+endTime);
        Cmn.pr(">> Time ("+reqTime+") has been taken to complete the process.");
        Cmn.look();
    }

    public static void testProcess00(){
        //        DbAction da=DbAction.create();
//        da.showCustomer();

//        String ss="insert into Customer(Name,Phone) values('Hurmoti','8899')";
//        da.runSql(ss);
//        FileXp.readFileLineByLine();

//        CustomerService service=new CustomerService();
//        System.out.println("Inserting Data");
//        service.insertCustomerFromFile();
//        System.out.println("Data inserted...");
        /*
        CustomerServiceThreaded serviceThreaded=new CustomerServiceThreaded(64);
        System.out.println("Inserting by thread.");
        serviceThreaded.insertAsync();
        System.out.println("Inserted by thread.");
        */
        /*
        String email="asdf.hel.lo@abc.co.gm";
        String phone="2223334444";
        Cmn.pr("Email: "+email+" >> "+String.valueOf(ValidExpert.email(email)));
        Cmn.pr("Phone: "+phone+" >> "+String.valueOf(ValidExpert.phone(phone)));
        */
    }
    public static void testProcess01() throws Exception{
        List<String> filesAll= FileExpert.readLines();
        CustomerServiceDb serviceDbAll=new CustomerServiceDb();
        serviceDbAll.setTableName("CustomerAll");
        serviceDbAll.insertAsync(filesAll);
    }
}
