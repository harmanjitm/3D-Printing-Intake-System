<%-- 
    Document   : verifyAccount
    Created on : 15-Mar-2019, 4:14:45 PM
    Author     : Harmanjit Mohaar (000758243)
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <ARIS3D:Header isAdmin="false" pageName="Verify Account"></ARIS3D:Header>
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
                                    <v-alert <c:if test='${successMessage != null}'>value="true"</c:if> type="success">
                                    ${successMessage}
                                </v-alert>
                                <v-alert <c:if test='${errorMessage != null}'>value="true"</c:if> type="error">
                                    ${errorMessage}
                                </v-alert>
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card xs12 class="elevation-12">
                                    <v-toolbar dark color="#1B222B">
                                        <v-toolbar-title>ARIS3D - Verify Account</v-toolbar-title>
                                    </v-toolbar>
                                    <v-card-text>
                                        <v-form id="verify" action="verify" method="post">
                                            <v-text-field prepend-icon="person" name="email" label="Email" <c:if test='${email != null}'>value="${email}"</c:if><c:if test='${email == null}'>v-model="verifyEmail"</c:if>></v-text-field>
                                            <v-text-field prepend-icon="lock" name="code" label="Verification Code"></v-text-field>
                                            <div class="text-xs-center">
                                                <v-btn type="submit" name="action" value="resend" color="#8B2635" large dark>Resend Code</v-btn>
                                                <v-btn type="submit" name="action" value="verify" color="#8B2635" large dark>Verify Account</v-btn><br>
                                                <span class="caption">Didn't receive a verification code? Click the button above to resend your code.</span>
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
