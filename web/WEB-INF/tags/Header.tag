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
                        <div class="accountDropdown">
                            <img id="accountImg" class="btn dropdown-toggle" data-toggle="accountDropdown" width="50px" height="auto" src="res/img/icon/account.png" alt="Account Menu"/>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="login">Login</a>
                                <a class="dropdown-item" href="register">Register</a>
                            </div>
                        </div>
                    </li>
                </ul>
            </nav>
        </header>