package servlets;

import com.google.gson.Gson;
import database.tables.EditDoctorTable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Doctor;

public class getLocDoctors extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ArrayList<Doctor> doctors = (new EditDoctorTable()).databaseToCertifiedDoctors();
            double cords[][] = new double[doctors.size()][2];
            for (int i = 0; i < doctors.size(); ++i) {
                cords[i][0] = doctors.get(i).getLat();
                cords[i][1] = doctors.get(i).getLon();
            }
            Gson gson = new Gson();
            String json = gson.toJson(cords, double[][].class);
            response.getWriter().write(json);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(getLocDoctors.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
