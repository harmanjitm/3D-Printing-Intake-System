<%-- 
    Document   : printerMgmt
    Created on : Jan 18, 2019, 10:37:00 AM
    Author     : 756852
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports/>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
        <title>ARIS3D - Printer Management</title>

    </head>
    <body>
        <div id="app">
            <v-app>
                <!-- Navbar header -->
                <ARIS3D:Header isAdmin="true" pageName="Printer Management"></ARIS3D:Header>
                <v-content>
                    <v-container grid-list-md>
                    <!-- Display error messages -->
                        <v-alert <c:if test='${successMessage != null}'>value="true"</c:if> type="success">
                            ${successMessage}
                        </v-alert>
                        <v-alert <c:if test='${errorMessage != null}'>value="true"</c:if> type="error">
                            ${errorMessage}
                        </v-alert>
                    <!-- container for printer Cards -->
                        <v-layout row wrap>
                            <v-flex @click="selectItem(item)" v-for="printer in printers" xs12 sm6 md6 lg4 xl4>
                            <!-- Edit printer modal -->    
                                <v-dialog v-model="dialog" max-width="750px" v-if="selectedItem">
                                    <v-card>
                                        <v-card-title>
                                            <span class="headline">Edit Printer Details</span>
                                        </v-card-title>
                                    </v-card>
                                </v-dialog>
                        <!-- Printer Cards -->
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card min-height="500px" class="elevation-5" class="clickable" @click.native="selectItem(printer)"> 
                                    <v-img :src="printer.img" aspect-ratio="1.5" contain></v-img>
                                    <v-card-title primary-title><h3 class="headline mb-0">{{printer.name}}</h3></v-card-title>
                                    <v-card-text>
                                        <table class="printer-card-table" width="100%">
                                            <tr>
                                                <td width="50%" class="text-xs-left">Build Volume(LxWxH): </td>
                                                <td class="text-xs-right">{{printer.size}}</td>
                                            </tr>
                                            <tr>
                                                <td class="text-xs-left">Run Cost: </td>
                                                <td class="text-xs-right">$2.50/h</td>
                                            </tr>
                                            <tr>
                                                <td class="text-xs-left">Status: </td>
                                                <td class="text-xs-right">{{printer.status}}</td>
                                            </tr>
                                            <tr>
                                                <td class="text-xs-left">Available Material: </td>
                                                <td class="text-xs-right">{{printer.materials}}</td>
                                            </tr>
                                        </table>
                                    </v-card-text>
                                </v-card>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-content>
            </v-app>
        </div>
        <style scoped>
            .clickable {
                cursor: pointer;
            }
        </style>
        <script>
            new Vue({
                el: '#app',
                data: {
                    drawer: 'false',//Add a selected item thingy somewhere here
                    dialog: false,
                    adminItems: //Navbar Items
                    [ 
                        {title: 'Home', icon: 'home', link: 'home'},
                        {title: 'Dashboard', icon: 'dashboard', link: 'dashboard'},
                        {title: 'Order Queue', icon: 'queue', link: 'queue'},
                        {title: 'Account Management', icon: 'people', link: 'accountmanagement'},
                        {title: 'Material Management', icon: 'texture', link: 'materialmanagement'},
                        {title: 'Printer Management', icon: 'print', link: 'printermanagement'},
                        {title: 'Reports', icon: 'poll', link: 'reportmanagement'}
                    ],
                    printers: //Printer info from database
                    [
                        <c:forEach items="${printers}" var="printer">
                            {printerId: '${printer.printerId}',
                             size: '${printer.size}',
                             status: '${printer.status}',
                             name: '${printer.name}',
                             materials: '${printer.materials}',
                             img: 'res/img/printers/${printer.printerId}.jpg'},
                        </c:forEach>
                    ],
                    selectedItem:
                    {
                        printerId: '',
                        size: '',
                        status: '',
                        name: '',
                        materials: '',
                    },
                },
                methods: {
                    selectItem(item) {
                        this.selectedItem = Object.assign({}, item);
                    },
                }
            });
        </script>
    </body>
</html>
