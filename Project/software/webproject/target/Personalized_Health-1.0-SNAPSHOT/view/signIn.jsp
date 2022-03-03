<%-- 
    Document   : signIn
    Created on : Jan 8, 2022, 11:03:25 AM
    Author     : Tehmike
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <title>Login Form</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <link rel="stylesheet" type="text/css" href="../css/basicStyle.css">
    <link rel="stylesheet" href="../css/loginStyle.css">
</head>

<body>
    <div class="container glass user-details">
        <h2 id="container-title">Login Page</h2><br>
        <form id="credentials" action="/Personalized_Health/login">
            <div class="input-box">
                <label for="username">User Name</label>
                <input type="text" name="username" id="username" placeholder="Username">
            </div>
            <div class="input-box">
                <label for="password">Password</label>
                <input type="Password" name="password" id="password" placeholder="Password">
            </div>
            <div style="color: red; margin-bottom: 10px">
                <%
                if(session.getAttribute("error") != null) out.print(session.getAttribute("error"));
                session.removeAttribute("error");
                %>
            </div>
        </form>

        <div id="button-group">
            <div id="sign-in">
                <button form="credentials" class="container-button"><i class="fas fa-sign-in-alt"></i></button>
            </div>
            <form id="go-back" action="/Personalized_Health/index.html">
                <button class="container-button"><i class="fas fa-arrow-left"></i></button>
            </form>
        </div>

    </div>
</body>

</html>