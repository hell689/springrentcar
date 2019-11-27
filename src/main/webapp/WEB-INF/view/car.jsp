<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix= "spring" uri= "http://www.springframework.org/tags"%>
<%@ taglib prefix= "security" uri= "http://www.springframework.org/security/tags" %>
<u:hf title="Автомобили">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <h1>Автомобили</h1>
            <c:if test="${not empty param.errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${param.errorMessage}
                </div>
            </c:if>
            <security:authorize access= "hasAnyRole('ROLE_ADMIN', 'ROLE_USER')" var="isUser"/>
            <table class="table table-hover">
                <thead>
                <th scope="col">Марка</th>
                <th scope="col">Цвет</th>
                <th scope="col">Объем двигателя</th>
                <th scope="col">Коробка передач</th>
                <c:if test="${isUser}">
                    <security:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
                        <th scope="col"></th>
                    </security:authorize>
                </c:if>
                </thead>
                <tbody>
                <c:forEach var="car" items="${cars}">
                    <tr>
                        <td>${car.mark.mark}</td>
                        <td>${car.color.color}</td>
                        <td>${car.volume}</td>
                        <td>${car.gearbox.gearbox}</td>
                        <c:if test="${isUser}">
                            <security:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
                            <td>
                                <form action="<c:url value="car/delete.html"/>" method="post">
                                <c:if test="${not empty car.id}"><input name="deleteId" value="${car.id}" type="hidden">
                                </c:if>
                                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                                value="<c:out value="${_csrf.token}"/>"/>
                                <button class="">Удалить</button>
                                </form>
                            </td>
                            </security:authorize>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <c:if test="${isUser}">
            <security:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
            <div  class="col-md-10 offset-md-2">
                <form class="form-inline" action="<c:url value="/car/save"/>" method=post>
                <div class="form-row align-items-center">
                    <div class="col-auto">
                        <select class="form-control" name="mark">
                            <c:forEach var="mark" items="${marks}">
                                <option value="${mark.id}">${mark.mark}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <select class="form-control" name="color">
                            <c:forEach var="color" items="${colors}">
                                <option value="${color.id}">${color.color}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <input type="text" class="form-control mr-sm-2" id="volume" name="volume" placeholder="Объём" required>
                    </div>
                    <div class="col-auto">
                        <select class="form-control" name="gearbox">
                            <c:forEach var="gearbox" items="${gearboxes}">
                                <option value="${gearbox.id}">${gearbox.gearbox}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                        value="<c:out value="${_csrf.token}"/>"/>
                        <button type="submit" class="btn btn-primary mb-2">Добавить</button>
                    </div>
                </div>
                </form>
            </div>
            </security:authorize>
        </c:if>
    </div>
</u:hf>
