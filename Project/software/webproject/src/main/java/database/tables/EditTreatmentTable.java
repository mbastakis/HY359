/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Treatment;

/**
 *
 * @author mountant
 */
public class EditTreatmentTable {

    
    public void addTreatmentFromJSON(String json) throws ClassNotFoundException{
         Treatment msg=jsonToTreatment(json);
         createNewTreatment(msg);
    }

    public void addNewTreatment(Treatment treatment) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " treatment (`doctor_id`, `user_id`, `start_date`, `end_date`, `treatment_text`, `bloodtest_id`) VALUES "
                    + "('" + treatment.getDoctor_id() + "',"
                    + "'" + treatment.getUser_id() + "',"
                    + "'" + treatment.getStart_date() + "',"
                    + "'" + treatment.getEnd_date() + "',"
                    + "'" + treatment.getTreatment_text() + "',"
                    + "'" + treatment.getBloodtest_id() + "')";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);

            /* Get the member id from the database and set it to the member */
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditSimpleUserTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String treatmentToJSON(Treatment tr) {
        Gson gson = new Gson();

        String json = gson.toJson(tr, Treatment.class);
        return json;
    }

    public Treatment jsonToTreatment(String json) {
        Gson gson = new Gson();
        Treatment tr = gson.fromJson(json, Treatment.class);
        return tr;
    }
    
    public Treatment databaseToTreatment(int id) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM treatment WHERE treatment_id= '" + id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Treatment tr  = gson.fromJson(json, Treatment.class);
            return tr;
        } catch (Exception e) {
            System.err.println("Exception databaseToTreatment where treatment_id: " + id + "! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Treatment> databaseToTreatments(int user_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        ArrayList<Treatment> treatments = new ArrayList<>();
        try {
            rs = stmt.executeQuery("SELECT * FROM treatment WHERE user_id= '" + user_id + "'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Treatment treat = gson.fromJson(json, Treatment.class);
                treatments.add(treat);
            }
            con.close();
            return treatments;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Exception databaseToTreatments where user_id: " + user_id + "! ");
            System.err.println(e.getMessage());
            con.close();
        }
        return null;
    }

    public void createTreatmentTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE treatment "
                + "(treatment_id INTEGER not NULL AUTO_INCREMENT, "
                + "doctor_id INTEGER not null,"
                + "user_id INTEGER not null,"
                + "start_date  DATE not NULL, "
                + "end_date DATE not NULL, "
                + "treatment_text VARCHAR(1000),"
                + "bloodtest_id INTEGER not null,"
                + "FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id), "
                + "FOREIGN KEY (user_id) REFERENCES users(user_id), "
                + "FOREIGN KEY (bloodtest_id) REFERENCES bloodtest(bloodtest_id), "
                + "PRIMARY KEY ( treatment_id ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewTreatment(Treatment tr) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " treatment (doctor_id,user_id,start_date,end_date,treatment_text,bloodtest_id) "
                    + " VALUES ("
                    + "'" + tr.getDoctor_id() + "',"
                    + "'" + tr.getUser_id() + "',"
                    + "'" + tr.getStart_date() + "',"
                    + "'" + tr.getEnd_date()+ "',"
                    + "'" + tr.getTreatment_text() + "',"
                    + "'" + tr.getBloodtest_id()+ "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The bloodtest was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();
                 con.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditBloodTestTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}