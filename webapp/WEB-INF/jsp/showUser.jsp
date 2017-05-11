<%--
  Created by IntelliJ IDEA.
  User: xq
  Date: 2017/3/8
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>All User Info</title>
</head>
<body>
    <c:if test="${!empty userList}">
        <c:forEach var="user" items="${userList}">
            name:${user.id}&nbsp;&nbsp;password:${user.password}<br>
        </c:forEach>
    </c:if>
</body>
</html>
