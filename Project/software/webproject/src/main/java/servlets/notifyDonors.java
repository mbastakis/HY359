package servlets;

import database.tables.EditSimpleUserTable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.SimpleUser;

public class notifyDonors extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String blood_type = request.getParameter("blood-type");

        try {
            ArrayList<SimpleUser> donors = (new EditSimpleUserTable()).databaseToDonors(blood_type);
            if (donors == null) {
                response.sendRedirect("/Personalized_Health/view/doctor.jsp");
                return;
            }
            for (SimpleUser donor : donors) {
                (new EditSimpleUserTable()).updateSimpleUser(String.valueOf(donor.getUser_id()), "blooddonor", "2");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(notifyDonors.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("/Personalized_Health/view/doctor.jsp");
    }
}
