<%-- 
    Document   : admin
    Created on : Jan 8, 2022, 5:15:56 PM
    Author     : Tehmike
--%>

<%@page import="database.tables.EditDoctorTable"%>
<%@page import="database.tables.EditSimpleUserTable"%>
<%@page import="model.Doctor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.SimpleUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <link rel="stylesheet" href="../css/basicStyle.css">
    <link rel="stylesheet" href="../css/adminStyle.css">
    <script src="../javascript/admin.js" defer></script>
    <title>Admin Page</title>
</head>
<% 
    String username = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");
    if(username == null || 
       password == null || 
       !username.contentEquals("admin") ||
       !password.contentEquals("admin12*"))
        response.sendRedirect("/Personalized_Health/logout");
    ArrayList<SimpleUser> simpleUsers = (new EditSimpleUserTable()).databaseToSimpleUsers();
    ArrayList<Doctor> doctors = (new EditDoctorTable()).databaseToDoctors();
%>

<body>
    <div class="container glass">
        <form action="/Personalized_Health/logout">
            <button id="logout" class="container-button"><i class="fas fa-sign-out"></i></button>
        </form>
        <div id="container-title">Admin Panel</div>
        <h3>Simple Users:</h3>
        <table>
            <tr>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
            </tr>
            <%
                String user_rows = "";
                for(int i = 0; i < simpleUsers.size(); ++i) {
                    SimpleUser tmp = simpleUsers.get(i);
                    user_rows += "<tr>";
                    user_rows += "<td>"+ tmp.getUsername() +"</td>";
                    user_rows += "<td>"+ tmp.getFirstname() +"</td>";
                    user_rows += "<td>"+ tmp.getLastname() +"</td>";
                    user_rows += "<td><button id='" + tmp.getUsername() + "' class='container-button user del'><i class='fas fa-trash'></i></button></td>";
                    user_rows += "</tr>";
                }
                out.print(user_rows);
            %>
        </table>
        <h3>Doctors:</h3>
        <table>
            <tr>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
            </tr>
            <%
                String doctor_rows = "";
                for(int i = 0; i < doctors.size(); ++i) {
                    Doctor tmp = doctors.get(i);
                    doctor_rows += "<tr>";
                    doctor_rows += "<td>"+ tmp.getUsername() +"</td>";
                    doctor_rows += "<td>"+ tmp.getFirstname() +"</td>";
                    doctor_rows += "<td>"+ tmp.getLastname() +"</td>";
                    doctor_rows += "<td><button id='" + tmp.getUsername() + "' class='container-button doc del'><i class='fas fa-trash'></i></button></td>";
                    int isCertified = tmp.getCertified();
                    String certify = isCertified == 1 ? "Certified" : "Certify";
                    doctor_rows += "<td><button id='certify-" + tmp.getUsername() + "' class='container-button doc cer'>" + certify + "</button></td>";
                    doctor_rows += "</tr>";
                }
                out.print(doctor_rows);
            %>
        </table>
    </div>
</body>

</html>