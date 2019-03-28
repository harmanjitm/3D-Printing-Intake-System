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
        <title>ARIS Submit New Order</title>
        <script type="text/javascript" src="node_modules/vuejs/dist/vue.min.js"></script>
        <style>
            body {
                text-align: center;
            }
            #stl_cont {
/*                border-style: solid;*/
                
/*                width: 600px;*/
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
            .dropdown {
             display: block;
             font-size: 16px;
             font-family: sans-serif;
            font-weight: 700;
            color: #444;
            line-height: 1.3;
            padding: .6em 1.4em .5em .8em;
            width: 200px;
            max-width: 100%; 
            box-sizing: border-box;
            margin: 0;
            border: 1px solid #aaa;
            box-shadow: 0 1px 0 1px rgba(0,0,0,.04);
            border-radius: .5em;
            -moz-appearance: none;
            -webkit-appearance: none;
            appearance: none;
            background-color: #fff;
            background-repeat: no-repeat, repeat;
            background-position: right .7em top 50%, 0 0;
            background-size: .65em auto, 100%;  
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
                    <!-- File select -->
                                <v-stepper-content step="1">
                                    <v-card class="mb-5" height="400px">
                                        <v-container fluid grid-list-md>
                                        <v-layout row wrap>
                                            <v-flex xs6 sm6 md4 lg8>
                                                <v-card id="stl_cont" >
                                                    <h2>Select an STL file</h2>
                                                    <input type="file" onchange='stl_viewer.add_model({local_file:this.files[0]});' @change="viewInfo" accept="*.*">
                                                    <p>Or Drag and drop</p>
                                                </v-card>
                                            </v-flex>
                                            <v-spacer></v-spacer>
                                            <v-flex xs6 sm6 md6 lg4>
                                                <v-card>
                                                 <v-card-title><h4>File Information</h4></v-card-title>
                                                 <v-divider></v-divider>
                                                    <v-list dense>
                                                    <v-list-tile>
                                                        <v-list-tile-content>File name: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ fileInfo.name }}</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Dimensions: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">Work in progress</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Volume: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ fileInfo.volume }} mm^3</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Area: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ fileInfo.area }} mm^2</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Triangles: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ fileInfo.triangles }}</v-list-tile-content>
                                                    </v-list-tile>
                                                        
                                                    </v-list>
                                                <v-btn @click="viewInfo">Get File Info</v-btn>
                                                </v-card>
                                            </v-flex>
                                        </v-layout>
                                        </v-container>
                                    </v-card>
                                    
                                    <v-btn color="primary" @click="e1 = 2">
                                        Continue
                                    </v-btn>
                                    <v-btn color="secondary">cancel</v-btn>
                                </v-stepper-content>
                <!-- Select printer -->
                                <v-stepper-content step="2">
                                    <v-card class="mb-5" height="400px">
                                        <v-container>
                                            <v-layout row wrap fluid>                                              
                                                <v-flex v-for="printer in printers" xs8 sm4 md4 lg4>
                                                    
                                                    <!-- Printer Cards -->
                                                    <v-card height="99%" width="95%" class="elevation-5"> 
                                                        <v-img :src="printer.img" aspect-ratio="2.5" contain></v-img>
                                                        <v-card-title secondary-title>
                                                            <h3 class="headline mb-0">{{printer.name}}</h3>
                                                            <div>{{printer.description}}</div>
                                                        </v-card-title>
                                                        <span>Run Cost: $</span>{{ printer.runCost }}<span>/h</span>
                                                        <v-card-actions>
<!--                                                            <input type="hidden" name="action" value="selectPrinter">-->
                                                            <input type="hidden" name="printerID" v-model="printerSelect.printerID">
                                                            <v-btn color="#8B2635" @click="selectPrinter(printer)">Select</v-btn>
                                                        </v-card-actions>
                                                    </v-card>
                                                </v-flex>
                                            </v-layout>
                                        </v-container>
                                    </v-card>
                                    <v-btn color="primary" @click="e1 = 3">
                                        Continue
                                    </v-btn>
                                    <v-btn color="secondary" @click="el = 1">Back</v-btn>
                                </v-stepper-content>
                <!-- Select material based on selected printer -->
                                <v-stepper-content step="3">
                                    <v-card class="mb-5" height="400px">
                                        <v-containter>
                                            <v-layout>
                                                <v-flex>
                                                    <!-- onchange="selectMaterial(material)" -->
                                                    <label>Material</label>
                                                    <select class="dropdown" v-model="material" :rules="[v => !!v || 'Item is required']" label="Select Material" required>
                                                        <option v-for="material in materials" v-if="selectPrinterName === material.printerName" 
                                                                value="material.materialId" v-on:change="selectMaterial(material)">
                                                            {{ material.materialName }}
                                                        </option>
                                                    </select>
                                                </v-flex>
                                                <v-flex>
                                                    <select v-model="selectMaterial" :items="materials" :rules="[v => !!v || 'Item is required']" label="Select Material Color" required>
                                                        <option v-for="material in materials" v-if="" value="material.value">{{ material }}</option>
                                                    </select>
                                                </v-flex>
                                            </v-layout>
                                        </v-containter>
                                    </v-card>

                                    <v-btn color="primary" @click="e1 = 4">
                                        Continue
                                    </v-btn>
                                    <v-btn color="secondary" @click="el = 2">Back</v-btn>
                                </v-stepper-content>
                <!-- Comments and payment opens -->
                                <v-stepper-content step="4">
                                    <v-card class="mb-5" height="400px">
                                        <v-container>
                                            <v-layout>
                                                <v-flex sm6 md4 lg6>
                                                    <v-textarea outline  label="Comments" min-width="300px" height="300px">
                                                    </v-textarea>
                                                </v-flex>
                                                <v-spacer></v-spacer>
                                                <v-flex>
                                                    <v-card sm6 md4 lg3>
                                                        <v-toolbar color="#1B222B" dark>
                                                             <v-toolbar-title>Payment</v-toolbar-title>
                                                        </v-toolbar>
                                                        
                                                        <v-card-text>You will be contacted by ARIS regarding payment options</v-card-text>
                                                    </v-card>
                                                </v-flex>
                                            </v-layout>
                                        </v-container>
                                    </v-card>

                                    <v-btn color="primary" @click="e1 = 5">
                                        Continue
                                    </v-btn>
                                    <v-btn color="secondary" @click="el = 3">Back</v-btn>
                                </v-stepper-content>
                        <!-- confirm choices -->
                                <v-stepper-content step="5">
                                    <v-card class="mb-5" height="400px">
                                    <v-container fluid grid-list-md>
                                        <v-layout row wrap>
                        <!-- User selected file information -->
                                        <v-flex xs12 sm4 md4 lg3>
                                            <v-card height="99%" width="80%">
                                                 <v-card-title><h4>File Information</h4></v-card-title>
                                                 <v-divider></v-divider>
                                                    <v-list dense>
                                                    <v-list-tile>
                                                        <v-list-tile-content>File name: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ fileInfo.name }}</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Dimensions: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">Work in progress</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Volume: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ fileInfo.volume }} mm^3</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Area: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ fileInfo.area }} mm^2</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Triangles: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ fileInfo.triangles }}</v-list-tile-content>
                                                    </v-list-tile>
                                                        
                                                    </v-list>
                                                </v-card>
                                        </v-flex>
                        <!-- User selected printer -->
                                        <v-flex xs12 sm6 md4 lg4>
                                            <v-card height="99%" width="95%" class="elevation-5" v-if="selectedPrinter"> 
                                                <v-img :src="selectedPrinter.img" aspect-ratio="2.5" contain></v-img>
                                                <v-card-title secondary-title>
                                                    <h3 class="headline mb-0">{{selectedPrinter.name}}</h3>
                                                    <div>{{selectedPrinter.description}}</div>
                                                </v-card-title>
                                                <span>Run Cost: $</span>{{ selectedPrinter.runCost }}<span>/h</span>
                                            </v-card>
                                        </v-flex>
                        <!-- User selected material -->
                                        <v-flex>
                                            <v-card>
                                                <span>{{ materialInfo }} Material info</span>
                                            </v-card>
                                        </v-flex>
                                        <v-flex>
                                            <v-card>
                                                <span>{{ materialInfo }} payment info</span>
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
        fileInfo: '',
        materialInfo: '',
        selectPrinterName: '',
        selectMaterialId: '',
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
            <c:forEach items="${materials}" var="material">
                {materialId: '${material.materialId}',
                 materialName: '${material.name}',
                 printerName: '${material.printerName}', 
                 materialDesc: '${material.description}', 
                 materialColor: '${material.colours}', 
                 materialVal: '${material.cost}', 
                 materialStat: '${material.status}'},
            </c:forEach>
        ],
        colours:
        [
            <c:forEach items="${materials}" var="material">
                <c:forEach items="${material.colours}" var="colour">
                    {materialId: '${material.materialId}',
                     colour: '${colour.color}',
                     status: '${colour.status}'},
                </c:forEach>
            </c:forEach>
        ],
        payments:
        [
            {text: 'Payments are currently unavailable', value: 'noPayment'}
        ],
    },
    methods: {
        selectPrinter(printer) {
            this.selectPrinterName = printer.name;
            alert(this.selectPrinterName);
            
            selectedPrinter = this.printer;
            return selectedPrinter;
        },
        selectMaterial(material) {
            this.selectMaterialId = material.materialId;
            alert(this.selectMaterialId);
        },
        selectPayment() {
        // Display payment info
        },
        viewInfo() {
                var info = JSON.stringify(stl_viewer.get_model_info(2));
                var obj = JSON.parse(info);
                this.fileInfo = obj
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