package com.example.desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.desktop.DB.criarConexao;

public class VendaFuncionario implements Initializable {

    @FXML
    private ListView<String> idListarClienteUti;

    @FXML
    private ListView<String> idListarData;

    @FXML
    private ListView<String> idListarFuncioUtiliz;

    @FXML
    private ListView<String> idListarValorU;

    @FXML
    private ListView<String> idListarVendaUtili;

    @FXML
    private Label nome;

    @FXML
    private Label lblEmail;

    public void btnSair(MouseEvent mouseEvent) {
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de término de sessão");
            alert.setHeaderText("Confirma sair?");
            alert.setContentText("Deseja terminar sessão?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("Entrar.fxml"));
                    Scene regCena = new Scene(root);
                    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    stage.setScene(regCena);
                    stage.setTitle("Login");
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnProdutosF(MouseEvent mouseEvent) {
        MenusFuncionario menusFuncionario = new MenusFuncionario();
        menusFuncionario.MenuProdutos(mouseEvent);
    }

    public void btnClientesF(MouseEvent mouseEvent) {
        MenusFuncionario menusFuncionario = new MenusFuncionario();
        menusFuncionario.MenuClientes(mouseEvent);
    }

    public void btnVendasF(MouseEvent mouseEvent) {
        MenusFuncionario menusFuncionario = new MenusFuncionario();
        menusFuncionario.MenuVendas(mouseEvent);
    }

    public void btnCriarVenda(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Criar_VendaFuncionario.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Principal");
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnVoltar(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("menu_principalFuncionario.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Principal");
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = criarConexao();
        String sqlCommand = "SELECT v.ID_VENDA, c.nome, f.nome_funcionario, v.DATA_VENDA, v.VALOR_TOTAL_VENDA FROM VENDAS v JOIN Clientes c ON v.ID_CLIENTE = c.ID_CLIENTE JOIN FUNCIONARIO F ON v.ID_FUNCIONARIO = f.ID_FUNCIONARIO";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sqlCommand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        try {
            rs = pst.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while (true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                idListarVendaUtili.getItems().addAll(rs.getString("ID_VENDA"));
                idListarClienteUti.getItems().addAll(rs.getString("nome"));
                idListarFuncioUtiliz.getItems().addAll(rs.getString("nome_funcionario"));
                idListarData.getItems().addAll(rs.getString("DATA_VENDA"));
                idListarValorU.getItems().addAll(rs.getString("VALOR_TOTAL_VENDA"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblEmail.setText(email);
    }
}
