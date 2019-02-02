<%-- 
    Document   : Header
    Created on : Jan 24, 2019, 12:06:46 PM
    Author     : 758243
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="The header duhhh" pageEncoding="UTF-8"%>
<%@attribute description="Specify if the user is a Tech or User. Will display specific page depening on which it is" required="true" name="isAdmin"%>

<link href="res/css/header.css" rel="stylesheet" type="text/css"/>
        <header>
            <div class="bar"></div>
            <nav id="main-nav" class="navbar navbar-expand-sm">
                <a class="navbar-brand" href="home"><h1>ARIS3D</h1></a>
                <ul class="navbar-nav ml-auto">
                    <c:if test="${user == null}">
                        <li class="nav-item">
                            <a href="login"><button type="button" class="btn btn-light btn-space">Login</button></a>
<!--                            <a href="login" class="nav-link btn btn-outline-light">Login</a>-->
                        </li>
                        <li class="nav-item">
                            <a href="register"><button type="button" class="btn btn-light btn-space" href="register">Register</button></a>
<!--                            <a href="register" class="nav-link btn btn-outline-light">Register</a>-->
                        </li>
                    </c:if>
                    <c:if test="${user != null}">
                        <li class="nav-item">
                            <div id="accountDropdown">
                                <img id="accountImg" width="50px" height="auto" src="res/img/icon/account.png" alt="Account Menu"/>
                                <div id="accountDropdown-content">
                                        <a href="account">Account</a>
                                        <a href="login?logout=true">Logout</a>
                                </div>
                            </div>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </header>