<%--
  Created by IntelliJ IDEA.
  User: joao_
  Date: 10/06/2023
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="entity.Clientes" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.*" %>
<%@ page import="entity.Produtos" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Produtos> listaDeProdutos = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection("jdbc:sqlserver://localhost;database=LojaInformatica", "sa", "123456");

        String query = "SELECT NOME_PRODUTO, MARCA_PRODUTO, PRECO_VENDA_PRODUTO, QTD_STOCK_PRODUTO FROM PRODUTOS";
        statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String nome = resultSet.getString("NOME_PRODUTO");
            String marca = resultSet.getString("MARCA_PRODUTO");
            String preco_venda_produto = resultSet.getString("PRECO_VENDA_PRODUTO");
            String qtd_stock_produto = resultSet.getString("QTD_STOCK_PRODUTO");

            Produtos produtos = new Produtos();
            produtos.setNomeProduto(nome);
            produtos.setMarcaProduto(marca);
            produtos.setPrecoVendaProduto(Double.parseDouble(preco_venda_produto));
            produtos.setQtdStockProduto(Integer.parseInt(qtd_stock_produto));


            listaDeProdutos.add(produtos);
        }

    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
%>

<html>
<head>
    <title>Lista de Produtos</title>
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

        /* Estilos para a tabela */
        .table-container {
            width: 80%;
            margin: 50px auto;
        }

        .table-container table {
            width: 100%;
            border-collapse: collapse;
        }

        .table-container th, .table-container td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        .table-container th {
            background-color: #f2f2f2;
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

<div class="table-container">
    <h1>Lista de Produtos</h1>
    <table>
        <thead>
        <tr>
            <th>Nome</th>
            <th>Marca</th>
            <th>Preço</th>
            <th>Quantidade Stock</th>
        </tr>
        </thead>
        <tbody>
        <% for (Produtos produtos : listaDeProdutos) { %>
        <tr>
            <td><%= produtos.getNomeProduto() %></td>
            <td><%= produtos.getMarcaProduto()%></td>
            <td><%= produtos.getPrecoVendaProduto() %></td>
            <td><%= produtos.getQtdStockProduto() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <div style="text-align: Left;margin-top: 20px;">
    <a href="MenuAdmin.jsp" class="button">Voltar ao Menu Principal</a>
    </div>
</div>
</body>
</html>
