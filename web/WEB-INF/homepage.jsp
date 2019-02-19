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
        <ARIS3D:Imports/>
        <link href="res/css/homepage.css" rel="stylesheet" type="text/css"/>
        <link href="res/css/carouselFadeTransition.css" rel="stylesheet" type="text/css"/>
        <title>ARIS3D Printing Service - Home</title>
    </head>
    <body style="background-color: #EEEAED">
        <div id="app">
            <v-app>
                <v-content>
                    <v-card color="#8B2635" height="5px"></v-card>
                    <v-toolbar dark color="#1B222B">
                        <v-toolbar-title>ARIS3D</v-toolbar-title>
                        <v-spacer></v-spacer>
                        <a href="register">
                            <v-btn outline color="pink">Register</v-btn>
                        </a>
                        <a href="login">
                            <v-btn outline color="pink">Login</v-btn>
                        </a>
                    </v-toolbar>
<!--3D Printing Intake System
Working on homepage currently.
Trying to get the text to appear over the carousel.-->
                    <div class="overlayContainer">
                        <span class="carouselOverlay">
                            <h1 class="display-1">ARIS 3D Printing Lab</h1>
                        </span>
                        <v-card elevation="10">
                            <v-carousel height="400" hide-delimiters>
                                <v-carousel-item v-for="(item, i) in carousel" :duration="2000" transition="carousel-slide-fade" :gradient="item.filter" :key="i" :src="item.src"></v-carousel-item>
                            </v-carousel>
                        </v-card>
                    </div>
                    <v-container>
                        <h1 class="display-1 text-xs-center font-weight-medium">Our Printing Service</h1>
                        <br>
                        <v-divider></v-divider>
                        <p class="body-1 text-xs-center" text-center>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse bibendum, eros vel congue tincidunt, enim metus ultricies nibh, in cursus ligula est consequat erat. Curabitur facilisis lectus quis felis fringilla, porta vehicula dolor mattis. Mauris quis dolor quis risus pellentesque ultrices. Donec aliquet dolor et risus viverra rutrum. Vestibulum placerat accumsan mi, in lacinia ex ornare congue. Vivamus tincidunt fermentum ex et aliquam. Pellentesque sit amet rhoncus dui, sed finibus massa. Morbi vestibulum, tellus in gravida dictum, lorem arcu consectetur magna, id pellentesque urna sapien ut augue. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla eu mollis enim, id blandit nisi. Quisque efficitur hendrerit nisi ut maximus. Mauris a libero sapien.</p>
                        <p class="body-1 text-xs-center" text-center>Donec et lectus sit amet lacus malesuada vehicula nec suscipit diam. Nulla commodo lorem ipsum, blandit scelerisque eros dignissim et. Etiam in urna eu nisi venenatis tristique vitae at ante. In hac habitasse platea dictumst. Cras vel commodo erat. Donec eu tortor vitae diam rhoncus finibus. Aenean in dignissim leo. Vestibulum convallis viverra nunc et porttitor. Suspendisse sed tempus ligula, ut bibendum urna. Nulla venenatis lacus odio, rhoncus luctus lorem ultricies ac. Sed aliquet justo sed feugiat hendrerit. Donec id tempor mi. Vestibulum et imperdiet lacus. Sed turpis augue, imperdiet in malesuada ac, tincidunt at diam. Donec ut vulputate lorem. Sed varius ligula vel maximus viverra.</p>
                    </v-container>
                </v-content>
                <v-footer dark height="auto">
                    <v-layout color="#1B222B" justify-center row wrap>
                        <a href="home"><v-btn color="white" flat round>Home</v-btn></a>
                        <a href="login"><v-btn color="white" flat round>Login</v-btn></a>
                        <a href="register"><v-btn color="white" flat round>Register</v-btn></a>
                        <a href="contact"><v-btn color="white" flat round>Contact Us</v-btn></a>
                        <v-flex color="white" py-1 text-xs-center white--text xs12>
                            <strong>ARIS3D</strong>
                        </v-flex>
                    </v-layout>
                </v-footer>
            </v-app>
        </div>
        
<!--        <div id="homepageImages" class="carousel slide" data-ride="carousel">
            <ul class="carousel-indicators">
                <li data-target="#homepageImages" data-slide-to="0" class="active"></li>
                <li data-target="#homepageImages" data-slide-to="1"></li>
                <li data-target="#homepageImages" data-slide-to="2"></li>
            </ul>
            
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <div id="homepageBannerImage1"></div>
                    <div class="carousel-caption">
                        <h1>ARIS 3D Printing Service</h1>
                        <p>To get started, sign up <a href="register">here</a>.</p>
                    </div>
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
                <div class="container">
                    <br>
                    <h2 class="center">Our Printing Service</h2>
                    <p class="center">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse bibendum, eros vel congue tincidunt, enim metus ultricies nibh, in cursus ligula est consequat erat. Curabitur facilisis lectus quis felis fringilla, porta vehicula dolor mattis. Mauris quis dolor quis risus pellentesque ultrices. Donec aliquet dolor et risus viverra rutrum. Vestibulum placerat accumsan mi, in lacinia ex ornare congue. Vivamus tincidunt fermentum ex et aliquam. Pellentesque sit amet rhoncus dui, sed finibus massa. Morbi vestibulum, tellus in gravida dictum, lorem arcu consectetur magna, id pellentesque urna sapien ut augue. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla eu mollis enim, id blandit nisi. Quisque efficitur hendrerit nisi ut maximus. Mauris a libero sapien.</p>
                    <p class="center">Donec et lectus sit amet lacus malesuada vehicula nec suscipit diam. Nulla commodo lorem ipsum, blandit scelerisque eros dignissim et. Etiam in urna eu nisi venenatis tristique vitae at ante. In hac habitasse platea dictumst. Cras vel commodo erat. Donec eu tortor vitae diam rhoncus finibus. Aenean in dignissim leo. Vestibulum convallis viverra nunc et porttitor. Suspendisse sed tempus ligula, ut bibendum urna. Nulla venenatis lacus odio, rhoncus luctus lorem ultricies ac. Sed aliquet justo sed feugiat hendrerit. Donec id tempor mi. Vestibulum et imperdiet lacus. Sed turpis augue, imperdiet in malesuada ac, tincidunt at diam. Donec ut vulputate lorem. Sed varius ligula vel maximus viverra.</p>
                </div>
                <div class="container">
                    <div class="card">
                        <div class="card-img-top"></div>
                        <div class="card-body">
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>-->
        <script src="res/js/vue.js" type="text/javascript"></script>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
    </body>
</html>
