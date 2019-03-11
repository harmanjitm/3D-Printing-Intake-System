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
        <title>JSP Page</title>

        <style>
            body {
                text-align: center;
            }
            #stl_cont {
                border-style: solid;
                width: 500px;
                height: 500px;
                margin: 0 auto;
            }
            .orderCon {
                width: 50%;
            }
        </style>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="false" pageName="New Order"></ARIS3D:Header>
                <br><br><br><br>

                <v-container class="orderCon">
                    <h2>Drag and Drop your file here</h2>
                    <div id="stl_cont" ></div>
                    <br>
                    <v-select
                        v-model="select"
                        :items="items"
                        :rules="[v => !!v || 'Item is required']"
                        label="Printer"
                        required
                        ></v-select>
                    <br>
                    <v-select
                        v-model="select"
                        :items="items"
                        :rules="[v => !!v || 'Item is required']"
                        label="Material"
                        required
                        ></v-select>
                    <br>
                    <v-select
                        v-model="select"
                        :items="items"
                        :rules="[v => !!v || 'Item is required']"
                        label="Method of payment"
                        required
                        ></v-select>
                    <br>
                    <v-btn>Submit</v-btn>
                </v-container>

            </v-app>
        </div>


        <script src="res/stl/stl_viewer.min.js"></script>
        <script>
            new Vue({
                el: '#app',
//                 data: {
//                    drawer: 'false',
//                    dialog: false,
//                    userItems:
//                    [ 
//                        {title: 'Home', icon: 'home', link: 'home'},
//                        {title: 'Dashboard', icon: 'dashboard', link: 'dashboard'},
//                        {title: 'Order Queue', icon: 'queue', link: 'queue'},
//                        {title: 'Account Management', icon: 'people', link: 'accountmanagement'},
//                        {title: 'Material Management', icon: 'texture', link: 'materialmanagement'},
//                        {title: 'Printer Management', icon: 'print', link: 'printermanagement'},
//                        {title: 'Reports', icon: 'poll', link: 'reportmanagement'}
//                    ]
//                }
            })
            var stl_viewer = new StlViewer
                    (
                            document.getElementById("stl_cont"),
                            {
                                auto_rotate: true,
                                bgcolor: "#20FAAC",
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
