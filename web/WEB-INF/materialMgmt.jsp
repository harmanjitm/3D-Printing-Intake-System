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
                <br><br>
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
                            <v-dialog v-model="dialog" max-width="750px" >
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
                                                        <v-text-field name="materialDesc" label="Description"></v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md6>
                                                        <v-text-field name="materialColor" label="Colors"></v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md6>
                                                        <v-text-field name="materialVal" label="Value"></v-text-field>
                                                    </v-flex>
                                                    <v-flex xs12 sm6 md6>
                                                        <v-text-field name="materialStat" label="Status"></v-text-field>
                                                    </v-flex>
                                                    <input type="hidden" name="action" value="add">
                                                </v-layout>
                                            </v-container>
                                        </v-card-text>
                                        <v-card-actions>
                                            <v-spacer></v-spacer>
                                            <v-btn flat color="primary" @click="close">Cancel</v-btn>
                                            <v-btn flat color="primary" @click="submit">Save</v-btn>
                                        </v-card-actions>
                                    </form>
                                </v-card>
                            </v-dialog>
                        </v-toolbar>
                        <v-data-table hide-actions expand item-key="materialName" class="elevation-3" :headers="materialheaders" :items="materials" :search="search">
                            <template slot="items" slot-scope="props">
                                <tr @click="props.expanded = !props.expanded">
                                    <td>{{ props.item.materialName }}</td>
                                    <td>{{ props.item.printerName }}</td>
                                    <td>{{ props.item.materialDesc }}</td>
                                    <td>{{ props.item.materialVal }}</td>
                                    <td class="justify-center">
                                    <!-- dialog window for editing an existing material -->
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
                                                            <input type="hidden" name="materialID" v-model="editItem.accountID">
                                                            <v-flex xs12 sm6 md6>
                                                                <v-text-field name="materialName" v-model="editItem.materialName" label="Material"></v-text-field>
                                                            </v-flex>
                                                            <v-flex xs12 sm6 md6>
                                                                <v-text-field name="printerName" v-model="editItem.printerName" label="Printer"></v-text-field>
                                                            </v-flex>
                                                            <v-flex xs12 sm6 md6>
                                                                <v-text-field name="materialDesc" v-model="editItem.materialDesc" label="Description"></v-text-field>
                                                            </v-flex>
                                                            <v-spacer></v-spacer>
                                                            <v-flex xs12 sm6 md6>
                                                            <v-text-field name="materialColor" v-model="editItem.materialColor" :items="materialColorDropdown" label="Colors"></v-text-field>
                                                            </v-flex>
                                                            <v-flex xs12 sm6 md6>
                                                                <v-text-field name="materialVal" v-model="editItem.materialVal" label="Value"></v-text-field>
                                                            </v-flex>
                                                            <v-flex xs12 sm6 md6>
                                                                <v-select v-model="editItem.materialStat" :items="materialStatusDropdown" item-text="type" item-value="value" label="Material Status" id="materialStat" name="materialStat"></v-select>
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
                            <template slot="expand" scope="props">
                                <v-card>
                                    <v-card-text>
                                        <v-data-table :headers="colourHeaders" :items="materials.colours" item-key="colour" hide-actions class="elevation-3">
                                            <template slot="colours" scope="props">
                                                <td>{{ props.item.colour }}</td>
                                                <td>{{ props.item.status }}</td>
                                            </template>
                                        </v-data-table>
                                    </v-card-text>
                                </v-card>
                            </template>
                        </v-data-table>
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
                    search: '',
                    editIndex: -1,
                    dialog: false,
                    editDialog: false,
                    account: '',
                    logout: '',
                    drawer: false,
                    materials:
                    [
                        <c:forEach items="${materials}" var="material">
                            {materialName: '${material.name}',
                             printerName: '${material.printerName}', 
                             materialDesc: '${material.description}', 
                             materialColor: '${material.colours}', 
                             materialVal: '${material.cost}', 
                             materialStat: '${material.status}',
                             colours: 
                             [
                                <c:forEach items="${material.colours}" var="colour">
                                        {colour: '${colour.color}', status: '${colour.status}'},
                                </c:forEach>
                             ]},
                        </c:forEach>
                    ],
                    colourHeaders:
                    [
                        {text: 'Colour', value: 'colour'},
                        {text: 'Status', value: 'status'}
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
                        materialName: '',
                        materialDesc: '',
                        materialColor: '',
                        materialVal: '',
                        materialStat: ''
                    },
                    adminItems: 
                    [ 
                        {title: 'Home', icon: 'home', link: 'techhome'},
                        {title: 'Dashboard', icon: 'dashboard', link: 'dashboard'},
                        {title: 'Order Queue', icon: 'queue', link: 'queue'},
                        {title: 'Account Management', icon: 'people', link: 'accountmanagement'},
                        {title: 'Material Management', icon: 'texture', link: 'materialmanagement'},
                        {title: 'Printer Management', icon: 'print', link: 'printermanagement'},
                        {title: 'Reports', icon: 'poll', link: 'reportmanagement'}
                    ],
                    materialheaders:
                    [
                        {text: 'Material', value: 'materialName'},
                        {text: 'Printer', value: 'printerName'},
                        {text: 'Description', value: 'materialDesc'},
                        {text: 'Value', value: 'materialVal'},
                        {text: 'Actions', value: 'actions', sortable: false}
                    ]
                },
                methods:
                {
                    close()
                    {
                        this.dialog = false,
                        this.editDialog = false
                    },
                    submit()
                    {
                        document.getElementById('create-account').submit();
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
                        document.getElementById('materialStat').value=this.editItem.status;
                        document.getElementById('edit-material').submit();
                    },
                    remove()
                    {
                        alert('eyyy')
                    },
                    editMaterial(item)
                    {
                        this.editIndex = this.materials.indexOf(item)
                        this.editItem = Object.assign({}, item)
                        this.editDialog = false
                    }
                }
            });
        </script>
    </body>
</html>
