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
                    <!--<v-card flat>-->
                        <!--<v-card-text>-->
                    <v-container grid-list-md>
                        <v-layout row wrap>
                            <v-flex v-for="i in 7" xs12 sm6 md4 lg3 xl2>
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card elevation="5" height="170">
                                    <v-card-title primary-title>
                                        <div>
                                            <div class="text-xs-left"><span style="color:darkslategray;" class="headline mb-0">Order #10{{i+(128%56)-(5%2)}}</span>
                                                <!--<v-spacer></v-spacer>-->
                                                <span class="ml-auto" style="color:#8B2635;font-weight: bold">Priority #</span><b style="font-size: 15px;font-weight: 500;">{{i}}</b>
                                            </div>
                                            <div>Benjamin Wozak</div>
                                        </div>
                                    </v-card-title>
                                    <!--<v-divider></v-divider>-->
                                    <v-card-actions>
                                        <v-btn flat outline round color="red">Cancel</v-btn><v-spacer></v-spacer>
                                        <v-btn flat outline round color="green">Complete</v-btn>
                                    </v-card-actions>
                                </v-card>
                            </v-flex>
                        </v-layout>
                    </v-container>
                    <!--<v-divider align></v-divider>-->
                    <v-bottom-nav shift :active.sync="bottomNav" :value="true" absolute colour="transparent">
                        <v-btn v-for="printer in miniNavPrinters" :key="printer.printerId" color="red" flat :value="printer.printerId">
                            <span>{{printer.name}}</span>
                            <v-icon>filter_{{printer.printerId}}</v-icon>
                        </v-btn>
                    </v-bottom-nav>
                        <!--</v-card-text>-->
                <!--</v-card>-->
                </v-content>
                
                
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
                    ]
                },
                methods:
                {
                    
                }
            });
        </script>
    </body>
</html>
