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

            System.out.println("start");
            InputStream is=socket.getInputStream();
            System.out.println("start");
            OutputStream os=socket.getOutputStream();
            System.out.println("start");
            ObjectInputStream ois=new ObjectInputStream(is);
            System.out.println("objectinput");
            ObjectOutputStream oos=new ObjectOutputStream(os);
            System.out.println("objectoutput");
            oos.writeObject("+OK Welcome");
            oos.flush();
//            PrintWriter pw=new PrintWriter(os);
//            System.out.println("start");
//            BufferedReader br=new BufferedReader(new InputStreamReader(is));
//            System.out.println("bufferedreader start");
//            pw.write("+OK Welcome\n");
//            pw.flush();
            int breakup=0;
            while(breakup==0){
                switch (state){
                    case "identification":
                        if (count==0){
                            info=(String)ois.readObject();
                            if (info.indexOf("quit")==0){
                                socket.close();
                                breakup=1;
                            }
                            else if (info.indexOf("user")==0){
                                username=info.substring(5);
                                user=dao.verifyUsername(username);
                                if (user.getUserid()==null){
//                                    pw.write("-ERR No username\n");
//                                    pw.flush();
                                    oos.writeObject("-ERR No username");
                                    oos.flush();
                                }
                                else {
//                                    pw.write("+OK\n");
//                                    pw.flush();
                                    System.out.println("username:"+username);
                                    oos.writeObject("+OK");
                                    oos.flush();
                                    count++;
                                }
                            }

                            else {
                                oos.writeObject("-ERR Unknown command");
                                oos.flush();
                            }
                        }
                        else if (count==1){
                            info=(String)ois.readObject();
                            if (info.indexOf("pass")==0){
                                pass=info.substring(5);
                                if (!pass.equals(user.getPassword())){
                                    oos.writeObject("-ERR Password failed");
                                    oos.flush();
                                }
                                else {
                                    System.out.println("password:"+pass);
                                    oos.writeObject("+OK");
                                    oos.flush();
                                    count=0;
                                    state="operation";
                                }
                            }
                            else {
                                oos.writeObject("-ERR Unknown command");
                                oos.flush();
                            }
                        }
                        break;
                    case "operation":

                        while (true){
                            info=(String)ois.readObject();
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
                                String id=info.substring(5);

                            }
                            else if (info.equals("quit")){
                                state="identification";
                                break;
                            }

                        }
                        break;
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
