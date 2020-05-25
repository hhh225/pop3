package test;

import databaseAccess.Dao;
import entity.User;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {


    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Socket socket=new Socket("106.54.231.32",8089);
        Scanner s=new Scanner(System.in);
        InputStream is=socket.getInputStream();
        OutputStream os=socket.getOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(os);
        ObjectInputStream ois=new ObjectInputStream(is);
        System.out.println((String)ois.readObject());
        int count=1;
        while (true){
            if (count==1){
                oos.writeObject("user user1");
                oos.flush();
                count++;
                System.out.println((String)ois.readObject());
            }
            else if (count==2){
                oos.writeObject("pass 1234567");
                oos.flush();
                count++;
                System.out.println((String)ois.readObject());
                break;
            }
            else if (count==3){
                socket.close();
            }

        }
    }
}
