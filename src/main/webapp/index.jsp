<%--
  Created by IntelliJ IDEA.
  User: joao_
  Date: 08/06/2023
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            background-image: url("..\src\main\resources\images\Background.jpg");
            background-size: cover;
            background-position: center;
        }
        .login-form {
            width: 300px;
            margin: 100px auto;
        }

        .login-form label {
            display: block;
            margin-bottom: 10px;
        }

        .login-form input[type="text"],
        .login-form input[type="password"] {
            width: 100%;
            padding: 5px;
            margin-bottom: 20px;
        }

        .login-form button {
            padding: 10px;
        }

        .error-message {
            color: red;
        }
    </style>
</head>
<body>
<div class="login-form">
    <h1>Login</h1>
    <form action="LoginController" method="POST">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <button type="submit">Entrar</button>
        <% if (request.getAttribute("errorMessage") != null) { %>
        <p class="error-message"><%= request.getAttribute("errorMessage") %></p>
        <% } %>
    </form>
</div>
</body>
</html>







