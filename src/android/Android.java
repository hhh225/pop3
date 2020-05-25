package android;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Android {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket=new Socket("106.54.231.32",8087);
        InputStream is=socket.getInputStream();
        OutputStream os=socket.getOutputStream();
        ObjectInputStream ois=new ObjectInputStream(is);
        ObjectOutputStream oos=new ObjectOutputStream(os);
        String username = "user0";       //用户名
        String pass = "1234567";         //密码
        String info;
        info=(String)ois.readObject();      //第一次，刚连接到这里接受信息
        System.out.println(info);
        int count=1;
        ArrayList<String> arrayList = null;
        while (true){
            if (count==1){
                oos.writeObject("user "+username);
                oos.flush();
                info=(String)ois.readObject();
                count++;
            }
            else if (count==2){
                oos.writeObject("pass "+pass);
                oos.flush();
                info=(String)ois.readObject();
                count++;
            }
            else if (count==3){
                oos.writeObject("list");
                oos.flush();
                arrayList=(ArrayList<String>)ois.readObject();         //得到这个用户的所有邮件的id
                count++;
            }
            else if (count==4){
                for (String id:arrayList){                //通过每个邮件的id来获取邮件
                    oos.writeObject("retr "+id);
                    //hashMap保存了一封邮件，可以用hashMap.get("mail_subject")的方式获得邮件标题，content、sendate也一样，和数据库字段名相同
                    HashMap<String,String> hashMap=(HashMap<String, String>)ois.readObject();
                }
                socket.close();
                break;
            }
        }
    }
}
