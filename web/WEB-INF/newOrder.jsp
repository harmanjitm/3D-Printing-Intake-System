<%-- 
    Document   : newOrder
    Created on : Jan 18, 2019, 10:35:54 AM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ARIS Submit Order</title>
        
        <style>
            body {
                text-align: center;
            }
            #stl_cont {
                border-style: solid;
                width: 500px;
                height: 500px;
                margin: 0 auto;
            }
            .justify-center {
                margin: 0 auto;
            }
        </style>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="false" pageName="New Order"></ARIS3D:Header>
                    <br><br><br>

                    <v-container grid-list-md text-xs-center>
                        <v-layout row wrap>
                        <!-- Order form -->
                            <v-flex>
                                <v-card>
                                    <v-card-text class="px-0">
                                        <div id="stl_cont" @change="viewInfo">
                                            <div>
                                                <span>Drag and Drop your file here <span>
                                                <span>OR <u> Browse your computer</u></span>
                                            </div>
                                        </div>
                                        <v-btn @click="viewInfo">Get STL Info</v-btn>
                                        <br>
                                        <v-flex class="justify-center" xs10>
                                            <v-select v-model="selectedPrinter" :items="printerSelect" :rules="[v => !!v || 'Item is required']" label="Select A Printer" required>
                                                <option @change="selectPrinter($event)" v-for="printer in printers" v-bind:value="printer.name">{{ printer }}</option>
                                            </v-select>
                                            <br>
                                            <v-select v-model="selectMaterial" :items="materials" :rules="[v => !!v || 'Item is required']" label="Select A Material" required>
                                                <option v-for="material in materials" v-bind:value="material.value">{{ material }}</option>
                                            </v-select>
                                            <br>
                                            <v-select v-model="selectPayment" :items="payments" :rules="[v => !!v || 'Item is required']" label="Method of payment" required>
                                                <option v-for="payment in payments" v-bind:value="payment.value">{{ payment }}</option>
                                            </v-select>
                                            <br>
                                            <v-textarea v-model="orderComment" label="Comments" placeholder="Additional comments" outline></v-textarea>
                                            <v-switch v-model="switch1" :label="`Save Order Preferences ${switch1.toString()}`"></v-switch>
                                        </v-flex>
                                        <br>
                                    <v-btn @click="viewInfo">Get STL Info</v-btn>
                                    <v-btn>Submit</v-btn>
                                </v-card-text>
                            </v-card>
                        </v-flex>
                <!-- Review Order form details -->        
                        <v-flex xs6>
                            <v-card>
                                <v-card-text class="px-0">
                            <!-- STL file info -->
                                    <v-flex>
                                        <v-card>
                                            <span>{{ fileMaterialInfo }}</span>
                                        </v-card>
                                    </v-flex>
                            <!-- Selected printer info -->
                                    <v-flex v-for="printer in printers" :key="printer.printerId">
                        <!-- Printer Cards -->
                                        <v-card color="#8B2635" height="5px"></v-card>
                                        <v-card min-height="500px" class="elevation-5" class="clickable"> 
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
                                </v-card>
                        </v-flex>
                    </v-layout>
                </v-container>
            </v-app>
        </div>

<script src="res/stl/stl_viewer.min.js"></script>
<script>
new Vue({
    el: '#app',
    data: {
        fileMaterialInfo: {},
        selectPrinterId: -1,
        switch1: true,
        drawer: '',
        orderComment: '',
        userItems:
                [
                    {title: 'Home', icon: 'home', link: 'home'},
                    {title: 'Dashboard', icon: 'dashboard', link: 'userhome'},
                    {title: 'New Order', icon: 'queue', link: 'order'}
                ],
        printerSelect:
                [
                    {text: 'Technicians Preference', value: '-1'},
                    <c:forEach items="${printers}" var="printer">
                        {value: '${printer.printerId}',
                         text: '${printer.name}'},
                    </c:forEach>
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
        materials:
                [
                    {text: 'Technician Preference', value: 'tech'}
                ],
        payments:
                [
                    {text: 'Payments are currently unavailable', value: 'noPayment'}
                ],
        
    },
    methods: {
        selectedPrinter() {
            
        },
        selectPrinter(event) {
            this.selectPrinterId = printerId;
            alert(event.target.value);
        },
        selectMaterial() {
            // Display material info
        },
        selectPayment() {
            // Display payment info
        },
        viewInfo() {
            this.fileMaterialInfo=JSON.stringify(stl_viewer.get_model_info(2));
            
        }
    },
    computed: {

    }
})

    var stl_viewer = new StlViewer
        (
                document.getElementById("stl_cont"),
                {
                    auto_rotate: false,
                    allow_drag_and_drop: true,
                    models:
                            [
                                {filename: "viewstl_plugin.stl"}
                            ]
                }
        );
</script>

</body>
</html>
