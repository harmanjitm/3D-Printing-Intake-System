<%-- 
    Document   : accountMgmt
    Created on : Jan 18, 2019, 10:36:48 AM
    Author     : 756852
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <ARIS3D:Imports/>
    </head>
    <body>
        <div id="app">
            <v-app>
                <v-content>
                    <v-toolbar color="#1B222B">
                        <v-toolbar-side-icon class="white--text"></v-toolbar-side-icon>
                        <v-toolbar-title class="white--text">Account Management</v-toolbar-title>
                        <v-toolbar-content>
                            <v-icon><i class="material-icons">account_circle</i></v-icon>
                        </v-toolbar-content>
                    </v-toolbar>
                    <v-container></v-container>
                </v-content>
                <v-footer color="#1B222B">
                    
                </v-footer>
            </v-app>
        </div>
        
        <script>
            new Vue({ el: '#app' });
        </script>
    </body>
</html>