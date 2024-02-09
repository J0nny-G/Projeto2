package com.example.desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class Fornecedores implements Initializable {

    @FXML
    private Label nome = null;

    @FXML
    private Label lblemail = null;

    @FXML
    private Button idCriarForn;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefone;

    @FXML
    private ListView<String> idListarFornE;

    @FXML
    private ListView<String> idListarFornN;

    @FXML
    private ListView<String> idListarFornT;

    private String selectedNome;

    private int selectedIndex;


    @FXML
    void btnCriar(ActionEvent event) throws SQLException {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Criar_FornecedorAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Principal");
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnRemover(ActionEvent event) {
        String selectedNome = idListarFornN.getSelectionModel().getSelectedItem();
        int selectedIndex = idListarFornN.getSelectionModel().getSelectedIndex();
        if (selectedNome == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de remoção");
        alert.setHeaderText("Confirma remover o fornecedor?");
        alert.setContentText("Tem certeza de que deseja remover o fornecedor: " + selectedNome + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // Remover o fornecedor do banco de dados
            Connection conn = criarConexao();
            String sqlCommand = "DELETE FROM FORNECEDORES WHERE NOME_FORNECEDOR = ?";
            try (PreparedStatement pst = conn.prepareStatement(sqlCommand)) {
                pst.setString(1, selectedNome);
                int rowsDeleted = pst.executeUpdate();
                if (rowsDeleted > 0) {
                    // Remoção bem-sucedida
                    idListarFornN.getItems().remove(selectedIndex);
                    idListarFornE.getItems().remove(selectedIndex);
                    idListarFornT.getItems().remove(selectedIndex);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Informação");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Fornecedor removido com sucesso!");
                    successAlert.showAndWait();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erro");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Falha ao remover o fornecedor.");
                    errorAlert.showAndWait();
                }
            } catch (SQLException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erro");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Ocorreu um erro ao remover o fornecedor.");
                errorAlert.showAndWait();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Connection conn = criarConexao();
        String sqlCommand = "SELECT NOME_FORNECEDOR, EMAIL_FORNECEDOR, TELEFONE_FORNECEDOR FROM FORNECEDORES";
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
                idListarFornN.getItems().addAll(rs.getString("NOME_FORNECEDOR"));
                idListarFornE.getItems().addAll(rs.getString("EMAIL_FORNECEDOR"));
                idListarFornT.getItems().addAll(rs.getString("TELEFONE_FORNECEDOR"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            LoginController loginController = new LoginController();
            String username = String.valueOf(loginController.getInstance().getUsername());
            nome.setText(username);
            String email = loginController.getInstance().getEmail();
            lblemail.setText(email);
        }
    }

    @FXML
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

    public void btnClientes(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuClientes(mouseEvent);
    }
}
