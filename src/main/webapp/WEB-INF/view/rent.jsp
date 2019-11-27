<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix= "spring" uri= "http://www.springframework.org/tags"%>
<%@ taglib prefix= "security" uri= "http://www.springframework.org/security/tags" %>
<u:hf title="Аренда">
    <div class="row">
        <div class="col-md-6 offset-md-1">
            <h1>Аренда</h1>
            <c:if test="${not empty param.errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${param.errorMessage}
                </div>
            </c:if>
        </div>
    </div>
    <security:authorize access= "hasAnyRole('ROLE_ADMIN', 'ROLE_USER')" var="isUser"/>
    <c:forEach var="rent" items="${rents}" varStatus="counter">
        <c:if test="${(counter.count mod 2) ne 0}">
            <div class="row">
        </c:if>
        <div class="col-sm-6">
            <div class="card">

                <div class="card-body">
                    <h5 class="card-title">Аренда № ${rent.id}</h5>
                    <p class="card-text">
                        Запрос: ${rent.request.id}<br>
                        Марка: ${rent.request.mark.mark}<br>
                        Цвет: ${rent.request.color.color}<br>
                        Объем: ${rent.request.volume}<br>
                        Коробка: ${rent.request.gearbox.gearbox}<br>
                        Дата начала: ${rent.request.startDate}<br>
                        Дата окончания: ${rent.request.endDate}<br>
                        Комментарий: ${rent.request.comment}<br>
                        Пользователь: ${rent.request.username}
                    </p>
                    <p class="card-text">
                        Выданный автомобиль:<br>
                        Марка: ${rent.car.mark.mark}<br>
                        Цвет: ${rent.car.color.color}<br>
                        Объем: ${rent.car.volume}<br>
                        Коробка: ${rent.car.gearbox.gearbox}<br>
                        Дата начала: ${rent.startDate}<br>
                        Дата окончания: ${rent.endDate}<br>
                    </p>
                    <c:if test="${isUser}">
                        <security:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
                        <form action="<c:url value="rent/delete"/>" method="post">
                            <c:if test="${not empty rent.id}">
                                <input name="deleteId" value="${rent.id}" type="hidden">
                            </c:if>
                            <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                            value="<c:out value="${_csrf.token}"/>"/>
                            <button class="btn btn-primary">Удалить</button>
                        </form>
                        </security:authorize>
                    </c:if>
                </div>
            </div>
        </div>
        <c:if test="${counter.count mod 2 ne 1}">
            </div>
        </c:if>
        </c:forEach>


</u:hf>
