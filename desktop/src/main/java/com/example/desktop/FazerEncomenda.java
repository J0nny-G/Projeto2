package com.example.desktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class FazerEncomenda implements Initializable {

    @FXML
    private Label nome = null;

    @FXML
    private Label lblemail = null;

    @FXML
    private DatePicker DataEnc;

    @FXML
    private ComboBox<String> ForncedorEnc;

    @FXML
    private ComboBox<String> AdminID;

    @FXML
    private ComboBox<String> EstadoEnc;

    @FXML
    private TextField ValorEnc;

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

    public void btnFornecedores(MouseEvent event) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuFornecedores(event);
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

    public void btnProdutos(MouseEvent event) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuProdutos(event);
    }

    public void btnFuncionarios(MouseEvent event) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuFuncionarios(event);
    }

    public void btnClientes(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuClientes(mouseEvent);
    }

    public void btnConfirmar(ActionEvent actionEvent) throws SQLException {
        Connection conn = criarConexao();
        String sqlCommand = "INSERT INTO PEDIDO_COMPRA (ID_FORNECEDOR , ID_ADMINISTRADOR, DATA_PEDIDO_COMPRA , VALOR_TOTAL_PEDIDO_COMPRA , STATUS_PEDIDO_COMPRA ) " +
                "VALUES (?, ?, ? ,? ,?)";
        PreparedStatement pst = null;
        pst = conn.prepareStatement(sqlCommand);

        if (DataEnc.getValue() == null || ForncedorEnc.getValue().isEmpty() || AdminID.getValue().isEmpty() || EstadoEnc.getValue().isEmpty()
                || ValorEnc.getText().isEmpty()) {
            Alert alertDatInv = new Alert(Alert.AlertType.ERROR);
            alertDatInv.setTitle("Erro!");
            alertDatInv.setHeaderText("Preencha todos os campos!");
            alertDatInv.show();
        } else {
            String selectedValue = ForncedorEnc.getValue();
            String[] parts = selectedValue.split("-");
            int fornecedorIdValue = Integer.parseInt(parts[0]);

            pst.setInt(1, fornecedorIdValue);

            String selectedValue1 = AdminID.getValue();
            String[] parts1 = selectedValue1.split("-");
            int adminIdValue = Integer.parseInt(parts1[0]);

            pst.setInt(2, adminIdValue);
            pst.setString(3, String.valueOf(DataEnc.getValue()));
            pst.setString(4, ValorEnc.getText());
            pst.setString(5, EstadoEnc.getValue());


            pst.execute();

            Alert alertSucesso = new Alert(Alert.AlertType.INFORMATION);
            alertSucesso.setTitle("Sucesso!");
            alertSucesso.setHeaderText("Encomenda inserido com sucesso!");
            alertSucesso.show();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("ListarEncomendasAdmin.fxml"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = criarConexao();
        String sqlFornecedores = "SELECT ID_FORNECEDOR, NOME_FORNECEDOR FROM FORNECEDORES";
        PreparedStatement pstFornecedores = null;
        try {
            pstFornecedores = conn.prepareStatement(sqlFornecedores);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rsFornecedores = null;
        try {
            rsFornecedores = pstFornecedores.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                if (!rsFornecedores.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String fornecedorid;
            String fornecedor;
            try {
                fornecedorid = rsFornecedores.getString("ID_FORNECEDOR");
                fornecedor = rsFornecedores.getString("NOME_FORNECEDOR");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String item = fornecedorid + "-" + fornecedor;
            ForncedorEnc.getItems().addAll(item);
        }

        String sqlAdministrador = "SELECT ID_ADMINISTRADOR , NOME_ADMINISTRADOR FROM ADMINISTRADOR";
        PreparedStatement pstAdministrador = null;
        try {
            pstAdministrador = conn.prepareStatement(sqlAdministrador);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rsAdministrador = null;
        try {
            rsAdministrador = pstAdministrador.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                if (!rsAdministrador.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String Adminid;
            String admin;
            try {
                Adminid = rsAdministrador.getString("ID_ADMINISTRADOR");
                admin = rsAdministrador.getString("NOME_ADMINISTRADOR");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String item = Adminid + "-" + admin;
            AdminID.getItems().addAll(item);
        }
        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblemail.setText(email);

        ObservableList<String> estados = FXCollections.observableArrayList("Pendente", "Em andamento", "Concluído");
        EstadoEnc.setItems(estados);
    }
}
