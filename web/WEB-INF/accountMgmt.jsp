<%-- 
    Document   : accountMgmt
    Created on : Jan 18, 2019, 10:36:48 AM
    Author     : 756852
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports/>
    </head>
    <body>
        <ARIS3D:Header isAdmin="true" pageName="Account Management"></ARIS3D:Header>
        <div id="app">
            <v-app>
                <v-content>
                    <v-card style="height: 5px; background-color: #8B2635;" elevation-25></v-card>
                    <v-container>
                        
                    </v-container>
                </v-content>
            </v-app>
        </div>
        <script src="res/js/v-accountmanagement.js" type="text/javascript"></script>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
    </body>
</html>