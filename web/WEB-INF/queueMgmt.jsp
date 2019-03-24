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
                <ARIS3D:Header mini="true" isAdmin="true" pageName="Order Queue"></ARIS3D:Header>
                <v-content>
                    <v-container grid-list-lg>
                        <v-layout row wrap>
                            <v-flex v-for="order in orders" xs12 sm12 md6 lg4 xl2 :key="order.orderId" v-if="order.printerId === bottomNav">
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card elevation="5" min-height="150">
                                    <v-card-title>
                                        <span primary class="headline text-xs-left">{{num}}{{order.orderId}}</span>
                                        <v-spacer></v-spacer>
                                        <span class="text-xs-right headline">{{num}}{{order.position}}</span>
                                    </v-card-title>
                                    <v-card-text class="pt-0">
                                        <span class="subheading font-weight-light"><v-icon>portrait</v-icon> {{order.firstname}} {{order.lastname}}</span><br/>
                                        <span class="subheading font-weight-light"><v-icon>email</v-icon> {{order.email}}</span><br/>
                                        <span class="subheading font-weight-light"><v-icon>print</v-icon> {{order.printerName}}</span><br/>
                                        <span class="subheading font-weight-light"><v-icon>texture</v-icon>{{order.material}} - {{order.materialColour}}</span><br/>
                                        <span class="subheading font-weight-light"><v-icon>attach_money</v-icon>{{order.cost}}</span>
                                    </v-card-text>
                                    <v-divider></v-divider>
                                    <!--<v-spacer vertical></v-spacer>-->
                                    <v-card-actions>
                                        <v-btn flat color="red accent-3">Cancel</v-btn>
                                        <v-spacer></v-spacer>
                                        <v-divider vertical></v-divider>
                                        <v-spacer></v-spacer>
                                        <v-btn flat color="orange darken-2">Download</v-btn>
                                        <v-spacer></v-spacer>
                                        <v-divider vertical></v-divider>
                                        <v-spacer></v-spacer>
                                        <v-btn flat color="light-green darken-2">Complete</v-btn>
                                    </v-card-actions>
                                </v-card>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-content>
                <v-bottom-nav dark app fixed shift :active.sync="bottomNav" :value="true" colour="transparent">
                    <v-btn v-for="printer in miniNavPrinters" :key="printer.printerId" flat :value="printer.printerId">
                        <span>{{printer.name}}</span>
                        <v-icon>filter_{{printer.printerId}}</v-icon>
                    </v-btn>
                </v-bottom-nav>
                
<!--                    <v-navigation-drawer width="250" permanent>
                        <v-toolbar flat>
                            <v-list>
                                <v-list-tile>
                                    <v-list-tile-action>
                                        <v-icon>printer</v-icon>
                                    </v-list-tile-action>
                                    <v-list-tile-title class="title">
                                        Printers
                                    </v-list-tile-title>
                                </v-list-tile>
                            </v-list>
                        </v-toolbar>
                        <v-divider></v-divider>
                        <v-list dense class="pt-0">
                            <v-list-tile v-for="printer in miniNavPrinters" :key="printer.printerId" @click="">
                                <v-list-tile-action>
                                    <v-icon>filter_{{printer.printerId}}</v-icon>
                                </v-list-tile-action>
                                <v-list-tile-content>
                                    <v-list-tile-title>{{printer.name}}</v-list-tile-title>
                                </v-list-tile-content>
                            </v-list-tile>
                        </v-list>
                    </v-navigation-drawer>-->
            </v-app>
        </div>
        
        <script>
            new Vue ({
                el: '#app',
                data:
                {
                    drawer: true,
                    bottomNav: '${printers[0].printerId}',
                    num: '#',
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
                    miniNavPrinters:
                    [
                        <c:forEach items="${printers}" var="printer">
                            {printerId: '${printer.printerId}', name: '${printer.name}', icon: 'res/img/printers/${printer.printerId}.jpg'},
                        </c:forEach>
                    ],
                    orders:
                    [
                        {orderId: 1001, position: 1, cost: 10.25, email: 'harmanjit.mohaar@edu.sait.ca', firstname: 'Harmanjit', lastname: 'Mohaar', printerId: '1', printerName: 'Fortus 400mc', material: 'ABS', materialColour: 'grey', comments: 'Test comment here.'},
                        {orderId: 1002, position: 1, cost: 6.76, email: 'benjamin.wozak@edu.sait.ca', firstname: 'Benjamin', lastname: 'Wozak', printerId: '2', printerName: 'Ultimaker 3 Extended', material: 'ABS', materialColour: 'red', comments: 'Test comment here.'},
                        {orderId: 1003, position: 1, cost: 2.52, email: 'emily.pegg@edu.sait.ca', firstname: 'Emily', lastname: 'Pegg', printerId: '3', printerName: 'Form 2+', material: 'ABS', materialColour: 'green', comments: 'Test comment here.'},
                        {orderId: 1004, position: 2, cost: 14.79, email: 'gregory.turnbull@edu.sait.ca', firstname: 'Gregory', lastname: 'Turnbull', printerId: '1', printerName: 'Fortus 400mc', material: 'ABS', materialColour: 'blue', comments: 'Test comment here.'}
                    ]
                },
                methods:
                {
                    
                }
            });
        </script>
    </body>
</html>
