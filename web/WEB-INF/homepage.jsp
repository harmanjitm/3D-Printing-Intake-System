<%-- 
    Document   : homepage
    Created on : Jan 18, 2019, 10:34:55 AM
    Author     : 756852
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <ARIS3D:Imports/>
        <link href="https://fonts.googleapis.com/css?family=Fredoka+One|Orbitron" rel="stylesheet">
        <link href="res/css/homepage.css" rel="stylesheet" type="text/css"/>
        <title>3D Printing Intake System - Home</title>
    </head>
    <body>
        <ARIS3D:Header isAdmin="false"></ARIS3D:Header>
        <div id="homepageImages" class="carousel slide" data-ride="carousel">
            <ul class="carousel-indicators">
                <li data-target="#homepageImages" data-slide-to="0" class="active"></li>
                <li data-target="#homepageImages" data-slide-to="1"></li>
                <li data-target="#homepageImages" data-slide-to="2"></li>
            </ul>
            
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <div id="homepageBannerImage1"></div>
                </div>
                <div class="carousel-item">
                    <div id="homepageBannerImage2"></div>
                </div>
                <div class="carousel-item">
                    <div id="homepageBannerImage3"></div>
                </div>
            </div>
            
            <a class="carousel-control-prev" href="#homepageImages" data-slide="prev">
                <span class="carousel-control-prev-icon"></span>
            </a>
            <a class="carousel-control-next" href="#homepageImages" data-slide="next">
                <span class="carousel-control-next-icon"></span>
            </a>
        </div>
        <div class="col-12">
            <div class="container">
                <div>
                    <h1 class="mainImageText">ARIS 3D Printing Service</h1>
                </div>
            </div>
        </div>
    </body>
</html>
