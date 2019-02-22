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
        <!-- Definitely still a work in progess. Just trying some things out. Please don't hate -->
        <title>ARIS3D - Printer Management</title>

    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="true" pageName="Printer Management"></ARIS3D:Header>
                <br><br>
                    <v-container grid-list-md >
                        <v-alert <c:if test='${successMessage != null}'>value="true"</c:if> type="success">
                            ${successMessage}
                        </v-alert>
                        <v-alert <c:if test='${errorMessage != null}'>value="true"</c:if> type="error">
                            ${errorMessage}
                        </v-alert>
                        <v-layout row wrap>
                            <v-flex @_click="selectItem(item)" v-for="i in 3" :key="`4${i}`" xs4>
                                <v-dialog v-model="dialog" max-width="750px" v-if="selectedItem">
                                    <v-card>
                                        <v-card-title>
                                            <span class="headline">Edit Printer Details</span>
                                        </v-card-title>
                                    </v-card>
                                </v-dialog>
                            <v-card class="elevation-3" v-if="i === 1" class="clickable" @click.native="selectItem(item)"> 
                                <v-img src="res/img/UM3X_Full_2048x.jpg" aspect-ratio="1.5" contain></v-img>
                                <v-card-title primary-title><h3 class="headline mb-0">Ultimaker 3 Extended</h3></v-card-title>
                                <v-card-text>
                                    <table class="printer-card-table">
                                        <tr>
                                            <td class="text-xs-left">Build Volume(x,y,z): </td>
                                            <td class="text-xs-right">406x355x406mm</td>
                                        </tr>
                                        <tr>
                                            <td class="text-xs-left">Run Cost: </td>
                                            <td class="text-xs-right">$2.50/h</td>
                                        </tr>
                                        <tr>
                                            <td class="text-xs-left">Status: </td>
                                            <td class="text-xs-right">Online</td>
                                        </tr>
                                        <tr>
                                            <td class="text-xs-left">Available Material: </td>
                                            <td class="text-xs-right">ABS-M30 | SR30</td>
                                        </tr>
                                    </table>
                                </v-card-text>
                            </v-card>

                            <!-- Info for card 2 -->
                            <v-card class="elevation-3" v-if="i === 2">
                                <v-img src="res/img/form-2-printer.jpg" aspect-ratio="1.5" contain></v-img>
                                <v-card-title primary-title><h3 class="headline mb-0">Form 2+</h3></v-card-title>
                                <v-card-text>
                                    <table class="printer-card-table">
                                        <tr>
                                            <td class="text-xs-left">Build Volume(x,y,z): </td>
                                            <td class="text-xs-right">406x355x406mm</td>
                                        </tr>
                                        <tr>
                                            <td class="text-xs-left">Run Cost: </td>
                                            <td class="text-xs-right">$2.50/h</td>
                                        </tr>
                                        <tr>
                                            <td class="text-xs-left">Status: </td>
                                            <td class="text-xs-right">Online</td>
                                        </tr>
                                        <tr>
                                            <td class="text-xs-left">Available Material: </td>
                                            <td class="text-xs-right">ABS-M30 | SR30</td>
                                        </tr>
                                    </table>
                                </v-card-text>

                                <!-- <v-spacer></v-spacer><v-btn color="#8B2635">Edit</v-btn>-->
                            </v-card>

                            <!-- Info for card 3 -->
                            <v-card class="elevation-3" v-if="i === 3">
                                <v-img src="res/img/Fortus 400mc.jpg" aspect-ratio="1.5" contain></v-img>
                                <v-card-title primary-title><h3 class="headline mb-0">Fortus 400mc</h3></v-card-title>
                                <v-card-text>
                                    <table class="printer-card-table">
                                        <tr>
                                            <td class="text-xs-left">Build Volume(x,y,z): </td>
                                            <td class="text-xs-right">406x355x406mm</td>
                                        </tr>
                                        <tr>
                                            <td class="text-xs-left">Run Cost: </td>
                                            <td class="text-xs-right">$2.50/h</td>
                                        </tr>
                                        <tr>
                                            <td class="text-xs-left">Status: </td>
                                            <td class="text-xs-right">Online</td>
                                        </tr>
                                        <tr>
                                            <td class="text-xs-left">Available Material: </td>
                                            <td class="text-xs-right">ABS-M30 | SR30</td>
                                        </tr>
                                    </table>
                                </v-card-text>
                            </v-card>
                    </v-layout>
                </v-container>
            </v-app>
        </div>
                            
        <style scoped>
            .clickable {
                cursor: pointer;
            }
        </style>

        <script src="res/js/vue.js" type="text/javascript"></script>
        <script>
            new Vue({
                el: '#app',
                data: {
                    
                }
                methods: {
                    selectItem(item) {
                        this.selectedItem = item;
                    },
                }
            });
        </script>

    </body>
</html>
