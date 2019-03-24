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
        <script type="text/javascript" src="node_modules/vuejs/dist/vue.min.js"></script>
        <style>
            body {
                text-align: center;
            }
            #stl_cont {
                /*                border-style: solid;*/
                width: 800px;
                height: 350px;
                margin: 0 auto;
            }
            .justify-center {
                margin: 0 auto;
            }
            #app {
                text-align: center;
            }
            img {
                width: 30%;
                margin: auto;
                display: block;
                margin-bottom: 10px;
            }
            button {

            }
        </style>

    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="false" pageName="New Order"></ARIS3D:Header>
                    <v-content>

                        <v-stepper v-model="e1">
                            <v-stepper-header>
                                <v-stepper-step :complete="e1 > 1" step="1">Submit a File</v-stepper-step>

                                <v-divider></v-divider>

                                <v-stepper-step :complete="e1 > 2" step="2">Choose a Printer</v-stepper-step>

                                <v-divider></v-divider>

                                <v-stepper-step :complete="e1 > 3" step="3">Choose a Material</v-stepper-step>

                                <v-divider></v-divider>

                                <v-stepper-step :complete="e1 > 4" step="4">Any Comments?</v-stepper-step>

                                <v-divider></v-divider>

                                <v-stepper-step step="5">Review Order</v-stepper-step>
                            </v-stepper-header>

                            <v-stepper-items>
                                <v-stepper-content step="1">
                                    <v-card class="mb-5" color="grey lighten-1" height="400px">
                                        <div id="stl_cont" >
                                            <h2>Select an STL file</h2>
                                            <input type="file" onchange='stl_viewer.add_model({local_file:this.files[0]});' @change="viewInfo" accept="*.*">
                                            <p>Or Drag and drop</p>
                                        </div>
                                    </v-card>
                                    
                                    <v-btn color="primary" @click="e1 = 2">
                                        Continue
                                    </v-btn>
                                    <v-btn color="secondary">cancel</v-btn>
                                </v-stepper-content>

                                <v-stepper-content step="2">
                                    <v-card class="mb-5" color="grey lighten-1" height="400px" xs12 sm6 md6 lg4 xl4 fill-height fluid>
                                        <v-container>
                                            <v-layout row wrap fluid>
                                                <form id="select-printer" method="post" action="ordermanagement">
                                                <v-flex v-for="printer in printers" xs12 sm6 md6 lg4 xl4>
                                                    <!-- Printer Cards -->
                                                    <v-card height="99%" width="95%" class="elevation-5" class="clickable"> 
                                                        <v-img :src="printer.img" aspect-ratio="2.5" contain></v-img>
                                                        <v-card-title secondary-title>
                                                            <h3 class="headline mb-0">{{printer.name}}</h3>
                                                            <div>{{printer.description}}</div>
                                                        </v-card-title>
                                                        <span>Run Cost: $</span>{{ printer.runCost }}<span>/h</span>
                                                        <v-card-actions>
                                                            <input type="hidden" name="action" value="add">
                                                            <v-btn color="#8B2635" @click="selectPrinter(selectedPrinter); e1 = 3;">Select</v-btn>
                                                        </v-card-actions>
                                                    </v-card>
                                                </v-flex>
                                                </form>
                                            </v-layout>
                                        </v-container>
                                    </v-card>
                                    <v-btn color="primary" @click="e1 = 3">
                                        Continue
                                    </v-btn>
                                    <v-btn color="secondary" @click="el = 1">Back</v-btn>
                                </v-stepper-content>

                                <v-stepper-content step="3">
                                    <v-card class="mb-5" color="grey lighten-1" height="400px">

                                    </v-card>

                                    <v-btn color="primary" @click="e1 = 4">
                                        Continue
                                    </v-btn>
                                    <v-btn color="secondary" @click="el = 2">Back</v-btn>
                                </v-stepper-content>
                                <v-stepper-content step="4">
                                    <v-card class="mb-5" color="grey lighten-1" height="400px">

                                    </v-card>

                                    <v-btn color="primary" @click="e1 = 5">
                                        Continue
                                    </v-btn>
                                    <v-btn color="secondary" @click="el = 3">Back</v-btn>
                                </v-stepper-content>
                                <v-stepper-content step="5">
                                    <v-card class="mb-5" color="grey lighten-1" height="400px">
                                    <v-container>
                                        <v-layout row wrap fluid>
                                    <!-- User selected file information -->
                                    <v-flex xs12 sm6 md6 lg4 xl4>
                                        <v-card>
                                            <span>{{ fileMaterialInfo }}</span>
                                            <p id="demo"></p>
                                            <v-btn @click="viewInfo">Get STL Info</v-btn>
                                        </v-card>
                                   `<!-- User selected printer -->
                                                <v-card height="99%" width="95%" class="elevation-5" v-if="selectPrinter"> 
                                                    <v-img :src="selectedPrinter.img" aspect-ratio="2.5" contain></v-img>
                                                    <v-card-title secondary-title>
                                                        <h3 class="headline mb-0">{{selectedPrinter.name}}</h3>
                                                        <div>{{selectedPrinter.description}}</div>
                                                    </v-card-title>
                                                    <span>Run Cost: $</span>{{ selectedPrinter.runCost }}<span>/h</span>
                                                </v-card>
                                            </v-flex>
                                        </v-layout>
                                    </v-container>
                                    </v-card> 

                                    <v-btn color="primary" @click="e1 = 1">
                                        Continue
                                    </v-btn>
                                    <v-btn flat>Cancel</v-btn>
                                </v-stepper-content>
                            </v-stepper-items>
                        </v-stepper>
                    </v-content>
                </v-app>
            </div>

            <script src="res/stl/stl_viewer.min.js"></script>
            <script>

new Vue({

    el: '#app',
    data: {
        e1: 0, //Stepper element
        image: '',
        fileMaterialInfo: '',
        selectPrinterId: '',
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
            description: '${printer.description}',
            runCost: '${printer.runCost}',
            name: '${printer.name}',
            materials: '${printer.materials}',
            img: 'res/img/printers/${printer.printerId}.jpg'},
            </c:forEach>
        ],
        selectedPrinter:
        {
            name: '',
            description: '',
            img: '',
            runCost: ''
            
        },
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
        selectPrinter() {
            name = this.printer.name,
            description = this.printer.description,
            img = this.printer.img,
            runCost = this.printer.runCost
        },
        selectMaterial() {
        // Display material info
        },
        selectPayment() {
        // Display payment info
        },
        viewInfo() {
            this.fileMaterialInfo = JSON.parse(JSON.stringify(stl_viewer.get_model_info(2)));
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
