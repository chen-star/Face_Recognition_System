/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproj.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author chenjiaxin
 */
public class StoreStudent {

    private Statement stmt;

    public void store(ArrayList<String> s, int label) {
        initializeDB();
        storeDB(s, label);
    }

    public int getCount() {
        initializeDB();
        int num = 0;
        try {
            String q = "select count(*) from StudentInfo";
            ResultSet res = stmt.executeQuery(q);
            num = res.getInt(1);
        } catch (Exception e) {
            System.out.println("count error");
        }
        return num;
    }

    // init the database connection
    private void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("Driver loaded");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Students", "root", "login");
            //System.out.println("Database Connected");

            stmt = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // store data into DB
    private void storeDB(ArrayList<String> s, int label) {

        try {
            //insert a new data
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String str = format.format(date);
            Date newd = format.parse(str);
            java.sql.Date sd = new java.sql.Date(newd.getTime());
            String updateStmt = "INSERT INTO StudentInfo (name, andrewId, age, program, photo, lastVisited, count, reason, label,gender) VALUES ("
                    + "\"" + s.get(0) + "\"," + "\"" + s.get(1) + "\"," + s.get(2) + ","
                    + "\"" + s.get(3) + "\"," + "\"" + s.get(4) + "\"," + sd + ","
                    + 1 + "," + "\"," + s.get(5) + "\"," + label + "\'" + s.get(6) + "\'" + ")";

            stmt.executeUpdate(updateStmt);
            //System.out.println("store success");

        } catch (Exception e) {
            System.out.print("MYSQL ERROR:" + e.getMessage());
        }

    }

}
