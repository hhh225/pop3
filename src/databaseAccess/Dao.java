package databaseAccess;

import entity.Email;
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

    public ResultSet list(String username){
        String sql="select mail_id from mail where user_to=?";
        ResultSet rs=dataBase.exeQuery(sql,username);
        return rs;
    }

    public Email getEmail(String id) throws SQLException {
        String sql="select * from mail where mail_id=?";
        ResultSet rs=dataBase.exeQuery(sql,id);
        Email email = new Email();
        while (rs.next()){
            email.setMail_id(rs.getString("mail_id"));
            email.setUser_id(rs.getString("user_id"));
            email.setUser_from(rs.getString("user_from"));
            email.setUser_to(rs.getString("user_to"));
            email.setMail_subject(rs.getString("mail_subject"));
            email.setContent(rs.getString("content"));
            email.setMail_date(rs.getString("mail_date"));
            email.setMIME_version(rs.getString("MIME_version"));
            email.setContent_type(rs.getString("content_type"));
            email.setContent_transfer_conding(rs.getString("content_transfer_conding"));
            email.setSendate(rs.getString("sendate"));
            email.setMessage_id(rs.getString("message_id"));
            email.setFlag(rs.getString("flag"));
        }
        return email;
    }

    public void delete(String id){
        String sql="update mail set flag='0' where user_to=?";
        dataBase.updateQuery(sql,id);

    }
}
