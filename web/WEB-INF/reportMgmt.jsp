<%-- 
    Document   : reportMgmt
    Created on : Mar 26, 2019, 3:29:26 PM
    Author     : Harmanjit Mohaar (000758243)
--%>

<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <ARIS3D:Imports></ARIS3D:Imports>
        <title>ARIS3D - Report Management</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="true" pageName="Report Management"></ARIS3D:Header>
                <!--Insert some trash ass data table in here to display past reports from DB-->
            </v-app>
        </div>
    </body>
    <script src="res/js/vue.js" type="text/javascript"></script>
</html>
