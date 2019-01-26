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
        <link href="res/css/login.css" rel="stylesheet" type="text/css"/>
        <title>ARIS 3D Printing Service - Login</title>
    </head>
    <body style="background-color:#DAD2D8;">
        <div class="bar"></div>
        <div class="container">
            <div class="container">
                <div class="mx-auto col-5 col-sm-5 col-md-4 col-lg-3 col-xl-3">
                    <img class="img-fluid" src="res/img/loginLogo.png" alt="ARIS 3D Printing Service - Logo"/>
                </div>
            </div>
            <br/>
            <div class="container justify-content-center">
                <div class="mx-auto col-12 col-sm-12 col-md-10 col-lg-8 col-xl-7">
                    <form method="post" action="login">
                        <div class="form-group">
                            <!--<label for="Email">Email Address</label>-->
                            <input id="email" type="email" name="email" placeholder="Email Address" class="form-control">
                            <input id="password" type="password" name="password" placeholder="Password" class="form-control">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
