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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;



import static com.example.desktop.DB.criarConexao;

public class CriarClientesAdmin implements Initializable {
    @FXML
    private TextField CCliente;

    @FXML
    private TextField ECliente;

    @FXML
    private TextArea MCliente;

    @FXML
    private TextField NCliente;

    @FXML
    private TextField TCliente;

    @FXML
    private Label lblemail;

    @FXML
    private Label nome;


    public void btnConfirmar(ActionEvent actionEvent) throws SQLException {
        Connection conn = criarConexao();

        String codPostal = CCliente.getText();

        String sqlVerificarCodPostal = "SELECT COUNT(*) FROM COD_POSTAL WHERE CODPOSTAL = ?";
        PreparedStatement pstVerificarCodPostal = conn.prepareStatement(sqlVerificarCodPostal);
        pstVerificarCodPostal.setString(1, codPostal);
        ResultSet rsVerificarCodPostal = pstVerificarCodPostal.executeQuery();
        rsVerificarCodPostal.next();
        int count = rsVerificarCodPostal.getInt(1);

        if (count > 0) {
            adicionarCliente(conn,actionEvent);
        } else {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Novo Código Postal");
            dialog.setHeaderText("Código Postal não existente");

            TextField tfCodPostal = new TextField();
            TextField tfLocalidade = new TextField();
            GridPane grid = new GridPane();
            grid.add(new Label("Código Postal:"), 0, 0);
            grid.add(tfCodPostal, 1, 0);
            grid.add(new Label("Localidade:"), 0, 1);
            grid.add(tfLocalidade, 1, 1);
            dialog.getDialogPane().setContent(grid);

            ButtonType confirmButtonType = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButtonType) {
                    return new Pair<>(tfCodPostal.getText(), tfLocalidade.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            if (result.isPresent()) {
                Pair<String, String> pair = result.get();
                String novoCodPostal = pair.getKey();
                String localidade = pair.getValue();

                pstVerificarCodPostal.setString(1, novoCodPostal);
                rsVerificarCodPostal = pstVerificarCodPostal.executeQuery();
                rsVerificarCodPostal.next();
                count = rsVerificarCodPostal.getInt(1);

                if (count > 0) {
                    Alert alertExistente = new Alert(Alert.AlertType.ERROR);
                    alertExistente.setTitle("Erro");
                    alertExistente.setHeaderText("Código Postal já existente");
                    alertExistente.setContentText("O código postal informado já existe.");
                    alertExistente.showAndWait();
                } else {
                    adicionarCodPostal(conn, novoCodPostal, localidade);
                    CCliente.setText(novoCodPostal);
                    adicionarCliente(conn , actionEvent);

                }
            }
        }
    }


    private void adicionarCodPostal(Connection conn, String codPostal, String localidade) throws SQLException {
        String sqlCommand = "INSERT INTO COD_POSTAL (CODPOSTAL, LOCALIDADE) VALUES (?, ?)";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);
        pst.setString(1, codPostal);
        pst.setString(2, localidade);
        pst.executeUpdate();
    }

    private void adicionarCliente(Connection conn , ActionEvent actionEvent) throws SQLException {
        String nome = NCliente.getText();
        String email = ECliente.getText();
        String telefone = TCliente.getText();
        String morada = MCliente.getText();
        String codPostal = CCliente.getText();

        // Validação do campo nome
        if (nome.isEmpty()) {
            exibirErro("Campo nome vazio", "Por favor, preencha o campo nome.");
            return;
        }

        // Validação do campo email
        if (email.isEmpty()) {
            exibirErro("Campo email vazio", "Por favor, preencha o campo email.");
            return;
        }

        // Validação do campo telefone
        if (telefone.isEmpty()) {
            exibirErro("Campo telefone vazio", "Por favor, preencha o campo telefone.");
            return;
        }

        // Validação do campo código postal
        if (codPostal.isEmpty()) {
            exibirErro("Campo código postal vazio", "Por favor, preencha o campo código postal.");
            return;
        }


        String sqlCommand = "INSERT INTO CLIENTES (NOME, EMAIL, TELEFONE, MORADA, CODPOSTAL) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);
        pst.setString(1, nome);
        pst.setString(2, email);
        pst.setString(3, telefone);
        pst.setString(4, morada);
        pst.setString(5, codPostal);
        pst.executeUpdate();

        exibirSucesso("Sucesso!", "Cliente adicionado com sucesso");

        try{
            Parent root = FXMLLoader.load(getClass().getResource("ListarClientesAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Principal");
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void exibirErro(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void exibirSucesso(String title, String message) {
        Alert alertSucesso = new Alert(Alert.AlertType.INFORMATION);
        alertSucesso.setTitle(title);
        alertSucesso.setHeaderText(message);
        alertSucesso.showAndWait();
    }



    public void btnVoltar(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("ListarClientesAdmin.fxml"));
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
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuFuncionarios(mouseEvent);
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
