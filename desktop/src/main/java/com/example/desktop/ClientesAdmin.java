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

public class ClientesAdmin implements Initializable {
    @FXML
    private Label nome = null;

    @FXML
    private Label lblemail = null;

    @FXML
    private ListView<String> idListarEmailC;

    @FXML
    private ListView<String> idListarNomeC;


    public void btnCriarCliente(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Criar_ClienteAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Principal");
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnFornecedores(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuFornecedores(mouseEvent);
    }
    public void btnEncomenda(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuEncomendas(mouseEvent);
    }

    public void btnProdutos(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuProdutos(mouseEvent);
    }

    public void btnFuncionarios(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuFuncionarios(mouseEvent);
    }

    public void btnVoltar(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("menu_principalAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Principal");
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnSair(MouseEvent mouseEvent) {
        try {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = criarConexao();
        String sqlCommand = "SELECT NOME, EMAIL FROM CLIENTES";
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
                idListarNomeC.getItems().addAll(rs.getString("NOME"));
                idListarEmailC.getItems().addAll(rs.getString("EMAIL"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblemail.setText(email);
    }

    public void btnRemover(ActionEvent actionEvent) {
        String selectedNome = idListarNomeC.getSelectionModel().getSelectedItem();
        int selectedIndex = idListarNomeC.getSelectionModel().getSelectedIndex();
        if (selectedNome == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de remoção");
        alert.setHeaderText("Confirma remover o cliente?");
        alert.setContentText("Tem certeza de que deseja remover o cliente: " + selectedNome + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Connection conn = criarConexao();
            String sqlCommand = "DELETE FROM CLIENTES WHERE NOME = ?";

            try (PreparedStatement pst = conn.prepareStatement(sqlCommand)) {
                pst.setString(1, selectedNome);
                int rowsDeleted = pst.executeUpdate();
                if (rowsDeleted > 0) {
                    idListarNomeC.getItems().remove(selectedIndex);
                    idListarEmailC.getItems().remove(selectedIndex);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Informação");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Cliente removido com sucesso!");
                    successAlert.showAndWait();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erro");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Falha ao remover o cliente.");
                    errorAlert.showAndWait();
                }
            } catch (SQLException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erro");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Ocorreu um erro ao remover o cliente.");
                errorAlert.showAndWait();
                e.printStackTrace();
            }
        }
    }
}
