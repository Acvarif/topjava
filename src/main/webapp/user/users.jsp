<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        table, th, td {
            border: 1px solid black;
            width: 50%;
            padding: 10px;
            text-align: left;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<p><a href="users?action=create">Add Meal</a></p>
<h2>Meals</h2>
<table >
    <thead>
    <tr>
        <th>
            Name
        </th>
        <th>
            Email
        </th>
        <th>
            Password
        </th>
        <th>

        </th>
        <th>

        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>
                    ${user.name}
            </td>
            <td>
                    ${user.email}
            </td>
            <td>
                    ${user.password}
            </td>
            <td>
                <a href="userss?action=update&id=${user.id}">Update</a>
            </td>
            <td>
                <a href="users?action=delete&id=${user.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>