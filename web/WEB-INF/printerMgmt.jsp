<%-- 
    Document   : printerMgmt
    Created on : Jan 18, 2019, 10:37:00 AM
    Author     : 756852
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports/>
        <title>ARIS3D - Printer Management</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="true" pageName="Printer Management"></ARIS3D:Header>
                    <br><br><br>
                    <v-container grid-list-md text-xs-center>
                        <v-layout row wrap>
                            <v-flex v-for="i in 3" :key="`4${i}`" xs4>
                            <v-card class="elevation-3">
                                <v-img src="res/img/UM3X_Full_2048x.jpg"
                                    aspect-ratio="1.5"
                                  ></v-img>
                                <v-card-title primary-title><h3 class="headline mb-0">Printer Name</h3></v-card-title>
                                <v-card-text class="px-0">Info about printer</v-card-text>
                                <div>
                                    <v-spacer></v-spacer><v-btn color="#8B2635">Edit</v-btn>
                                </div>
                                
                            </v-card>
                    </v-layout>
                </v-container>
            </v-app>
        </div>

        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
        <script src="res/js/vue.js" type="text/javascript"></script>
        <script>
//            new Vue({
//                el: '#app'
//            })
        </script>

    </body>
</html>
