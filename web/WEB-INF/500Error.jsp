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
        <title>ARIS3D - Page Not Found</title>

        <!-- Google font -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:700,900" rel="stylesheet">

        <!-- Font Awesome Icon -->
        <link type="text/css" rel="stylesheet" href="res/css/font-awesome.min.css" />

        <!-- Custom stlylesheet -->
        <link type="text/css" rel="stylesheet" href="res/css/404style.css" />
        <link href="res/css/homepage.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <div id="app">
            <v-app>
                <v-content>
                    <v-card color="#8B2635" height="5px"></v-card>
                    <ARIS3D:Header pageName="Page Not Found!" isAdmin=""></ARIS3D:Header>
                    <div id="notfound">
                        <div class="notfound-bg"></div>
                        <div class="notfound">
                            <div class="notfound-404">
                                <h1>Oops</h1>
                            </div>
                            <h2>we are sorry, but the page you requested was not found</h2>
                        </div>
                    </div>
                    <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
                    <script src="res/js/vue.js" type="text/javascript"></script>
                </v-content>
            </v-app>
        </div>
    </body>
</html>
