/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import model.Doctor;
import com.google.gson.Gson;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class EditDoctorTable {

    public void addDoctorFromJSON(String json) throws ClassNotFoundException {
        Doctor doc = jsonToDoctor(json);
        addNewDoctor(doc);
    }

    public Doctor jsonToDoctor(String json) {
        Gson gson = new Gson();

        Doctor doc = gson.fromJson(json, Doctor.class);
        return doc;
    }

    public String doctorToJSON(Doctor doc) {
        Gson gson = new Gson();

        String json = gson.toJson(doc, Doctor.class);
        return json;
    }

    public void updateDoctor(String username, int height) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE doctors SET height='" + height + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
        con.close();
    }

    public void updateDoctor(String username, Boolean certified) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String update = "UPDATE doctors SET certified='" + (certified ? "0" : "1") + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
        con.close();
    }

    public void updateDoctor(String username, String field, String value) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String update = "UPDATE doctors SET " + field + "='" + value + "' WHERE username='" + username + "'";
        stmt.executeUpdate(update);
        con.close();
    }

    public void printDoctorDetails(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "' AND password='" + password + "'");
            while (rs.next()) {
                System.out.println("===Result===");
                DB_Connection.printResults(rs);
            }

        } catch (Exception e) {
            System.err.println("Exception in printDoctorDetails where username: " + username + " and password: " + password + "! ");
            System.err.println(e.getMessage());
        }
        con.close();
    }

    public Doctor databaseToDoctor(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Doctor doc = gson.fromJson(json, Doctor.class);
            con.close();
            return doc;
        } catch (Exception e) {
            System.err.println("Exception in databoseToDoctor where username: " + username + " and password: " + password + "! ");
            System.err.println(e.getMessage());
        }
        con.close();
        return null;
    }

    public Doctor databaseToDoctor(int doctor_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE doctor_id = " + doctor_id);
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Doctor doc = gson.fromJson(json, Doctor.class);
            con.close();
            return doc;
        } catch (Exception e) {
            System.err.println("Exception in databoseToDoctor where doctor_id: " + doctor_id);
            System.err.println(e.getMessage());
        }
        con.close();
        return null;
    }
//TODO test this
    public ArrayList<Doctor> databaseToDoctorsMetWithUser(String user_id) throws SQLException, ClassNotFoundException {
        Set<Integer> doctorIDs = (new EditRandevouzTable()).databaseToCompletedRandevouz(user_id);
        ArrayList<Doctor> doctors = new ArrayList<>();
        for (Integer id : doctorIDs) {
            Doctor doc = databaseToDoctor(id);
            doctors.add(doc);
        }

        return doctors;
    }

    public ArrayList<Doctor> databaseToDoctors() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Doctor> doctors=new ArrayList<Doctor>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Doctor doc = gson.fromJson(json, Doctor.class);
                doctors.add(doc);
            }
            con.close();
            return doctors;

        } catch (Exception e) {
            System.err.println("Exception databaseToDoctors! ");
            System.err.println(e.getMessage());
        }
        con.close();
        return null;
    }

    public ArrayList<Doctor> databaseToCertifiedDoctors() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Doctor doc = gson.fromJson(json, Doctor.class);
                if (doc.getCertified() == 0) {
                    continue;
                }
                doctors.add(doc);
            }
            con.close();
            return doctors;

        } catch (Exception e) {
            System.err.println("Exception databaseToDoctors! ");
            System.err.println(e.getMessage());
        }
        con.close();
        return null;
    }

    public void deleteDoctorFromDatabase(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        int rs;
        try {
            rs = stmt.executeUpdate("DELETE FROM doctors WHERE username = '" + username + "'");
        } catch (Exception e) {
            System.err.println("Exception in deleteDoctorFromDatabase wher username: " + username + "! ");
            System.err.println(e.getMessage());
        }
        con.close();
    }
    
    public String databaseToJSONfromEmail(String email) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE email = '" + email + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            con.close();
            return json;
        } catch (Exception e) {
            System.err.println("Exception databaseToJsonFromEmail! ");
            System.err.println(e.getMessage());
        }
        con.close();
        return null;
    }

    public String databaseToJSON(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            con.close();
            return json;
        } catch (Exception e) {
            System.err.println("Exception in databaseToJson! ");
            System.err.println(e.getMessage());
        }
        con.close();
        return null;
    }

    public void createDoctorTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE doctors"
                + "(doctor_id INTEGER not NULL AUTO_INCREMENT, "
                + "    username VARCHAR(30) not null unique,"
                + "    email VARCHAR(40) not null unique,	"
                + "    password VARCHAR(32) not null,"
                + "    firstname VARCHAR(20) not null,"
                + "    lastname VARCHAR(30) not null,"
                + "    birthdate DATE not null,"
                + "    gender  VARCHAR (7) not null,"
                + "    amka VARCHAR (11) not null,"
                + "    country VARCHAR(30) not null,"
                + "    city VARCHAR(50) not null,"
                + "    address VARCHAR(50) not null,"
                + "    lat DOUBLE,"
                + "    lon DOUBLE,"
                + "    telephone VARCHAR(14) not null,"
                + "    height INTEGER,"
                + "    weight DOUBLE,"
                + "   blooddonor BOOLEAN,"
                + "   bloodtype VARCHAR(7) not null,"
                + "   specialty VARCHAR(30) not null,"
                + "   doctor_info VARCHAR(500) not null,"
                + "   certified BOOLEAN,"
                + " PRIMARY KEY ( doctor_id))";
        stmt.execute(query);
        stmt.close();
        con.close();
    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void addNewDoctor(Doctor doc) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " doctors (username,email,password,firstname,lastname,birthdate,gender,amka,country,city,address,"
                    + "lat,lon,telephone,height,weight,blooddonor,bloodtype,specialty,doctor_info,certified)"
                    + " VALUES ("
                    + "'" + doc.getUsername() + "',"
                    + "'" + doc.getEmail() + "',"
                    + "'" + doc.getPassword() + "',"
                    + "'" + doc.getFirstname() + "',"
                    + "'" + doc.getLastname() + "',"
                    + "'" + doc.getBirthdate() + "',"
                    + "'" + doc.getGender() + "',"
                    + "'" + doc.getAmka() + "',"
                    + "'" + doc.getCountry() + "',"
                    + "'" + doc.getCity() + "',"
                    + "'" + doc.getAddress() + "',"
                    + "'" + doc.getLat() + "',"
                    + "'" + doc.getLon() + "',"
                    + "'" + doc.getTelephone() + "',"
                    + "'" + doc.getHeight() + "',"
                    + "'" + doc.getWeight() + "',"
                    + "'" + doc.isBloodDonor() + "',"
                    + "'" + doc.getBloodtype() + "',"
                    + "'" + doc.getSpecialty() + "',"
                    + "'" + doc.getDoctor_info() + "',"
                    + "'" + doc.getCertified() + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The doctor was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditDoctorTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
