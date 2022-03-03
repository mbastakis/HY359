package servlets;

import com.google.gson.Gson;
import database.tables.EditBloodTestTable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BloodTest;

public class userTests extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String amka = request.getParameter("amka");

        try {
            ArrayList<BloodTest> bloodtests = (new EditBloodTestTable()).databaseToBloodTests(amka);
            Gson gson = new Gson();
            response.setStatus(200);
            String tests_json = gson.toJson(bloodtests);
            response.getWriter().write(gson.toJson(bloodtests));

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(userTests.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(400);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
