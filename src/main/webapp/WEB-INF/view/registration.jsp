<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix= "spring" uri= "http://www.springframework.org/tags"%>
<%@ taglib prefix= "security" uri= "http://www.springframework.org/security/tags" %>
<u:hf title="Регистрация">
    <h1>Регистрация</h1>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
            ${errorMessage}
        </div>
    </c:if>
    <c:if test="${empty currentUser}">
        <div class="row">
            <form method="post" action="<c:url value="/registration"/>">
                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="inputSurname">Имя</label>
                        <input type="text" class="form-control" name="username" id="inputSurname" placeholder="Login" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="inputPassword">Пароль</label>
                        <input type="password" class="form-control" name="password" id="inputPassword" placeholder="Password" required>
                    </div>
                </div>
                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                value="<c:out value="${_csrf.token}"/>"/>
                <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
            </form>
        </div>
    </c:if>
</u:hf>
