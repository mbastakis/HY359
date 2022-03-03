package servlets;

import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class admin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String isCertified = request.getParameter("isCertified");
        Boolean bool = Boolean.valueOf(isCertified);

        try {
            (new EditDoctorTable()).updateDoctor(username, bool);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String isUser = request.getParameter("isUser");

        try {
            if (isUser.contentEquals("true")) {
                (new EditSimpleUserTable()).deleteSimpleUserFromDatabase(username);
            } else {
                (new EditDoctorTable()).deleteDoctorFromDatabase(username);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
