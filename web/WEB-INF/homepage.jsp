<%-- 
    Document   : homepage
    Created on : Jan 18, 2019, 10:34:55 AM
    Author     : 756852
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <ARIS3D:Imports/>
        <link href="https://fonts.googleapis.com/css?family=Fredoka+One|Orbitron" rel="stylesheet">
        <link href="res/css/homepage.css" rel="stylesheet" type="text/css"/>
        <title>3D Printing Intake System - Home</title>
    </head>
    <body>
        <header>
            <div class="bar"></div>
            <nav id="main-nav" class="navbar">
                <a class="navbar-brand" href="home"><h1>ARIS3D</h1></a>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <div class="accountDropdown">
                            <img id="accountImg" class="btn dropdown-toggle" data-toggle="accountDropdown" width="50px" height="auto" src="res/img/icon/account.png" alt="Account Menu"/>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="login">Login</a>
                                <a class="dropdown-item" href="register">Register</a>
                            </div>
                        </div>
                    </li>
                </ul>
            </nav>
        </header>
        <button class="btn btn-primary">YAH YEET</button>
    </body>
</html>
