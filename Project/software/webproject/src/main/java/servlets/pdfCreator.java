package servlets;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.tables.EditRandevouzTable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Randevouz;

public class pdfCreator extends HttpServlet {

    private void addTableHeader(PdfPTable table, Date date) {
        String[] headers = {"User", "Date & Time", "Price", "Doctor Info", "User Info", "Status"};
        PdfPCell content = new PdfPCell(new Phrase("Doctor's Randevouz for " + date.toString()));
        for (int i = 0; i < 6; ++i) {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(headers[i]));
            table.addCell(header);
        }
    }

    private void addRows(PdfPTable table, ArrayList<Randevouz> randevouzForPdf) {
        for (Randevouz randevou : randevouzForPdf) {
            table.addCell(String.valueOf(randevou.getUser_id()));
            table.addCell(randevou.getDate_time());
            table.addCell(String.valueOf(randevou.getPrice()));
            table.addCell(randevou.getDoctor_info());
            table.addCell(randevou.getUser_info());
            table.addCell(randevou.getStatus());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String doctor_id = request.getParameter("doctor_id");
        String dateStr = request.getParameter("date");
        dateStr = dateStr.replace("T", " ") + ":00";

        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr));
            // Check if date is new
            Date currentDate = new Date();
            if (date.before(currentDate)) {
                request.getSession().setAttribute("error", "The date you entered is old.");
                response.sendRedirect("view/doctor.jsp");
                return;
            }

            ArrayList<Randevouz> randevouzList = (new EditRandevouzTable()).databaseToRandevouzArraylist(Integer.parseInt(doctor_id));
            ArrayList<Randevouz> randevouzForPdf = new ArrayList<>();

            for (Randevouz randevou : randevouzList) {
                String tmpDate = randevou.getDate_time();
                tmpDate = tmpDate.replace("T", " ") + ":00";
                Date tmp = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tmpDate));
                if (date.getDay() == tmp.getDay() && date.getMonth() == tmp.getMonth() && date.getYear() == tmp.getYear()) {
                    randevouzForPdf.add(randevou);
                }
            }

            Document document = new Document();
            File pdf_file = new File("randevouz.pdf");
            if (!pdf_file.createNewFile()) {
                request.getSession().setAttribute("error", "Something went wrong with the pdf creation.");
                response.sendRedirect("view/doctor.jsp");
                return;
            }
            PdfWriter.getInstance(document, new FileOutputStream(pdf_file));

            document.open();
            // Write to pdf
            PdfPTable table = new PdfPTable(6);
            addTableHeader(table, date);
            addRows(table, randevouzForPdf);
            document.add(table);
            document.close();
            // End of pdf

            // Start process to download file.
            ServletContext context = getServletContext();
            String mime = context.getMimeType(pdf_file.getAbsolutePath());
            if (mime == null) {
                request.getSession().setAttribute("error", "Something went wrong with the pdf creation.");
                response.sendRedirect("view/doctor.jsp");
                return;
            }

            response.setContentType(mime);
            response.setContentLength((int) pdf_file.length());
            // force download
            response.setHeader("Content-Disposition", "attachment; filename=\"randevouz.pdf\"");

            // Get outputStream of the response.
            OutputStream outStream = response.getOutputStream();
            // Read the created file.
            FileInputStream inStream = new FileInputStream("randevouz.pdf");

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inStream.close();
            outStream.close();
            if (!pdf_file.delete()) {
                request.getSession().setAttribute("error", "Something went wrong with the pdf creation.");
            }
            
        } catch (ParseException | SQLException | ClassNotFoundException | DocumentException ex) {
            Logger.getLogger(pdfCreator.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("view/doctor.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
