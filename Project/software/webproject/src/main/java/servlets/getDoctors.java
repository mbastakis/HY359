package servlets;

import com.google.gson.Gson;
import database.tables.EditDoctorTable;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Doctor;

public class getDoctors extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ArrayList<Doctor> doctors = (new EditDoctorTable()).databaseToCertifiedDoctors();
            Gson gson = new Gson();
            response.setStatus(200);
            String tests_json = gson.toJson(doctors);
            response.getWriter().write(gson.toJson(doctors));

        } catch (Exception e) {
            System.err.println(e.toString());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
