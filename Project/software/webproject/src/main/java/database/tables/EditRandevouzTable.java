/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import model.Randevouz;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SimpleUser;

/**
 *
 * @author Mike
 */
public class EditRandevouzTable {

   
    public void addRandevouzFromJSON(String json) throws ClassNotFoundException{
         Randevouz r=jsonToRandevouz(json);
         createNewRandevouz(r);
    }

    public void addNewRandevouz(Randevouz randevou) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " randevouz (doctor_id,user_id,date_time,price,doctor_info,user_info,status)"
                    + " VALUES ("
                    + "'" + randevou.getDoctor_id() + "',"
                    + "'" + randevou.getUser_id() + "',"
                    + "'" + randevou.getDate_time() + "',"
                    + "'" + randevou.getPrice() + "',"
                    + "'" + randevou.getDoctor_info() + "',"
                    + "'" + randevou.getUser_info() + "',"
                    + "'" + randevou.getStatus() + "'"
                    + ")";
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
    
    
     public Randevouz databaseToRandevouz(int id) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE randevouz_id= '" + id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Randevouz bt = gson.fromJson(json, Randevouz.class);
            return bt;
        } catch (Exception e) {
            System.err.println("Exception in databaseToRandevouz where randevouz_id: " + id + "! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Randevouz> databaseToRandevouzArraylist(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> randevouz = new ArrayList<>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE doctor_id= '" + id + "' AND status='free'");
            while (rs.next()) {

                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz doc = gson.fromJson(json, Randevouz.class);
                randevouz.add(doc);
            }
            con.close();
            randevouz.sort(
                    new Comparator<Randevouz>() {
                        @Override
                        public int compare(Randevouz r1, Randevouz r2) {
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
            return randevouz;
        } catch (Exception e) {
            System.err.println("Exception in databaseToRandevouzArraylist doctor_id: " + id + "! ");
            System.err.println(e.getMessage());
        }
        con.close();
        return null;
    }

    public ArrayList<Randevouz> databaseToUserRandevouz(int user_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> randevouz = new ArrayList<>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE user_id= '" + user_id + "' AND status='selected'");
            while (rs.next()) {

                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz doc = gson.fromJson(json, Randevouz.class);
                randevouz.add(doc);
            }
            con.close();
            randevouz.sort(
                    new Comparator<Randevouz>() {
                @Override
                public int compare(Randevouz r1, Randevouz r2) {
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
            return randevouz;
        } catch (Exception e) {
            System.err.println("Exception in databaseToRandevouzArraylist doctor_id: " + user_id + "! ");
            System.err.println(e.getMessage());
        }
        con.close();
        return null;
    }

    public Set<Integer> databaseToCompletedRandevouz(String user_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> randevouz = new ArrayList<>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE user_id= '" + user_id + "' AND status='done'");
            while (rs.next()) {

                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz doc = gson.fromJson(json, Randevouz.class);
                randevouz.add(doc);
            }
            con.close();

            Set<Integer> finishedSet = new HashSet<>();
            boolean sameUserFlag = false;
            for (Randevouz randevou : randevouz) {
                for (Integer tmp : finishedSet) {
                    if (randevou.getDoctor_id() == tmp) {
                        sameUserFlag = true;
                        break;
                    }
                }
                if (sameUserFlag == true) {
                    sameUserFlag = false;
                    continue;
                }
                finishedSet.add(randevou.getDoctor_id());
            }

            return finishedSet;
        } catch (Exception e) {
            System.err.println("Exception in databaseToRandevouzArraylist doctor_id: " + user_id + "! ");
            System.err.println(e.getMessage());
        }
        con.close();
        return null;
    }

    public Set<SimpleUser> databaseToPatients(int doctor_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> randevouz = new ArrayList<>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE doctor_id=" + doctor_id + " AND status='DONE';");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz doc = gson.fromJson(json, Randevouz.class);
                randevouz.add(doc);
            }
            con.close();
            Set<SimpleUser> users = new HashSet<SimpleUser>();

            for (Randevouz randevou : randevouz) {
                if (randevou.getUser_id() == 0) {
                    continue;
                }
                users.add((new EditSimpleUserTable()).databaseToSimpleUser(randevou.getUser_id()));
            }

            Set<SimpleUser> finishedSet = new HashSet<SimpleUser>();
            boolean sameUserFlag = false;
            for (SimpleUser user : users) {
                for (SimpleUser tmp : finishedSet) {
                    if (user.getUsername().contentEquals(tmp.getUsername())) {
                        sameUserFlag = true;
                        break;
                    }
                }
                if (sameUserFlag == true) {
                    sameUserFlag = false;
                    continue;
                }
                finishedSet.add(user);
            }

            return finishedSet;

        } catch (Exception e) {
            System.err.println("Exception in databaseToPatients where doctor_id: " + doctor_id + "! ");
            System.err.println(e.getMessage());
        }
        con.close();
        return null;
    }
      
     public Randevouz jsonToRandevouz(String json) {
        Gson gson = new Gson();
        Randevouz r = gson.fromJson(json, Randevouz.class);
        return r;
    }
     
         
      public String randevouzToJSON(Randevouz r) {
        Gson gson = new Gson();

        String json = gson.toJson(r, Randevouz.class);
        return json;
    }


    public void updateRandevouz(int randevouzID, int userID, String info, String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET user_id='" + userID + "',status='" + status +"',user_info='" + info + "' WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }

    public void updateRandevouz(String randevouzID, String datetime, String price, String doctor_info, String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET date_time='" + datetime + "',price='" + price + "',doctor_info='" + doctor_info + "',status='" + status + "' WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }

    public void updateRandevouz(String randevouzID, String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET status='" + status + "' WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }

    public void deleteRandevouz(int randevouzID) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM randevouz WHERE randevouz_id='" + randevouzID + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }



    public void createRandevouzTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE randevouz "
                + "(randevouz_id INTEGER not NULL AUTO_INCREMENT, "
                + " doctor_id INTEGER not NULL, "
                + " user_id INTEGER not NULL, "
                + " date_time TIMESTAMP not NULL, "
                + " price INTEGER  not NULL, "
                + " doctor_info VARCHAR(500),"
                + " user_info VARCHAR(500),"
                + " status VARCHAR(15) not null,"
                + "FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id), "
                + " PRIMARY KEY ( randevouz_id  ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewRandevouz(Randevouz rand) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " randevouz (doctor_id,user_id,date_time,price,doctor_info,user_info,status)"
                    + " VALUES ("
                    + "'" + rand.getDoctor_id() + "',"
                    + "'" + rand.getUser_id() + "',"
                    + "'" + rand.getDate_time() + "',"
                    + "'" + rand.getPrice() + "',"
                    + "'" + rand.getDoctor_info() + "',"
                    + "'" + rand.getUser_info() + "',"
                    + "'" + rand.getStatus() + "'"
                    + ")";
            //stmt.execute(table);

            stmt.executeUpdate(insertQuery);
            System.out.println("# The randevouz was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditRandevouzTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
