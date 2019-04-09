<%-- 
    Document   : homepage
    Created on : Jan 18, 2019, 10:34:55 AM
    Author     : 756852
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <c:if test="${account == null}">
                            <v-toolbar dark color="#1B222B">
                        <v-toolbar-title>ARIS3D</v-toolbar-title>
                        <v-spacer></v-spacer>
                            <a href="register">
                                <v-btn outline color="#8B2635">Register</v-btn>
                            </a>
                            <a href="login">
                                <v-btn color="#8B2635">Login</v-btn>
                            </a>
                        </v-toolbar>
                        </c:if>
                        <c:if test="${account.accountType == 'admin'}">
                         <ARIS3D:Header isAdmin="true" pageName=""></ARIS3D:Header>
                            <!--<a href="dashboard">
                                <v-btn color="#8B2635">Dashboard</v-btn>
                            </a>-->
                        </c:if>
                        <c:if test="${account.accountType == 'user'}">
                         <ARIS3D:Header isAdmin="false" pageName=""></ARIS3D:Header>
                        <!--    <a href="login?logout=true">
                                <v-btn color="#8B2635">Logout</v-btn>
                            </a>-->
                        </c:if>
                    
                    <div class="overlayContainer">
                        <span class="carouselOverlay mt-5">
                            <c:if test="${account == null}"><h1 class="display-2 font-weight-bold">ARIS 3D Printing Service</h1></c:if>
                            <c:if test="${account != null}"><h1 class="display-2 font-weight-bold">Welcome Back, ${sessionScope.account.firstname}!</h1></c:if>
                            <c:if test="${account == null}"><h1 class="subheading font-weight-regular">Sign up now to get started!</h1></c:if>
                            <c:if test="${account != null}"><h1 class="subheading font-weight-regular">Submit your 3D project below!</h1></c:if>
                            <c:if test="${account == null}"><a href="register"><v-btn large color="#8B2635" dark round>Get Started!</v-btn></a></c:if>
                            <c:if test="${account != null}"><a href="order"><v-btn large color="#8B2635" dark round>Submit a Project</v-btn></a></c:if>
                        </span>
                        <v-card elevation="10">
                            <v-carousel height="400" hide-delimiters>
                                <v-carousel-item v-for="(item, i) in carousel" :duration="2000" transition="carousel-slide-fade" :gradient="item.filter" :key="i" :src="item.src"></v-carousel-item>
                            </v-carousel>
                        </v-card>
                    </div>
                    <c:if test="${account == null}">
                    <v-container>
                        <h1 class="text-xs-center font-weight-regular">About Us</h1>
                        <br>
                        <v-divider></v-divider>
                        <p class="body-1 text-xs-center" text-center>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse bibendum, eros vel congue tincidunt, enim metus ultricies nibh, in cursus ligula est consequat erat. Curabitur facilisis lectus quis felis fringilla, porta vehicula dolor mattis. Mauris quis dolor quis risus pellentesque ultrices. Donec aliquet dolor et risus viverra rutrum. Vestibulum placerat accumsan mi, in lacinia ex ornare congue. Vivamus tincidunt fermentum ex et aliquam. Pellentesque sit amet rhoncus dui, sed finibus massa. Morbi vestibulum, tellus in gravida dictum, lorem arcu consectetur magna, id pellentesque urna sapien ut augue. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla eu mollis enim, id blandit nisi. Quisque efficitur hendrerit nisi ut maximus. Mauris a libero sapien.</p>
                        <p class="body-1 text-xs-center" text-center>Donec et lectus sit amet lacus malesuada vehicula nec suscipit diam. Nulla commodo lorem ipsum, blandit scelerisque eros dignissim et. Etiam in urna eu nisi venenatis tristique vitae at ante. In hac habitasse platea dictumst. Cras vel commodo erat. Donec eu tortor vitae diam rhoncus finibus. Aenean in dignissim leo. Vestibulum convallis viverra nunc et porttitor. Suspendisse sed tempus ligula, ut bibendum urna. Nulla venenatis lacus odio, rhoncus luctus lorem ultricies ac. Sed aliquet justo sed feugiat hendrerit. Donec id tempor mi. Vestibulum et imperdiet lacus. Sed turpis augue, imperdiet in malesuada ac, tincidunt at diam. Donec ut vulputate lorem. Sed varius ligula vel maximus viverra.</p>
                        <v-divider></v-divider>
                    </v-container>
                    </c:if>
                    <c:if test="${account != null}">
                    <v-container>
                        <h1 class="display-1 text-xs-center font-weight-thin">Notifications</h1>
                        <v-divider></v-divider>
                        <v-container grid-list-lg>
                            <v-layout row wrap>
                                <v-flex v-for="i in 3" xs12 sm12 md6 lg4 xl2 :key="i">
                                    <v-card color="#8B2635" height="5px"></v-card>
                                    <v-card elevation="5" min-height="200px">
                                        <v-card-title>
                                            <span primary class="headline text-xs-left">Notification {{i}}</span>
                                            <v-spacer></v-spacer>
                                            <v-btn color="#8B2635" dark>Remove</v-btn>
                                        </v-card-title>
                                        <v-divider></v-divider>
                                        <br/>
                                        <v-card-text class="pt-0 text-xs-center">
                                            <span class="text-xs-center">Notification Text.</span>
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-container>
                    
                    </c:if>
                    <v-container grid-list-md text-xs-center>
                        <h1 class="display-1 text-xs-center font-weight-thin">More Information</h1>
                        <v-divider></v-divider>
                        <br>
                        <v-layout row wrap>
                            <v-flex xs12 sm12 md6 lg4 xl4>
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card elevation="10" min-height="325px" min-width="300px">
                                    <v-img aspect-ratio="2.75" src="res/img/carousel/carouselImage1.jpg">
                                        <v-container fill-height fluid></v-container>
                                    </v-img>
                                    <v-card-title primary-title>
                                            <h3 class="headline mb-0">Printers</h3>
                                            <v-spacer></v-spacer>
                                            <v-btn color="#8B2635" dark flat outline round>Browse</v-btn>
                                    </v-card-title>
                                    <v-card-text>Our printing service offers a variety of printers. Browse through our selection to find the one best for you!</v-card-text>
                                </v-card>
                            </v-flex>
                            <v-flex xs12 sm12 md6 lg4 xl4>
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card elevation="10" min-height="325px" min-width="300px">
                                    <v-img aspect-ratio="2.75" src="res/img/MaterialsLogo.jpg">
                                        <v-container fill-height fluid></v-container>
                                    </v-img>
                                    <v-card-title primary-title>
                                            <h3 class="headline mb-0">Materials</h3>
                                            <v-spacer></v-spacer>
                                            <v-btn color="#8B2635" dark flat outline round>Browse</v-btn>
                                    </v-card-title>
                                    <v-card-text>We offer many different materials to use for your prints. Browse through our selection of different materials to get the best suitable one for you.</v-card-text>
                                </v-card>
                            </v-flex>
                            <v-flex xs12 sm12 md6 lg4 xl4>
                                <v-card color="#8B2635" height="5px"></v-card>
                                <v-card elevation="10" min-height="325px" min-width="300px">
                                    <v-img aspect-ratio="2.75" src="res/img/ContactUsSait.jpg">
                                        <v-container fill-height fluid></v-container>
                                    </v-img>
                                    <v-card-title primary-title>
                                            <h3 class="headline mb-0">Contact</h3>
                                            <v-spacer></v-spacer>
                                            <v-btn color="#8B2635" dark flat outline round>Contact Us</v-btn>
                                    </v-card-title>
                                    <v-card-text>For any questions or concerns, please contact us through our contact page.</v-card-text>
                                </v-card>
                            </v-flex>
                        </v-layout>
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
        <script src="res/js/vue.js" type="text/javascript"></script>
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
    </body>
</html>
