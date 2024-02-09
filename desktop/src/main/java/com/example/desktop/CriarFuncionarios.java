package com.example.desktop;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
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
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.desktop.DB.criarConexao;

public class CriarFuncionarios implements Initializable {

    @FXML
    private Label nome = null;

    @FXML
    private Label lblemail = null;

    @FXML
    private TextField Cfuncionario;

    @FXML
    private TextField Efuncionario;

    @FXML
    private TextArea Mfuncionario;

    @FXML
    private TextField Nfuncionario;

    @FXML
    private PasswordField Pfuncionario;

    @FXML
    private TextField Sfuncionario;

    @FXML
    private TextField Tfuncionario;

    @FXML
    private DatePicker dpdc;


    public void btnConfirmar(ActionEvent actionEvent) throws SQLException {
        Connection conn = criarConexao();
        String sqlCommand = "INSERT INTO FUNCIONARIO (NOME_FUNCIONARIO, EMAIL_FUNCIONARIO, TELEFONE_FUNCIONARIO, MORADA_FUNCIONARIO, CODPOSTAL, DATA_CONTRATACAO_FUNCIONARIO,SALARIO_FUNCIONARIO, PASSWORD ) " +
                "VALUES (?, ?, ? ,? ,? ,? ,?, ?)";
        PreparedStatement pst = null;
        pst = conn.prepareStatement(sqlCommand);

        if (Nfuncionario.getText().isEmpty() || Efuncionario.getText().isEmpty() || Tfuncionario.getText().isEmpty() || Mfuncionario.getText().isEmpty()
                || Cfuncionario.getText().isEmpty() || dpdc.getValue() == null || Sfuncionario.getText().isEmpty() || Pfuncionario.getText().isEmpty()) {
            Alert alertDatInv = new Alert(Alert.AlertType.ERROR);
            alertDatInv.setTitle("Erro!");
            alertDatInv.setHeaderText("Preencha todos os campos!");
            alertDatInv.show();
        } else {
            pst.setString(1, Nfuncionario.getText());
            pst.setString(2, Efuncionario.getText());
            pst.setString(3, Tfuncionario.getText());
            pst.setString(4, Mfuncionario.getText());
            pst.setString(5, Cfuncionario.getText());
            pst.setString(6, dpdc.getValue().toString());
            pst.setString(7, Sfuncionario.getText());
            pst.setString(8,Pfuncionario.getText());


            pst.execute();

            Alert alertSucesso = new Alert(Alert.AlertType.INFORMATION);
            alertSucesso.setTitle("Sucesso!");
            alertSucesso.setHeaderText("Funcionário inserido com sucesso!");
            alertSucesso.show();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("ListarFuncionariosAdmin.fxml"));
                Scene regCena = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Principal");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void btnVoltar(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("ListarFuncionariosAdmin.fxml"));
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

    public void btnFornecedores(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuFornecedores(mouseEvent);
    }

    public void btnClientes(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuClientes(mouseEvent);
    }

    public void btnFuncionarios(MouseEvent mouseEvent) {
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
