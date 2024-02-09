package com.example.projetoteste;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/AdicionarFornecedores")
public class AdicionarFornecedores extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");


        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Estabelecer conexão com a base de dados
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost;database=LojaInformatica", "sa", "123456");

            String query = "INSERT INTO FORNECEDORES (NOME_FORNECEDOR, EMAIL_FORNECEDOR, TELEFONE_FORNECEDOR) " +
            "VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setString(3, telefone);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                response.sendRedirect("MenuAdmin.jsp");
            } else {
                request.setAttribute("errorMessage", "Erro ao adicionar Fornecedor. Por favor, tente novamente.");
                request.getRequestDispatcher("AdicionarFornecedores.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erro ao adicionar Fornecedor. Por favor, tente novamente.");
            request.getRequestDispatcher("AdicionarFornecedores.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erro ao adicionar Fornecedor. Por favor, tente novamente.");
            request.getRequestDispatcher("AdicionarFornecedores.jsp").forward(request, response);
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
