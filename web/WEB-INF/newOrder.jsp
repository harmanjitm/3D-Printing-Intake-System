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
<script type="text/javascript" src="node_modules/vuetify-upload-button/dist/upload-button.min.js"></script>
        <style>
            body {
                text-align: center;
            }
            #stl_cont {
/*                border-style: solid;*/
                width: 400px;
                height: 400px;
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
                                    <div id="stl_cont" v-if="!image" @change="viewInfo">

                                        <h2>Select an STL file</h2>
                                        <input type="file" @change="onFileChange">

<!--                                      <div v-else>

                                        <button @click="removeImage">Remove file</button>
                                      </div>-->
                                    </div>
                                </v-card>
                                <v-btn color="primary" @click="e1 = 2">
                                    Continue
                                </v-btn>
                                <v-btn flat>Cancel</v-btn>
                            </v-stepper-content>

                            <v-stepper-content step="2">
                                <v-card class="mb-5" color="grey lighten-1" height="400px" xs12 sm6 md6 lg4 xl4>
                                    <v-container fill-height fluid>
                                        <v-layout row wrap>
                                    <v-flex v-for="printer in printers" xs12 sm6 md6 lg4 xl4>
                        <!-- Printer Cards -->
                                        <v-card height="95%" width="95%" class="elevation-5" class="clickable"> 
                                            <v-img :src="printer.img" aspect-ratio="2.0" contain></v-img>
                                            <v-card-title primary-title><h3 class="headline mb-0">{{printer.name}}</h3></v-card-title>
                                            <v-card-text>
                                                <table class="printer-card-table" width="100%">
                                                    <tr>
                                                        <td class="text-xs-left">Run Cost: </td>
                                                        <td class="text-xs-right"><span>$</span>{{ printer.runCost }}<span>/h</span> </td>
                                                    </tr>
<!--                                                    <tr>-->
<!--                                                        <td class="text-xs-left">Description: </td>-->
                                                        <tr>{{printer.description}}</td>
<!--                                                    </tr>-->

                                                </table>
                                            </v-card-text>
                                        </v-card>
                                    </v-flex>
                                            </v-layout>
                                </v-container>
                                </v-card>

                                <v-btn color="primary" @click="e1 = 3">
                                    Continue
                                </v-btn>
                                <v-btn flat>Cancel</v-btn>
                            </v-stepper-content>

                            <v-stepper-content step="3">
                                <v-card class="mb-5" color="grey lighten-1" height="400px">
                                    
                                </v-card>

                                <v-btn color="primary" @click="e1 = 4">
                                    Continue
                                </v-btn>
                                <v-btn flat>Cancel</v-btn>
                            </v-stepper-content>
                            <v-stepper-content step="4">
                                <v-card class="mb-5" color="grey lighten-1" height="400px">
                                    
                                </v-card>

                                <v-btn color="primary" @click="e1 = 5">
                                    Continue
                                </v-btn>
                                <v-btn flat>Cancel</v-btn>
                            </v-stepper-content>
                            <v-stepper-content step="5">
                                <v-card class="mb-5" color="grey lighten-1" height="400px">
                                    
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
        fileMaterialInfo: {},
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
        onFileChange(e) {
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length)
                return;
                this.createImage(files[0]);
        },
        createImage(file) {
            var image = new Image();
            var reader = new FileReader();
            var vm = this;

            reader.onload = (e) => {
            vm.image = e.target.result;
        };
        reader.readAsDataURL(file);
        },
        removeImage: function (e) {
            this.image = '';
        },
        selectPrinter(event) {
            this.selectPrinterId = printer.printerId;
            alert(event.target.value);
        },
        selectedPrinter() {
            
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
