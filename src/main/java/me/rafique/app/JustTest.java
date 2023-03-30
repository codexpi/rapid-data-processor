package me.rafique.app;

import me.rafique.core.config.AppConfig;
import me.rafique.core.data.DbAction;
import me.rafique.core.entity.Customer;
import me.rafique.core.repository.CustomerRepository;
import me.rafique.core.service.CustomerServiceDb;
import me.rafique.core.service.ServiceHelper;
import me.rafique.core.util.Cmn;

import java.io.File;

public class JustTest {
    //this is only for testing
    public static void test01(){

        String pathTarget="";
        String classPath=Main.class.getResource("Main.class").getPath();
        if(classPath.contains(".jar!")){
            File fileNothing = new File("x");
            pathTarget = fileNothing.getAbsoluteFile().getParent();
        }else{
            pathTarget="D:/Me/Job/OrrangeToolz/Srcs/AppJar";
        }
        Cmn.pr("Target Path: "+pathTarget);
        String pathData=pathTarget+"/data";
        String pathConfig=pathTarget+"/config";
        Cmn.pr("Path Data: "+pathData);
        Cmn.pr("Path Config: "+pathConfig);
    }

    public static void test02(){
        try{
            File fileTarget = new File("fileTarget");
            String pathTarget=fileTarget.getAbsoluteFile().getParentFile().getAbsolutePath()+"\\assets\\AppJar";
//            String pathNothing=fileNothing.getAbsoluteFile().getParentFile().getAbsolutePath();
            Cmn.prIn(pathTarget);
            Cmn.appPathsTree();
        }
        catch(Exception ex){Cmn.prEx(ex);}
    }

    public static void test03(){
        CustomerRepository cr=new CustomerRepository();
        cr.setTableName("CustomerValid");
        String cline="Duane,Jones,Kansas city,MO,64131,8166452368,kelseyfrechin@yahoo.com,192.231.171.1023";
        DbAction da=new DbAction();
        boolean exist=cr.existByDa(da,cline.split(","));
        Cmn.pr("data exist:  "+exist);
    }
    public static void test04(){
        Cmn.pr("del tab: "+ AppConfig.tableNeedToDeleteBeforeInsert);
        Cmn.pr("check item: "+ AppConfig.checkItemExistsBeforeInsert);

        CustomerServiceDb csDb=new CustomerServiceDb();


        csDb.setTableName(AppConfig.tableNameValidCustomer);
        csDb.setExportPathOut(AppConfig.exportPathValidData);
        csDb.exportAllAsync();

        Cmn.prOk("Export Batch.");
        csDb.setExportPathBatch(AppConfig.exportPathBatch);
        csDb.setFlag(AppConfig.flagForValid);
        csDb.exportBatchAsync();

        csDb.exportAllWait();
        Cmn.prOk("Export All Done.");
        csDb.exportBatchWait();
        Cmn.prOk("Export Batch Done.");
        Cmn.prOk("++ DONE ++");
    }
}
