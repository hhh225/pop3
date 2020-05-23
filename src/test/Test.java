package test;

import databaseAccess.Dao;
import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class Test {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String st="hell(\\s)+llo";
        System.out.println("hellllo".matches(st));
        ArrayList<String> ids=new ArrayList<>();
        System.out.println(ids.size());
    }
}
