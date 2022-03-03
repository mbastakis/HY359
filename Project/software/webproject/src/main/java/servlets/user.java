package servlets;

import database.tables.EditSimpleUserTable;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class user extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String field = request.getParameter("field");
        String value = request.getParameter("value");
        String username = (String) request.getSession().getAttribute("username");

        try {
            (new EditSimpleUserTable()).updateSimpleUser(username, field, value, value);
            response.sendRedirect("view/user.jsp");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
