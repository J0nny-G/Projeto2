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

public class CriarProdutos implements Initializable {
    @FXML
    private Label nome = null;

    @FXML
    private Label lblemail = null;
    @FXML
    private TextArea DProduto;

    @FXML
    private TextField MarProduto;

    @FXML
    private TextField MoProduto;

    @FXML
    private TextField NProduto;

    @FXML
    private TextField PreProduto;

    @FXML
    private TextField SProduto;

    @FXML
    private TextField SProdutoA;

    @FXML
    private ComboBox<String> cmbFornecedor;


    public void btnFornecedores(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuFornecedores(mouseEvent);
    }
    public void btnFuncionarios(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuFuncionarios(mouseEvent);
    }

    public void btnEncomenda(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuEncomendas(mouseEvent);
    }

    public void btnClientes(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuClientes(mouseEvent);
    }

    public void btnConfirmar(ActionEvent actionEvent) throws SQLException {
        Connection conn = criarConexao();
        String sqlCommand = "INSERT INTO PRODUTOS (NOME_PRODUTO, ID_FORNECEDOR ,DESCRICAO_PRODUTO, MARCA_PRODUTO, MODELO_PRODUTO, PRECO_VENDA_PRODUTO, QTD_STOCK_PRODUTO, QTD_MIN_STOCK  ) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = null;
        pst = conn.prepareStatement(sqlCommand);


        if(DProduto.getText().isEmpty() || MarProduto.getText().isEmpty() || MoProduto.getText().isEmpty() || NProduto.getText().isEmpty() || PreProduto.getText().isEmpty() || SProduto.getText().isEmpty() || cmbFornecedor.getValue().isEmpty()){
            Alert alertDatInv = new Alert(Alert.AlertType.ERROR);
            alertDatInv.setTitle("Erro!");
            alertDatInv.setHeaderText("Preencha todos os campos!");
            alertDatInv.show();
        } else {
            String sqlVerificarProduto = "SELECT COUNT(*) FROM PRODUTOS WHERE NOME_PRODUTO = ?";
            PreparedStatement pstVerificar = conn.prepareStatement(sqlVerificarProduto);
            pstVerificar.setString(1, NProduto.getText());
            ResultSet rs = pstVerificar.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                Alert alertFornecedorExistente = new Alert(Alert.AlertType.ERROR);
                alertFornecedorExistente.setTitle("Erro!");
                alertFornecedorExistente.setHeaderText("Já existe um produto com este nome!");
                alertFornecedorExistente.show();
            } else {
                String selectedValue = cmbFornecedor.getValue();
                String[] parts = selectedValue.split("-");
                int fornecedorIdValue = Integer.parseInt(parts[0]);

                pst.setString(1, NProduto.getText());
                pst.setInt(2, fornecedorIdValue);
                pst.setString(3, DProduto.getText());
                pst.setString(4, MarProduto.getText());
                pst.setString(5, MoProduto.getText());
                pst.setString(6, PreProduto.getText());
                pst.setString(7, SProdutoA.getText());
                pst.setString(8, SProduto.getText());

                pst.execute();

                Alert alertSucesso = new Alert(Alert.AlertType.INFORMATION);
                alertSucesso.setTitle("Sucesso!");
                alertSucesso.setHeaderText("Produto inserido com sucesso!");
                alertSucesso.show();

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("produtosAdmin.fxml"));
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
    }

    public void btnVoltar(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("produtosAdmin.fxml"));
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
            cmbFornecedor.getItems().addAll(item);
        }
        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblemail.setText(email);
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

    public void btnProdutos(MouseEvent mouseEvent) {
    }
}
