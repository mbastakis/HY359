<%-- 
    Document   : doctor
    Created on : Jan 8, 2022, 11:17:42 PM
    Author     : Tehmike
--%>

<%@page import="model.SimpleUser"%>
<%@page import="java.util.Set"%>
<%@page import="model.User"%>
<%@page import="database.tables.EditSimpleUserTable"%>
<%@page import="database.tables.EditRandevouzTable"%>
<%@page import="model.Randevouz"%>
<%@page import="java.util.ArrayList"%>
<%@page import="database.tables.EditDoctorTable"%>
<%@page import="model.Doctor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js" defer></script>
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <link rel="stylesheet" href="../css/basicStyle.css">
    <link rel="stylesheet" href="../css/doctorStyle.css">
    <script src="../javascript/doctor.js" defer></script>
    <title>Doctor Page</title>
</head>
<%
    String username = (String)session.getAttribute("username");
    String password = (String)session.getAttribute("password");
    Doctor doctor = (new EditDoctorTable()).databaseToDoctor(username, password);
    if(doctor == null) {
        response.sendRedirect("/Personalized_Health/logout");
        return;
    }
//    Make the randevouz List
    ArrayList<Randevouz> randevouz = (ArrayList<Randevouz>) (new EditRandevouzTable()).databaseToRandevouzArraylist(doctor.getDoctor_id());
    Set<SimpleUser> patients = (Set<SimpleUser>) (new EditRandevouzTable()).databaseToPatients(doctor.getDoctor_id());
    
%>

<body>
    <div id="pop-up" class="container glass"></div>
    <div class="container-group">
        <div id="information" class="container glass">
            <div id="container-title">Information</div>
            <%
                if(doctor == null) return;
               out.print("<div class='info-group'><div id='username' ><b>Username:</b> "+ doctor.getUsername() +"</div><button id='username' class='container-button' disabled><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='email'><b>Email:</b> "+ doctor.getEmail() +"</div><button id='email' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='password'><b>Password:</b> "+ doctor.getPassword() +"</div><button id='password' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='firstname'><b>Firstname:</b> "+ doctor.getFirstname() +"</div><button id='firstname' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='lastname'><b>Lastname:</b> "+ doctor.getLastname()  +"</div><button id='lastname' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='birthdate'><b>Birthdate:</b> "+ doctor.getBirthdate() +"</div><button id='birthdate' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='gender'><b>Gender:</b> "+ doctor.getGender() +"</div><button id='gender' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='amka'><b>AMKA:</b> "+ doctor.getAmka() +"</div><button id='amka' class='container-button' disabled><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='country'><b>Country:</b> "+ doctor.getCountry() +"</div><button id='country' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='city'><b>City:</b> "+ doctor.getCity() +"</div><button id='city' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='address'><b>Address:</b> "+ doctor.getAddress() +"</div><button id='address' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='lat'><b>Lat:</b> "+ doctor.getLat() +"</div><button id='lat' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='lon'><b>Lon:</b> "+ doctor.getLon() +"</div><button id='lon' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='telephone'><b>Telephone:</b> "+ doctor.getTelephone() +"</div><button id='telephone' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='height'><b>Height:</b> "+ doctor.getHeight() +"cm</div><button id='height' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='weight'><b>Weight:</b> "+ doctor.getWeight() +"kg</div><button id='weight' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='blooddonor'><b>BloodDonor:</b> "+ (doctor.getBlooddonor()==1 ? "Yes" : "No") +"</div><button id='blooddonor' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='bloodtype'><b>BloodType:</b> "+ doctor.getBloodtype() +"</div><button id='bloodtype' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='specialty'><b>Specialty:</b> "+ doctor.getSpecialty() +"</div><button id='specialty' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='doctor_info'><b>DoctorInfo:</b> "+ doctor.getDoctor_info() +"</div><button id='doctor_info' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='certified'><b>Certified:</b> "+ (doctor.getCertified()==1 ? "Yes" : "No") +"</div><button id='certified' class='container-button'><i class='fad fa-pencil'></i></button></div>");
           %>
        </div>
        <div id="randevouz" class="container glass">
            <form action="/Personalized_Health/logout"><button id="go-back" class="container-button"><i class="fas fa-sign-out"></i></button>
            </form>
            <button id="pdf-btn" class="container-button"><i class="far fa-file-pdf"></i></button>
            <div id="container-title">Randevouz</div>
            <%
                String error = (String)session.getAttribute("error");
                if(error  != null) {
                    session.removeAttribute("error");
                    out.print("<div id='error' style='color: red'>" + error + "</div>");
                }
            %>
            <table>
                <tr>
                    <th>User</th>
                    <th>Date & Time</th>
                    <th>Price</th>
                    <th>Doctor Info</th>
                    <th>User Info</th>
                    <th>Status</th>
                </tr>
                <%
                String user_rows = "";
                user_rows += "<tr>";
                user_rows += "<form method='post' id='add-randevouz-form' action='/Personalized_Health/doctor'>";
                user_rows += "<td>-</td>";
                user_rows += "<td class='input-box'><input  type='datetime-local' id='birthdaytime' name='birthdaytime' required></td>";
                user_rows += "<td class='input-box'><input id='price' name='price' type='number' min='10' max='80' required/></td>";
                user_rows += "<td class='input-box'><textarea id='doctor-info-area' name='doctor-info-area' rows='2' cols='20' required> </textarea></td>";
                user_rows += "<td>-</td>";
                user_rows += "<td>free</td>";
                user_rows += "<td><button type='submit' id='add-randevouz' class='container-button'><i class='fas fa-plus'></i></button></td>";
                user_rows += "<td> <input type='hidden' id='doctorId' name='doctorId' value='" + doctor.getDoctor_id() + "'></td>";
                user_rows += "</form>";
                user_rows += "</tr>";
                
                EditSimpleUserTable ut = new EditSimpleUserTable();
                if( randevouz == null ) return;
                for(int i = 0; i < randevouz.size(); ++i) {
                    Randevouz tmp = randevouz.get(i);
                    if(tmp.getStatus().contentEquals("cancelled") || tmp.getStatus().contentEquals("done")) continue;
                    user_rows += "<tr>";
                    user_rows += "<td>"+ (tmp.getUser_id() == 0 ? "-" : ut.databaseToSimpleUser(tmp.getUser_id()).getUsername()) + "</td>";
                    user_rows += "<td>"+ tmp.getDate_time()+"</td>";
                    user_rows += "<td>"+ tmp.getPrice() +"</td>";
                    user_rows += "<td>"+ tmp.getDoctor_info()+"</td>";
                    user_rows += "<td>"+ (tmp.getUser_info().contentEquals("null") ? "-" : tmp.getUser_info()) +"</td>";
                    user_rows += "<td>"+ tmp.getStatus() +"</td>";
                    user_rows += "<td><button id='" + tmp.getRandevouz_id()+ "' class='container-button doc-edit'><i class='fad fa-pencil'></i></button></td>";
                    user_rows += "</tr>";
                }
                out.print(user_rows);
            %>
            </table>
        </div>
        <div id="patients" class="container glass">
            <div id="container-title">Patients</div>
            <%
                user_rows = "<div class='card-grid'>";
                if( patients == null ) return;
                if(doctor == null) return;
                for(SimpleUser patient : patients ) {
                    user_rows += "<div class='container-card'>";
                    user_rows += "<button user_id='"+patient.getUser_id()+"' doctor_id='"+doctor.getDoctor_id()+"' class='container-button chat'><i class='fas fa-comment-dots'></i></button>";
                    user_rows += "<div class='card-top'></div>";
                    user_rows += "<div class='avatar-holder'><img src='/Personalized_Health/assets/"+patient.getGender()+".svg'></div>";
                    user_rows += "<div class='name'><text>"+patient.getFirstname() + " " + patient.getLastname() +"</text></div>";
                    user_rows += "<div class='button-card-group'>";
                    user_rows += "<button amka='"+patient.getAmka()+"' id='"+patient.getUser_id()+"' class='container-button show-tests' type='sumbit'>Show Tests</button>";
                    user_rows += "<button user_id='"+patient.getUser_id()+"' doctor_id='"+doctor.getDoctor_id() +"' class='container-button add-treatment'>Add Treatment</button>";
                    user_rows += "</div>";
                    user_rows += "</div>";
                }
                user_rows += "</div>";
                out.print(user_rows);
            %>
            
        </div>
    
            <div class='container glass'>
                <div id='container-title'>Emergency Blood Call</div>
                <label for="blood-type">
                    <form method="POST" action='/Personalized_Health/notifyDonors?'>
                        <label><b>Blood Type <span>*</span></b></label>
                        <select id="blood-type" name="blood-type">
                          <option value="A-">A-</option>
                          <option value="A+">A+</option>
                          <option value="B-">B-</option>
                          <option value="B+">B+</option>
                          <option value="AB-">AB-</option>
                          <option value="AB+">AB+</option>
                          <option value="0-">0-</option>
                          <option value="0+">0+</option>
                          <option value="Unknown">Unknown</option>
                        </select>
                        <button class='container-button' style='width: 80px' type='submit'>Submit</button>
                    </form>
            </div>
            
            </div>

</body>

</html>