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
public class ReportController {

    private Statement stmt;

    public ArrayList<String> queryDate(String d1, String d2) {
        initializeDB();
        return queryDB1(d1, d2);
    }

    public ArrayList<String> queryCount(String d1, String d2) {
        initializeDB();
        return queryDB2(d1, d2);
    }

    // init the database connection
    private void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver3 loaded");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Students", "root", "login");
            System.out.println("Database3 Connected");

            stmt = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<String> queryDB1(String d1, String d2) {
        ArrayList<String> result = new ArrayList<>();

        try {
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format1.parse(d1);
            Date date2 = format1.parse(d2);
            java.sql.Date sd1 = new java.sql.Date(date1.getTime());
            java.sql.Date sd2 = new java.sql.Date(date2.getTime());

            String queryStmt = "SELECT name, andrewId FROM StudentInfo WHERE lastVisited between " + "\'" + sd1
                    + "\'" + "and " + "\'" + sd2 + "\'";

            ResultSet rs = stmt.executeQuery(queryStmt);
            while (rs.next()) {
                String name = rs.getString("name");
                String id = rs.getString("andrewId");
                result.add(name);
                result.add(id);
            }
            System.out.println("query date success");

        } catch (Exception e) {
            System.out.print("MYSQL ERROR:" + e.getMessage());
        }
        return result;
    }

    private ArrayList<String> queryDB2(String d1, String d2) {
        ArrayList<String> result = new ArrayList<>();

        try {
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format1.parse(d1);
            Date date2 = format1.parse(d2);
            java.sql.Date sd1 = new java.sql.Date(date1.getTime());
            java.sql.Date sd2 = new java.sql.Date(date2.getTime());

            String queryStmt = "SELECT name, andrewId, count, gender FROM StudentInfo WHERE lastVisited between " + "\'" + sd1
                    + "\'" + " and " + "\'" + sd2 + "\'" + " order BY " + "gender";

            ResultSet rs = stmt.executeQuery(queryStmt);
            while (rs.next()) {
                String name = rs.getString("name");
                String id = rs.getString("andrewId");
                int count = rs.getInt("count");
                String c = ""+count;
                String gender = rs.getString("gender");
                result.add(name);
                result.add(id);
                result.add(c);
                result.add(gender);
            }
            System.out.println("query date success");

        } catch (Exception e) {
            System.out.print("MYSQL ERROR:" + e.getMessage());
        }
        return result;
    }

}
