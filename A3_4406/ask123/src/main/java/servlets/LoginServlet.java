/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditSimpleUserTable;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mainClasses.SimpleUser;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

//        Create User
        try {
            SimpleUser simpleUser = (new EditSimpleUserTable()).databaseToSimpleUser(username, password);
            if (simpleUser == null) {
                response.setStatus(403); // Incorrect credentials
            } else {
                // Create session attribute
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("password", password);
                // Send json response
                String json = (new EditSimpleUserTable()).simpleUserToJSON(simpleUser);
                response.setStatus(201); // Created
                response.getWriter().write(json);
                response.getWriter().flush();
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
