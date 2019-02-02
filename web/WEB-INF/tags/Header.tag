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
            <nav id="main-nav" class="navbar">
                <a class="navbar-brand" href="home"><h1>ARIS3D</h1></a>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <div id="accountDropdown">
                            <img id="accountImg" width="50px" height="auto" src="res/img/icon/account.png" alt="Account Menu"/>
                            <div id="accountDropdown-content">
                                <c:if test="${user == null}">
                                    <a href="login">Login</a>
                                    <a href="register">Register</a>
                                </c:if>
                                <c:if test="${user != null}">
                                    <a href="account">Account</a>
                                    <a href="login?logout=true">Logout</a>
                                </c:if>
                                
                            </div>
                        </div>
                    </li>
                </ul>
            </nav>
        </header>