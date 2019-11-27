<%@tag language="java" pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix= "security" uri= "http://www.springframework.org/security/tags" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
    <title>${title}</title>
</head>
<body >
    <security:authorize access= "hasAnyRole('ROLE_ADMIN', 'ROLE_USER')" var= "isUser"/>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="<c:url value="/"/>">Rent Car</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value="/"/>">Главная <span class="sr-only">(current)</span></a>
                </li>
            <c:if test="${isUser}">
                <security:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/color"/>">Цвета</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/mark"/>">Марки авто</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/gearbox"/>">Коробки передач</a>
                    </li>
                </security:authorize>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/car"/>">Автомобили</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/request"/>">Заявки</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/rent"/>">Аренда</a>
                    </li>
             </c:if>


            </ul>
            <c:if test="${isUser}">
                <form class="form-inline my-2 my-lg-0" method="post" action="<c:url value="/spring_security_logout"/>">
                    <security:authentication property= "principal.username"/>
                      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Выйти</button>
                      <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                                              value="<c:out value="${_csrf.token}"/>"/>
                </form>
            </c:if>
        </div>
    </nav>

        <div class="container">
            <jsp:doBody/>
        </div>
    <div id="back"></div>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>