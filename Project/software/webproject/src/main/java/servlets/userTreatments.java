package servlets;

import com.google.gson.Gson;
import database.tables.EditBloodTestTable;
import database.tables.EditSimpleUserTable;
import database.tables.EditTreatmentTable;
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
import model.SimpleUser;
import model.Treatment;

public class userTreatments extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user_id = request.getParameter("user_id");

        try {
            ArrayList<Treatment> treatments = (new EditTreatmentTable()).databaseToTreatments(Integer.parseInt(user_id));
            Gson gson = new Gson();
            response.setStatus(200);
            String tests_json = gson.toJson(treatments);
            response.getWriter().write(gson.toJson(treatments));

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(userTreatments.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user_id = request.getParameter("user_id");
        String doctor_id = request.getParameter("doctor_id");
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        String text = request.getParameter("text_treatment");

        try {
            SimpleUser su = (new EditSimpleUserTable()).databaseToSimpleUser(Integer.parseInt(user_id));
            ArrayList<BloodTest> list = (new EditBloodTestTable()).databaseToBloodTests(su.getAmka());
            if (list.size() == 0) {
                return;
            }
            BloodTest latestTest = list.get(list.size() - 1);
            Treatment treatment = new Treatment();
            treatment.setDoctor_id(Integer.parseInt(doctor_id));
            treatment.setUser_id(Integer.parseInt(user_id));
            treatment.setStart_date(start_date);
            treatment.setEnd_date(end_date);
            treatment.setTreatment_text(text);
            treatment.setBloodtest_id(latestTest.getBloodtest_id());
            (new EditTreatmentTable()).addNewTreatment(treatment);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(userTreatments.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
