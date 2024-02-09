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
import javafx.scene.control.TextField;
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

public class CriarFornecedores implements Initializable {
    @FXML
    private Label nome = null;

    @FXML
    private Label lblemail = null;

    @FXML
    private TextField idCriarEForn;

    @FXML
    private TextField idCriarNForn;

    @FXML
    private TextField idCriarTForn;

    @FXML
    void btnCriarFornecedor(ActionEvent event) throws SQLException {
        Connection conn = criarConexao();
        String sqlCommand = "INSERT INTO FORNECEDORES (NOME_FORNECEDOR, EMAIL_FORNECEDOR, TELEFONE_FORNECEDOR) " +
                "VALUES (?, ?, ?)";
        PreparedStatement pst = null;
        pst = conn.prepareStatement(sqlCommand);

        if(idCriarNForn.getText().isEmpty() || idCriarEForn.getText().isEmpty() || idCriarTForn.getText().isEmpty()){
            Alert alertDatInv = new Alert(Alert.AlertType.ERROR);
            alertDatInv.setTitle("Erro!");
            alertDatInv.setHeaderText("Preencha todos os campos!");
            alertDatInv.show();
        } else {
            String sqlVerificarFornecedor = "SELECT COUNT(*) FROM FORNECEDORES WHERE NOME_FORNECEDOR = ?";
            PreparedStatement pstVerificar = conn.prepareStatement(sqlVerificarFornecedor);
            pstVerificar.setString(1, idCriarNForn.getText());
            ResultSet rs = pstVerificar.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                Alert alertFornecedorExistente = new Alert(Alert.AlertType.ERROR);
                alertFornecedorExistente.setTitle("Erro!");
                alertFornecedorExistente.setHeaderText("Já existe um fornecedor com este nome!");
                alertFornecedorExistente.show();
            } else {
                pst.setString(1, idCriarNForn.getText());
                pst.setString(2, idCriarEForn.getText());
                pst.setString(3, idCriarTForn.getText());

                pst.execute();

                Alert alertSucesso = new Alert(Alert.AlertType.INFORMATION);
                alertSucesso.setTitle("Sucesso!");
                alertSucesso.setHeaderText("Fornecedor inserido com sucesso!");
                alertSucesso.show();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("ListarFornecedoresAdmin.fxml"));
                    Scene regCena = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(regCena);
                    stage.setTitle("Menu Principal");
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
            Parent root = FXMLLoader.load(getClass().getResource("ListarFornecedoresAdmin.fxml"));
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

    public void btnFornecedores(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblemail.setText(email);
    }
}
