<%-- 
    Document   : userHome
    Created on : Jan 18, 2019, 10:35:42 AM
    Author     : 756852
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports></ARIS3D:Imports>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ARIS3D - User Home</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="false" pageName="Dashboard"></ARIS3D:Header>
                <v-content>
                    <v-container>
                        <br>
                        <h1 class="display-2 font-weight-thin text-xs-center">Welcome back ${sessionScope.account.firstname}!</h1>
                        <br>
                        <v-layout row wrap>
                            <v-flex xs12 sm12 md6 lg6 xl6 class="pa-2">
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card min-height="300" elevation="10" class="flexcard">
                                    <v-toolbar dark color="#1B222B">
                                        <v-toolbar-text class="font-weight-light headline">Notifications</v-toolbar-text>
                                    </v-toolbar>
                                    <v-card-text class="grow">
                                        <p class="headline font-weight-thin">No information to display</p>
                                    </v-card-text>
                                    <v-card-actions fill-height>
                                        <v-btn flat outline color="pink">View All Notifications</v-btn>
                                    </v-card-actions>
                                </v-card>
                            </v-flex>
                            <v-flex xs12 sm12 md6 lg6 xl6 class="pa-2">
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card min-height="300" elevation="10" class="flexcard">
                                    <v-toolbar dark color="#1B222B">
                                        <v-toolbar-text class="font-weight-light headline">Previous Orders</v-toolbar-text>
                                    </v-toolbar>
                                    <v-card-text class="grow">
                                        <p class="headline font-weight-thin">No information to display</p>
                                    </v-card-text>
                                    <v-card-actions fill-height>
                                        <v-btn flat outline color="pink">Order History</v-btn>
                                    </v-card-actions>
                                </v-card>
                            </v-flex>
                            <v-flex xs12 sm12 md6 lg6 xl6 class="pa-2">
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card min-height="300" elevation="10">
                                    <v-toolbar dark color="#1B222B">
                                        <v-toolbar-text class="font-weight-light headline">Pending Orders</v-toolbar-text>
                                    </v-toolbar>
                                    <v-card-text>
                                        <p class="headline font-weight-light">No information to display</p>
                                    </v-card-text>
                                </v-card>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-content>
            </v-app>
        </div>
        <script src="res/js/vue.js" type="text/javascript"></script>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
    </body>
</html>
