<%-- 
    Document   : Header
    Created on : Jan 24, 2019, 12:06:46 PM
    Author     : 758243
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="The header." pageEncoding="UTF-8"%>
<%@attribute description="Specify if the user is a Tech or User. Will display specific page depening on which it is" required="true" name="isAdmin"%>
<%@attribute description="The page name to display on the header." name="pageName" required="true" %>
        <div id="header">
            <v-app>
                <v-navigation-drawer dark v-model="drawer" stateless clipped app fixed>
                    <v-list dense>
                        <v-list-tile @click="" v-for="item in items">
                            <v-list-tile-action>
                                <v-icon>{{ item.icon }}</v-icon>
                            </v-list-tile-action>
                            <v-list-tile-content>
                                <v-list-tile-title>{{ item.title }}</v-list-tile-title>
                            </v-list-tile-content>
                        </v-list-tile>
                    </v-list>
                </v-navigation-drawer>
                <v-toolbar header fixed clipped-left color="#1B222B">
                    <v-toolbar-side-icon @click.stop="drawer = !drawer" class="white--text"></v-toolbar-side-icon>
                    <v-toolbar-title class="white--text">${pageName}</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-toolbar-items>
                        <c:if test="${user != null}">
                            <v-menu open-on-hover offset-y>
                                <v-btn slot="activator" flat round>
                                    <v-icon large color="white">account_circle</v-icon>
                                </v-btn>
                                <v-list>
                                    <a href="account">
                                        <v-list-tile @click="">
                                            <v-list-tile-title>Account</v-list-tile-title>
                                        </v-list-tile>
                                    </a>
                                    <a href="login?logout=true">
                                        <v-list-tile @click="">
                                            <v-list-tile-title>Logout</v-list-tile-title>
                                        </v-list-tile>
                                    </a>
                                </v-list>
                            </v-menu>
                        </c:if>
                        <c:if test="${user == null}">
                            <v-btn href="register" flat small color="#ffffff">
                                Register
                            </v-btn>
                            <v-btn href="login" small flat color="#ffffff">
                                Login
                            </v-btn>
                        </c:if>
                    </v-toolbar-items>
                </v-toolbar>
                <v-content>
                    <v-card header fixed style="height: 5px; background-color: #8B2635;" elevation-25></v-card>
                </v-content>
            </v-app>
        </div>
        <script src="res/js/v-header.js" type="text/javascript"></script>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>