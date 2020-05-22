package databaseAccess;

import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Dao {
    DataBase dataBase;
    public Dao() throws SQLException, ClassNotFoundException {
        dataBase=new DataBase();
    }

    public User verifyUsername(String username) throws SQLException {
        User user=new User();
        String sql="select * from user where user_name=?";
        ResultSet rs=dataBase.exeQuery(sql,username);
        while (rs.next()){
            user.setUserid(rs.getString("user_id"));
            user.setUsername(rs.getString("user_name"));
            user.setPassword(rs.getString("user_psw"));
            user.setType(rs.getString("user_type"));
            user.setAuthor(rs.getString("user_author"));
        }
        return user;
    }
}
