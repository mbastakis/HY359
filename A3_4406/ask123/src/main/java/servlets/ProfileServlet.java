/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tehmike
 */
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        /// if session exists then load otherwise login
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(404);
            response.getWriter().write("no ses");
            response.getWriter().flush();
        } else {
            String login, password;
            login = (String) session.getAttribute("username");
            password = (String) session.getAttribute("password");
            response.setStatus(200);
            response.getWriter().write("username=" + login + "&password=" + password);
            response.getWriter().flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher("/login.html").forward(request, response);
    }

}
