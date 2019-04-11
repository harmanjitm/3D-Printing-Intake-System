<%-- 
    Document   : accountInfo
    Created on : Jan 18, 2019, 10:36:18 AM
    Author     : 756852
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <title>Account Information</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <v-content>
                    <ARIS3D:Header isAdmin="" pageName="Account Information"></ARIS3D:Header>
                    <v-container>
                        <v-layout>
                            <v-flex xs6 sm6 md6 lg6>
                                <v-card>
                                    <v-toolbar color="#1B222B" dark>
                                        <v-toolbar-title>Account Information: # ${sessionScope.account.accountID}</v-toolbar-title>
                                    </v-toolbar>           
                                    <v-list dense>
                                        <v-list-tile>
                                            <v-list-tile-content>First Name: </v-list-tile-content>
                                            <v-list-tile-content class="align-end">${sessionScope.account.firstname}</v-list-tile-content>
                                        </v-list-tile>
                                        <v-list-tile>
                                            <v-list-tile-content>Last Name: </v-list-tile-content>
                                            <v-list-tile-content class="align-end">${sessionScope.account.lastname}</v-list-tile-content>
                                        </v-list-tile>
                                        <v-list-tile>
                                            <v-list-tile-content>Email: </v-list-tile-content>
                                            <v-list-tile-content class="align-end">${sessionScope.account.email}</v-list-tile-content>
                                        </v-list-tile>
                                        <v-list-tile>
                                            <v-list-tile-content>Password: </v-list-tile-content>
                                            <v-list-tile-content class="align-end">${sessionScope.account.password}</v-list-tile-content>
                                            <v-list-tile-content class="align-end"></v-list-tile-content>
                                                <v-dialog v-model="dialog" max-width="450px">
                                                    <v-btn slot="activator" dark color="#8B2635">Change Password</v-btn>
                                                    <v-card>
                                                        <v-card-title>
                                                            <span class="headline">Change Password</span>
                                                        </v-card-title>
                                                        <v-container grid-list-md>
                                                            <v-layout wrap>
                                                                <v-card-text>
                                                                    <v-flex>
                                                                        <v-text-field label="Current Password"></v-text-field>
                                                                    </v-flex>
                                                                    <v-flex>
                                                                        <v-text-field label="New Password"></v-text-field>
                                                                        <v-text-field label="Confirm New Password"></v-text-field>
                                                                    </v-flex>
                                                                </v-card-text>
                                                                <v-card-actions>
                                                                    <v-spacer></v-spacer>
                                                                    <v-btn flat color="primary" @click="close">Cancel</v-btn>
                                                                    <v-btn flat color="primary" @click="submit">Save</v-btn>
                                                                </v-card-actions>
                                                                </v-flex>
                                                            </v-layout>
                                                        </v-container>
                                                    </v-card>
                                                </v-dialog>
                                            </v-list-tile-content>
                                        </v-list-tile>
                                    </v-list>     
                                </v-card>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-content>
            </v-app>
        </div>
    <script>
        new Vue({
            el: '#app',
            data: 
            {
                drawer: false,
                dialog: '',
                adminItems: 
                [ 
                    {title: 'Home', icon: 'home', link: 'home'},
                    {title: 'Dashboard', icon: 'dashboard', link: 'dashboard'},
                    {title: 'Order Queue', icon: 'queue', link: 'queue'},
                    {title: 'Account Management', icon: 'people', link: 'accountmanagement'},
                    {title: 'Material Management', icon: 'texture', link: 'materialmanagement'},
                    {title: 'Printer Management', icon: 'print', link: 'printermanagement'},
                    {title: 'Reports', icon: 'poll', link: 'reportmanagement'},
                    {title: 'Backups', icon: 'restore', link: 'backup'},
                    {title: 'New Order', icon: 'folder_open', link: 'order'}
                ],
                userItems:
                [
                    {title: 'Home', icon: 'home', link: 'home'},
                    {title: 'Dashboard', icon: 'dashboard', link: 'userhome'},
                    {title: 'New Order', icon: 'folder_open', link: 'order'}
                ],
            },
            methods:
            {
                close()
                {
                    this.dialog = false;

                },
            }
        });
    </script>
    </body>
</html>
