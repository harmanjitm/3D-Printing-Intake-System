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
        <div id="app">
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
                <v-toolbar app fixed clipped-left color="#1B222B">
                    <v-toolbar-side-icon @click.stop="drawer = !drawer" class="white--text"></v-toolbar-side-icon>
                    <v-toolbar-title class="white--text">Account Management</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-toolbar-items>
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
                    </v-toolbar-items>
                </v-toolbar>
                <v-content>
                    <v-card style="height: 5px; background-color: #8B2635;" elevation-25></v-card>
                    <v-container>
                        
                    </v-container>
                </v-content>
                <v-footer app fixed dark>
                    <span color="#ffffff">ARIS3D Printing</span>
                </v-footer>
            </v-app>
        </div>
        <script>
            new Vue({ 
                el: '#app', 
                data: {
                    account: '',
                    logout: '',
                    drawer: false,
                    items: [ {title: 'Dashboard', icon: 'dashboard'}, { title: 'Account Management', icon: 'people'}]
                }});
        </script>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
    </body>
</html>