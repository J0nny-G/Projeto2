package com.example.projetoteste;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/FazerEncomenda")
public class FazerEncomenda extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idfornecedor = request.getParameter("idfornecedor");
        String idadmin = request.getParameter("idadmin");
        String data = request.getParameter("data");
        String valor = request.getParameter("valor");
        String estado = request.getParameter("estado");


        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Estabelecer conexão com a base de dados
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost;database=LojaInformatica", "sa", "123456");

            String query = "INSERT INTO PEDIDO_COMPRA (ID_FORNECEDOR , ID_ADMINISTRADOR, DATA_PEDIDO_COMPRA , VALOR_TOTAL_PEDIDO_COMPRA , STATUS_PEDIDO_COMPRA ) " +
                    "VALUES (?, ?, ? ,? ,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, idfornecedor);
            statement.setString(2, idadmin);
            statement.setString(3, data);
            statement.setString(4, valor);
            statement.setString(5, estado);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                response.sendRedirect("MenuAdmin.jsp");
            } else {
                request.setAttribute("errorMessage", "Erro ao adicionar Funcionário. Por favor, tente novamente.");
                request.getRequestDispatcher("FazerEncomenda.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erro ao adicionar Encomenda. Por favor, tente novamente.");
            request.getRequestDispatcher("FazerEncomenda.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erro ao adicionar Encomenda. Por favor, tente novamente.");
            request.getRequestDispatcher("FazerEncomenda.jsp").forward(request, response);
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
    }
}
