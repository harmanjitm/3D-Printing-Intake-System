<%-- 
    Document   : login
    Created on : Jan 18, 2019, 10:16:53 AM
    Author     : 758243
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <ARIS3D:Imports/>
        <title>ARIS 3D Printing Service - Login</title>
    </head>
    <body>
        <div class="container">
            <form method="post" action="login">
                <div class="form-group">
                    <label for="Email">Email Address</label>
                    if on mobile display dont display label, if on desktop display label but dont display placehodler
                    <input type="email" name="email" placeholder="Email Address" class="form-control">
                </div>
            </form>
        </div>
    </body>
</html>
