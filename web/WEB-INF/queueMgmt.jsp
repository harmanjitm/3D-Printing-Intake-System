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
                
            </v-app>
        </div>
        
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
                    ]
                },
                methods:
                {
                    
                }
            });
        </script>
    </body>
</html>
