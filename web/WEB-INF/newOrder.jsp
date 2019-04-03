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
             <v-alert <c:if test='${successMessage != null}'>value="true"</c:if> type="success">
                ${successMessage}
            </v-alert>
            <v-alert <c:if test='${errorMessage != null}'>value="true"</c:if> type="error">
                ${errorMessage}
            </v-alert>
            
          <form id="create-order" method="post" action="order">  
          <v-stepper-items>
          <!-- File select -->
            <v-stepper-content step="1">
              <v-card class="mb-5" height="400px" flat>
                <v-container fluid grid-list-md>
                  <v-layout row wrap>
                    <v-flex xs6 sm6 md4 lg8>
                      <v-card flat>
                        <div id="stl_cont" @change="showInfo">
                          <h2>Select an STL file</h2>
                          <input type="file" onchange='stl_viewer.add_model({local_file:this.files[0]}); ' accept="*.*">
                            <p>Or Drag and drop</p>
                        </div>

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
                                  <v-list-tile-content class="align-end">{{ fileInfo.volume }} mm&#179</v-list-tile-content>
                               </v-list-tile>
                               <v-list-tile>
                                  <v-list-tile-content>Area: </v-list-tile-content>
                                  <v-list-tile-content class="align-end">{{ fileInfo.area }} mm&#178</v-list-tile-content>
                               </v-list-tile>
                               <v-list-tile>
                                    <v-list-tile-content>Triangles: </v-list-tile-content>
                                    <v-list-tile-content class="align-end">{{ fileInfo.triangles }}</v-list-tile-content>
                                </v-list-tile>

                                </v-list>
<!--                            <v-btn @click="viewInfo">Get File Info</v-btn>-->
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
                            <v-card class="mb-5" height="400px" flat>
                                <v-container>
                                    <v-layout row wrap fluid>                                              
                                        <v-flex v-for="printer in printers" xs8 sm4 md4 lg4>
                                    <!-- Printer Cards -->
                                    <v-hover>
                                            <v-card id="printerCard" @click="selectPrinter(printer)" onmouseover="" 
                                                    style="cursor: pointer;" width="344"
                                                    slot-scope="{ hover }"
                                                    :class="`elevation-${hover ? 24 : 2}`"
                                                    class="mx-auto"
                                                > 
                                                <v-img :src="printer.img" aspect-ratio="2.5" contain></v-img>
                                                <v-card-title secondary-title>
                                                    <h3 class="headline mb-0">{{printer.name}}</h3>
                                                    <div>{{printer.description}}</div>
                                                </v-card-title>
                                                <span><h4>Run Cost:</h4> $</span>{{ printer.runCost }}<span>/h</span>
                                                <v-card-actions>
<!--                                                 <input type="hidden" name="action" value="selectPrinter">-->
                                                    <input type="hidden" name="printerID" v-model="printerSelect.printerID">
<!--                                                 <v-btn dark color="#8B2635" @click="selectPrinter(printer)">Select</v-btn>-->
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
                            <v-btn color="secondary" @click="el = 1">Back</v-btn>
                        </v-stepper-content>
                                                    
                                                    
        <!-- Select material based on selected printer -->
                        <v-stepper-content step="3">
                            <v-card class="mb-5" height="400px" flat>
                                <v-containter>
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
                                        
                                <!-- Color select menu option v-for="material in materials" :key="material" -->        
                                        <v-flex>
<!--                                            <v-toolbar dark flat class="headline blue-grey darken-4 white--text">
                                                <v-toolbar-title>Choose a Color</v-toolbar-title>
                                            </v-toolbar>
                                            <v-expansion-panel focusable>
                                                <v-expansion-panel-content min-width="400"
                                                  v-for="material in materials" v-if="material.materialId===selectMaterialId">
                                                  <template v-slot:header>
                                                    <div>{{ selectMaterialId.colour }}</div>
                                                  </template>
                                                  <v-card min-width="400"  v-for="colour in colours">
                                                    <v-card-text class="grey lighten-3">{{ colours.colour }}</v-card-text>
                                                    <v-card-text class="grey lighten-3">{{ colours.status }}
                                                        <v-btn class="align-end">Select</v-btn>
                                                    </v-card-text>
                                                  </v-card>
                                                </v-expansion-panel-content>
                                            </v-expansion-panel>-->

                                         <v-toolbar dark flat class="headline blue-grey darken-4 white--text">
                                                <v-toolbar-title>Material Options</v-toolbar-title>
                                            </v-toolbar>   
                                        <v-data-table :headers="colourHeaders" :items="colours" item-key="colour" hide-actions class="elevation-2">
                                            <template slot="items" slot-scope="props">
                                                <tr v-if="props.item.materialId===selectMaterialId">
                                                    <td>{{ props.item.colour }}</td>
                                                    <td>{{ props.item.status }}</td>
                                                </tr>
                                            </template>
                                        </v-data-table>
                                    

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
                            <v-card class="mb-5" height="400px" flat>
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
                            <v-btn color="secondary" @click="el = 3">Back</v-btn>
                        </v-stepper-content>
        
        
                <!-- confirm choices -->
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
                                                <v-list-tile-content class="align-end">{{ fileInfo.name }}</v-list-tile-content>
                                            </v-list-tile>
                                            <v-list-tile>
                                                <v-list-tile-content>Volume: </v-list-tile-content>
                                                <v-list-tile-content class="align-end">{{ fileInfo.volume }} mm&#179</v-list-tile-content>
                                            </v-list-tile>
                                            <v-list-tile>
                                                <v-list-tile-content>Triangles: </v-list-tile-content>
                                                <v-list-tile-content class="align-end">{{ fileInfo.triangles }}</v-list-tile-content>
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
                                                        <v-list-tile-content>Selected Material: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ selectedMaterial.materialName }}</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Material Cost: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">$ {{ selectedMaterial.materialVal }}/ mm&#179</v-list-tile-content>
                                                    </v-list-tile>
                                                    <v-list-tile>
                                                        <v-list-tile-content>Material Type: </v-list-tile-content>
                                                        <v-list-tile-content class="align-end">{{ selectedMaterial.materialColor }}</v-list-tile-content>
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
                            
<!--        /**
         * order submit needs to take in:
         *          fileName,
         *          printerID,
         *          materialID,
         *          selectedColour,
         *          comments,    
         */-->
<!--                    <input type="hidden" v-model="fileInfo.name" name="fileName" value="">
                    <input type="hidden" v-model="selectedPrinter.printerId" name="printerId" value="">
                    <input type="hidden" v-model="selectedMaterial.materialId" name="materialId" value="">
                    <input type="hidden" v-model="selectedMaterial.materialId" name="materialId" value="">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" id="newMaterialDescription" name="materialDescription" value="">-->
                    <v-btn dark color="#8B2635" @click="submit">
                        Submit
                    </v-btn>
                    <v-btn color="secondary" @click="e1 = 1">Make Changes</v-btn>
                </v-stepper-content>
          </form>
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
        maxWidth: 4000,
        image: '',
        fileInfo: '',
        comments: '',
        selectPrinterName: '',
        selectMaterialId: '',
        
        selectedColourID: 0,
        
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
            printerId: '',
            size: '',
            status: '',
            description: '',
            runCost: '',
            name: '',
            materials: '',
            img: ''
            
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
        selectedMaterial:
        {
            materialId: '',
            materialName: '',
            printerName: '', 
            materialDesc: '', 
            materialColor: '', 
            materialVal: '', 
            materialStat: ''
        },
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
        colourHeaders:
        [
            {text: 'Colour', value: 'colour'},
            {text: 'Status', value: 'status'},
        ],
        payments:
        [
            {text: 'Payments are currently unavailable', value: 'noPayment'}
        ],
    },
    methods: {
        submit() {
//            document.getElementById('create-order').submit();
            alert('We did it!')
        },
        selectPrinter(printer) {
            this.selectPrinterName = printer.name;
            alert('you selected ' + this.selectPrinterName);
            
            this.selectedPrinter = Object.assign({}, printer);
            return selectedPrinter;
        },
        selectMaterial(material) {
            this.selectMaterialId = material.materialId;
            this.selectedMaterial = Object.assign({}, material);
            alert('you selected ' + this.selectMaterialId);
            
            return selectedMaterial;
        },
        loadMaterialColour(material) {
            this.selectMaterialId = material.materialId;
        },
        selectPayment() {
        // Display payment info
        },
        viewInfo() {
                var info = JSON.stringify(stl_viewer.get_model_info(2));
                var obj = JSON.parse(info);
                this.fileInfo = obj
            },
            showInfo() {
                setTimeout(this.viewInfo, 3000)
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