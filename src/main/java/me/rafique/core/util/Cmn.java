package me.rafique.core.util;

import me.rafique.core.config.AppConfig;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cmn {
    public static void pr(String line){
        System.out.println(line);
    }
    public static void prIn(String line){
        pr("-- "+line);
    }
    public static void prOk(String line){
        pr("== "+line);
    }
    public static String timeDuration(LocalDateTime startTime, LocalDateTime endTime){
        Duration diff = Duration.between(startTime, endTime);
        long millis=diff.toMillis();
        long hours=millis/(1000*60*60);
        millis=millis%(1000*60*60);
        long minutes=millis/(1000*60);
        millis=millis%(1000*60);
        double seconds=millis/1000.000;
        DecimalFormat df=new DecimalFormat("00.###");
        String secsStr=df.format(seconds);
        String hms = String.format("%02d:%02d:",hours,minutes)+secsStr;
        return hms;
    }
    public static String strArrVal(String[] arr, int index){
        try{
            return arr[index].trim();
        }catch(Exception ex){return "";}
    }

    public static void sleep(long millis){
        try{
            Thread.sleep(millis);
        }
        catch(InterruptedException ex){ex.printStackTrace();}
    }
    public static void look(){
        sleep(400);
        pr("..");
    }

    public static <T> List<List<T>> splitListAsItemCount(List<T> list, int itemsCount){
        int totalCount=list.size();
        int listCountTmp= totalCount/itemsCount;
        int listCount=totalCount%itemsCount==0?listCountTmp:listCountTmp+1;
        List<List<T>> listLists=new ArrayList<List<T>>();
        for(int i=0;i<listCount;i++){
            listLists.add(i,new ArrayList<T>());
        }
        for(int i=0;i<totalCount;i++){
            int index=i/itemsCount;
            listLists.get(index).add(list.get(i));
        }
        return listLists;
    }
    public static void prEx(Exception ex){
        String exMsg=ex.getMessage()==null?"N/A":ex.getMessage();
        pr("?? Exception: "+ex.toString());
        pr("?? ExMessage: "+exMsg);
    }
    public static void prEx(Exception ex,String message){
        pr("?? ErMessage: "+message);
        prEx(ex);
    }
    public static void banner(){
        Cmn.pr("###################################");
        Cmn.pr("###            Hello            ###");
        Cmn.pr("###   "+ AppConfig.appName +"   ###");
        Cmn.pr("###       Version: 1.0.0        ###");
        Cmn.pr("###################################");
    }
    public static void appPathsTree(){
        String tree="" +
                "#\tApp Required Paths Tree\n" +
                "#\t---------------------------------\n" +
                "#\tAppJar\n" +
                "#\t│   AppCustomer.bat\n" +
                "#\t│   AppCustomer.jar\n" +
                "#\t│\n" +
                "#\t├───config\n" +
                "#\t│       app.properties\n" +
                "#\t│\n" +
                "#\t└───data\n" +
                "#\t    ├───in\n" +
                "#\t    │       in.customers.1m.txt\n" +
                "#\t    │       in.customers.sample.txt\n" +
                "#\t    │\n" +
                "#\t    └───out\n" +
                "#\t        │   out.invalid.txt\n" +
                "#\t        │   out.valid.txt\n" +
                "#\t        │\n" +
                "#\t        └───batch\n" +
                "#\t                out.batch.001.txt\n" +
                "#\t                out.batch.002.txt\n" +
                "#\t                ...\n" +
                "#\t                ...\n" +
                "#\t                out.batch.010.txt\n" +
                "#\t---------------------------------" +
                "";
        pr(tree);
    }
}
