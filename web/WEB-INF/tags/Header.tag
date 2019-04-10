<%-- 
    Document   : Header
    Created on : Jan 24, 2019, 12:06:46 PM
    Author     : 758243
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="The header." pageEncoding="UTF-8"%>
<%@attribute description="Specify if the user is a Tech or User. Will display specific page depening on which it is" required="true" name="isAdmin"%>
<%@attribute description="The page name to display on the header." name="pageName" required="true" %>
<%@attribute description="This is used to make the nav drawer minified." name="mini" required="false" %>
            <c:if test="${account != null}">
                <v-navigation-drawer <c:if test="${mini == true}">mini-variant</c:if> dark v-model="drawer" stateless clipped app fixed>
                    <v-list dense>
                        <c:if test="${account.accountType == 'admin'}">
                            <v-list-tile :href="item.link" :key="item.title" v-for="item in adminItems">
                                <c:if test="${mini==true}">
                                    <v-tooltip right>
                                        <template v-slot:activator="{ on }">
                                            <v-list-tile-action v-on="on">
                                                <v-icon medium>{{ item.icon }}</v-icon>
                                            </v-list-tile-action>
                                        </template>
                                        <span>{{item.title}}</span>
                                    </v-tooltip>
                                </c:if>
                                <c:if test="${mini!=true}">
                                    <v-list-tile-action>
                                        <v-icon>{{ item.icon }}</v-icon>
                                    </v-list-tile-action>
                                </c:if>
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
            </c:if>
                <v-toolbar style="z-index: 100;" app fixed clipped-left color="#1B222B">
                    <c:if test="${account != null}">
                        <v-toolbar-side-icon @click.stop="drawer = !drawer" class="white--text"></v-toolbar-side-icon>
                    </c:if>
                    <v-toolbar-title class="white--text">${pageName}</v-toolbar-title>
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
                            <c:if test="${pageName != 'Register'}">
                                <v-btn href="register" flat small color="#ffffff">
                                    Register
                                </v-btn>
                            </c:if>
                            <c:if test="${pageName != 'Login'}">
                                <v-btn href="login" small flat color="#ffffff">
                                    Login
                                </v-btn>
                            </c:if>
                        </c:if>
                    </v-toolbar-items>
                </v-toolbar>