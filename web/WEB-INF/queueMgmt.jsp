<%-- 
    Document   : queueMgmt
    Created on : Jan 18, 2019, 10:37:35 AM
    Author     : 756852 (Harmanjit M.)
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ARIS3D - Order Queue</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="true" pageName="Order Queue"></ARIS3D:Header>
                <v-content>
                    <v-alert <c:if test='${successMessage != null}'>value="true"</c:if> type="success">
                        ${successMessage}
                    </v-alert>
                    <v-alert <c:if test='${errorMessage != null}'>value="true"</c:if> type="error">
                        ${errorMessage}
                    </v-alert>
                    <c:if test="${orders[0] == null}">
                        <v-container>
                            <h1 class="mt-5 headline text-xs-center">No orders to display</h1>
                            <v-divider></v-divider>
                            <h1 class="subheading text-xs-center">Approved orders will display here!</h1>
                        </v-container>
                    </c:if>
                    <v-container grid-list-lg>
                        <v-layout row wrap>
                            <v-flex v-for="order in orders" xs12 sm12 md6 lg4 xl2 :key="order.orderId" v-if="order.printerId === bottomNav">
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card elevation="5" min-height="150">
                                    <v-card-title class="blue-grey darken-4 white--text" @click="selectOrder(order)">
                                        <span primary class="headline text-xs-left">{{num}}{{order.orderId}}</span>
                                        <v-spacer></v-spacer>
                                        <span class="text-xs-right headline">{{num}}{{order.position}}</span>
                                    </v-card-title>
                                    <br/>
                                    <v-card-text @click="selectOrder(order)" class="pt-0">
                                        <span class="subheading font-weight-light"><v-icon>portrait</v-icon> {{order.firstname}} {{order.lastname}}</span><br/>
                                        <span class="subheading font-weight-light"><v-icon>email</v-icon> {{order.email}}</span><br/>
                                        <span class="subheading font-weight-light"><v-icon>print</v-icon> {{order.printerName}}</span><br/>
                                        <span class="subheading font-weight-light"><v-icon>texture</v-icon>{{order.material}} - {{order.materialColour}}</span><br/>
                                        <span class="subheading font-weight-light"><v-icon>attach_money</v-icon>{{order.cost}}</span>

                                    </v-card-text>
                                    <v-divider></v-divider>

                                    <v-card-actions>
                                        <form action="queue" method="post">
                                            <input type="hidden" name="action" value="cancel">
                                            <input type="hidden" name="orderId" :value="order.orderId">
                                            <v-btn type="submit" flat color="red accent-3">Cancel</v-btn>
                                        </form>
                                        <v-spacer></v-spacer>
                                        <v-divider vertical></v-divider>
                                        <v-spacer></v-spacer>
                                        <form method="post" action="queue">
                                            <input type="hidden" name="path" :value="order.filePath">
                                            <input type="hidden" name="action" value="download">
                                            <v-btn type="submit" flat color="orange darken-2">Download</v-btn>
                                        </form>
                                        <v-spacer></v-spacer>
                                        <v-divider vertical></v-divider>
                                        <v-spacer></v-spacer>
                                        <form action="queue" method="post">
                                            <input type="hidden" name="action" value="complete">
                                            <input type="hidden" name="orderId" :value="order.orderId">
                                            <v-btn type="submit" flat color="light-green darken-2">Complete</v-btn>
                                        </form>
                                    </v-card-actions>
                                </v-card>
                            </v-flex>
                            <v-dialog v-model="dialog" max-width="1000px" persistent>
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
                            </v-dialog>
                        </v-layout>
                        <form action="queue" method="post" id="download">
                            <input type="hidden" name="action" value="download">
                            <input type="hidden" name="filePath" value="" id="filePath">

                        </form>
                    </v-container>
                </v-content>
                <v-bottom-nav dark app fixed shift :active.sync="bottomNav" :value="true" colour="transparent">
                    <v-btn v-for="printer in miniNavPrinters" :key="printer.printerId" flat :value="printer.printerId">
                        <span>{{printer.name}}</span>
                        <v-icon>filter_{{printer.printerId}}</v-icon>
                    </v-btn>
                </v-bottom-nav>
            </v-app>
        </div>
        
        <script>
            new Vue ({
                el: '#app',
                data:
                {
                    drawer: false,
                    dialog: false,
                    bottomNav: '${printers[0].printerId}',
                    num: '#',
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
                    miniNavPrinters:
                    [
                        <c:forEach items="${printers}" var="printer">
                            {printerId: '${printer.printerId}', name: '${printer.name}', icon: 'res/img/printers/${printer.printerId}.jpg'},
                        </c:forEach>
                    ],
                    orders:
                    [
                        <c:forEach items="${orders}" var="order">
                            {orderId: ${order.orderId}, position: ${order.position}, cost: ${order.cost}, printerDimensions: '${order.printerDimensions}', dimensions: '${order.fileDimensions}', filePath: '${order.filePath}', fileName: '${order.fileName}', email: '${order.email}', firstname: '${order.firstname}', lastname: '${order.lastname}', printerId: '${order.printerId}', printerName: '${order.printerName}', material: '${order.materialName}', materialColour: '${order.materialColour}', comments: '${order.comments}'},
                        </c:forEach>
                    ]
                },
                methods:
                {
                    selectOrder(order)
                    {
                        this.viewOrder = Object.assign({}, order);
                        this.dialog = true;
                    },
                    downloadOrder()
                    {
                        document.getElementById('filePath').value = this.viewOrder.filePath;
                        document.getElementById('download').submit();
                    }
                }
            });
        </script>
    </body>
</html>
