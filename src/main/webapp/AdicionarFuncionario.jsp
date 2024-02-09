<%--
  Created by IntelliJ IDEA.
  User: joao_
  Date: 09/06/2023
  Time: 00:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        /* Estilos para o menu no topo */
        .top-menu {
            background-color: #f7f6f6;
            height: 60px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 20px;
        }

        .top-menu ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            display: flex;
        }

        .top-menu ul li {
            margin-right: 20px;
        }

        .top-menu ul li a {
            text-decoration: none;
            color: #000000;
            font-size: 18px;
            padding: 10px;
        }

        .top-menu ul li a:hover {
            background-color: #ececec;
        }

        .top-menu .logout-button {
            margin-left: auto;
            padding: 10px;
            background-color: #ececec;
            color: #000000;
            text-decoration: none;
        }

        /* Estilos para o menu dropdown */
        .dropdown {
            position: relative;
            display: inline-block;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }

        .dropdown-content a {
            color: #000000;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }

        .dropdown:hover .dropdown-content {
            display: block;
        }

        .add-button {
            margin-top: 20px;
        }
        .form-container {
            width: 300px;
            margin: 50px auto;
        }

        .form-container label {
            display: block;
            margin-bottom: 10px;
        }

        .form-container input[type="text"] {
            width: 100%;
            padding: 5px;
            margin-bottom: 10px;
        }

        .form-container button {
            padding: 10px 20px;
        }
        .button {
            display: inline-block;
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            font-size: 16px;
            border-radius: 4px;
            transition: background-color 0.3s;
        }

        .button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="top-menu">
    <ul>
        <li class="dropdown">
            <a href="#" class="dropbtn">Fornecedores</a>
            <div class="dropdown-content">
                <a href="ListarFornecedores.jsp">Lista</a>
                <a href="AdicionarFornecedores.jsp">Adicionar Fornecedores</a>
            </div>
        </li>
        <li class="dropdown">
            <a href="#" class="dropbtn">Encomendas</a>
            <div class="dropdown-content">
                <a href="ListarEncomendas.jsp">Lista</a>
                <a href="FazerEncomenda.jsp">Fazer Encomenda</a>
            </div>
        </li>
        <li class="dropdown">
            <a href="ListarProdutos.jsp" class="dropbtn">Produtos</a>
        </li>
        <li class="dropdown">
            <a href="#" class="dropbtn">Funcionários</a>
            <div class="dropdown-content">
                <a href="ListarFuncionarios.jsp">Lista </a>
                <a href="AdicionarFuncionario.jsp">Adicionar Funcionarios</a>
            </div>
        </li>
        <li class="dropdown">
            <a href="#" class="dropbtn">Clientes</a>
            <div class="dropdown-content">
                <a href="ListarClientes.jsp">Lista</a>
                <a href="AdicionarClientes.jsp">Criar Clientes</a>
            </div>
        </li>
    </ul>
    <a href="Logout.jsp" class="logout-button">Sair</a>
</div>
<div class="form-container">
    <h1>Formulário</h1>
    <form action="AdicionarFuncionario" method="POST">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required>

        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required>

        <label for="telefone">Telefone:</label>
        <input type="text" id="telefone" name="telefone" required>

        <label for="codigoposta">Codigo-postal:</label>
        <input type="text" id="codigoposta" name="codigopostal" required>

        <label for="salariobase">Salario base:</label>
        <input type="text" id="salariobase" name="salariobase" required>

        <label for="morada">Morada:</label>
        <input type="text" id="morada" name="morada" required>

        <label for="datacontratacao">Data Contratação:</label>
        <input type="date" id="datacontratacao" name="datacontratacao" required>

        <label for="palavrapasse">Palavra-passe:</label>
        <input type="password" id="palavrapasse" name="palavrapasse" required>

        <button type="submit">Confirmar</button>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <div style="color: red; margin-bottom: 10px;"><%= errorMessage %></div>
        <% } %>
    </form>
    <div style="text-align: Left;margin-top: 20px;">
        <a href="MenuAdmin.jsp" class="button">Voltar ao Menu Principal</a>
    </div>
</div>
</body>
</html>
