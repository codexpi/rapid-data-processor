package me.rafique.core.config;


import me.rafique.app.Main;
import me.rafique.core.util.Cmn;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigExpert {
    private static Properties prop;
    private static String pathTarget;
    private static String pathDirData;
    public static void loadConfig(){
        prop = new Properties();
        try {
            pathTarget=getPathTarget();
            pathDirData=pathTarget+"/data";
            String pathPropLocal=pathTarget+"/config/app.properties";
            if((new File(pathPropLocal).exists())){
                InputStream inputStream = new FileInputStream(pathPropLocal);
                prop.load(inputStream);
                Cmn.prIn("Config (in local) found.");
            }else{
                java.net.URL urlProp = ClassLoader.getSystemResource("app.properties");
                prop.load(urlProp.openStream());
                Cmn.prIn("Config (in jar) found.");
            }
            // printProperties(prop);
            setAppConfig();
        }
        catch(Exception ex) {
            Cmn.prEx(ex);
        }
    }
    private static String getPathTarget(){
        File fileTarget = new File("fileTarget");
        String pathTarget=fileTarget.getAbsoluteFile().getParentFile().getAbsolutePath()+"\\assets\\AppRdp";
        String pathClass= Main.class.getResource("Main.class").getPath();
        if(pathClass.contains(".jar!")){
            File fileNothing = new File("x");
            pathTarget = fileNothing.getAbsoluteFile().getParentFile().getAbsolutePath();
        }
        return pathTarget;
    }
    private static void setAppConfig() throws Exception{
        AppConfig.appName=propStr("appName");
        AppConfig.appVersion=propStr("appVersion");
        AppConfig.dbConnStr=propStr("dbConnStr");


        setProppathFolderData();
        String folderData=AppConfig.pathFolderData;
        AppConfig.customerDataFileNameSample=folderData+"/"+propStr("customerDataFileNameSample");
        AppConfig.customerDataFileName=folderData+"/"+propStr("customerDataFileName");
        AppConfig.customerDataOutFileValid=folderData+"/"+propStr("customerDataOutFileValid");
        AppConfig.customerDataOutFileInvalid=folderData+"/"+propStr("customerDataOutFileInvalid");
        AppConfig.customerDataOutBatch=folderData+"/"+propStr("customerDataOutBatch");

        AppConfig.noOfMaxLinesPerBatchFile=propInt("noOfMaxLinesPerBatchFile");
        AppConfig.threadCountMax=propInt("threadCountMax");
        AppConfig.threadCountMin=propInt("threadCountMin");
        AppConfig.threadCountLimit=propInt("threadCountLimit");
        AppConfig.threadCountFactor=propInt("threadCountFactor");

        AppConfig.tableColumnList=propStr("tableColumnList");
        AppConfig.tableNameValidCustomer=propStr("tableNameValidCustomer");
        AppConfig.tableNameInvalidCustomer=propStr("tableNameInvalidCustomer");
        AppConfig.tableNeedToDeleteBeforeInsert=propBool("tableNeedToDeleteBeforeInsert");
        AppConfig.checkItemExistsBeforeInsert=propBool("checkItemExistsBeforeInsert");

        AppConfig.exportFromDbRequired=propBool("exportFromDbRequired");
        AppConfig.exportPathValidData=folderData+"/"+propStr("exportPathValidData");
        AppConfig.exportPathInvalidData=folderData+"/"+propStr("exportPathInvalidData");
        AppConfig.exportPathBatch=folderData+"/"+propStr("exportPathBatch");

        AppConfig.columnIndexToValidatePhone=propInt("columnIndexToValidatePhone");
        AppConfig.columnIndexToValidateEmail=propInt("columnIndexToValidateEmail");

        AppConfig.flagForValid=propStr("flagForValid");
        AppConfig.flagForInvalid=propStr("flagForInvalid");



    }
    private static void setProppathFolderData() throws Exception{
        String folderData=propStr("pathFolderData");
        if(folderData.equals("")){
            if((new File(pathDirData).exists())){
                folderData=pathDirData;
            }else{
                String msg="" +
                        "?? A folder name data is required for input and output files.\n" +
                        "?? Value of pathFolderData property is blank and default data folder does not exist.\n" +
                        "?? Either set value for pathFolderData property or confirm data folder exists in app jar folder.\n" +
                        "?? Please check app required paths tree.";
                Cmn.pr(msg);
                Cmn.appPathsTree();
                throw new Exception("Data folder does not exist.");
            }
        }else{
            if((new File(folderData).exists()==false)){
                String msg="?? Data folder path provided in app.properties does not exist.";
                Cmn.pr(msg);
                Cmn.appPathsTree();
                throw new Exception("Data folder does not exist.");
            }
        }
        AppConfig.pathFolderData=folderData;
    }
    private static String propStr(String name){
        try{
            String val=String.valueOf(prop.getProperty(name).trim());
            return val;
        }
        catch(Exception ex){return "";}
    }
    private static int propInt(String name){
        try{
            int val=Integer.valueOf(prop.getProperty(name).trim());
            return val;
        }
        catch(Exception ex){return -1;}
    }
    private static boolean propBool(String name){
        try{
            boolean val=Boolean.valueOf(prop.getProperty(name).trim());
            return val;
        }
        catch(Exception ex){return false;}
    }
    private static void printProperties(Properties prop)
    {
        for (Object key: prop.keySet()) {
            System.out.println(key + ": " + prop.getProperty(key.toString()));
        }
    }
}
