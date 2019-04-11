<%-- 
    Document   : techHome
    Created on : Jan 18, 2019, 10:36:32 AM
    Author     : 756852
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports></ARIS3D:Imports>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ARIS3D - Dashboard</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="true" pageName="Dashboard"></ARIS3D:Header>
                <v-content>
                    <v-container>
                        <h1 class="display-2 font-weight-thin text-xs-center">Welcome back, ${sessionScope.account.firstname}!</h1>
                        <v-divider></v-divider>
                        <br>
                    <!-- Graph cards -->
                        <v-layout fluid row wrap>
                            <template width="100%">
                                <v-card class="mt-3 mx-auto" width="32%">
                                    <v-sheet class="v-sheet--offset mx-auto" color="purple lighten-3" elevation="12" max-width="calc(100% - 32px)">
                                        <v-sparkline :labels="printerLabels" :value="value1" color="white" line-width="2" padding="16" auto-draw smooth></v-sparkline>
                                    </v-sheet>
                                    <v-card-text class="pt-0">
                                        <div class="title font-weight-light mb-2">Printer Queues</div>
                                        <div class="subheading font-weight-light grey--text">Pending orders for printers.</div>
                                        <v-divider class="my-2"></v-divider>
                                        <v-icon class="mr-2" small>today</v-icon>
                                        <span class="caption grey--text font-weight-light">last order was completed on April 04, 2019.</span>
                                    </v-card-text>
                                </v-card>
                            </template>
                            <template width="100%">
                                <v-card class="mt-3 mx-auto" width="32%">
                                    <v-sheet class="v-sheet--offset mx-auto" color="red lighten-3" elevation="12" max-width="calc(100% - 32px)">
                                        <v-sparkline :labels="labels" :value="value2" color="white" line-width="2" padding="16" auto-draw smooth></v-sparkline>
                                    </v-sheet>
                                    <v-card-text class="pt-0">
                                        <div class="title font-weight-light mb-2">Order Submissions</div>
                                        <div class="subheading font-weight-light grey--text">Order submissions for current month.</div>
                                        <v-divider class="my-2"></v-divider>
                                        <v-icon class="mr-2" small>today</v-icon>
                                        <span class="caption grey--text font-weight-light">last order was submitted on April 03, 2019.</span>
                                    </v-card-text>
                                </v-card>
                            </template>
                            <template width="100%">
                                <v-card class="mt-3 mx-auto" width="32%">
                                    <v-sheet class="v-sheet--offset mx-auto" color="teal lighten-3" elevation="12" max-width="calc(100% - 32px)">
                                        <v-sparkline :labels="labels" :value="value3" color="white" line-width="2" padding="16" auto-draw smooth></v-sparkline>
                                    </v-sheet>
                                    <v-card-text class="pt-0">
                                        <div class="title font-weight-light mb-2">Sales</div>
                                        <div class="subheading font-weight-light grey--text">Sales for current month.</div>
                                        <v-divider class="my-2"></v-divider>
                                        <v-icon class="mr-2" small>today</v-icon>
                                        <span class="caption grey--text font-weight-light">last order was completed on April 04, 2019.</span>
                                    </v-card-text>
                                </v-card>
                            </template>
                        </v-layout>
                    
                    <!-- Pending order queue starts here -->
                        <v-container grid-list-lg>
                            <!--Alert Messages-->
                            <v-alert <c:if test='${successMessage != null}'>value="true"</c:if> type="success">
                                ${successMessage}
                            </v-alert>
                            <v-alert <c:if test='${errorMessage != null}'>value="true"</c:if> type="error">
                                ${errorMessage}
                            </v-alert>
                            <!--Show message if there are no orders in any queue-->
                            <h1 class="headline font-weight-light text-xs-center">Orders Pending Approval</h1>
                            <v-divider></v-divider>
                            <c:if test="${orders[0] == null}">
                                <h1 class="subheading font-weight-light text-xs-center">No orders are pending approval.</h1>
                            </c:if>
                            <v-layout row wrap>
                                <v-flex v-for="order in orders" xs12 sm12 md6 lg4 xl2 :key="order.orderId">
                                    <v-card color="#8B2635" height="5px"></v-card>
                                    <v-card elevation="5" min-height="150">
                                        <v-card-title class="blue-grey darken-4 white--text">
                                            <span primary class="headline text-xs-left">Order {{num}}{{order.orderId}}</span>
                                        </v-card-title>
                                        <br/>
                                        
                                    <!-- Order Queue cards -->    
                                        <v-card-text class="pt-0">
                                            <v-alert <c:if test='${warningMessage != null}'>value="true"</c:if> type="warning">
                                                ${warningMessage}
                                            </v-alert>
                                            <span class="subheading font-weight-light"><v-icon>portrait</v-icon> {{order.firstname}} {{order.lastname}}</span><br/>
                                            <span class="subheading font-weight-light"><v-icon>email</v-icon> {{order.email}}</span><br/>
                                            <span class="subheading font-weight-light"><v-icon>print</v-icon> {{order.printerName}}</span><br/>
                                            <span class="subheading font-weight-light"><v-icon>texture</v-icon>{{order.material}} - {{order.materialColour}}</span><br/>
                                            <span class="subheading font-weight-light"><v-icon>attach_money</v-icon>{{order.cost}}</span>
                                        </v-card-text>
                                        <v-divider></v-divider>

                                        <v-card-actions>
                                            <form method="post" action="dashboard">
                                                <input type="hidden" name="path" :value="order.filePath">
                                                <input type="hidden" name="action" value="download">
                                                <v-btn type="submit" flat color="orange darken-2">Download</v-btn>
                                            </form>
                                            <v-spacer></v-spacer>
                                            <v-divider vertical></v-divider>
                                            <v-spacer></v-spacer>
                                            
                                        <!-- Review order pop up -->    
                                            <v-dialog v-model="dialog" max-width="750px" >
                                                <v-btn @click="reviewOrder(order)" slot="activator" flat color="light-green darken-2">Review</v-btn>
                                                <v-card color="#8B2635" height="5px"></v-card>
                                                <v-card>
                                                    <v-card-title class="blue-grey darken-4 white--text">
                                                        <span class="headline">Review Order: {{num}}{{viewOrder.orderId}}</span>
                                                    </v-card-title>
                                                    <v-container>
                                                        <v-layout>
                                                            <v-flex>
                                                                <v-card-text><b>File Submitted:</b> {{viewOrder.fileName}}</v-card-text>
                                                                <v-card-text><b>File Dimensions:</b> {{viewOrder.dimensions}}mm</v-card-text>
                                                                <v-card-text><b>Clients Comment:</b> {{viewOrder.comments}}</v-card-text>
                                                                <v-card-text><b>Estimated Cost:</b> $ {{viewOrder.cost}}</v-card-text>
                                                                <v-flex lg6>
                                                                    <v-text-field id="actualCost" type="number" v-model="viewOrder.cost" name="cost" label="Actual Cost" prepend-icon="$" append-icon=""></v-text-field>
                                                                </v-flex>
                                                                <br><br><br><br>
                                                                <v-card-actions>
                                                                    <form method="post" action="dashboard">
                                                                        <input type="hidden" name="path" :value="viewOrder.filePath">
                                                                        <input type="hidden" name="action" value="download">
                                                                        <v-btn type="submit" flat color="orange darken-2">Download File</v-btn>
                                                                    </form>
                                                                </v-card-actions>
                                                            </v-flex>
                                                        
                                                        <!-- approve an order and submit to printer Queue -->
                                                            <v-flex>
                                                                <form id="approveOrder" method="post" action="dashboard">
                                                                    <v-card-text><b>Selected Printer:</b> {{viewOrder.printerName}}</v-card-text>
                                                                    <v-card-text><b>Printer Dimensions:</b> {{viewOrder.printerDimensions}}</v-card-text>
                                                                    <v-card-text><b>Material Selected:</b> {{viewOrder.material}}</v-card-text>
                                                                    <v-card-text><b>Material Type:</b> {{viewOrder.materialColour}}</v-card-text>
                                                                    <v-textarea id="comments" name="comments" v-model="viewOrder.techComments" solo label="Message to Client"></v-textarea>
                                                                    <input id="actionRevision" type="hidden" name="action" value="approve">
                                                                    <input type="hidden" name="orderId" :value="viewOrder.orderId">
                                                                    <input id="approveOrderCost" type="hidden" name="cost" value="">
                                                                    <v-card-actions>
                                                                        <v-spacer></v-spacer>
                                                                        <v-btn flat color="light-green darken-2" @click="approve">Approve</v-btn>
                                                                        <v-btn flat color="red accent-3" @click="revision">Needs Revision</v-btn>
                                                                    </v-card-actions>
                                                                </form>
                                                            </v-flex>
                                                        </v-layout>
                                                    </v-container>
                                                    <v-spacer></v-spacer>
                                                </v-card>
                                            </v-dialog>
                                        </v-card-actions>
                                    </v-card>
                                </v-flex>
                            </v-layout>
                        </v-container>
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
                    dialog: false,
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
                    viewOrder:
                    {
                        orderId: 0,
                        position: 0,
                        cost: 0,
                        email: '',
                        firstname: '',
                        lastname: '',
                        printerId: '',
                        printerName: '',
                        material: '',
                        materialColour: '',
                        comments: '',
                        techComments: '',
                        fileName: '',
                        dimensions: '',
                        filePath: ''
                    },
                    orders:
                    [
                        <c:forEach items="${orders}" var="order">
                            {orderId: ${order.orderId}, 
                             position: ${order.position}, 
                             cost: ${order.cost}, 
                             printerDimensions: '${order.printerDimensions}', 
                             dimensions: '${order.fileDimensions}', 
                             filePath: '${order.filePath}', 
                             fileName: '${order.fileName}', 
                             email: '${order.email}', 
                             firstname: '${order.firstname}', 
                             lastname: '${order.lastname}', 
                             printerId: '${order.printerId}', 
                             printerName: '${order.printerName}', 
                             material: '${order.materialName}', 
                             materialColour: '${order.materialColour}', 
                             comments: '${order.comments}'},
                        </c:forEach>
                    ],
                    num: '#',
                    labels: ['1','3','6','9','12','15','18','21','24','27','30'],
                    printerLabels: ['Fortus','Ultimaker 3 Extended','Form 2+'],
                    value1: [6,12,9],
                    value2: [0,2,0,1,0,4,0,1,0,2,4],
                    value3: [0.25,0,0.5,12,0,4,6,1,0.5,0,5]
                },
                methods:
                {
                    approve()
                    {
                        document.getElementById('approveOrderCost').value = document.getElementById('actualCost').value;
                        document.getElementById('approveOrder').submit();
                    },
                    revision()
                    {
                        document.getElementById('actionRevision').value = 'cancel';
                        document.getElementById('approveOrder').submit();
                    },
                    reviewOrder(order)
                    {
                        this.viewOrder = Object.assign({}, order);
                    }
                }
            });
        </script>
        <style>
            .v-sheet--offset {
              top: -24px;
              position: relative;
            }
        </style>
    </body>
</html>
