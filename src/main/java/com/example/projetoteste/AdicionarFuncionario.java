package com.example.projetoteste;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/AdicionarFuncionario")
public class AdicionarFuncionario extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String codigopostal = request.getParameter("codigopostal");
        String morada = request.getParameter("morada");
        String datacontratacao = request.getParameter("datacontratacao");
        String palavrapasse = request.getParameter("palavrapasse");
        String salariobase = request.getParameter("salariobase");

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Estabelecer conexão com a base de dados
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost;database=LojaInformatica", "sa", "123456");

            String query = "INSERT INTO FUNCIONARIO (NOME_FUNCIONARIO, EMAIL_FUNCIONARIO, TELEFONE_FUNCIONARIO, MORADA_FUNCIONARIO, CODPOSTAL, DATA_CONTRATACAO_FUNCIONARIO, SALARIO_FUNCIONARIO, PASSWORD ) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setString(3, telefone);
            statement.setString(4, morada);
            statement.setString(5, codigopostal);
            statement.setString(6, datacontratacao);
            statement.setString(7, salariobase);
            statement.setString(8, palavrapasse);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                response.sendRedirect("MenuAdmin.jsp");
            } else {
                request.setAttribute("errorMessage", "Erro ao adicionar Funcionário. Por favor, tente novamente.");
                request.getRequestDispatcher("AdicionarFuncionario.jsp").forward(request, response);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erro ao adicionar Funcionário. Por favor, tente novamente.");
            request.getRequestDispatcher("AdicionarFuncionario.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erro ao adicionar Funcionário. Por favor, tente novamente.");
            request.getRequestDispatcher("AdicionarFuncionario.jsp").forward(request, response);
        }finally {
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
