<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<u:hf title="404">
    <div class="jumbotron">
        <h1 class="display-4">Page not found!</h1>
        <p class="lead">Sorry, but the page you are looking for is not found. Please, make sure you have typed the
            current URL.</p>
        <hr class="my-4">
        <a class="btn btn-primary btn-lg" href="<c:url value="/"/>" role="button">Back home</a>
    </div>
</u:hf>