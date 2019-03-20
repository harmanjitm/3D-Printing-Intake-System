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
        <title>JSP Page</title>

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
                    <br><br><br><br>

                    <v-container grid-list-md text-xs-center>
                        <v-layout row wrap>
                        <!-- Order form -->
                            <v-flex>
                                <v-card>
                                    <v-card-text class="px-0">
                                        <div id="stl_cont" >
                                            <div>
                                                <span>Drag and Drop your file here <span>
                                                <span>OR <u> Browse your computer</u></span>
                                            </div>
                                        </div>
                                        <br>
                                        <v-flex class="justify-center" xs10>
                                            <v-select v-model="selectPrinter" :items="printers" :rules="[v => !!v || 'Item is required']" label="Select A Printer" required>
                                                <option v-for="printer in printers" v-bind:value="printer.value">{{ printer }}</option>
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
                                            <v-textarea
                                                v-model="orderComment"
                                                label="Comments"
                                                placeholder="Additional comments"
                                                outline
                                                ></v-textarea>
                                            <v-switch
                                                v-model="switch1"
                                                :label="`Save Order Preferences ${switch1.toString()}`"
                                            ></v-switch>
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
                                            <span>{{ showInfo }}</span>
                                        </v-card>
                                    </v-flex>
                                    <!-- Selected printer info -->
                                    <v-flex>
<!--                                        <div v-for="printer in printers">-->
                                            <v-card v-if="selectPrinter === tech">
                                                <span>Tech</span>
                                            </v-card>
                                            <v-card v-if="selectPrinter === form2">
                                                <span>Form 2</span>
                                            </v-card>
                                            <v-card v-if="selectPrinter === fortus">
                                                <span>Fortus 4000mc</span>
                                            </v-card>
                                            <v-card v-if="selectPrinter === ultimaker">
                                                <span>Ultimaker 3 Extended</span>
                                            </v-card>
                                    </v-flex>
                                    <v-flex>
                                    <!-- Material info -->
                                        <v-card>
                                            <span>Info displayed here</span>
                                            <v-card-text>
                                                {{fileMaterialInfo}}
                                            </v-card-text>
                                        </v-card>
                                    </v-flex>
                                    <!-- Payment info -->
                                    <v-flex>
                                        <v-card>
                                            <span>More info</span>
                                        </v-card>
                                    </v-flex>
                                </v-card-text>
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
        selectPrinter: '',
        switch1: true,
        drawer: '',
        orderComment: [],
        showInfo: '',
        tech: [],
        fortus: [],
        form2: [],
        ultimaker: [],
        userItems:
                [
                    {title: 'Home', icon: 'home', link: 'home'},
                    {title: 'Dashboard', icon: 'dashboard', link: 'userhome'},
                    {title: 'New Order', icon: 'queue', link: 'order'}
                ],
        printers:
                [
                    {text: 'Technicians Preference', value: 'tech'},
                    {text: 'Form 2', value: 'form2'},
                    {text: 'Fortus 400mc', value: 'fortus'},
                    {text: 'Ultimaker 3E', value: 'ultimaker'}
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
        selectPrinter() {
            // Display printer info
        },
        selectMaterial() {
            // Display material info
        },
        selectPayment() {
            // Display payment info
        },
        viewInfo: function () {
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
