package servlets;

import com.google.gson.Gson;
import database.tables.EditDoctorTable;
import database.tables.EditRandevouzTable;
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
import model.Randevouz;

public class randevouz extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Randevouz> doctors_randevouz = new ArrayList<>();
        try {
            ArrayList<Doctor> doctors = (new EditDoctorTable()).databaseToDoctors();
            for (Doctor doctor : doctors) {
                ArrayList<Randevouz> randevouz = (new EditRandevouzTable()).databaseToRandevouzArraylist(doctor.getDoctor_id());
                if (randevouz.isEmpty() || doctor.getCertified() == 0) {
                    continue;
                }
                doctors_randevouz.add(randevouz.get(randevouz.size() - 1));
            }
            Gson gson = new Gson();
            response.setStatus(200);
            response.getWriter().write(gson.toJson(doctors_randevouz));

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(randevouz.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String randevouzID = request.getParameter("randevouz_id");
        String user_id = request.getParameter("user_id");
        try {
            (new EditRandevouzTable()
).updateRandevouz(Integer.parseInt(randevouzID), Integer.parseInt(user_id), "", "selected");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(randevouz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String randevouz_id = request.getParameter("randevouz_id");
        try {
            (new EditRandevouzTable()).updateRandevouz(randevouz_id, "cancelled");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(randevouz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
