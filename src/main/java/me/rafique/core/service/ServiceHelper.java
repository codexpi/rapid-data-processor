package me.rafique.core.service;

import me.rafique.core.config.AppConfig;
import me.rafique.core.entity.Customer;
import me.rafique.core.util.Cmn;

public class ServiceHelper {
    public static Customer customerFromLine(String line){
        String[] fieldItems=line.split(",");
        Customer customer=new Customer(
                0,
                Cmn.strArrVal(fieldItems,0),
                Cmn.strArrVal(fieldItems,1),
                Cmn.strArrVal(fieldItems,2),
                Cmn.strArrVal(fieldItems,3),
                Cmn.strArrVal(fieldItems,4),
                Cmn.strArrVal(fieldItems,5),
                Cmn.strArrVal(fieldItems,6),
                Cmn.strArrVal(fieldItems,7)
        );
        return customer;
    }
    public static int getThreadCount(int itemCount){
        int threadCount=AppConfig.threadCountMin;
        if(itemCount>AppConfig.threadCountLimit){
            threadCount=AppConfig.threadCountMax;
        }else{
            int threadCountTmp=itemCount/AppConfig.threadCountFactor;
            int rem=itemCount%AppConfig.threadCountFactor;
            threadCount=rem==0?threadCountTmp:threadCountTmp+1;
        }
        return threadCount;
    }
}
