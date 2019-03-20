<%-- 
    Document   : techHome
    Created on : Jan 18, 2019, 10:36:32 AM
    Author     : 756852
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
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
                                        <span class="caption grey--text font-weight-light">last order was completed on March 20, 2019{{date}}.</span>
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
                                        <span class="caption grey--text font-weight-light">last order was submitted on March 20, 2019{{date}}.</span>
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
                                        <span class="caption grey--text font-weight-light">last order was completed on March 19, 2019{{date}}.</span>
                                    </v-card-text>
                                </v-card>
                            </template>
                        </v-layout>
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
                    labels: ['12am','3am','6am','9am','12pm','3pm','6pm','9pm'],
                    value: [200,675,410,390,310,460,250,240]
                },
                methods:
                {
                    
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
