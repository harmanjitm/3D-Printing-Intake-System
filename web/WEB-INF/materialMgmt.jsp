<%-- 
    Document   : materialMgmt
    Created on : Jan 18, 2019, 10:37:11 AM
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
        <title>ARIS3D - Material Management</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="true" pageName="Material Management"></ARIS3D:Header>
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
                            <v-toolbar-title>Manage Materials</v-toolbar-title>
                            <v-spacer></v-spacer>
                            <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
                    <!-- dialog window for adding a new material -->
                            <v-dialog persistent v-model="dialog" max-width="750px" >
                                <v-btn slot="activator" color="#8B2635" dark class="mb-2">New Material</v-btn>
                                <v-card>
                                    <v-card-title>
                                        <span class="headline">New Material</span>
                                    </v-card-title>
                                    
                                    <form id="create-material" method="post" action="materialmanagement">
                                        <v-card-text>
                                            <v-container grid-list-md>
                                                <v-layout wrap>
                                                        <v-flex xs12 sm6 md6>
                                                            <v-text-field name="materialName" label="Material"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12 sm6 md6>
                                                            <v-text-field type="number" name="materialCost" prefix="$" label="Price/mm&#179"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12 sm6 md6>
                                                            <v-select v-model="selectPrinterName" :items="printers" label="Printer" item-text="type" item-value="value"> 
                                                            </v-select>
                                                        </v-flex>
                                                        <input type="hidden" name="action" value="add">
                                                        <input type="hidden" name="printerName" :value="selectPrinterName">
                                                        <v-flex xs12 sm6 md6>
                                                            <v-text-field name="materialDescription" label="Description"></v-text-field>
                                                        </v-flex>
                                                </v-layout>
                                            </v-container>
                                        </v-card-text>
                                        <v-card-actions>
                                            <v-spacer></v-spacer>
                                            <v-btn flat color="primary" @click="close">Cancel</v-btn>
                                            <v-btn flat color="primary" @click="submitMaterial">Save</v-btn>
                                        </v-card-actions>
                                    </form>
                                </v-card>
                            </v-dialog>
                        </v-toolbar>
                        
                        
                        <v-data-table expand item-key="materialName" :expand="expand" class="elevation-3" :headers="materialheaders" :items="materials" :search="search">
                            <template slot="items" slot-scope="props">
                                <tr @click="loadMaterialColour(props.item); props.expanded = !props.expanded">
                                    <td>{{ props.item.materialName }}</td>
                                    <td>{{ props.item.printerName }}</td>
                                    <td>{{ props.item.materialDesc }}</td>
                                    <td>{{ props.item.materialVal }}</td>
                                    <td class="justify-center">
                                    
                                <!-- dialog window for editing an existing material -->
                                    <v-dialog v-model="addColourDialog" max-width="600px">
                                        <v-icon small slot="activator" @click="editMaterial(props.item)">invert_colors</v-icon>
                                        <v-card color="#8B2635" height="5px"></v-card>
                                        <v-card>
                                            <v-card-title class="headline blue-grey darken-4 white--text" primary-title>
                                                <span class="headline">New Colour for {{editItem.materialName}} in {{editItem.printerName}}</span>
                                            </v-card-title>
                                            <form id="addMaterialColour" method="post" action="materialmanagement">
                                                <v-card-text>
                                                    <v-container grid-list-md>
                                                        <v-layout wrap>
                                                            <v-flex xs12>
                                                                <input type="hidden" name="action" value="addColour">
                                                                <v-text-field id="colourName" name="colourName" v-model="newColourName" label="Colour Name"></v-text-field>
                                                            </v-flex>
                                                            <v-flex xs4>
                                                                <input id="newColourMaterialId" type="hidden" name="materialId" value="editItem.materialId" v-model="editItem.materialId">
                                                            </v-flex>
                                                        </v-layout>
                                                    </v-container>
                                                </v-card-text>
                                                <v-card-actions>
                                                    <v-spacer></v-spacer>
                                                    <v-btn flat color="primary" @click="close">Cancel</v-btn>
                                                    <v-btn flat color="primary" @click="addMaterialColour">Save</v-btn>
                                                </v-card-actions>
                                            </form>
                                        </v-card>
                                    </v-dialog>
                                    <v-dialog v-model="editDialog" max-width="750px" v-show="editMaterial">
                                        <v-icon small slot="activator" @click="editMaterial(props.item)">edit</v-icon>
                                        <v-card>
                                            <v-card-title>
                                                <span class="headline">Edit Material</span>
                                            </v-card-title>
                                            <form id="edit-material" method="post" action="materialmanagement">
                                                <v-card-text>
                                                    <v-container grid-list-md>
                                                        <v-layout wrap>
                                                            <input type="hidden" name="action" value="edit">
                                                            <input type="hidden" name="materialId" v-model="editItem.materialId">
                                                            <v-flex xs12 sm4 md4>
                                                                <v-text-field name="materialName" v-model="editItem.materialName" label="Material"></v-text-field>
                                                            </v-flex>
                                                            <v-flex xs12 sm8 md8>
                                                                <v-text-field name="materialDescription" v-model="editItem.materialDesc" label="Description"></v-text-field>
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
                                    <v-icon small @click="remove">delete</v-icon>
                                    </td>
                                </tr>
                            </template>
                            <template v-slot:expand="props">
                                <v-card>
                                    <v-card-text>
                                        <v-data-table :headers="colourHeaders" :items="colours" item-key="colour" hide-actions class="elevation-3">
                                            <template slot="items" slot-scope="props">
                                                <tr v-if="props.item.materialId===selectedColourID">
                                                    <td>{{props.item.colour}}</td>
                                                    <td>
                                                        <v-switch @change="changeColourStatus(props.item)" v-if="props.item.status === 'in-stock'" v-model="this.colourStatus = true" color="red"></v-switch>
                                                        <v-switch @change="changeColourStatus(props.item)" v-if="props.item.status === 'out-of-stock'" v-model="this.colourStatus = false" color="red"></v-switch>
                                                    </td>
                                                    <td class="justify-center"><v-icon small @click="deleteColour(props.item)">delete</v-icon></td>
                                                </tr>
                                            </template>
                                        </v-data-table>
                                    </v-card-text>
                                </v-card>
                            </template>
                        </v-data-table>
                        <form method="post" id="addMaterial" action="materialmanagement">
                            <input type="hidden" id="newMaterialName" name="materialName" value="">
                            <input type="hidden" id="newMaterialPrice" name="materialPrice" value="">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" id="newMaterialDescription" name="materialDescription" value="">
                        </form>
                        <form method="post" id="changeColourStatus" action="materialmanagement">
                            <input type="hidden" id="colourStatusName" name="colourName">
                            <input type="hidden" id="colourStatusMaterialId" name="materialId">
                            <input type="hidden" name="action" value="changeColourStatus">
                        </form>
                        <form method="post" action="materialmanagement" id="deleteMaterial">
                            <input type="hidden" id="materialIdToDelete" name="materialId">
                            <input type="hidden" name="action" value="delete">
                        </form>
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
                    selectedColourID: 0,
                    colourData: [],
                    search: '',
                    editIndex: -1,
                    selectPrinterName: '',
                    newColourName: '',
                    dialog: false,
                    expand: false,
                    editDialog: false,
                    addColourDialog: false,
                    account: '',
                    logout: '',
                    drawer: false,
                    colourStatus: true,
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
                    printers: //Available printers
                    [
                        {type: 'Fortus 400mc', value: 'Fortus 400mc', name: 'printer'},
                        {type: 'Form 2+', value: 'Form 2+', name: 'printer'},
                        {type: 'Ultimaker 3 Extended', value: 'Ultimaker 3 Extended', name: 'printer'}
                    ],
                    colourHeaders:
                    [
                        {text: 'Colour', value: 'colour'},
                        {text: 'Status', value: 'status'},
                        {text: 'Actions', value: 'delete', sortable: false}
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
                    materialColorDropdown:
                    [
                        
                    ],
                    materialStatusDropdown:
                    [
                        {type: 'instock', value: 'instock', name: 'materialStat'},
                        {type: 'outstock', value: 'outstock', name: 'materialStat'}
                    ],
                    editItem: 
                    {
                        materialId: '',
                        materialName: '',
                        materialDesc: '',
                        materialColor: '',
                        materialVal: '',
                        materialStat: '',
                        printerName: ''
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
                    materialheaders:
                    [
                        {text: 'Material', value: 'materialName'},
                        {text: 'Printer', value: 'printerName'},
                        {text: 'Description', value: 'materialDesc'},
                        {text: 'Value($/mm3)', value: 'materialVal'},
                        {text: 'Actions', value: 'actions', sortable: false}
                    ]
                },
                methods:
                {
                    changeColourStatus(colour)
                    {
                        document.getElementById('colourStatusName').value = colour.colour;
                        document.getElementById('colourStatusMaterialId').value = colour.materialId;
                        document.getElementById('changeColourStatus').submit();
                    },
                    addMaterialColour()
                    {
                        document.getElementById('newColourMaterialId').value = this.editItem.materialId;
                        document.getElementById('addMaterialColour').submit();
                    },
                    deleteColour(item)
                    {
                        document.getElementById('').value = this.selectedMaterial.materialId;//TODO
                    },
                    loadMaterialColour(material)
                    {  
                        this.selectedColourID = material.materialId;
                        <%--<c:set var="selectedMaterial" value="this.selectedColourID"></c:set>--%>
                        
                        

                    },
                    close()
                    {
                        this.dialog = false,
                        this.editDialog = false,
                        this.addColourDialog = false
                    },
                    submitMaterial()
                    {
                        document.getElementById('create-material').submit();
                    },
                    editMaterial() 
                    {
                        materialId = this.materialId,
                        materialName = this.materialName,
                        materialDesc = this.materailDesc,
                        materialColor = this.materialColor,
                        materialVal = this.materialValue,
                        materailStatus = this.materialStatus
                    },
                    edit()
                    {
                        document.getElementById('edit-material').submit();
                    },
                    remove()
                    {
                        alert('Please try again later!');
                    },
                    editMaterial(item)
                    {
                        this.editIndex = this.materials.indexOf(item);
                        this.editItem = Object.assign({}, item);
                    }
                }
            });
        </script>
    </body>
</html>
