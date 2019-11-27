<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<u:hf title="Марки авто">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <h1>Марки автомобилей</h1>
            <c:if test="${not empty param.errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${param.errorMessage}
                </div>
            </c:if>
            <table class="table table-hover">
                <thead>
                <th scope="col">Марка автомобиля</th>
                <th scope="col"></th>
                </thead>
                <tbody>
                <c:forEach var="mark" items="${marks}">
                    <tr>
                        <td>${mark.mark}</td>
                        <td>
                            <form action="<c:url value="mark/delete"/>" method="post">
                            <c:if test="${not empty mark.id}">
                                <input name="deleteId" value="${mark.id}" type="hidden">
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
            <form class="form-inline" action="<c:url value="/mark/save"/>" method=post>
            <input type="text" class="form-control mb-2 mr-sm-2" id="mark" name="mark" placeholder="Марка" required>
            <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
            value="<c:out value="${_csrf.token}"/>"/>
            <button type="submit" class="btn btn-primary mb-2">Добавить</button>
            </form>
        </div>
    </div>
</u:hf>
