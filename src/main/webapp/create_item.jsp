<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="roomDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<jsp:useBean id="room" class="com.epam.hotel.entity.Room"/>
<jsp:useBean id="orderFacilityDetailDAO" class="com.epam.hotel.dao.impl.FacilityPackageDAOImpl"/>
<jsp:useBean id="facilityDAO" class="com.epam.hotel.dao.impl.FacilityDAOImpl"/>
<c:set var="facilityPackageList" value="${OrderFacilityDetailDAOImpl.allFacilityPackages}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="title.create.item"/></title>
</head>
<body>
<div class="container">

    <jsp:include page="header.jsp"/>
    <h3><fmt:message key="room.reservation" /> №${param.room_id}</h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/create_item" method="post">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="start_date"><fmt:message key="start.date"/></label>
                        <input type="date" class="form-control" id="start_date" name="start_date">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="end_date"><fmt:message key="end.date"/></label>
                        <input type="date" class="form-control" id="end_date" name="end_date">
                    </div>
                </div>
                <h3><fmt:message key="choose.package"/></h3>

                <div class="form-row">

                    <c:set var="price" value="0"/>
                    <jsp:useBean id="facility" class="com.epam.hotel.entity.Facility"/>
                    <c:forEach var="orderFacilityDetail" items="${orderFacilityDetailDAO.all}">
                        <div class="col-3" style="border-top:1px solid lightgray;">
                            <div>
                                <label for="1"><h4>${orderFacilityDetail.facilityPackageName} </h4></label>
                                <input type="radio" id="1" name="detail_id" value="${orderFacilityDetail.id}">
                                <ul>
                                    <c:forEach var="facility" items="${facilityDAO.getFacilityListByPackageId(orderFacilityDetail.id)}">
                                        <li><strong>${facility.name}</strong> <br/> ${facility.description}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <br/>
                <input type="hidden" name="room_id" value="${param.room_id}" >
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary"><fmt:message key="add.to.card"/></button>
                </div>
            </form>
        </div>
    </div>

</div>
</body>
</html>