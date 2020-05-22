package databaseAccess;

import java.sql.*;

public class DataBase {
    Connection conn;
    final String url="jdbc:mysql://106.54.231.32:3306/mail?serverTimezone=UTC";
    final String userName="root";
    final String pass="yszn1704";
    ResultSet rs;
    PreparedStatement stat;

    public DataBase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn= DriverManager.getConnection(url,userName,pass);
    }

    public ResultSet exeQuery(String sql,Object... obj){

        try{
            stat=conn.prepareStatement(sql);
            for (int i=0;i<obj.length;i++){
                stat.setObject(i+1,obj[i]);
            }
            rs=stat.executeQuery();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }

    public void updateQuery(String sql,Object... obj){
        try{
            stat=conn.prepareStatement(sql);
            for (int i=0;i<obj.length;i++){
                stat.setObject(i+1,obj[i]);
            }
            stat.executeUpdate();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void close(){
        if (rs!=null){
            try{
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();

            }

        }

        if(stat!=null){
            try{
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (conn!=null){
            try{
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }
}
