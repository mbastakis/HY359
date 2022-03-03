package servlets;

import database.tables.EditBloodTestTable;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BloodTest;

public class userBloodtest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String amka = request.getParameter("amka");
        String td = request.getParameter("test_date");
        String mc = request.getParameter("medical_center");
        String bs = request.getParameter("blood_sugar");
        String ch = request.getParameter("cholesterol");
        String ir = request.getParameter("iron");
        String vd = request.getParameter("vitamin_d3");
        String vb = request.getParameter("vitamin_b12");

        BloodTest bt = new BloodTest();
        bt.setAmka(amka);
        bt.setTest_date(td);
        bt.setMedical_center(mc);
        bt.setBlood_sugar(Integer.parseInt(bs));
        bt.setCholesterol(Integer.parseInt(ch));
        bt.setIron(Integer.parseInt(ir));
        bt.setVitamin_d3(Double.parseDouble(vd));
        bt.setVitamin_b12(Double.parseDouble(vb));
        bt.setValues();

        if (bt.getAmka().equals("") || bt.getTest_date().equals("") || bt.getMedical_center().equals("")) {
            return;
        }
        if (bt.getVitamin_d3_level().equals("")
                && bt.getBlood_sugar_level().equals("")
                && bt.getVitamin_b12_level().equals("")
                && bt.getCholesterol_level().equals("")
                && bt.getIron_level().equals("")) {
            return;
        }

        if (bt.getVitamin_d3_level().equals("Invalid")
                || bt.getBlood_sugar_level().equals("Invalid")
                || bt.getVitamin_b12_level().equals("Invalid")
                || bt.getCholesterol_level().equals("Invalid")
                || bt.getIron_level().equals("Invalid")) {
            return;
        }
        try {
            (new EditBloodTestTable()).createNewBloodTest(bt);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        response.sendRedirect("/Personalized_Health/view/user.jsp");
    }
}
