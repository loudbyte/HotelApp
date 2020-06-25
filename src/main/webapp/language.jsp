<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="title.languages"/></title>
    <jsp:include page="style.jsp"/>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <p>
    <h4><fmt:message key="page.warning_if_delete_language_data_lost"/></h4>
    </p>
    <div class="row">
        <div class="col-9">
            <table class="table table-sm">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col"><fmt:message key="page.languages"/></th>
                    <th scope="col">
                    </td>
                    <th scope="col">
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="languageMap" items="${languageDAO.languageMap}">
                    <tr>
                        <td>${languageMap.key}</td>
                        <td>${languageMap.value}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/delete_language" method="post">
                                <input type="hidden" name="language_id" value="${languageMap.key}">
                                <button type="submit"
                                        class="btn btn-danger"><fmt:message key="page.delete_language"/></button>
                            </form>
                        </td>
                        <form action="${pageContext.request.contextPath}/controller/edit_language" method="post">
                            <td>
                                <label>
                                    <input type="text" name="locale" placeholder="<fmt:message key="page.enter_locale"/> ">
                                </label>
                                <input type="hidden" name="language_id" value="${languageMap.key}">
                                <button type="submit"
                                        class="btn btn-warning"><fmt:message key="page.edit_language"/></button>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
            <form action="${pageContext.request.contextPath}/controller/create_language" method="post">
                <h4><fmt:message key="page.add_language"/>:</h4>
                <label>
                    <input type="text" name="locale" placeholder="<fmt:message key="page.enter_locale"/> ">
                </label>
                <button type="submit"
                        class="btn btn-info"><fmt:message key="page.add_language"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
