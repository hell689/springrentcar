<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix= "spring" uri= "http://www.springframework.org/tags"%>
<%@ taglib prefix= "security" uri= "http://www.springframework.org/security/tags" %>
<u:hf title="Аренда автомобилей">
    <h1>Аренда автомобилей</h1>
    <c:if test="${not empty param.message}">
        <div class="col-md-6 offset-md-3">
            <div class="alert alert-success" role="alert">
                ${param.message}
            </div>
        </div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger" role="alert">
            Ошибка авторизации (<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>)
        </div>
    </c:if>
    <security:authorize access= "hasAnyRole('ROLE_ADMIN', 'ROLE_USER')" var="isUser"/>
    <c:if test="${not isUser}">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <div class="alert alert-warning" role="alert">
                    Для работы с системой необходимо авторизироваться!
                </div>
                <form method="post" action="spring_security_check" class= "form-signin">
                    <div class="form-group">
                        <label for="username">Login (напр. admin или user1)</label>
                        <input class="form-control" name="username" id="username" placeholder="Login" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="password">Password (напр. 123)</label>
                        <input type="password" class="form-control" name="password" id="password" placeholder="Password" required>
                    </div>
                    <input type= "checkbox" id= "rememberme"  name= "spring_security_remember_me"/>Запомнить меня
                    <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                        value="<c:out value="${_csrf.token}"/>"/>
                    <button type="submit" class="btn btn-primary">Войти</button>
                </form>
                <a href="<c:url value="/registration"/>">Зарегистрироваться</a>
            </div>
        </div>
    </c:if>

    <c:if test="${isUser}">
        <security:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
            <div class="row">
                <div class="col-sm-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Цвета</h5>
                            <p class="card-text">Цвета автомобилей</p>
                            <a href="<c:url value="/color"/>" class="btn btn-primary">Перейти</a>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Марки</h5>
                            <p class="card-text">Марки автомобилей</p>
                            <a href="<c:url value="/mark"/>" class="btn btn-primary">Перейти</a>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Коробки передач</h5>
                            <p class="card-text">Виды коробок передач</p>
                            <a href="<c:url value="/gearbox"/>" class="btn btn-primary">Перейти</a>
                        </div>
                    </div>
                </div>
            </div>
        </security:authorize>
        <br>
        <div class="row">
            <div class="col-sm-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Автомобили</h5>
                        <p class="card-text">Автомобили, для аренды</p>
                        <a href="<c:url value="/car"/>" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Заявки</h5>
                        <p class="card-text">Заявки на аренду автомобиля</p>
                        <a href="<c:url value="/request"/>" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Аренда</h5>
                        <p class="card-text">Обработанные заявки на аренду</p>
                        <a href="<c:url value="/rent"/>" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</u:hf>
