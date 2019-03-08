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
    </head>
    <body>
        <div id="stl_cont" style="width:500px;height:500px;margin:0 auto;"></div>

        <script src="res/stl/stl_viewer.min.js"></script>        
        <script>
            var stl_viewer=new StlViewer
            (
                document.getElementById("stl_cont"),
                {
                    auto_rotate:true,
                    bgcolor:"#20FAAC",
                    allow_drag_and_drop:true,
                    models:
                    [
                        {filename:"viewstl_plugin.stl"}
                    ]
                }
            );
        </script>
    </body>
</html>
