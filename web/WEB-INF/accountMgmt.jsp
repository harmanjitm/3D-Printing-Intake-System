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
                <ARIS3D:Header isAdmin="true" pageName="Account Management"></ARIS3D:Header>
                <v-content>
                    <v-container>
                        <v-toolbar class="elevation-1" dark>
                            <v-toolbar-title>Manage Accounts</v-toolbar-title>
                            <v-spacer></v-spacer>
                            <v-dialog v-model="dialog" max-width="750px">
                                <v-btn slot="activator" color="#8B2635" dark class="mb-2">New Account</v-btn>
                                <v-card>
                                    <v-card-title>
                                        <span class="headline">New Account</span>
                                    </v-card-title>
                                    <v-card-text>
                                        <v-container grid-list-md>
                                            <v-layout wrap>
                                                <v-flex xs12 sm6 md4>
                                                    <v-text-field label="First Name"></v-text-field>
                                                </v-flex>
                                                <v-flex xs12 sm6 md4>
                                                    <v-text-field label="Last Name"></v-text-field>
                                                </v-flex>
                                                <v-flex xs12 sm6 md4>
                                                    <v-text-field label="Email Address"></v-text-field>
                                                </v-flex>
                                                <v-flex xs12 sm6 md4>
                                                    <v-text-field label="Password"></v-text-field>
                                                </v-flex>
                                            </v-layout>
                                        </v-container>
                                    </v-card-text>
                                    <v-card-actions>
                                        <v-spacer></v-spacer>
                                        <v-btn flat color="primary" @click="">Cancel</v-btn>
                                        <v-btn flat color="primary" @click="">Save</v-btn>
                                    </v-card-actions>
                                </v-card>
                            </v-dialog>
                        </v-toolbar>
                        <v-data-table class="elevation-3" :headers="accountmanagementheaders" :items="accounts">
                            <template slot="items" slot-scope="props">
                                <td>{{ props.item.email }}</td>
                                <td>{{ props.item.firstname }}</td>
                                <td>{{ props.item.lastname }}</td>
                                <td>{{ props.item.status }}</td>
                                <td class="justify-center">
                                    <v-icon small class="mr-2">edit</v-icon>
                                    <v-icon small>delete</v-icon>
                                </td>
                            </template>
                        </v-data-table>
                    </v-container>
                </v-content>
            </v-app>
        </div>
        <script src="res/js/vue.js" type="text/javascript"></script>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
        <form method="post" action="accountmanagement">
            <input name="testCreateAccount" type="hidden">
            <input type="submit" value="Create Account">
        </form>
    </body>
</html>