<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<u:hf title="Коробки передач">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <h1>Коробки передач</h1>
            <c:if test="${not empty param.errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${param.errorMessage}
                </div>
            </c:if>
            <table class="table table-hover">
                <thead>
                <th scope="col">Коробки передач</th>
                <th scope="col"></th>
                </thead>
                <tbody>
                <c:forEach var="gearbox" items="${gearboxes}">
                    <tr>
                        <td>${gearbox.gearbox}</td>
                        <td>
                            <form action="<c:url value="gearbox/delete"/>" method="post">
                            <c:if test="${not empty gearbox.id}">
                                <input name="deleteId" value="${gearbox.id}" type="hidden">
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
            <form class="form-inline" action="<c:url value="/gearbox/save"/>" method=post>
            <input type="text" class="form-control mb-2 mr-sm-2" id="gearbox" name="gearbox" placeholder="Коробка передач" required>
            <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
            value="<c:out value="${_csrf.token}"/>"/>
            <button type="submit" class="btn btn-primary mb-2">Добавить</button>
            </form>
        </div>
    </div>
</u:hf>
