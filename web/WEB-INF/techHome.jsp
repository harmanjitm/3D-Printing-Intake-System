<%-- 
    Document   : techHome
    Created on : Jan 18, 2019, 10:36:32 AM
    Author     : 756852
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports></ARIS3D:Imports>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ARIS3D - Dashboard</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="true" pageName="Dashboard"></ARIS3D:Header>
                <v-content>
                    <v-container>
                        
                    </v-container>
                </v-content>
            </v-app>
        </div>
        <script src="res/js/vue.js" type="text/javascript"></script>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
    </body>
</html>
