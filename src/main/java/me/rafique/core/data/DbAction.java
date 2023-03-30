package me.rafique.core.data;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import me.rafique.core.config.AppConfig;
import me.rafique.core.util.Cmn;

import java.sql.*;

public class DbAction {
    private Connection cnn;

    private boolean openExplicitly=false;

    private static DbAction da;
    public static DbAction create(){
        if(da==null){
            da=new DbAction();
        }
        return da;
    }
    public DbAction() {
        try{
            DriverManager.registerDriver(new SQLServerDriver());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static boolean checkDbConnection(){
        try{
            boolean res=false;
            DbAction action=create();
            action.openConnIn();
            res=action.isOpened();
            action.closeConnIn();
            return res;
        }
        catch(Exception ex){
            Cmn.prEx(ex);
            return false;
        }
    }

    private boolean isOpened(){
        try{
            if(cnn==null){return false;}
            if(cnn.isClosed()){return false;}
            return true;
        }catch(SQLException ex){return false;}
    }
    private void openConnVerbose(){
        if(isOpened()) return;
        try{
            cnn=DriverManager.getConnection(AppConfig.dbConnStr);
            if (cnn==null){Cmn.pr("?? DB connection problem.");}
        }catch(SQLException ex){
            Cmn.prEx(ex,"Error in database connection.");
            Cmn.pr("?? Check database host, user, password, dbname etc. in app properties.");
//            System.exit(-1);
        }
    }
    private void closeConnVerbose(){
        if(!isOpened()){return;}
        try{
            cnn.close();
        }catch(SQLException ex){ex.printStackTrace();}

    }
    private void openConnIn(){
        if(openExplicitly){return;}
        openConnVerbose();
    }
    private void closeConnIn(){
        if(openExplicitly){return;}
        closeConnVerbose();
    }
    public void openConnEx(){
        openConnVerbose();
        openExplicitly=true;
    }
    public void closeConnEx(){
        closeConnVerbose();
        openExplicitly=false;
    }
    public int runSql(String sql){
        try{
            openConnIn();
            Statement statement = cnn.createStatement();
            int result=statement.executeUpdate(sql);
            closeConnIn();
            return result;
        }
        catch(Exception ex){ex.printStackTrace();return -1;}

    }

    public boolean rowsExist(String sql){
        boolean result=false;
        openConnIn();
        try{

            Statement statement = cnn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            result=resultSet.next();
        }
        catch(SQLException ex){ex.printStackTrace();}
        closeConnIn();
        return result;
    }

    public int rowsCount(String sql){
        int result=0;
        openConnIn();
        try{

            Statement statement = cnn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                result=resultSet.getInt(1);
            }
        }
        catch(SQLException ex){ex.printStackTrace();}
        closeConnIn();
        return result;
    }


    public ResultSet getResultSet(String sql){
        try{
            Statement statement = cnn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return  resultSet;
        }catch(SQLException ex){ex.printStackTrace();return null;}
    }

    public void showCustomer(){
        openConnIn();
        String sql="SELECT * FROM Customer";
        try{
            Statement statement = cnn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                final String id=result.getString("Id");
                final String name=result.getString("Name");
                final String phone=result.getString("Phone");
                System.out.println(id+","+name+","+phone);
            }
        }catch(SQLException ex){ex.printStackTrace();}
        closeConnIn();
    }
}
