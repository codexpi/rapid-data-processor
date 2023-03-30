package me.rafique.core.util;

import me.rafique.core.config.AppConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

public class FileExpert {
    public static List<String> readLines(){
        try{
            List<String> allLines = Files.readAllLines(Paths.get(AppConfig.customerDataFileName));
            return allLines;
        }
        catch(Exception ex){
            Cmn.prEx(ex,"Error in reading file.");
            return null;
        }
    }
    public static void writeFile(List<String> lines,String fileName){
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            for(String line:lines){
                fileWriter.write(line+"\n");
            }
            fileWriter.close();
        }
        catch(Exception ex){Cmn.prEx(ex,"Error in writing file.");;}
    }

    public static void writeFile(ResultSet resultSet, String fileName){
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            int collCount = resultSet.getMetaData().getColumnCount();
            while(resultSet.next()){
                String[] cols=new String[collCount];
                for(int i=1;i<=collCount;i++){
                    String collVal=resultSet.getString(i);
                    cols[i-1]=collVal;
                }
                String lineOne=String.join(",",cols);
                fileWriter.write(lineOne+"\n");
            }
            fileWriter.close();
        }
        catch(Exception ex){Cmn.prEx(ex,"Error in writing file.");;}
    }
}
