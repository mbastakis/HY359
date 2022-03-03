package servlets;

import com.google.gson.Gson;
import database.tables.EditMessageTable;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Message;

public class userMessages extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user_id = request.getParameter("user_id");
        String doctor_id = request.getParameter("doctor_id");
        String message = request.getParameter("message");
        log(message);

        try {
            (new EditMessageTable()).addMessage(doctor_id, user_id, message, "user");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(userMessages.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user_id = request.getParameter("user_id");
        String doctor_id = request.getParameter("doctor_id");

        try {
            ArrayList<Message> messages = (new EditMessageTable()).databaseToMessages(user_id, doctor_id);
            Gson gson = new Gson();
            response.setStatus(200);
            response.getWriter().write(gson.toJson(messages));

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(userMessages.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(400);
        } catch (ParseException ex) {
            Logger.getLogger(userMessages.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
