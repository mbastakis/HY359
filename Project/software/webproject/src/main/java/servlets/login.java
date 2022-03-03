/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Doctor;
import model.SimpleUser;

public class login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            SimpleUser simpleUser = (new EditSimpleUserTable()).databaseToSimpleUser(username, password);
            Doctor doctor = (new EditDoctorTable()).databaseToDoctor(username, password);
            if (simpleUser == null && doctor == null) { // If credentials are incorrect.
                HttpSession session = request.getSession();
                session.setAttribute("error", "Wrong username or password.");
                response.sendRedirect("view/signIn.jsp");
            } else {
                // Create session attribute
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("password", password);
                // Redirect to personal page.
                if (simpleUser != null) { // If he is a user.
                    session.setAttribute("user", simpleUser);
                    if (simpleUser.getUser_id() == 2) { // If he is a simple admin.
                        response.sendRedirect("view/admin.jsp");
                    } else { // If he is an user.
                        response.sendRedirect("view/user.jsp");
                    }
                } else { // If he is a doctor.
                    if (doctor.getCertified() == 1) {
                        session.setAttribute("doctor", doctor);
                        response.sendRedirect("view/doctor.jsp");
                    } else {
                        session.setAttribute("error", "You must be certified from the admin in order to login.");
                        response.sendRedirect("view/signIn.jsp");
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
