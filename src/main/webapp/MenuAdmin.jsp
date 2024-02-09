<%--
  Created by IntelliJ IDEA.
  User: joao_
  Date: 08/06/2023
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
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

        /* Estilos para a grade dos quadrados */
        .grid-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            align-items: center;
            margin-top: 100px;
        }

        .grid-item {
            width: 200px;
            height: 200px;
            background-color: #ececec;
            border: 1px solid #000000;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 18px;
            margin: 10px;
        }

        .grid-item span {
            font-weight: bold;
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

<div class="grid-container">
    <div class="grid-item">
        Balanço diário: <span>1000</span>
    </div>
    <div class="grid-item">
        Balanço Semanal: <span>5000</span>
    </div>
    <div class="grid-item">
        Encomendas Pendentes: <span>10</span>
    </div>
    <div class="grid-item">
        Produto perto de restock: <span>5</span>
    </div>
</div>
</body>
</html>
