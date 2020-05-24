package mainPack;

import databaseAccess.Dao;
import entity.Email;
import entity.User;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SocketHandle extends Thread{
    Socket socket;
    InputStream is;
    OutputStream os;
    String info;
    String state="identification";
    String username;
    String pass;
    User user;
    int count=0;
    Dao dao=new Dao();

    public SocketHandle(Socket soc) throws SQLException, ClassNotFoundException {
        socket=soc;
    }

    @Override
    public void run() {

        super.run();
        String info;
        try{
            is=socket.getInputStream();
            os=socket.getOutputStream();
            PrintWriter pw=new PrintWriter(os);
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            ObjectInputStream ois=new ObjectInputStream(is);
            ObjectOutputStream oos=new ObjectOutputStream(os);
            pw.write("+OK Welcome\n");
            pw.flush();
            while(true){
                switch (state){
                    case "identification":
                        if (count==0){
                            info=br.readLine();
                            if (info.indexOf("user")==0){
                                username=info.substring(5);
                                user=dao.verifyUsername(username);
                                if (user.getUserid()==null){
                                    pw.write("-ERR No username\n");
                                    pw.flush();
                                }
                                else {
                                    pw.write("+OK\n");
                                    pw.flush();
                                    count++;
                                }
                            }
                            else {
                                pw.write("-ERR Unknown command\n");
                                pw.flush();
                            }
                        }
                        else if (count==1){
                            info=br.readLine();
                            if (info.indexOf("pass")==0){
                                pass=info.substring(5);
                                if (!pass.equals(user.getPassword())){
                                    pw.write("-ERR Password failed\n");
                                    pw.flush();
                                }
                                else {
                                    pw.write("-OK\n");
                                    pw.flush();
                                    count=0;
                                    state="operation";
                                }
                            }
                            else {
                                pw.write("-ERR Unknown command\n");
                                pw.flush();
                            }
                        }
                        break;
                    case "operation":
                        while (true){
                            info=br.readLine();
                            if (info.equals("list")){
                                ArrayList<String> ids=new ArrayList<String>();
                                ResultSet rs=dao.list(user.getUsername());
                                while (rs.next()){
                                    ids.add(rs.getString("mail_id"));
                                }
                                oos.writeObject(ids);
                                oos.flush();
                            }
                            else if (info.indexOf("retr")==0){
                                String id=info.substring(5);
                                Email email =  dao.getEmail(id);
                                HashMap<String,String> hashMap=new HashMap<String, String>();
                                hashMap.put("mail_id",email.getMail_id());
                                hashMap.put("mail_subject",email.getMail_subject());
                                hashMap.put("mail_date",email.getMail_date());
                                hashMap.put("message_id",email.getMessage_id());
                                hashMap.put("MIME_version",email.getMIME_version());
                                hashMap.put("content",email.getContent());
                                hashMap.put("content_transfer_conding",email.getContent_transfer_conding());
                                hashMap.put("content_type",email.getContent_type());
                                hashMap.put("flag",email.getFlag());
                                hashMap.put("user_from",email.getUser_from());
                                hashMap.put("user_to",email.getUser_to());
                                hashMap.put("user_id",email.getUser_id());
                                hashMap.put("sendate",email.getSendate());
                                oos.writeObject(hashMap);
                                oos.flush();
                            }
                            else if (info.indexOf("dele")==0){

                            }
                            break;
                        }
                        break;
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
