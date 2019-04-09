<%-- 
    Document   : 404Error
    Created on : Apr 9, 2019, 3:15:09 PM
    Author     : 672762
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <ARIS3D:Imports/>
	<title>404 - ARIS 3D</title>

	<!-- Google font -->
	<link href="https://fonts.googleapis.com/css?family=Montserrat:700,900" rel="stylesheet">

	<!-- Font Awesome Icon -->
	<link type="text/css" rel="stylesheet" href="res/css/font-awesome.min.css" />

	<!-- Custom stlylesheet -->
	<link type="text/css" rel="stylesheet" href="res/css/404style.css" />
        <link href="res/css/homepage.css" rel="stylesheet" type="text/css"/>

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->

</head>

<body>
    <div id="app">
        <v-app>
            <v-content>
                
            <v-card color="#8B2635" height="5px"></v-card>
                <c:if test="${account == null}">
                    <v-toolbar dark color="#1B222B">
                <v-toolbar-title>ARIS3D</v-toolbar-title>
                <v-spacer></v-spacer>
                    <a href="register">
                        <v-btn outline color="#8B2635">Register</v-btn>
                    </a>
                    <a href="login">
                        <v-btn color="#8B2635">Login</v-btn>
                    </a>
                </v-toolbar>
                </c:if>
                <c:if test="${account.accountType == 'admin'}">
                 <ARIS3D:Header isAdmin="true" pageName=""></ARIS3D:Header>
                    <!--<a href="dashboard">
                        <v-btn color="#8B2635">Dashboard</v-btn>
                    </a>-->
                </c:if>
                <c:if test="${account.accountType == 'user'}">
                 <ARIS3D:Header isAdmin="false" pageName=""></ARIS3D:Header>
                <!--    <a href="login?logout=true">
                        <v-btn color="#8B2635">Logout</v-btn>
                    </a>-->
                </c:if>
        
        
                        
	<div id="notfound">
		<div class="notfound-bg"></div>
		<div class="notfound">
			<div class="notfound-404">
				<h1>Oops</h1>
			</div>
			<h2>we are sorry, but the page you requested was not found</h2>
<!--			<a href="#" class="home-btn">Go Home</a>
			<a href="#" class="contact-btn">Contact us</a>-->
<!--			<div class="notfound-social">
				<a href="#"><i class="fa fa-facebook"></i></a>
				<a href="#"><i class="fa fa-twitter"></i></a>
				<a href="#"><i class="fa fa-pinterest"></i></a>
				<a href="#"><i class="fa fa-google-plus"></i></a>
			</div>-->
		</div>
	</div>
        <script src="res/js/vue.js" type="text/javascript"></script>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
                </v-content>
        </v-app>
    </div>
</body><!-- This templates was made by Colorlib (https://colorlib.com) -->

</html>
