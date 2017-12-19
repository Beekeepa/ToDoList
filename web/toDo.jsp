<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
            "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>To Do List</title>
</head>

<style>
    table, th, td {
        border: 1px solid black;
        border-collapse: collapse;
    }
    th, td {  padding: 15px;  }
    th {  text-align: left;  }
</style>


<body>
<table style="width: 100%">
    <tr>
        <th>Description</th>
    </tr>
    <c:forEach var="item" items="${todolist}">
        <tr>
            <td><c:out value="${item}" /></td>
        </tr>
    </c:forEach>
</table>

<form action="ToDo" method="POST">
    <p>
        <br/><h3>Insert Item</h3>
            Insert new todo item.
        <br/><br/>
    <%--<input type="hidden" name="userid" value="${userid}">
    <input type="hidden" name="option" value="3">
    <label for="tododesc">Enter Description:</label>
    <input id="tododesc" name="tododesc" value="${tododesc}">>
    --%>

    <input type="text" name="addDescr" required placeholder="Enter Description">
    <input type="submit">
    </p>
</form>

<form action="ToDo" method="POST">
    <p>
        <br/><h3>Delete Item</h3>
        Delete todo item.
    <br/><br/>

    <input type="text" name="delDescr" required placeholder="Enter Description">
    <input type="submit">
    </p>
</form>
</body>
</html>