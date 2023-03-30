package me.rafique.app;

import me.rafique.core.config.AppConfig;
import me.rafique.core.config.ConfigExpert;
import me.rafique.core.util.Cmn;

public class Main {
    public static void main(String[] args) {
        Cmn.banner();
        Cmn.prIn("App started.");
        Cmn.prIn("App Working.");
        try{
//            ConfigExpert.loadConfig();
//            JustTest.test01();
//            JustTest.test02();
//            JustTest.test03();
//            JustTest.test04();

            ConfigExpert.loadConfig();
            Cmn.prIn("Config loaded.");
            Cmn.prIn("App Version: "+ AppConfig.appVersion);
            Cmn.look();
            DataProcessor.processCustomer();
            Cmn.prIn("App execution has been done.");
            Cmn.pr("___THANKS___");
        }
        catch(Exception ex){
            Cmn.prEx(ex,"Error in app main().");
            Cmn.prIn("App execution has been done with error.");
        }

    }
}