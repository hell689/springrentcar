<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<u:hf title="Цвета авто">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <h1>Цвета авто</h1>
            <c:if test="${not empty param.errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${param.errorMessage}
                </div>
            </c:if>
            <table class="table table-hover">
                <thead>
                <th scope="col">Цвет</th>
                <th scope="col"></th>
                </thead>
                <tbody>
                <c:forEach var="color" items="${colors}">
                    <tr>
                        <td>${color.color}</td>
                        <td>
                            <form action="<c:url value="color/delete"/>" method="post">
                            <c:if test="${not empty color.id}">
                                <input name="deleteId" value="${color.id}" type="hidden">
                                <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                                value="<c:out value="${_csrf.token}"/>"/>
                            </c:if>
                            <button class="">Удалить</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form class="form-inline" action="<c:url value="/color/save"/>" method=post>
            <input type="text" class="form-control mb-2 mr-sm-2" id="color" name="color" placeholder="Цвет" required>
            <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
            value="<c:out value="${_csrf.token}"/>"/>
            <button type="submit" class="btn btn-primary mb-2">Добавить</button>
            </form>
        </div>
    </div>
</u:hf>
