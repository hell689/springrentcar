<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix= "spring" uri= "http://www.springframework.org/tags"%>
<%@ taglib prefix= "security" uri= "http://www.springframework.org/security/tags" %>
<jsp:useBean id="now" class="java.util.Date"/>
<u:hf title="Заявки">
    <div class="row">
        <div class="col-sm-6">
            <h1>Заявки</h1>
            <c:if test="${not empty param.errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${param.errorMessage}
                </div>
            </c:if>
            <security:authorize access= "hasAnyRole('ROLE_ADMIN', 'ROLE_USER')" var="isUser"/>
            <table class="table table-hover">
                <thead>
                <th scope="col">№</th>
                <th scope="col">Марка</th>
                <th scope="col">Цвет</th>
                <th scope="col">Объем двигателя</th>
                <th scope="col">Коробка передач</th>
                <th scope="col">Дата начала</th>
                <th scope="col">Дата окончания</th>
                <th scope="col">Комментарий</th>
                <th scope="col">Пользователь</th>
                <th scope="col"></th>
                <c:if test="${isUser}">
                    <security:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
                        <th scope="col"></th>
                    </security:authorize>
                </c:if>
                </thead>
                <tbody>
                <c:forEach var="request" items="${requests}">
                    <tr>
                        <td>${request.id}</td>
                        <td>${request.mark.mark}</td>
                        <td>${request.color.color}</td>
                        <td>${request.volume}</td>
                        <td>${request.gearbox.gearbox}</td>
                        <td>${request.startDate}</req></td>
                        <td>${request.endDate}</req></td>
                        <td>${request.comment}</req></td>
                        <td>${request.username}</req></td>
                        <td>
                            <c:if test="${request.processed}">
                                обработана
                            </c:if>
                            <c:if test="${request.endDate >= now}">
                                <c:if test="${not request.processed}">
                                    не обработана
                                    <c:if test="${isUser}">
                                        <security:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
                                            <form action="<c:url value="/request/process"/>" method="post">
                                            <c:if test="${not empty request.id}">
                                                <input name="requestId" value="${request.id}" type="hidden">
                                                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                                                value="<c:out value="${_csrf.token}"/>"/>
                                            </c:if>
                                            <button class="">Обработать</button>
                                            </form>
                                        </security:authorize>
                                    </c:if>
                                </c:if>
                            </c:if>
                            <c:if test="${(request.endDate < now) && not request.processed}">
                                Просрочена
                            </c:if>
                        </td>
                        <c:if test="${isUser}">
                            <security:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
                                <td>
                                    <form action="<c:url value="/request/delete"/>" method="post">
                                    <c:if test="${not empty request.id}">
                                        <input name="deleteId" value="${request.id}" type="hidden">
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

        <div class="col-md-6 offset-md-3">
            <h2>Добавить заявку:</h2>
            <form action="<c:url value="/request/save"/>" method=post>
            <div class="form-group">
                <label for="mark">Марка</label>
                <select class="form-control" id="mark" name="mark">
                    <c:forEach var="mark" items="${marks}">
                        <option value="${mark.id}">${mark.mark}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="color">Цвет</label>
                <select class="form-control" id="color" name="color">
                    <c:forEach var="color" items="${colors}">
                        <option value="${color.id}">${color.color}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="volume">Объем двигателя</label>
                <input type="text" class="form-control mr-sm-2" id="volume" name="volume" placeholder="Объём" required>
            </div>
            <div class="form-group">
                <label for="gearbox">Коробка передач</label>
                <select class="form-control" id="gearbox" name="gearbox">
                    <c:forEach var="gearbox" items="${gearboxes}">
                        <option value="${gearbox.id}">${gearbox.gearbox}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="startDate">Дата начала</label>
                <input type="date" class="form-control" id="startDate" name="startDate" placeholder="Дата начала" required>
            </div>
            <div class="form-group">
                <label for="endDate">Дата окончания</label>
                <input type="date" class="form-control" id="endDate" name="endDate" placeholder="Дата окончания" required>
            </div>
            <div class="form-group">
                <label for="comment">Комментарий</label>
                <input type="text" class="form-control mr-sm-2" id="comment" name="comment" placeholder="Комментарий">
            </div>
            <div class="form-group">
                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                value="<c:out value="${_csrf.token}"/>"/>
                <button type="submit" class="btn btn-primary mb-2">Добавить</button>
            </div>
            </form>
        </div>
    </div>
</u:hf>
