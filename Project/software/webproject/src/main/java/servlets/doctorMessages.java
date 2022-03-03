package servlets;

import database.tables.EditMessageTable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class doctorMessages extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user_id = request.getParameter("user_id");
        String doctor_id = request.getParameter("doctor_id");
        String message = request.getParameter("message");

        try {
            (new EditMessageTable()).addMessage(doctor_id, user_id, message, "doctor");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(userMessages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
