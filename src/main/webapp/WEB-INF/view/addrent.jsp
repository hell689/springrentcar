<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix= "spring" uri= "http://www.springframework.org/tags"%>
<%@ taglib prefix= "security" uri= "http://www.springframework.org/security/tags" %>
<u:hf title="Аренда автомобилей">
    <div class="row">
        <div class="col-sm-8">
            <h1>Обработка заявки N ${processedRequest.id}</h1>
            <c:if test="${not empty param.errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${param.errorMessage}
                </div>
            </c:if>
            <table class="table table-hover">
                <thead>
                <th scope="col">Марка</th>
                <th scope="col">Цвет</th>
                <th scope="col">Объем двигателя</th>
                <th scope="col">Коробка передач</th>
                <th scope="col">Дата начала</th>
                <th scope="col">Дата окончания</th>
                <th scope="col">Комментарий</th>
                <th scope="col">Пользователь</th>
                <th scope="col"></th>
                </thead>
                <tbody>
                    <tr>
                        <td>${processedRequest.mark.mark}</td>
                        <td>${processedRequest.color.color}</td>
                        <td>${processedRequest.volume}</td>
                        <td>${processedRequest.gearbox.gearbox}</td>
                        <td>${processedRequest.startDate}</req></td>
                        <td>${processedRequest.endDate}</req></td>
                        <td>${processedRequest.comment}</req></td>
                        <td>${processedRequest.username}</req></td>
                    </tr>
                </tbody>
            </table>
        </div>

    </div>

    <h3>Подходящие автомобили:</h3>

    <c:forEach var="car" items="${suitableCars}" varStatus="counter">
        <c:if test="${(counter.count mod 2) ne 0}">
            <div class="row">
        </c:if>
        <div class="col-sm-6">
            <div class="card">
                <div class="card-header">Автомобиль № ${counter.count}</div>
                <div class="card-body">

                    <p class="card-text">
                        Марка: ${car.mark.mark}<br>
                        Цвет: ${car.color.color}<br>
                        Объем: ${car.volume}<br>
                        Коробка: ${car.gearbox.gearbox}<br>
                    </p>
                    <form action="<c:url value="../rent/add"/>" method="post">
                    <c:if test="${not empty car.id}">
                        <input name="requestId" value="${processedRequest.id}" type="hidden">
                        <input name="carId" value="${car.id}" type="hidden">
                    </c:if>
                    <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                    value="<c:out value="${_csrf.token}"/>"/>
                    <button class="btn btn-primary">Выбрать</button>
                    </form>
                </div>
            </div>
        </div>
        <c:if test="${counter.count mod 2 ne 0}">
            </div>
        </c:if>
    </c:forEach>

    <h3>Свободные автомобили:</h3>
    <c:forEach var="car" items="${freeCars}" varStatus="counter">
        <c:if test="${(counter.count mod 2) ne 0}">
            <div class="card-group">
        </c:if>
            <div class="card">
                <div class="card-header">Автомобиль № ${counter.count}</div>
                <div class="card-body">
                    <p class="card-text">
                        Марка: ${car.mark.mark}<br>
                        Цвет: ${car.color.color}<br>
                        Объем: ${car.volume}<br>
                        Коробка: ${car.gearbox.gearbox}<br>
                    </p>
                    <form action="<c:url value="../rent/add"/>" method="post">
                    <c:if test="${not empty car.id}">
                        <input name="requestId" value="${processedRequest.id}" type="hidden">
                        <input name="carId" value="${car.id}" type="hidden">
                    </c:if>
                    <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>"
                    value="<c:out value="${_csrf.token}"/>"/>
                    <button class="btn btn-primary">Выбрать</button>
                    </form>
                </div>
            </div>
        <c:if test="${counter.count mod 2 ne 1}">
            </div>
        </c:if>
    </c:forEach>

</u:hf>
