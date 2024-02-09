package com.example.desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.PublicKey;
import java.sql.*;

import static com.example.desktop.DB.criarConexao;

public class LoginController {

    @FXML
    private Button btnEntrar;

    @FXML
    private PasswordField idlblPassword;

    @FXML
    private TextField idlblUsername;

    private String username;
    private String email;
    private int id;

    private static LoginController instance;

    @FXML
    void btnEntrar(ActionEvent event) throws SQLException, IOException {
        boolean found = false;

        Connection conn = criarConexao();
        String sqlAdmin = "SELECT NOME_ADMINISTRADOR, SENHA_ADMINISTRADOR , EMAIL_ADMINISTRADOR FROM ADMINISTRADOR";
        PreparedStatement pstAdmin = conn.prepareStatement(sqlAdmin);
        ResultSet rsAdmin = pstAdmin.executeQuery();
        
        while (rsAdmin.next()) {
            if (rsAdmin.getString("NOME_ADMINISTRADOR").equals(idlblUsername.getText()) && rsAdmin.getString("SENHA_ADMINISTRADOR").equals(idlblPassword.getText())) {
                found = true;
                String emailAdmin = rsAdmin.getString("EMAIL_ADMINISTRADOR");
                System.out.println("Login com Sucesso!");
                username = idlblUsername.getText();
                email = emailAdmin;
                instance = this;
                Parent root = FXMLLoader.load(getClass().getResource("Menu_principalAdmin.fxml"));
                Scene regCena = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Utilizador");
                stage.show();
                break;
            }
        }

        String sqlFuncionario = "SELECT ID_FUNCIONARIO , NOME_FUNCIONARIO , PASSWORD , EMAIL_FUNCIONARIO FROM FUNCIONARIO";
        PreparedStatement pstFuncionario = conn.prepareStatement(sqlFuncionario);
        ResultSet rsFuncionario = pstFuncionario.executeQuery();

        while (rsFuncionario.next()) {
            if(rsFuncionario.getString("nome_funcionario").equals(idlblUsername.getText()) && rsFuncionario.getString("password").equals(idlblPassword.getText())) {
                found = true;
                System.out.println("Login com Sucesso!");
                String emailFuncionario = rsFuncionario.getString("EMAIL_FUNCIONARIO");
                String idFuncionario = String.valueOf(rsFuncionario.getInt("ID_FUNCIONARIO"));
                username = idlblUsername.getText();
                email = emailFuncionario;
                id = Integer.parseInt(idFuncionario);
                instance = this;
                Parent root = FXMLLoader.load(getClass().getResource("menu_principalFuncionario.fxml"));
                Scene regCena = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Utilizador");
                stage.show();
                break;
            }
        }

        if (!found) {
            Alert alertDatInv = new Alert(Alert.AlertType.ERROR);
            alertDatInv.setTitle("Erro");
            alertDatInv.setHeaderText("Utilizador/Password Errada!");
            alertDatInv.show();
        }
    }

    public static LoginController getInstance() {
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}
