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
        <title>ARIS3D Printing Service - Login</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <v-card color="#8B2635" height="5px"></v-card>
                <ARIS3D:Header isAdmin="false" pageName="Login"></ARIS3D:Header>
                <v-content>
                    <v-container fluid>
                        <v-layout align-center justify-center>
                            <v-flex xs3 sm3 md2 lg1 xl1>
                                <a href="home"><v-img src="res/img/SAITARISLogo.png"></v-img></a>
                            </v-flex>
                        </v-layout>
                        <br>
                        <v-layout align-center justify-center>
                            <v-flex xs12 sm10 md6 lg4 xl3>
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card xs12 class="elevation-12">
                                    <v-toolbar dark color="#1B222B">
                                        <v-toolbar-title>ARIS3D Login</v-toolbar-title>
                                    </v-toolbar>
                                    <v-card-text>
                                        <v-form>
                                            <v-text-field label="Email" name="email" v-model="email" :rules="emailRules"></v-text-field>
                                            <v-text-field label="Password" name="password"></v-text-field>
                                            <div class="text-xs-center">
                                                <v-btn color="#8B2635" large dark>Login</v-btn><br>
                                                <span class="caption">Don't have an account? Sign up <a href="register">here</a></span>
                                            </div>
                                        </v-form>
                                    </v-card-text>
                                </v-card>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-content>
                <v-footer dark height="auto">
                    <v-layout color="#1B222B" justify-center row wrap>
                        <a href="home"><v-btn color="white" flat round>Home</v-btn></a>
                        <a href="login"><v-btn color="white" flat round>Login</v-btn></a>
                        <a href="register"><v-btn color="white" flat round>Register</v-btn></a>
                        <a href="contact"><v-btn color="white" flat round>Contact Us</v-btn></a>
                        <v-flex color="white" py-1 text-xs-center white--text xs12>
                            <strong>ARIS3D</strong>
                        </v-flex>
                    </v-layout>
                </v-footer>
            </v-app>
        </div>
        <script src="res/js/vue.js" type="text/javascript"></script>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
    </body>
</html>

