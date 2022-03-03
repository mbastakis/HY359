/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Tehmike
 */
public class ExecuteQueryObj {

    public ResultSet executeQuery(String query) {
        try {
            Connection connection = DB_Connection.getConnection();
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

}
