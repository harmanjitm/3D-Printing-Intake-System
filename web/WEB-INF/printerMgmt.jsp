<%-- 
    Document   : printerMgmt
    Created on : Jan 18, 2019, 10:37:00 AM
    Author     : 756852
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports/>
        <title>ARIS3D: Printer Management</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="true" pageName="Printer Management"></ARIS3D:Header>
                <br>
                <br>
                <br>
                <v-container>
                    <v-layout>
                        <v-flex xs12 sm8 offset-sm2>
                            <v-card>
                                <div>
                                    <v-tabs v-model="active" color="#1B222B" dark slider-color="#8B2635">
                                        <v-tab v-for="n in 3" :key="n" ripple> Printer {{ n }} </v-tab>
                                        <v-tab-item v-for="n in 3" :key="n">
                                            <v-card flat>
                                                <v-card-text>{{ text }}</v-card-text>
                                            </v-card>
                                        </v-tab-item>
                                    </v-tabs>
                                </div>
                            </v-card>
                        </v-flex>
                    </v-layout>
                </v-container>
            </v-app>
        </div>

        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
        <script>
            new Vue({
                el: '#app',
                data() {
                    return {
                        active: null,
                        text: 'This section will be replaced with printer table data'
                    }
                },
                methods: {
                    next() {
                        const active = parseInt(this.active)
                        this.active = (active < 2 ? active + 1 : 0)
                    }
                }
            })
        </script>

    </body>
</html>
