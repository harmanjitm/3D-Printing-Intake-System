<%-- 
    Document   : login
    Created on : Jan 18, 2019, 10:16:53 AM
    Author     : 758243
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports/>
        <title>ARIS 3D Printing Service - Login</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="false" pageName="Login"></ARIS3D:Header>
                <v-content>
                    <v-container fluid fill-height>
                        <v-layout align-center justify-center>
                            <v-card class="elevation-12">
                                <v-toolbar dark color="#8B2635">
                                    
                                </v-toolbar>
                            </v-card>
                        </v-layout>
                    </v-container>
                </v-content>
            </v-app>
        </div>
        <script src="res/js/vue.js" type="text/javascript"></script>
    </body>
</html>
