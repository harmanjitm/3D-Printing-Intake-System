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
        <title>ARIS3D - Submit New Order</title>
        <script type="text/javascript" src="node_modules/vuejs/dist/vue.min.js"></script>
        
        <link href="res/css/newOrder.css" rel="stylesheet" type="text/css"/>    
    </head>
    
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="false" pageName="Order Submission"></ARIS3D:Header>
                <v-content>
                <v-container>
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
                        
                    <!-- success/error handling -->    
                        <v-alert <c:if test='${successMessage != null}'>value="true"</c:if> type="success">
                            ${successMessage}
                        </v-alert>
                        <v-alert <c:if test='${errorMessage != null}'> id="error" value="true"</c:if> type="error">
                            ${errorMessage}
                        </v-alert>


                    <v-stepper-items>
                        <!-- File select -->
                        <v-stepper-content step="1">
                            <v-card class="mb-5" height="350px" flat>
                                <v-container fluid grid-list-md>
                                    <v-layout row wrap>
                                        <v-flex xs6 sm6 md4 lg8>
                                            <v-card class="elevation-4">
                                                <div id="stl_cont" @change="showInfo">
                                                    <h2 class="fileInput">Select an STL file</h2>
                                                    <!-- form submission is placed here because this is where the file is being taken in -->
                                                    <form id="create-order" method="post" action="order" enctype="multipart/form-data">  
                                                        <input type="hidden" id="action" name="action" value="submit">
                                                        <input type="file" class="fileInput" name="file" onchange='stl_viewer.add_model({local_file:this.files[0]}); ' accept="*.*" loading>
                                                        <input type="hidden" id="selectedPrinter" name="printer" value="">
                                                        <input type="hidden" id="selectedMaterial" name="material" value="">
                                                        <input type="hidden" id="selectedMaterialColour" name="colour" value="">
                                                        <input type="hidden" id="comment" name="comments" value="">
                                                        <input type="hidden" id="dimensions" name="dimensions" value="">
                                                        <input type="hidden" id="volume" name="volume" value="">
                                                        <input type="hidden" id="area" name="area" value="">
                                                    </form>
                                                </div>
                                            </v-card>
                                        </v-flex>
                                        <v-spacer></v-spacer>
                                        <v-flex xs6 sm6 md6 lg4>
                                            <v-card min-height="350px">
                                                <v-card-title><h4>File Information</h4></v-card-title>
                                                <v-divider></v-divider>
                                                <v-list dense>
                                                    <v-list-tile>
                                                        <v-list-tile-content>File name: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ selectedFile.name }}</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Dimensions(mm): </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ selectedFile.xAxis }} {{ selectedFile.yAxis }} {{ selectedFile.zAxis }}</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Volume: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ selectedFile.volume }} mm&#179</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Area: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ selectedFile.area }} mm&#178</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Triangles: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ selectedFile.triangles }}</v-list-tile-content>
                                                    </v-list-tile>
                                                </v-list>
                                            </v-card>
                                        </v-flex>
                                    </v-layout>
                                </v-container>
                            </v-card>
                            <v-btn color="primary" @click="e1 = 2">
                                Continue
                            </v-btn>
                            <v-btn color="secondary" href="home">cancel</v-btn>
                        </v-stepper-content>


                    <!-- Select a printer -->
                        <v-stepper-content step="2">
                            <v-card class="mb-5" height="380px" flat>
                                <v-alert id='showPrinter' style='display: none' v-if='infoMessage != null' value="true" type="info">
                                </v-alert>
                                <v-container>
                                    <v-layout row wrap fluid>    
                                        <v-flex v-for="printer in printers" v-bind:key="printer.printerId" xs8 sm4 md4 lg4>
                                    <!-- Printer Cards -->
                                            <v-hover>
                                                <v-card id="printerCard" @click="selectPrinter(printer)" 
                                                        style="cursor: pointer;" width="344"
                                                        v-model="selected"
                                                        v-bind:class="{ [`elevation-${selected}`]: true }"
                                                        class="mx-auto"
                                                        > 
                                                    <v-img :src="printer.img" aspect-ratio="2.5" contain></v-img>
                                                    <v-card-title secondary-title>
                                                        <h3 class="headline mb-0">{{printer.name}}</h3>
                                                        <div>{{printer.description}}</div>
                                                    </v-card-title>
                                                    <span><h4>Run Cost:</h4> $</span>{{ printer.runCost }}<span>/h</span>
                                                    <v-card-actions>
                                                        <input type="hidden" name="printerID" v-model="printerSelect.printerID">
                                                    </v-card-actions>
                                                </v-card>
                                            </v-hover>        
                                        </v-flex>
                                    </v-layout>
                                </v-container>
                            </v-card>
                            <v-btn color="primary" @click="e1 = 3">
                                Continue
                            </v-btn>
                            <v-btn color="secondary" href="home">cancel</v-btn>
                        </v-stepper-content>


                <!-- Select material based on selected printer -->
                        <v-stepper-content step="3">
                            <v-card class="mb-5" height="500px" flat>
                                <v-container>
                                    <v-layout>
                                        <v-flex xs8 sm4 md4 lg6>
                                            <v-toolbar dark flat class="headline blue-grey darken-4 white--text">
                                                <v-toolbar-title>Choose {{ selectedPrinter.name }} Material</v-toolbar-title>
                                            </v-toolbar>
                                            <v-expansion-panel focusable>
                                                <v-expansion-panel-content min-width="400"
                                                                           v-for="material in materials" :key="material" v-if="selectPrinterName === material.printerName">
                                                    <template v-slot:header>
                                                        <div>{{ material.materialName }}</div>
                                                    </template>
                                                    <v-card min-width="400">
                                                        <v-card-text class="grey lighten-3">
                                                            <span><h4>About Material: </h4>{{ material.materialDesc }}</span>
                                                            <p><h4>Cost per mm&#179: </h4>{{ material.materialVal }}</p>
                                                            <!--                                                              <input type="hidden" name="materialID" v-model="materialSelect.materialID">-->
                                                            <v-spacer></v-spacer><v-btn dark color="#8B2635" @click="selectMaterial(material)">Select</v-btn>
                                                        </v-card-text>
                                                    </v-card>
                                                </v-expansion-panel-content>
                                            </v-expansion-panel>
                                        </v-flex>       
                                        
                                        <v-flex>
                                            <v-toolbar dark flat class="headline blue-grey darken-4 white--text">
                                                <v-toolbar-title>Material Options</v-toolbar-title>
                                            </v-toolbar>   
                                            <v-data-table v-model="selected" :headers="colourHeaders" :items="colours" 
                                                          item-key="colour" select-all item-key="props.item" hide-actions hide-headers class="elevation-2">
                                                <template slot="items" slot-scope="props">
                                                    <tr v-if="props.item.materialId === selectMaterialId" @click="selectColour(props.item)">
                                                        <td>
                                                            <v-checkbox :input-value="props.selected" primary hide-details></v-checkbox>
                                                        </td>
                                                        <td>{{ props.item.colour }}</td>
                                                        <td>{{ props.item.status }}</td>
                                                    </tr>
                                                </template>
                                            </v-data-table>
                                        </v-flex>
                                    </v-layout>
                                </v-container>
                            </v-card>

                            <v-btn color="primary" @click="e1 = 4">
                                Continue
                            </v-btn>
                            <v-btn color="secondary" href="home">cancel</v-btn>
                        </v-stepper-content>

                    <!-- Comments and payment opens -->
                        <v-stepper-content step="4">
                            <v-card class="mb-5" height="350px" flat>
                                <v-container>
                                    <v-layout>
                                        <v-flex sm6 md4 lg6>
                                            <v-textarea outline v-model="comments" label="Comments" min-width="300px" height="300px">
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
                            <v-btn color="secondary" href="home">cancel</v-btn>
                        </v-stepper-content>


                    <!-- confirm selections and submit order -->
                        <v-stepper-content step="5">
                            <v-card class="mb-5" height="500px" flat>
                                <div style="max-width: 800px; margin: auto;" class="grey lighten-3">
                                    <v-toolbar color="#1B222B" dark>
                                        <v-toolbar-title>Order Confirmation</v-toolbar-title>
                                        <v-spacer></v-spacer>
                                    </v-toolbar>
                                    <v-card>
                                        <v-container fluid grid-list-lg>
                                            <v-layout row wrap>
                                                <v-flex xs8 sm4 md4 lg6>
                                                    <v-card flat min-height="300px">
                                                        <v-card-title><h4>File Information</h4></v-card-title>
                                                        <v-divider></v-divider>
                                                        <v-list dense>
                                                            <v-list-tile>
                                                                <v-list-tile-content>File name: </v-list-tile-content>
                                                                <v-list-tile-content class="align-end">{{ selectedFile.name }}</v-list-tile-content>
                                                            </v-list-tile>
                                                            <v-list-tile>
                                                                <v-list-tile-content>Volume: </v-list-tile-content>
                                                                <v-list-tile-content class="align-end">{{ selectedFile.volume }} mm&#179</v-list-tile-content>
                                                            </v-list-tile>
                                                            <v-list-tile>
                                                                <v-list-tile-content>Triangles: </v-list-tile-content>
                                                                <v-list-tile-content class="align-end">{{ selectedFile.triangles }}</v-list-tile-content>
                                                            </v-list-tile>
                                                        </v-list>
                                                        <v-divider light></v-divider>
                                                        <v-list dense two-line flat>
                                                            <v-list-tile>
                                                                <v-list-tile-content><h4>Payment: </h4>You will be contacted by the lab once your submission has been processed.</v-list-tile-content>
                                                            </v-list-tile>
                                                        </v-list>
                                                    </v-card>
                                                </v-flex>

                                                <v-flex xs8 sm4 md4 lg6>
                                                    <v-card v-if="selectedPrinter" flat>
                                                        <v-layout>
                                                            <v-flex xs5>
                                                                <v-img :src="selectedPrinter.img" height="174px" contain></v-img>
                                                            </v-flex>
                                                            <v-flex xs7>
                                                                <v-card-title secondary-title>
                                                                    <h3 class="headline mb-0">{{selectedPrinter.name}}</h3>
                                                                </v-card-title>
                                                                <v-list dense>
                                                                    <v-list-tile>
                                                                        <v-list-tile-content><h4>Run Cost:</h4> </v-list-tile-content>
                                                                        <v-list-tile-content class="align-end"> $ {{ selectedPrinter.runCost }} /h</v-list-tile-content>
                                                                    </v-list-tile>
                                                                </v-list>
                                                            </v-flex>
                                                        </v-layout>
                                                        <v-divider light></v-divider>
                                                        <v-card-text>
                                                            <v-list dense flat>
                                                                <v-list-tile>
                                                                    <v-list-tile-content>Material: </v-list-tile-content>
                                                                    <v-list-tile-content class="align-end">{{ selectedMaterial.materialName }}</v-list-tile-content>
                                                                </v-list-tile>
                                                                <v-list-tile>
                                                                    <v-list-tile-content>Type: </v-list-tile-content>
                                                                    <v-list-tile-content class="align-end">{{ selectedColour.colour }}</v-list-tile-content>
                                                                </v-list-tile>
                                                                <v-list-tile>
                                                                    <v-list-tile-content>Cost per mm&#179: </v-list-tile-content>
                                                                    <v-list-tile-content class="align-end">$ {{ selectedMaterial.materialVal }}</v-list-tile-content>
                                                                </v-list-tile>
                                                        </v-card-text>
                                                    </v-card>
                                            </v-layout>
                                            <v-flex>
                                                <v-card flat>
                                                    <v-list dense flat>
                                                        <v-list-tile>
                                                            <v-list-tile-content><h4>Your Message: </h4> {{ comments }}</v-list-tile-content>
                                                        </v-list-tile>
                                                    </v-list>
                                                </v-card>
                                            </v-flex>
                                        </v-container>
                                    </v-card>
                                </div>
                            </v-card> 

                                <v-btn dark color="#8B2635" @click="submit">
                                    Submit
                                </v-btn>
                                <v-btn color="secondary" @click="e1 = 1">Make Changes</v-btn>
                            </v-stepper-content>
                        </v-stepper-items>
                    </v-stepper>
                </v-container>
                </v-content>
            </v-app>
        </div>

<script src="res/stl/stl_viewer.min.js"></script>
<script>
new Vue({

    el: '#app',
    data: {
        e1: 0, //Stepper element
        infoMessage: '',
        image: '',
        fileInfo: '',
        comments: '',
        selectPrinterName: '',
        selectMaterialId: '',
        selectedColourID: 0,
        drawer: false,
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
        userItems:
        [
            {title: 'Home', icon: 'home', link: 'home'},
            {title: 'New Order', icon: 'folder_open', link: 'order'}
        ],
        selectedFile: {
            name: '',
            xAxis: '', 
            yAxis: '',
            zAxsis: '',
            volume: '',
            area: '',
            triangles: ''  
        },
        printerSelect: // unused?
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
        selectedPrinter: //holds a selected printer from the list of printers
        {
            printerId: '',
            size: '',
            status: '',
            description: '',
            runCost: '',
            name: '',
            materials: '',
            img: ''
            
        },
        materials: //Material info from the database
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
        selectedMaterial: //holds selected material info
        {
            materialId: '',
            materialName: '',
            printerName: '', 
            materialDesc: '', 
            materialColor: '', 
            materialVal: '', 
            materialStat: ''
        },
        colours: // colours from the database
        [
            <c:forEach items="${materials}" var="material">
                <c:forEach items="${material.colours}" var="colour">
                    {materialId: '${material.materialId}',
                     colour: '${colour.color}',
                     status: '${colour.status}'},
                </c:forEach>
            </c:forEach>
        ],
        selectedColour: // holds selected colour
        {
            materialId: '',
            colour: '',
            status: ''
        },
        colourHeaders:
        [
            {text: 'Colour', value: 'colour'},
            {text: 'Status', value: 'status'},
        ],
        payments: //placeholder for if/when the system is set up to take in payment
        [
            {text: 'Payments are currently unavailable', value: 'noPayment'}
        ],
    },
    methods: 
    {
        submit() {  
            var file = JSON.parse(JSON.stringify(stl_viewer.get_model_info(2)));
            var dims = JSON.parse(JSON.stringify(file["dims"]));
            document.getElementById('dimensions').value = dims["z"] + "x" + dims["y"] + "x" + dims["z"];
            document.getElementById('area').value = file["area"];
            document.getElementById('volume').value = file["volume"];
            document.getElementById('selectedPrinter').value = this.selectedPrinter.printerId;
            document.getElementById('selectedMaterial').value = this.selectedMaterial.materialId;
            document.getElementById('selectedMaterialColour').value = this.selectedColour.colour;
            document.getElementById('comment').value = this.comments;
            document.getElementById('create-order').submit();
        },
        selectPrinter(printer) {
            this.selectPrinterName = printer.name;
            this.selectedPrinter = Object.assign({}, printer);
            document.getElementById('showPrinter').innerHTML = this.selectPrinterName + ' selected';
            document.getElementById("showPrinter").style.display = "block";
            
        },
        selectMaterial(material) {
            this.selectMaterialId = material.materialId;
            this.selectedMaterial = Object.assign({}, material);   
        },
        loadMaterialColour(material) {
            this.selectMaterialId = material.materialId;
        },
        selectColour(colour) {
            this.selectedColour = Object.assign({}, colour);
        },
        selectPayment() {
        // PLACEHOLDER: Display payment info
        },
        viewInfo() {
                var info = JSON.stringify(stl_viewer.get_model_info(2));
                var obj = JSON.parse(info);
                this.fileInfo = obj;
                this.selectedFile.name = obj.name;
                this.selectedFile.xAxis = obj.dims["x"].toFixed(2) + " x";
                this.selectedFile.yAxis = obj.dims["y"].toFixed(2) + " x";
                this.selectedFile.zAxis = obj.dims["z"].toFixed(2);
                this.selectedFile.area = obj.area.toFixed(2);
                this.selectedFile.volume = obj.volume.toFixed(2);
                this.selectedFile.triangles = obj.triangles;
                document.getElementsByClassName('fileInput')[0].style.display = 'none';
                document.getElementsByClassName('fileInput')[1].style.display = 'none';
                
            },
            showInfo() {
                setTimeout(this.viewInfo, 3000)
            }
        },
})
    // method to call stl viewer API
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