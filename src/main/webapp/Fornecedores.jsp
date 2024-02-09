<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
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
        /* Estilos para a barra superior */
        .top-bar {
            background-color: #f1f1f1;
            padding: 10px;
        }

        /* Estilos para os botões de dropdown */
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

        .dropdown:hover .dropdown-content {
            display: block;
        }
        .add-button {
            margin-top: 20px;
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
<h2>Fornecedores</h2>
<ul>
    <%-- Código JSP para exibir a lista de fornecedores --%>
    <%
        // Aqui você pode colocar sua lógica para obter a lista de fornecedores do banco de dados ou de alguma outra fonte de dados
        List<String> fornecedores = new ArrayList<>();
        fornecedores.add("Fornecedor 1");
        fornecedores.add("Fornecedor 2");
        fornecedores.add("Fornecedor 3");

        for (String fornecedor : fornecedores) {
    %>
    <li><%= fornecedor %></li>
    <%
        }
    %>
</ul>

<button class="add-button">Adicionar Fornecedor</button>


</body>
</html>
