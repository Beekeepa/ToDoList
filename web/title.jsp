<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
    <head>
        <meta charset="utf-8">
        <title>ToDo</title>
    </head>

    <body style="min-width: 1000px">
    <div>
        <form action = "MainServlet" method = "POST">
            <h1 align="center">Вход</h1>
            <hr>
            <p align="center">
                <input type="text" name="login" required placeholder="Логин">
            </p>
            <p align="center">
                <input type="password" name="password" required placeholder="Пароль">
            </p>
            <hr>
            <p align="center">
                <input type="submit" value="Войти" style="width: 150px; height: 25px;">
        </form>
    </div>
    </body>
</html>

