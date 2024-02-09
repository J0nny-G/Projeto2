<%--
  Created by IntelliJ IDEA.
  User: joao_
  Date: 10/06/2023
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" %>
<%
  // Invalida a sessão
  session.invalidate();

  // Redireciona para a página de login
  response.sendRedirect("index.jsp");
%>
