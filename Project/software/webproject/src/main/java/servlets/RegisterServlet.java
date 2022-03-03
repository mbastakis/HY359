/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import model.Doctor;
import model.ExecuteQueryObj;
import model.SimpleUser;

/**
 *
 * @author mike
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String field = request.getParameter("field");
        String value = request.getParameter("value");

        log(field + " " + value);
        ResultSet rs1 = new ExecuteQueryObj().executeQuery("SELECT * from users WHERE " + field + "='" + value + "';");
        ResultSet rs2 = new ExecuteQueryObj().executeQuery("SELECT * from doctors WHERE " + field + "='" + value + "';");

        try {
            if (rs1.next() || rs2.next()) {
                response.setStatus(409);
            } else {
                response.setStatus(200);
            }
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user_type = request.getParameter("user-type");

        try {
            if (user_type.compareToIgnoreCase("user") == 0) {
                (new EditSimpleUserTable()).addNewSimpleUser(new SimpleUser(request));
            } else {
                (new EditDoctorTable()).addNewDoctor(new Doctor(request));
            }
            response.setStatus(201);
            response.sendRedirect("/Personalized_Health/view/signIn.jsp");

        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(400);
        }
    }

}
