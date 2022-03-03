package servlets;

import com.google.gson.Gson;
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
import model.Randevouz;

public class getDocRandevouz extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String doctor_id = request.getParameter("doctor_id");
        try {
            ArrayList<Randevouz> randevouz = (new EditRandevouzTable()).databaseToRandevouzArraylist(Integer.parseInt(doctor_id));
            Gson gson = new Gson();
            response.setStatus(200);
            response.getWriter().write(gson.toJson(randevouz));

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(getDocRandevouz.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
