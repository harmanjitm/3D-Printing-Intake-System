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
                <!--Navigation drawer-->
                    <v-navigation-drawer <c:if test="${mini == true}">mini-variant</c:if> dark v-model="drawer" stateless clipped app fixed>
                        <v-list dense>
                            <c:if test="${account.accountType == 'admin'}">
                                <v-list-tile :href="item.link" :key="item.title" v-for="item in adminItems">
                                    <v-list-tile-action>
                                        <v-icon>{{ item.icon }}</v-icon>
                                    </v-list-tile-action>
                                    <v-list-tile-content>
                                        <v-list-tile-title>{{ item.title }}</v-list-tile-title>
                                    </v-list-tile-content>
                                </v-list-tile>
                            </c:if>
                            <c:if test="${account.accountType == 'user'}">
                                <v-list-tile :href="item.link" :key="item.title" v-for="item in userItems">
                                    <v-list-tile-action>
                                        <v-icon>{{ item.icon }}</v-icon>
                                    </v-list-tile-action>
                                    <v-list-tile-content>
                                        <v-list-tile-title>{{ item.title }}</v-list-tile-title>
                                    </v-list-tile-content>
                                </v-list-tile>
                            </c:if>
                        </v-list>
                    </v-navigation-drawer>
                
                <!--Navigation Toolbar-->
                    <v-toolbar app fixed clipped-left color="#1B222B">
                        <c:if test="${account != null}">
                            <v-toolbar-side-icon @click.stop="drawer = !drawer" class="white--text"></v-toolbar-side-icon>
                        </c:if>
                        <v-toolbar-title class="white--text">ARIS3D</v-toolbar-title>
                        <v-spacer></v-spacer>
                        <v-toolbar-items>
                            <c:if test="${account != null}">
                                <v-btn href="account" slot="activator" icon>
                                    <v-icon large color="white">perm_identity</v-icon>
                                </v-btn>
                                <v-btn href="login?logout=true" slot="activator" icon>
                                    <v-icon large color="white">logout</v-icon>
                                </v-btn>
                            </c:if>
                            <c:if test="${account == null}">
                                <v-btn href="register" flat small color="#ffffff">
                                    Register
                                </v-btn>
                                <v-btn href="login" small flat color="#ffffff">
                                    Login
                                </v-btn>
                            </c:if>
                        </v-toolbar-items>
                    </v-toolbar>  
                    
                <!--Carousel Images-->
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
                    
                <!--Alert Messages-->
                    <v-alert <c:if test='${successMessage != null}'>value="true"</c:if> type="success">
                        ${successMessage}
                    </v-alert>
                    <v-alert <c:if test='${errorMessage != null}'> id="error" value="true"</c:if> type="error">
                        ${errorMessage}
                    </v-alert>
                    
            <!--**MAIN TEXT IN HERE**-->
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
            
            
            <!--Information for users that are logged in-->
                    
                <!--Orders Pending User Approval-->
                    <c:if test="${approval[0] != null}">
                    <v-container>
                        <h1 class="display-1 text-xs-center font-weight-thin">Orders Pending Your Approval</h1>
                        <v-divider></v-divider>
                        <v-container grid-list-lg>
                            <v-layout row wrap>
                                <v-flex v-for="order in approval" xs12 sm12 md6 lg4 xl2 :key="order.orderId">
                                    <v-card color="#8B2635" height="5px"></v-card>
                                    <v-card elevation="5" min-height="200px">
                                        <v-card-title class="blue-grey darken-4 white--text">
                                            <span primary class="headline text-xs-left">Order {{num}}{{order.orderId}}</span>
                                            <v-spacer></v-spacer>
                                        </v-card-title>
                                        <br>
                                        <v-card-text class="pt-0">
                                            <span class="text-xs-center subheading">Order Information</span>
                                            <v-divider></v-divider>
                                            <span class="subheading font-weight-light"><v-icon>print</v-icon> {{order.printerName}}</span>
                                            <v-spacer></v-spacer>
                                            <span class="subheading font-weight-light"><v-icon>confirmation_number</v-icon>{{num}}{{order.orderId}}</span>
                                            <v-spacer></v-spacer>
                                            <span class="subheading font-weight-light"><v-icon>attach_money</v-icon> {{order.cost}}</span>
                                            <v-spacer></v-spacer>
                                            <span class="subheading font-weight-light"><v-icon>file_copy</v-icon> {{order.fileName}}</span>
                                        </v-card-text>
                                        <v-card-actions>
                                            <v-btn @click="viewOrder(order)" color="#8B2635" dark>Cancel</v-btn>
                                            <v-spacer></v-spacer>
                                            <v-btn @click="viewOrder(order)" color="green darken-3" dark>Approve</v-btn>
                                            <v-dialog max-width="50%" v-model="orderDialog" >
                                                <v-card color="#8B2635" height="5px"></v-card>
                                                <v-toolbar class="blue-grey darken-4 white--text">
                                                    <v-toolbar-title>Confirm Order for Printing</v-toolbar-title>
                                                    <v-spacer></v-spacer>
                                                </v-toolbar>
                                                <v-card>
                                                    <v-container fluid grid-list-lg>
                                                        <v-layout row wrap>
                                                            <v-flex xs12 sm4 md4 lg4>
                                                                <v-card flat max-height="200px">
                                                                    <v-card-title><h4>File Information</h4></v-card-title>
                                                                    <v-divider></v-divider>
                                                                    <v-list dense>
                                                                        <v-list-tile>
                                                                            <v-list-tile-content>File Name: </v-list-tile-content>
                                                                            <v-list-tile-content class="align-end">{{ selectedOrder.fileName }}</v-list-tile-content>
                                                                        </v-list-tile>
                                                                        <v-list-tile>
                                                                            <v-list-tile-content>File Dimensions: </v-list-tile-content>
                                                                            <v-list-tile-content class="align-end">{{ selectedOrder.dimensions }} mm&#179</v-list-tile-content>
                                                                        </v-list-tile>
                                                                    </v-list>
                                                                </v-card>
                                                            </v-flex>
                                                            <v-flex xs12 sm8 md8 lg8>
                                                                <v-card flat max-height="200px">
                                                                    <v-card-title><h4>Printer Information</h4></v-card-title>
                                                                    <v-divider></v-divider>
                                                                    <v-layout>
                                                                        <v-flex xs7>
                                                                            <v-list dense>
                                                                                <v-list-tile>
                                                                                    <v-list-tile-content>Printer Name: </v-list-tile-content>
                                                                                    <v-list-tile-content class="align-end">{{selectedOrder.printerName}}</v-list-tile-content>
                                                                                </v-list-tile>
                                                                                <v-list-tile>
                                                                                    <v-list-tile-content>Printer Dimensions: </v-list-tile-content>
                                                                                    <v-list-tile-content class="align-end">{{selectedOrder.printerDimensions}}&#179</v-list-tile-content>
                                                                                </v-list-tile>
                                                                            </v-list>
                                                                        </v-flex>
                                                                        <v-flex xs5>
                                                                            <v-img :src="selectedOrder.img" height="30%" contain></v-img>
                                                                        </v-flex>
                                                                    </v-layout>
                                                                </v-card>
                                                            </v-flex>
                                                            <v-flex xs12 sm4 md4 lg4>
                                                                <v-card flat max-height="200px">
                                                                    <v-card-title><h4>Material Information</h4></v-card-title>
                                                                    <v-divider></v-divider>
                                                                    <v-list dense>
                                                                        <v-list-tile>
                                                                            <v-list-tile-content>Material: </v-list-tile-content>
                                                                            <v-list-tile-content class="align-end">{{ selectedOrder.material }}</v-list-tile-content>
                                                                        </v-list-tile>
                                                                        <v-list-tile>
                                                                            <v-list-tile-content>Type: </v-list-tile-content>
                                                                            <v-list-tile-content class="align-end">{{ selectedOrder.materialColour }}</v-list-tile-content>
                                                                        </v-list-tile>
                                                                    </v-list>
                                                                </v-card>
                                                            </v-flex>
                                                            <v-flex xs12 sm8 md8 lg8>
                                                                <v-card flat max-height="200px">
                                                                    <v-card-title><h4>Cost Information</h4></v-card-title>
                                                                    <v-divider></v-divider>
                                                                    <v-list dense>
                                                                        <v-list-tile>
                                                                            <v-list-tile-content>Total Cost: </v-list-tile-content>
                                                                            <v-list-tile-content class="align-end">$ {{selectedOrder.cost}}</v-list-tile-content>
                                                                        </v-list-tile>
                                                                        <v-list-tile>
                                                                            <v-list-tile-content>Payment: </v-list-tile-content>
                                                                            <v-list-tile-content class="align-end">You will be contacted regarding payment options.</v-list-tile-content>
                                                                        </v-list-tile>
                                                                    </v-list>
                                                                </v-card>
                                                            </v-flex>
                                                        </v-layout>
                                                        <v-flex>
                                                            <v-card flat>
                                                                <v-list dense flat>
                                                                    <v-list-tile>
                                                                        <v-list-tile-content><h4>Your Message:</h4> {{ selectedOrder.comments }}</v-list-tile-content>
                                                                    </v-list-tile>
                                                                </v-list>
                                                            </v-card>
                                                        </v-flex>
                                                    </v-container>
                                                    <v-card-actions>
                                                        <v-btn @click="cancel()" color="#8B2635" dark>Cancel</v-btn>
                                                        <v-spacer></v-spacer>
                                                        <v-btn @click="approve()" color="green darken-3" dark>Approve</v-btn>
                                                    </v-card-actions>
                                                </v-card>
                                            </v-dialog>
                                        </v-card-actions>
                                    </v-card>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-container>
                    </c:if>
                
                <!--Previous Orders-->
                    <c:if test="${previous[0] != null}">
                    <v-container>
                        <h1 class="display-1 text-xs-center font-weight-thin">Previous Orders</h1>
                        <v-divider></v-divider>
                        <v-container grid-list-lg>
                            <v-layout row wrap>
                                <v-flex v-for="order in previous" xs12 sm12 md6 lg4 xl2 :key="order.orderId">
                                    <v-card color="#8B2635" height="5px"></v-card>
                                    <v-card elevation="5" min-height="200px">
                                        <v-card-title class="blue-grey darken-4 white--text">
                                            <span primary class="headline text-xs-left">Order {{num}}{{order.orderId}}</span>
                                            <v-spacer></v-spacer>
                                        </v-card-title>
                                        <br>
                                        <v-card-text class="pt-0">
                                            <span class="text-xs-center subheading">Order Information</span>
                                            <v-divider></v-divider>
                                            <span class="subheading font-weight-light"><v-icon>date_range</v-icon> {{order.date}}</span>
                                            <v-spacer></v-spacer>
                                            <span class="subheading font-weight-light"><v-icon>announcement</v-icon> {{order.status}}</span>
                                            <v-spacer></v-spacer>
                                            <span class="subheading font-weight-light"><v-icon>attach_money</v-icon> {{order.cost}}</span>
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-container>
                    </c:if>
                    
                <!--Default Materials, Printers, and Contact cards-->
                    <v-container grid-list-md text-xs-center>
                        <h1 class="display-1 text-xs-center font-weight-thin">ARIS Printing Service Information</h1>
                        <v-divider></v-divider>
                        <br>
                        <v-layout row wrap>
                    
                    <!--Printers-->
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
                       
                        <!--Materials-->
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
                        
                        <!--Contact Us-->
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
                    
            <!--Footer-->
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
        <link href="res/css/header.css" rel="stylesheet" type="text/css"/>
        <form method="post" action="home" id="submitOrder">
            <input id="action" type="hidden" name="action" value="">
            <input id="submitOrderId" type="hidden" name="orderId">
        </form>
    </body>
    <script>
        new Vue(
        { 
            el: '#app', 
            data: 
            {
                dialog: false,
                orderDialog: false,
                num: '#',
                account: '',
                logout: '',
                password: '',
                email: '',
                verifyEmail: '',
                drawer: false,
                valid: false,
                carouselDelimiter: true,
                adminItems: 
                [ 
                    {title: 'Home', icon: 'home', link: 'home'},
                    {title: 'Dashboard', icon: 'dashboard', link: 'dashboard'},
                    {title: 'Order Queue', icon: 'queue', link: 'queue'},
                    {title: 'Account Management', icon: 'people', link: 'accountmanagement'},
                    {title: 'Material Management', icon: 'texture', link: 'materialmanagement'},
                    {title: 'Printer Management', icon: 'print', link: 'printermanagement'},
                    {title: 'Reports', icon: 'poll', link: 'reportmanagement'},
                    {title: 'Backups', icon: 'restore', link: 'backup'},
                    {title: 'New Order', icon: 'folder_open', link: 'order'}
                ],
                userItems: 
                [ 
                    {title: 'Home', icon: 'home', link: 'home'},
                    {title: 'Submit Order', icon: 'folder_open', link: 'order'}
                ],
                accountmanagementheaders:
                [
                    {text: 'Email Address', value: 'email'},
                    {text: 'First Name', value: 'firstname'},
                    {text: 'Last Name', value: 'lastname'},
                    {text: 'Status', value: 'status'},
                    {text: 'Actions', value: 'actions'}
                ],
                carousel:
                [
                    {src: 'res/img/carousel/carouselImage1.jpg', filter: 'to top right, rgba(0,0,0,.33), rgba(0,0,0,.33)'},
                    {src: 'res/img/carousel/carouselImage2.jpg', filter: 'to top right, rgba(0,0,0,.33), rgba(0,0,0,.33)'},
                    {src: 'res/img/carousel/carouselImage3.jpg', filter: 'to top right, rgba(0,0,0,.33), rgba(0,0,0,.33)'}
                ],
                emailRules: 
                [
                    v => !!v || 'E-mail is required',
                    v => /.+@.+/.test(v) || 'E-mail must be valid'
                ],
                approval:
                [
                <c:forEach items="${approval}" var="order">
                    {orderId: ${order.orderId}, cost: ${order.cost}, printerDimensions: '${order.printerDimensions}', img: 'res/img/printers/${order.printerId}.jpg', dimensions: '${order.fileDimensions}', fileName: '${order.fileName}', email: '${order.email}', firstname: '${order.firstname}', lastname: '${order.lastname}', printerId: '${order.printerId}', printerName: '${order.printerName}', material: '${order.materialName}', materialColour: '${order.materialColour}', comments: '${order.comments}'},
                </c:forEach>
                ],
                previous:
                [
                <c:forEach items="${previous}" end="2" var="order">
                    {orderId: '${order.orderId}', date: '${order.orderDate}', cost: '${order.cost}', status: '${order.status}'},
                </c:forEach>
                ],
                selectedOrder:
                {
                    orderId: 0,
                    cost: 0,
                    email: '',
                    firstname: '',
                    lastname: '',
                    printerId: '',
                    printerName: '',
                    printerDimensions: '',
                    material: '',
                    materialColour: '',
                    comments: '',
                    fileName: '',
                    dimensions: '',
                    img: ''
                }
            },
            methods:
            {
                viewOrder(order)
                {
                    this.selectedOrder = Object.assign({}, order);
                    this.orderDialog = true;
                },
                cancel()
                {
                    document.getElementById('submitOrderId').value = this.selectedOrder.orderId;
                    document.getElementById('action').value = 'cancel';
                    document.getElementById('submitOrder').submit();
                },
                approve()
                {
                    document.getElementById('submitOrderId').value = this.selectedOrder.orderId;
                    document.getElementById('action').value = 'approve';
                    document.getElementById('submitOrder').submit();
                }
            }
        });
    </script>
</html>