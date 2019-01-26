<%-- 
    Document   : Header
    Created on : Jan 24, 2019, 12:06:46 PM
    Author     : 758243
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@attribute description="Specify if the user is a Tech or User. Will display specific page depening on which it is" required="true" type="boolean" name="isAdmin"%>
<div class="">
    
</div>
<c:if test="${isAdmin == true}">
    
</c:if>
<c:if test="${isAdmin == false}">
    
</c:if>