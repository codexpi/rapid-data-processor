package me.rafique.core.config;

public class AppConfig {
    public static String appName="Customer Data Processor";
    public static String appVersion="1.0.0";
    public static String dbConnStr="jdbc:sqlserver://RAFIQUEPC\\SQL2022XP;databaseName=DataProcZ;user=sa;password=s@1234;encrypt=true;trustServerCertificate=true";
    public static String pathFolderData="D:/Me/Job/OrrangeToolz/Srcs/data";
    public static String customerDataFileNameSample="D:\\Me\\Job\\OrrangeToolz\\Srcs\\data\\in\\in.customers.sample.txt";
    public static String customerDataFileName="D:\\Me\\Job\\OrrangeToolz\\Srcs\\data\\in\\in.customers.1m.txt";
    public static String customerDataOutFileValid="D:\\Me\\Job\\OrrangeToolz\\Srcs\\data\\out\\out.valid.txt";
    public static String customerDataOutFileInvalid="D:\\Me\\Job\\OrrangeToolz\\Srcs\\data\\out\\out.invalid.txt";
    public static String customerDataOutBatch="D:\\Me\\Job\\OrrangeToolz\\Srcs\\data\\out\\batch";
    public static int noOfMaxLinesPerBatchFile=10000;
    public static int threadCountMax =64;
    public static int threadCountMin =1;
    public static int threadCountLimit=1000;
    public static int threadCountFactor=100;

    public static String tableColumnList="[firstName],[lastName],[city],[state],[zip],[phone],[email],[ip]";
    public static String tableNameValidCustomer="CustomerValid";
    public static String tableNameInvalidCustomer="CustomerInvalid";
    public static boolean tableNeedToDeleteBeforeInsert=false;
    public static boolean checkItemExistsBeforeInsert=false;

    public static boolean exportFromDbRequired=false;
    public static String exportPathValidData="pathValid";
    public static String exportPathInvalidData="pathInvalid";
    public static String exportPathBatch="out/batchDb";

    public static int columnIndexToValidatePhone=5;
    public static int columnIndexToValidateEmail=6;
    public static String flagForValid="validFlag";
    public static String flagForInvalid="invalidFlag";
}
