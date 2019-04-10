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
                        <v-layout fluid row wrap>
                            <template width="100%">
                                <v-card class="mt-3 mx-auto" width="32%">
                                    <v-sheet class="v-sheet--offset mx-auto" color="purple lighten-3" elevation="12" max-width="calc(100% - 32px)">
                                        <v-sparkline :labels="labels" :value="value" color="white" line-width="2" padding="16" auto-draw smooth></v-sparkline>
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
                                        <v-sparkline :labels="labels" :value="value" color="white" line-width="2" padding="16" auto-draw smooth></v-sparkline>
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
                                        <v-sparkline :labels="labels" :value="value" color="white" line-width="2" padding="16" auto-draw smooth></v-sparkline>
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
                        <v-container grid-list-lg>
                            
                            <h1 class="headline font-weight-light text-xs-center">Orders Pending Approval</h1>
                            <v-divider></v-divider>
                            <c:if test="${orders[0] == null}">
                                <h1 class="subheading font-weight-light text-xs-center">No orders are pending approval.</h1>
                            </c:if>
                            <v-alert <c:if test='${successMessage != null}'>value="true"</c:if> type="success">
                                ${successMessage}
                            </v-alert>
                            <v-alert <c:if test='${errorMessage != null}'>value="true"</c:if> type="error">
                                ${errorMessage}
                            </v-alert>
                            <v-layout row wrap>
                                <v-flex v-for="order in orders" xs12 sm12 md6 lg4 xl2 :key="order.orderId">
                                    <v-card color="#8B2635" height="5px"></v-card>
                                    <v-card elevation="5" min-height="150">
                                        <v-card-title class="blue-grey darken-4 white--text">
                                            <span primary class="headline text-xs-left">Order {{num}}{{order.orderId}}</span>
                                            
                                        </v-card-title>
                                        <br/>
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
                                        <!--<v-spacer vertical></v-spacer>-->
                                        <v-card-actions>
                                            <form action="dashboard" method="post">
                                                <input type="hidden" name="action" value="cancel">
                                                <input type="hidden" name="orderId" :value="order.orderId">
                                                <v-btn type="submit" flat color="red accent-3">Cancel</v-btn>
                                            </form>
                                            <v-spacer></v-spacer>
                                            <v-divider vertical></v-divider>
                                            <v-spacer></v-spacer>
                                            <form method="post" action="dashboard">
                                                <input type="hidden" name="path" :value="order.filePath">
                                                <input type="hidden" name="action" value="download">
                                                <v-btn type="submit" flat color="orange darken-2">Download</v-btn>
                                            </form>
                                            <v-spacer></v-spacer>
                                            <v-divider vertical></v-divider>
                                            <v-spacer></v-spacer>
                                            <v-dialog v-model="dialog" max-width="750px" >
                                                <v-btn @click="reviewOrder(order)" slot="activator" flat color="light-green darken-2">Review</v-btn>
                                                <v-card color="#8B2635" height="5px"></v-card>
                                                <v-card>
                                                    <v-card-title class="blue-grey darken-4 white--text">
                                                        <span class="headline">Review Order: {{num}}{{viewOrder.orderId}}</span>
                                                    </v-card-title>
                                                    <!--<form action="dashboard" method="post">-->
                                                    <v-container>
                                                        <v-layout>
                                                            <v-flex>
                                                                <v-card-text><b>File Submitted:</b> {{viewOrder.fileName}}</v-card-text>
                                                                <v-card-text><b>File Dimensions:</b> {{viewOrder.dimensions}}mm</v-card-text>
                                                                <v-card-text><b>Clients Comment:</b> {{viewOrder.comments}}</v-card-text>
                                                                <v-card-text><b>Estimated Cost:</b> $ {{viewOrder.cost}}.00</v-card-text>
                                                                <v-flex lg6>
                                                                    <v-text-field id="actualCost" type="number" value="234" name="cost" label="Actual Cost" prepend-icon="$" append-icon=""></v-text-field>
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
                                                            <v-flex>
                                                                <form id="approveOrder" method="post" action="dashboard">
                                                                    <v-card-text><b>Selected Printer:</b> {{viewOrder.printerName}}</v-card-text>
                                                                    <v-card-text><b>Printer Dimensions:</b> {{viewOrder.printerDimensions}}</v-card-text>
                                                                    <v-card-text><b>Material Selected:</b> {{viewOrder.material}}</v-card-text>
                                                                    <v-card-text><b>Material Type:</b> {{viewOrder.materialColour}}</v-card-text>
                                                                    <v-textarea id="comments" name="comments" solo label="Message to Client"></v-textarea>
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
    <!--                            <v-dialog v-model="dialog" max-width="1000px" persistent>
                                    <v-card color="#8B2635" height="5px"></v-card>
                                    <v-card min-height="500px">
                                        <v-card-title class="headline blue-grey darken-4 white--text" primary-title>{{viewOrder.printerName}}<v-spacer></v-spacer>Order {{num}}{{viewOrder.orderId}}<v-spacer></v-spacer>Position {{num}}{{viewOrder.position}}</v-card-title>
                                        <v-card-text>
                                            <v-layout row wrap>
                                                <v-flex xs6>
                                                    <v-text-field readonly prepend-icon="account_box" v-model="viewOrder.firstname+' '+viewOrder.lastname" label="Name"></v-text-field>
                                                    <v-text-field readonly prepend-icon="email" v-model="viewOrder.email" label="Email"></v-text-field>
                                                    <v-text-field readonly prepend-icon="print" v-model="viewOrder.printerName" label="Selected Printer"></v-text-field>
                                                    <v-text-field readonly prepend-icon="texture" v-model="viewOrder.material" label="Selected Material"></v-text-field>
                                                    <v-text-field readonly prepend-icon="invert_colors" v-model="viewOrder.materialColour" label="Material Colour"></v-text-field>
                                                </v-flex>
                                                <v-divider vertical></v-divider>
                                                <v-flex xs6>
                                                    <v-text-field readonly prepend-icon="insert_drive_file" v-model="viewOrder.fileName" label="File Name"></v-text-field>
                                                    <v-text-field readonly prepend-icon="format_shapes" v-model="viewOrder.dimensions" label="Dimensions LxWxH"></v-text-field>
                                                    <v-textarea readonly rows="4" prepend-icon="message" name="input-7-1" label="Comments" v-model="viewOrder.comments"></v-textarea>
                                                    <v-alert type="warning" <c:if test="${warning!=null}">value="true"</c:if>>
                                                        ${warning}
                                                    </v-alert>
                                                </v-flex>
                                            </v-layout>
                                        </v-card-text>
                                        <v-card-actions>
                                            <v-btn flat @click="dialog = false" color="primary">Close</v-btn>
                                            <v-spacer></v-spacer>
                                            <v-btn flat outline color="red accent-3">Cancel Order</v-btn>
                                            <v-btn flat outline color="light-green darken-2">Complete Order</v-btn>
                                        </v-card-actions>
                                    </v-card>
                                </v-dialog>-->
                            </v-layout>
                        </v-container>
                    </v-container>
                </v-content>
            </v-app>
        </div>
        <!--<script src="res/js/vue.js" type="text/javascript"></script>-->
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
                        {title: 'Reports', icon: 'poll', link: 'reportmanagement'}
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
                    labels: ['12am','3am','6am','9am','12pm','3pm','6pm','9pm'],
                    value: [200,675,410,390,310,460,250,240]
                },
                methods:
                {
                    approve()
                    {
                        document.getElementById('approveOrderCost').value = document.getElementById('actualCost').value;
                        alert("Actual Cost: " + document.getElementById('actualCost').value + "\nApproved Cost: " + document.getElementById('comments').value);
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
