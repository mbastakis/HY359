<%@page import="database.tables.EditRandevouzTable"%>
<%@page import="model.Randevouz"%>
<%@page import="model.Doctor"%>
<%@page import="database.tables.EditDoctorTable"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="database.tables.EditTreatmentTable"%>
<%@page import="model.Treatment"%>
<%@page import="database.tables.EditBloodTestTable"%>
<%@page import="model.BloodTest"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.SimpleUser"%>
<%@page import="database.tables.EditSimpleUserTable"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js" defer></script>
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
        <link rel="stylesheet" href="../css/basicStyle.css">
        <link rel="stylesheet" href="../css/user.css">
        <script src="../javascript/user.js" defer></script>
        <title>User Page</title>
    </head>
    
    <%
    String username = (String)session.getAttribute("username");
    String password = (String)session.getAttribute("password");
    SimpleUser user = (new EditSimpleUserTable()).databaseToSimpleUser(username, password);
    if(user == null) {
        response.sendRedirect("/Personalized_Health/logout");
        return;
    }
    ArrayList<BloodTest> bloodtests = (new EditBloodTestTable()).databaseToBloodTests(user.getAmka());
    System.err.print(bloodtests.toString());
    ArrayList<Treatment> treatments = (new EditTreatmentTable()).databaseToTreatments(user.getUser_id());
    ArrayList<Doctor> doctors = (new EditDoctorTable()).databaseToCertifiedDoctors();
    ArrayList<Randevouz> randevouz = (new EditRandevouzTable()).databaseToUserRandevouz(user.getUser_id());
    ArrayList<Doctor> doctorsMetWithUser = (new EditDoctorTable()).databaseToDoctorsMetWithUser(String.valueOf(user.getUser_id()));
    
%>
    
    <body>
        <div id="pop-up" class="container glass "></div>
        <div class="window glass">
            <form action="/Personalized_Health/logout"><button id="go-back" class="container-button "><i class="fas fa-sign-out"></i></button>
            </form>
                                    <%
                            if(user == null) return;
                            if(user.getBlooddonor() == 2) {
                                out.print("<div><div style='color: red'>There is an urgent need for your bloodtype! Please donate blood as soon as possible.</div></div>");
                                (new EditSimpleUserTable()).updateSimpleUser(String.valueOf(user.getUser_id()), "blooddonor", "1");
                            }
                        %>
            <div id="information">
            <div id="container-title">Information</div>
        <%
                if(user == null) return;
               out.print("<div class='info-group'><div id='username' ><b>Username:</b> "+ user.getUsername() +"</div><button id='username' class='container-button' disabled><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='email'><b>Email:</b> "+ user.getEmail() +"</div><button id='email' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='password'><b>Password:</b> "+ user.getPassword() +"</div><button id='password' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='firstname'><b>Firstname:</b> "+ user.getFirstname() +"</div><button id='firstname' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='lastname'><b>Lastname:</b> "+ user.getLastname()  +"</div><button id='lastname' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='birthdate'><b>Birthdate:</b> "+ user.getBirthdate() +"</div><button id='birthdate' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='gender'><b>Gender:</b> "+ user.getGender() +"</div><button id='gender' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='amka'><b>AMKA:</b> "+ user.getAmka() +"</div><button id='amka' class='container-button' disabled><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='country'><b>Country:</b> "+ user.getCountry() +"</div><button id='country' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='city'><b>City:</b> "+ user.getCity() +"</div><button id='city' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='address'><b>Address:</b> "+ user.getAddress() +"</div><button id='address' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='lat'><b>Lat:</b> "+ user.getLat() +"</div><button id='lat' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='lon'><b>Lon:</b> "+ user.getLon() +"</div><button id='lon' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='telephone'><b>Telephone:</b> "+ user.getTelephone() +"</div><button id='telephone' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='height'><b>Height:</b> "+ user.getHeight() +"cm</div><button id='height' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='weight'><b>Weight:</b> "+ user.getWeight() +"kg</div><button id='weight' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='blooddonor'><b>BloodDonor:</b> "+ (user.getBlooddonor()==1 ? "Yes" : "No") +"</div><button id='blooddonor' class='container-button'><i class='fad fa-pencil'></i></button></div>");
               out.print("<div class='info-group'><div id='bloodtype'><b>BloodType:</b> "+ user.getBloodtype() +"</div><br><button id='bloodtype' class='container-button'><i class='fad fa-pencil'></i></button></div>");               
           %>
           </div>
           <div id="bmi-calculator">
                <div id="container-title">BMI Calculator</div>
                <div style="display: flex"><button id="bmi" class="container-button bigger-btn">Get BMI</button>
                <button id="ideal-weight" class="container-button bigger-btn" >Get Ideal Weight</button></div>
                <div id="results-bmi" style="display: flex; justify-content: center; color: red"></div>
           </div>
           <div id="insert-bloodtest">
                <div id="container-title">Insert BloodTest</div>
            <form id="add-bloodtest" name="post-form" method="POST" action="/Personalized_Health/userBloodtest">
                <label for='amka1'>AMKA:</label>
                <input id='amka1' type='text' name='amka' required><br>
                <label for='date'>Date:</label>
                <input id='date' type="text" name='test_date' required><br>
                <label for='medical-center'>Medical Center:</label>
                <input id='medical-center' type="text" name='medical_center' required><br>

                <label for='blood-sugar'>Blood Sugar:</label>
                <input id='blood-sugar' type="text" name='blood_sugar'><br>
                

                <label for='cholesterol'>Cholesterol:</label>
                <input id='cholesterol' type="text" name='cholesterol'><br>
               

                <label for='iron'>Iron:</label>
                <input id='iron' type="text" name='iron'><br>
                

                <label for='vitamin-d3'>Vitamin d3:</label>
                <input id='vitamin-d3' type="text" name='vitamin_d3'><br>
                

                <label for='vitamin-b12'>Vitamin b12:</label>
                <input id='vitamin-b12' type="text" name='vitamin_b12'><br>
                

                <input type='submit' class="container-button" style="width: 130px" value="Add Blood Test">
            </form>
           </div>
           <div id="print-bloodtests">
                <div id="container-title">BloodTests</div>
                <table align='center'>
                <tr>
                    <th>Test Date</th>
                    <th>Medical Center</th>
                    <th>Blood Sugar</th>
                    <th>Blood Sugar Level</th>
                    <th>Cholesterol</th>
                    <th>Cholesterol Level</th>
                    <th>Iron</th>
                    <th>Iron Level</th>
                    <th>Vitamin D3</th>
                    <th>Vitamin D3 Level</th>
                    <th>Vitamin B12</th>
                    <th>Vitamin B12 Level</th>
                </tr>
                <%
                    if(bloodtests == null) return;
                    String user_rows = "";               
                for(int i = 0; i < bloodtests.size(); ++i) {
                    BloodTest tmp = bloodtests.get(i);
                    user_rows += "<tr>";
                    user_rows += "<td>"+ tmp.getTest_date()+"</td>";
                    user_rows += "<td>"+ tmp.getMedical_center() +"</td>";
                    user_rows += "<td>"+ tmp.getBlood_sugar()+"</td>";
                    user_rows += "<td>"+ tmp.getBlood_sugar_level()+"</td>";
                    user_rows += "<td>"+ tmp.getCholesterol()+"</td>";
                    user_rows += "<td>"+ tmp.getCholesterol_level()+"</td>";
                    user_rows += "<td>"+ tmp.getIron() +"</td>";
                    user_rows += "<td>"+ tmp.getIron_level()+"</td>";
                    user_rows += "<td>"+ tmp.getVitamin_d3() +"</td>";
                    user_rows += "<td>"+ tmp.getVitamin_d3_level() +"</td>";
                    user_rows += "<td>"+ tmp.getVitamin_b12() +"</td>";
                    user_rows += "<td>"+ tmp.getVitamin_b12_level() +"</td>";
                    user_rows += "</tr>";
                }
                out.print(user_rows);
                %>
                </table>
                <br>
                <div id="container-title">BloodTest Comparison</div>
                <table align='center'>
                <tr>
                    <th>Test Date</th>
                    <th>Blood Sugar</th>
                    <th>Diff</th>
                    <th>Cholesterol</th>
                    <th>Diff</th>
                    <th>Iron</th>
                    <th>Diff</th>
                    <th>Vitamin D3</th>
                    <th>Diff</th>
                    <th>Vitamin B12</th>
                    <th>Diff</th>

                </tr>
                <%
                    if(bloodtests == null) return;
                    user_rows = "";
                for(int i = 0; i < bloodtests.size(); ++i) {
                    BloodTest tmp = bloodtests.get(i);
                    BloodTest prev = null;
                    Double diff = 0.0;
                    if(i != 0 ) {
                        prev = bloodtests.get(i - 1);
                    } 
                    
                    user_rows += "<tr>";
                    user_rows += "<td>"+ tmp.getTest_date()+"</td>";
                    user_rows += "<td>"+ tmp.getBlood_sugar()+"</td>";
                    if(i != 0) diff = tmp.getBlood_sugar() - prev.getBlood_sugar();
                    user_rows += "<td>" + (diff > 0 ? "+" : "") + ( i == 0 ? 0 : tmp.getBlood_sugar() - prev.getBlood_sugar() ) +"</td>";
                    user_rows += "<td>"+ tmp.getCholesterol()+"</td>";
                    user_rows += "<td>"+ (diff > 0 ? "+" : "") +( i == 0 ? 0 : tmp.getCholesterol() - prev.getCholesterol() ) +"</td>";
                    user_rows += "<td>"+ tmp.getIron() +"</td>";
                    user_rows += "<td>"+ (diff > 0 ? "+" : "") +( i == 0 ? 0 : tmp.getIron() - prev.getIron()) +"</td>";
                    user_rows += "<td>"+ tmp.getVitamin_d3() +"</td>";
                    user_rows += "<td>"+ (diff > 0 ? "+" : "") +( i == 0 ? 0 : tmp.getVitamin_d3() - prev.getVitamin_d3() ) +"</td>";
                    user_rows += "<td>"+ tmp.getVitamin_b12() +"</td>";
                    user_rows += "<td>"+ (diff > 0 ? "+" : "") +( i == 0 ? 0 : tmp.getVitamin_b12() - prev.getVitamin_b12() ) +"</td>";
                    user_rows += "</tr>";
                }
                out.print(user_rows);
                %>  
                </table>
        </div>
                <br>
        <div id='visualization'>
            <div id='container-title'>Test Visualization and Treatments</div>
            <%
                if(user == null) return;
                user_rows = "<button class='container-button show-tests bigger-btn' amka='" + user.getAmka() + "' user_id ='" + user.getUser_id() + "'>Visualize</button>";
                out.print(user_rows);
            %>
            
                    
        </div>
            <br>
        <div id='Treatments'>
            <div id='container-title'>Running Treatments</div>
            <table align='center'>
                <tr>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Information</th>
                </tr>
            <% 
                if(treatments == null) return;
                Date currentDate = new Date();
                
                user_rows = "";
                for (Treatment treatment : treatments) {
                    
                    Date start_date = new SimpleDateFormat("yyyy-MM-dd").parse(treatment.getStart_date());
                    Date end_date = new SimpleDateFormat("yyyy-MM-dd").parse(treatment.getEnd_date());
                    if(!currentDate.after(start_date) || !currentDate.before(end_date)) continue;
                    
                    user_rows += "<tr>";
                    user_rows += "<td>" + treatment.getStart_date() + "</td>";
                    user_rows += "<td>" + treatment.getEnd_date() + "</td>";
                    user_rows += "<td>" + treatment.getTreatment_text() + "</td>";
                    user_rows += "</tr>";
                }
                out.print(user_rows);
            %>
            </table>
        </div>
            <br>
        <div id="find-doctors">
            <div id='container-title'>Available Doctors</div>
            <%
                out.print("<input id='lat-lon' lat='" + user.getLat() + "' lon='" + user.getLon() + "'  hidden>");
            %>
            <div>
            <button class='container-button bigger-btn' onclick='printDocsSortedBy("distance")'>Sort by duration</button>
            <button class='container-button bigger-btn' onclick='printDocsSortedBy("duration")'>Sort by distance</button>
            <button class='container-button bigger-btn' onclick='printDocsSortedBy("price")'>Sort by Price</button>
            </div>
            <div id="doctor-list">
                <table border="1" cellpadding="3" id="printTable">
                    <tbody id="doctors-table">
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>      
                            <th>Email</th>
                            <th>Birthdate</th>
                            <th>City</th>
                            <th>Address</th>
                            <th>Telephone</th>
                            <th>Specialty</th>
                            <th>Doctor Info</th>
                            <th>Distance</th>
                            <th>Drive Time</th>
                            <th>Price</th>
                        </tr>
                        
                    </tbody>
                </table>
            </div>
        </div>
            <br>
        <div id='book-appointment'>
            <div id='container-title'>Book Appointment</div>
            <%
                if(doctors == null) return;
                user_rows = "";
                user_rows += "<select id='doctor-selection' user_id='" + user.getUser_id() + "'>";
                for (Doctor doctor : doctors) {
                    user_rows += "<option value='" + doctor.getDoctor_id() + "'>" + doctor.getFirstname() + " " + doctor.getLastname() + "</option>";
                }
                user_rows += "</select><button id='see-apointments' class='container-button bigger-btn' style='width: 120px; margin-left: 15px;'>Available Apointments</button>";
                out.print(user_rows);
                
            %>
            <div id='doc-appointments'>
                <table border="1" cellpadding="3" id="printTable">
                    <tbody id="doc-appointments-table">
                        <tr>
                            <th>Date and Time</th>
                            <th>Price</th>      
                            <th>Doctor Note</th>
                            <th></th>
                        </tr>
                        
                    </tbody>
                </table>
            </div>

        </div>
            <br>
        <div id='appointments'>
            <div id='container-title'>Appointments</div>
   
            <table border="1" cellpadding="3" id="printTable">
                    <tbody id="doc-appointments-table">
                        <tr>
                            <th>Date and Time</th>
                            <th>Price</th>      
                            <th>Doctor Note</th>
                            <th></th>
                        </tr>
                        <%
                            if( randevouz == null)  return;
                            user_rows = "";
                            for(Randevouz randevou : randevouz) {
                                user_rows += "<tr>";
                                user_rows += "<td>" + randevou.getDate_time() + "</td>";
                                user_rows += "<td>" + randevou.getPrice()+ "</td>";
                                user_rows += "<td>" + randevou.getDoctor_info()+ "</td>";
                                user_rows += "<td><button id='" + randevou.getRandevouz_id() + "' class='cancel-appointment container-button bigger-btn'>Cancel</button></td>";
                                user_rows += "</tr>";
                            }
                            out.print(user_rows);
                        %>
                        
                    </tbody>
                </table>


        </div>
        <div id='appointments'>
            <div id='container-title'>Chat with Doctors</div>
   
            <table border="1" cellpadding="3" id="printTable">
                    <tbody id="doc-appointments-table">
                        <tr>
                            <th>Firstname</th>
                            <th>Lastname</th>      
                            <th>Doctor Notes</th>   
                            <th>Email</th>
                            <th></th>
                        </tr>
                        <%
                            if( doctorsMetWithUser == null)  return;
                            user_rows = "";
                            for(Doctor doc : doctorsMetWithUser) {
                                user_rows += "<tr>";
                                user_rows += "<td>" + doc.getFirstname() + "</td>";
                                user_rows += "<td>" + doc.getLastname()+ "</td>";
                                user_rows += "<td>" + doc.getDoctor_info()+ "</td>";
                                user_rows += "<td>" + doc.getEmail()+ "</td>";
                                user_rows += "<td><button user_id = '" + user.getUser_id() + "' doctor_id='" + doc.getDoctor_id()+ "' class='container-button bigger-btn chat'>Chat</button></td>";
                                user_rows += "</tr>";
                            }
                            out.print(user_rows);
                        %>
                        
                    </tbody>
                </table>


        </div>          
                        <div style='margin-top: 10px'>Enter a food to get it's nutritional value: </div>
                        <input id='food' type="text" placeholder="Eg: banana"><button id='getCommonDisease' class="container-button" style="width: 70px">Submit</button>
                        <div id='nutrition-api-response'></div>
        

             <footer>
                <a href="https://www.vrisko.gr/efimeries-farmakeion/irakleio" target="_blank">
                <img src="https://www.vrisko.gr/graphlink/Pharmacies/image/160x60_Banner_n/?Region=irakleio&SmallRegion=true&NativeRegion=%ce%97%cf%81%ce%ac%ce%ba%ce%bb%ce%b5%ce%b9%ce%bf&" /></a>
                
                <a href="https://www.vrisko.gr/efimeries-nosokomeion?SelectedCity=hrakleio" target="_blank">
                    <img src="https://www.vrisko.gr/graphlink/Hospitals/image/160x60_Banner_n/?Prefecture=hrakleio&SmallPrefecture=true&NativePrefecture=%ce%97%ce%a1%ce%91%ce%9a%ce%9b%ce%95%ce%99%ce%9f&" /></a>
                <a href="https://covid19.gov.gr" target="_blank">
                    <img src="https://www.gov.gr/gov_gr-thumb-1200.png"  /></a>
            </footer>
        </div>
        
           
    </body>
</html>
