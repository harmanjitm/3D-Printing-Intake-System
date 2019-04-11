<%-- 
    Document   : accountMgmt
    Created on : Jan 18, 2019, 10:36:48 AM
    Author     : 756852 (Harmanjit M.)
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports/>
        <title>ARIS3D - Account Management</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="true" pageName="Account Management"></ARIS3D:Header>
                <v-content>
                    <v-container>
                        <v-alert <c:if test='${successMessage != null}'>value="true"</c:if> type="success">
                            ${successMessage}
                        </v-alert>
                        <v-alert <c:if test='${errorMessage != null}'>value="true"</c:if> type="error">
                            ${errorMessage}
                        </v-alert>
                        <v-card color="#8B2635" height="5px"></v-card>
                        <v-toolbar class="elevation-1" dark>
                            <v-toolbar-title>Manage Accounts</v-toolbar-title>
                            <v-spacer></v-spacer>
                            <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
                    
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
                                                    <v-flex xs12 sm6 md6>
                                                        <v-text-field name="firstname" label="First Name"></v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md6>
                                                        <v-text-field name="lastname" label="Last Name"></v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md6>
                                                        <v-text-field name="email" label="Email Address"></v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md6>
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
                        <v-data-table  class="elevation-3" :headers="accountmanagementheaders" :items="accounts" :search="search">
                            <template slot="items" v-bind:accountId="props.item.accountId" slot-scope="props">
                                <td>{{ props.item.email }}</td>
                                <td>{{ props.item.firstname }}</td>
                                <td>{{ props.item.lastname }}</td>
                                <td>{{ props.item.status }}</td>
                                <td class="justify-center">
                        <!-- dialog window for editing an existing account -->
                                <v-dialog v-model="editDialog" max-width="750px" v-show="editUser">
                                    <v-icon small slot="activator" @click="editAccount(props.item)">edit</v-icon>
                                    <v-card>
                                        <v-card-title>
                                            <span class="headline">Edit Account</span>
                                        </v-card-title>
                                        <form id="edit-account" method="post" action="accountmanagement">
                                            <v-card-text>
                                                <v-container grid-list-md>
                                                    <v-layout wrap>
                                                        <input type="hidden" name="action" value="edit">
                                                        <input type="hidden" name="accountID" v-model="editItem.accountID">
                                                        <v-flex xs12 sm6 md6>
                                                            <v-text-field name="firstname" v-model="editItem.firstname" label="First Name"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12 sm6 md6>
                                                            <v-text-field name="lastname" v-model="editItem.lastname" label="Last Name"></v-text-field>
                                                        </v-flex>
                                                        <v-spacer></v-spacer>
                                                        <v-flex xs12 sm6 md6>
                                                            <v-text-field name="email" v-model="editItem.email" label="Email Address"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12 sm6 md6>
                                                            <v-select v-model="editItem.status" :items="accountStatusDropdown" item-text="type" item-value="value" label="Account Type" id="accountType" name="accountType"></v-select>
                                                        </v-flex>
                                                    </v-layout>
                                                </v-container>
                                            </v-card-text>
                                            <v-card-actions>
                                                <v-spacer></v-spacer>
                                                <v-btn flat color="primary" @click="close">Cancel</v-btn>
                                                <v-btn flat color="primary" @click="edit">Save</v-btn>
                                            </v-card-actions>
                                        </form>
                                    </v-card>
                                </v-dialog>
                                <v-icon small @click="remove(props.item)">delete</v-icon>
                                </td>
                            </template>
                        </v-data-table>
                        <form id="remove-account" method="post" action="accountmanagement"><input type="hidden" name="action" value="remove"/><input type="hidden" id="removeAccountID" name="removeAccountID" value=""/></form>
                    </v-container>
                </v-content>
            </v-app>
        </div>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
        <script>
            new Vue ({ 
                el: '#app',
                data: 
                {
                    editIndex: -1,
                    dialog: false,
                    editDialog: false,
                    account: '',
                    search: '',
                    logout: '',
                    drawer: false,
                    accountStatusDropdown:
                    [
                        {type: 'User', value: 'user', name: 'accountType'},
                        {type: 'Technician', value: 'admin', name: 'accountType'}
                    ],
                    editItem: 
                    {
                        accountID: '',
                        email: '',
                        firstname: '',
                        lastname: '',
                        status: ''
                    },
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
                    accountmanagementheaders:
                    [
                        {text: 'Email Address', value: 'email'},
                        {text: 'First Name', value: 'firstname'},
                        {text: 'Last Name', value: 'lastname'},
                        {text: 'Status', value: 'status'},
                        {text: 'Actions', value: 'actions', sortable: false}
                    ],
                    accounts:
                    [
                    <c:forEach items="${accounts}" var="account">
                        {accountID: '${account.accountID}', 
                         email: '${account.email}', 
                         firstname: '${account.firstname}', 
                         lastname: '${account.lastname}', 
                         status: '${account.accountType}'},
                    </c:forEach>
                    ]
                },
                methods:
                {
                    close()
                    {
                        this.dialog = false,
                        this.editDialog = false
                    },
                    submit()
                    {
                        document.getElementById('create-account').submit();
                    },
                    editUser() 
                    {
                        accountID = this.accountID,
                        email = this.email,
                        firstname = this.firstname,
                        lastname = this.lastname,
                        status = this.status
                    },
                    edit()
                    {
                        document.getElementById('accountType').value=this.editItem.status;
                        document.getElementById('edit-account').submit();
                    },
                    remove(item)
                    {
                        this.editIndex = this.accounts.indexOf(item)
                        this.editItem = Object.assign({}, item)
                        document.getElementById('removeAccountID').value=this.editItem.accountID;
                        document.getElementById('remove-account').submit();
                    },
                    editAccount(item)
                    {
                        this.editIndex = this.accounts.indexOf(item)
                        this.editItem = Object.assign({}, item)
                        this.editDialog = false
                    }
                }
            });
        </script>
    </body>
</html>