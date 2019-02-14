<%-- 
    Document   : accountMgmt
    Created on : Jan 18, 2019, 10:36:48 AM
    Author     : 756852
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports/>
        <title>ARIS 3D Printing Service - Account Management</title>
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
                    <!-- dialog window for adding a new account -->
                            <v-dialog v-model="dialog" max-width="750px" >
                                <v-btn slot="activator" color="#8B2635" dark class="mb-2">New Account</v-btn>
                                <v-card>
                                    <v-card-title>
                                        <span class="headline">New Account</span>
                                    </v-card-title>
                                    <form id="create-account" method="post" action="accountmanagement">
                                        <v-card-text>
                                            <v-container grid-list-md>
                                                <v-layout wrap>
                                                    <v-flex xs12 sm6 md4>
                                                        <v-text-field name="firstname" label="First Name"></v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md4>
                                                        <v-text-field name="lastname" label="Last Name"></v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md4>
                                                        <v-text-field name="email" label="Email Address"></v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md4>
                                                        <v-text-field name="password" label="Password"></v-text-field>
                                                    </v-flex>
                                                    <input type="hidden" name="action" value="add">
                                                </v-layout>
                                            </v-container>
                                        </v-card-text>
                                        <v-card-actions>
                                            <v-spacer></v-spacer>
                                            <v-btn flat color="primary" @click="close">Cancel</v-btn>
                                            <v-btn flat color="primary" @click="submit">Save</v-btn>
                                        </v-card-actions>
                                    </form>
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
                    <!-- dialog window for editing an existing account -->
                            <v-dialog v-model="dialog" max-width="750px" v-show="editUser">
                                <v-btn slot="activator" small class="mr-2" @click="edit-account">edit</v-btn>
                                <v-card>
                                    <v-card-title>
                                        <span class="headline">Edit Account</span>
                                    </v-card-title>
                                    <form id="edit-account" method="post" action="accountmanagement">
                                        <v-card-text>
                                            <v-container grid-list-md>
                                                <v-layout wrap>
                                                    
                                                    <v-flex xs12 sm6 md4>
                                                        <v-text-field name="firstname" label="First Name"> {{ this.firstname }}</v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md4>
                                                        <v-text-field name="lastname" label="Last Name">{{ this.lastname }}</v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md4>
                                                        <v-text-field name="email" label="Email Address">{{  this.email }}</v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md4>
                                                        <v-text-field name="password" label="Password">{{ this.status }}</v-text-field>
                                                    </v-flex>
                                                    <input type="hidden" name="action" value="edit">
                                                </v-layout>
                                            </v-container>
                                        </v-card-text>
                                        <v-card-actions>
                                            <v-spacer></v-spacer>
                                            <v-btn flat color="primary" @click="close">Cancel</v-btn>
                                            <v-btn flat color="primary" @click="submit">Save</v-btn>
                                        </v-card-actions>
                                    </form>
                                </v-card>
                            </v-dialog>
                                    
                                    <v-icon small @click="remove">delete</v-icon>
                                </td>
                            </template>
                        </v-data-table>
                    </v-container>
                </v-content>
            </v-app>
        </div>
        
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
        <script>
            new Vue ({ 
                el: '#app',
                methods:
                {
                    close()
                    {
                        this.dialog = false
                    },
                    submit()
                    {
                        document.getElementById('create-account').submit();
                    },
                    editUser() {
                      email = this.email,
                      firstname = this.firstname,
                      lastname = this.lastname,
                      status = this.status
                    },
                    edit()
                    {
                        document.getElementById('edit-account').submit();
                    },
                    remove()
                    {
                        alert('yoo')
                    }
                    
                },
                data: 
                {
                    dialog: false,
                    account: '',
                    logout: '',
                    drawer: false,
                    adminItems: 
                    [ 
                        {title: 'Dashboard', icon: 'dashboard', link: 'dashboard'},
                        {title: 'Order Queue', icon: 'queue', link: 'queue'},
                        {title: 'Account Management', icon: 'people', link: 'accountmanagement'},
                        {title: 'Material Management', icon: 'texture', link: 'materialmanagement'},
                        {title: 'Printer Management', icon: 'print', link: 'printermanagement'},
                        {title: 'Reports', icon: 'poll', link: 'reportmanagement'}
                    ],
                    accountmanagementheaders:
                    [
                        {text: 'Email Address', value: 'email'},
                        {text: 'First Name', value: 'firstname'},
                        {text: 'Last Name', value: 'lastname'},
                        {text: 'Status', value: 'status'},
                        {text: 'Actions', value: 'actions'}
                    ],
                    accounts:
                    [
                    <c:forEach items="${accounts}" var="account">
                        {email: '${account.email}', firstname: '${account.firstname}', lastname: '${account.lastname}', status: '${account.accountType}'},
                    </c:forEach>
                    ]
                }
            });
        </script>
    </body>
</html>