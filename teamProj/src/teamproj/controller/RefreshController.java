/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproj.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author chenjiaxin
 */
public class RefreshController {

    private Statement stmt;

    public void store(ArrayList<String> s) {
        initializeDB();
        storeDB(s);
    }

    // init the database connection
    private void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver2 loaded");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Students", "root", "login");
            System.out.println("Database2 Connected");

            stmt = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
        // store data into DB
    private void storeDB(ArrayList<String> s) {

        try {
            //insert a new data
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String str = format.format(date);
            Date newd = format.parse(str);
            java.sql.Date sd = new java.sql.Date(newd.getTime());
            String updateStmt = "UPDATE StudentInfo SET count = " + "\"" + s.get(1) + "\"," + "reason = " + "\"" + s.get(2) +"\"," + "lastVisited = " + "\"" + sd + "\""
                                + "where andrewid = " + "\"" + s.get(0)+ "\"";

            stmt.executeUpdate(updateStmt);
            System.out.println("store success");

        } catch (Exception e) {
            System.out.print("MYSQL ERROR:" + e.getMessage());
        }

    }


}
