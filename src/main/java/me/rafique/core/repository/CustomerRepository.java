package me.rafique.core.repository;

import me.rafique.core.config.AppConfig;
import me.rafique.core.data.DbAction;
import me.rafique.core.util.Cmn;
import java.sql.ResultSet;

public class CustomerRepository {
    private String tableName;
    private String columnList;
    private DbAction da;
    public CustomerRepository() {
        tableName="CustomerAll";
        columnList=AppConfig.tableColumnList;
        da=DbAction.create();
    }


    public void setTableName(String tableName){
        this.tableName=tableName;
    }

    public void setColumnList(String columnList) {
        this.columnList = columnList;
    }

    public void createRaw(String[] row){
        String[] columns=columnList.split(",");
        int columnCount=columns.length;
        String colValues="";
        for(int i=0;i<columnCount-1;i++){
            colValues+="\t'" + Cmn.strArrVal(row,i) + "',\n";
        }
        colValues+="\t'" + Cmn.strArrVal(row,columnCount-1) + "'\n";
        String insertSql="INSERT INTO "+tableName+" ("+columnList+") \n" +
                "VALUES(\n" +
                colValues +
                ")";
        da.runSql(insertSql);
    }
    public void createTable(){
        String[] columns=columnList.split(",");
        String columnsDeclaration="";
        for(String column:columns){
            columnsDeclaration+="   "+column+" [nvarchar](255) NULL,\n";
        }
        String sql="IF NOT EXISTS (SELECT * FROM sys.objects " +
                "WHERE object_id = OBJECT_ID(N'[dbo].["+tableName+"]') AND type in (N'U'))\n" +
                "BEGIN\n" +
                "CREATE TABLE [dbo].["+tableName+"](\n" +
                "   [id] [int] IDENTITY(1,1) NOT NULL,\n" +
                columnsDeclaration+
                "   CONSTRAINT [PK_"+tableName+"] PRIMARY KEY CLUSTERED ([id] ASC)\n" +
                ")\n" +
                "END" +
                "";
        da.runSql(sql);
    }
    public void deleteCustomersAll(){
        String sql="DELETE FROM "+tableName;
        da.runSql(sql);
        sql="DBCC CHECKIDENT ("+tableName+", RESEED, 0)";
        da.runSql(sql);
    }
    public int rowsCount(){
        String sql="SELECT COUNT([id]) AS Idx FROM ["+tableName+"]";
        int rc=da.rowsCount(sql);
        return rc;
    }

    public boolean existByDa(DbAction dbAction, String[] row){
        String[] columns=columnList.split(",");
        int columnCount=columns.length;
        String filterExp="";
        for(int i=0;i<columnCount;i++){
            filterExp+="AND "+columns[i]+"='"+Cmn.strArrVal(row,i)+"' \n";
        }
        String sql="" +
                "SELECT [id] FROM "+tableName+" WHERE 1=1 \n" +
                filterExp +
                "";
        boolean exist=dbAction.rowsExist(sql);
        return exist;
    }
    public void createByDa(DbAction dbAction, String[] row){
        String[] columns=columnList.split(",");
        int columnCount=columns.length;
        String colValues="";
        for(int i=0;i<columnCount-1;i++){
            colValues+="\t'" + Cmn.strArrVal(row,i) + "',\n";
        }
        colValues+="\t'" + Cmn.strArrVal(row,columnCount-1) + "'\n";

        String insertSql="INSERT INTO "+tableName+" ("+columnList+") \n" +
                "VALUES(\n" +
                colValues +
                ")";
        dbAction.runSql(insertSql);
    }

    public ResultSet getResultSet(DbAction dbAction){
        String sql="" +
                "SELECT "+columnList+" \n" +
                "FROM ["+tableName+"]" +
                "";
        ResultSet resultSet=dbAction.getResultSet(sql);
        return resultSet;
    }
    public ResultSet getResultSet(DbAction dbAction,int offset,int rowCountPerPage){
        String sql="" +
                "SELECT "+columnList+" \n" +
                "FROM ["+tableName+"]\n" +
                "ORDER BY [id] \n" +
                "OFFSET "+offset+" ROWS FETCH NEXT "+rowCountPerPage+" ROWS ONLY" +
                "";
        ResultSet resultSet=dbAction.getResultSet(sql);
        return resultSet;
    }

}
