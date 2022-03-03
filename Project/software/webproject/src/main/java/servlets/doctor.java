package servlets;

import database.tables.EditDoctorTable;
import database.tables.EditRandevouzTable;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Randevouz;

public class doctor extends HttpServlet {

    protected Boolean checkDate(HttpServletRequest request, HttpServletResponse response, String dateTime, String doctorId) throws IOException {
        try {
            // Check if date is new
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
            Date currentDate = new Date();
            if (date.before(currentDate)) {
                request.getSession().setAttribute("error", "The date you entered is old.");
                response.sendRedirect("view/doctor.jsp");
                return false;
            }
            // Check if between 8:00 - 20:30
            Date min = (Date) date.clone();
            Date max = (Date) date.clone();
            min.setMinutes(0);
            min.setHours(8);
            max.setMinutes(30);
            max.setHours(20);

            if (date.before(min) || date.after(max)) {
                request.getSession().setAttribute("error", "Can only create randevouz around 08:00 and 20:30");
                response.sendRedirect("view/doctor.jsp");
                return false;
            }
            // Check if any other randevou is at that time.
            ArrayList<Randevouz> randevouz = (new EditRandevouzTable()).databaseToRandevouzArraylist(Integer.parseInt(doctorId));
            Boolean validDate = true;
            for (int i = 0; i < randevouz.size(); ++i) {
                Randevouz randevou = randevouz.get(i);
                Date randevouDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(randevou.getDate_time());
                Date randevouStartDate = new Date(randevouDate.getTime() - (30 * 60 * 1000));
                Date randevouEndDate = new Date(randevouDate.getTime() + (30 * 60 * 1000));

                if (date.after(randevouStartDate) && date.before(randevouEndDate)) {
                    request.getSession().setAttribute("error", "Another randevou is at that time.");
                    response.sendRedirect("view/doctor.jsp");
                    return false;
                }
            }
        } catch (ParseException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(doctor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dateTime = request.getParameter("birthdaytime");
        String price = request.getParameter("price");
        String doctorInfo = request.getParameter("doctor-info-area");
        String doctorId = request.getParameter("doctorId");
        dateTime = dateTime.replace("T", " ") + ":00";

        try {
            if (!checkDate(request, response, dateTime, doctorId)) {
                return;
            }
            // We create the randevou
            Randevouz newRandevou = new Randevouz();
            newRandevou.setDate_time(dateTime);
            newRandevou.setDoctor_id(Integer.parseInt(doctorId));
            newRandevou.setDoctor_info(doctorInfo);
            newRandevou.setPrice(Integer.parseInt(price));
            newRandevou.setStatus("free");
            (new EditRandevouzTable()).addNewRandevouz(newRandevou);

            //Reload the page to show the randevou
            response.sendRedirect("view/doctor.jsp");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(doctor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String randevou_id = request.getParameter("id");

        try {
            (new EditRandevouzTable()).deleteRandevouz(Integer.parseInt(randevou_id));
        } catch (SQLException ex) {
            Logger.getLogger(doctor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(doctor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String field = request.getParameter("field");
        String value = request.getParameter("value");
        String username = (String) request.getSession().getAttribute("username");

        try {
            (new EditDoctorTable()).updateDoctor(username, field, value);
            response.sendRedirect("view/doctor.jsp");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String price = request.getParameter("price");
        String datetime = request.getParameter("datetime");
        String doctorInfo = request.getParameter("doctorInfo");
        String state = request.getParameter("state");
        datetime = datetime.replace("T", " ") + ":00";


        try {
            if (datetime.contentEquals("null:00")) {
                (new EditRandevouzTable()).updateRandevouz(id, state);
                return;
            }

            int doctor_id = (new EditRandevouzTable()).databaseToRandevouz(Integer.parseInt(id)).getDoctor_id();

            if (!checkDate(request, response, datetime, String.valueOf(id))) {
                request.getSession().setAttribute("error", "Invalid date.");
                response.sendRedirect("view/doctor.jsp");
                return;
            }
            (new EditRandevouzTable()).updateRandevouz(id, datetime, price, doctorInfo, state);

        } catch (SQLException ex) {
            Logger.getLogger(doctor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(doctor.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
