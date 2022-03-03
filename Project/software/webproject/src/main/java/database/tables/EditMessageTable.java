/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import database.tables.EditBloodTestTable;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;

/**
 *
 * @author micha
 */
public class EditMessageTable {

    
     public void addMessageFromJSON(String json) throws ClassNotFoundException{
         Message msg=jsonToMessage(json);
         createNewMessage(msg);
    }

    public void addMessage(String doctor_id, String user_id, String message, String sender) throws ClassNotFoundException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Message msg = new Message();
        msg.setDate_time(dtf.format(now));
        msg.setDoctor_id(Integer.parseInt(doctor_id));
        msg.setUser_id(Integer.parseInt(user_id));
        msg.setMessage(message);
        msg.setSender(sender);

        createNewMessage(msg);

    }
    
      public Message jsonToMessage(String json) {
        Gson gson = new Gson();
        Message msg = gson.fromJson(json, Message.class);
        return msg;
    }
     
    public String messageToJSON(Message msg) {
        Gson gson = new Gson();

        String json = gson.toJson(msg, Message.class);
        return json;
    }


   
    
    public Message databaseToMessage(int id) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM message WHERE message_id= '" + id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Message bt = gson.fromJson(json, Message.class);
            return bt;
        } catch (Exception e) {
            System.err.println("Exception in databaseToMessage where message_id: " + id + "! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Message> databaseToMessages(String user_id, String doctor_id) throws SQLException, ClassNotFoundException, ParseException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        ArrayList<Message> messages = new ArrayList<>();
        try {
            rs = stmt.executeQuery("SELECT * FROM message WHERE user_id= '" + user_id + "' AND doctor_id= '" + doctor_id + "'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Message message = gson.fromJson(json, Message.class);
                messages.add(message);
            }

            messages.sort(
                    new Comparator<Message>() {
                @Override
                        public int compare(Message r1, Message r2) {
                    try {
                        Date firstdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(r1.getDate_time().replace("T", " ") + ":00");
                        Date seconddate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(r2.getDate_time().replace("T", " ") + ":00");

                        return firstdate.compareTo(seconddate);
                    } catch (ParseException ex) {
                        Logger.getLogger(EditRandevouzTable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return 0;
                }
            });

            con.close();
            return messages;
        } catch (JsonSyntaxException e) {
            System.err.println("Exception databaseToTreatments where user_id: " + user_id + "! ");
            System.err.println(e.getMessage());
            con.close();
        }
        return null;
    }

    public void createMessageTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE message "
                + "(message_id INTEGER not NULL AUTO_INCREMENT, "
                + "doctor_id INTEGER not null,"
                + "user_id INTEGER not null,"
                + "date_time TIMESTAMP not NULL, "
                + "message VARCHAR(1000) not NULL, "
                + "sender VARCHAR(15),"
                + "blood_donation BOOLEAN,"
                + "bloodtype VARCHAR(15),"
                + "FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id), "
                + "FOREIGN KEY (user_id) REFERENCES users(user_id), "
                + "PRIMARY KEY ( message_id ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewMessage(Message msg) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " message (doctor_id,user_id,date_time,message,sender,blood_donation,bloodtype) "
                    + " VALUES ("
                    + "'" + msg.getDoctor_id() + "',"
                    + "'" + msg.getUser_id() + "',"
                    + "'" + msg.getDate_time() + "',"
                    + "'" + msg.getMessage() + "',"
                    + "'" + msg.getSender() + "',"
                    + "'" + msg.getBlood_donation() + "',"
                    + "'" + msg.getBloodtype()+ "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The bloodtest was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditBloodTestTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
