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

public class ProdutosFuncionario implements Initializable {

    @FXML
    private ListView<String> MarcaP;

    @FXML
    private ListView<String> PreçoP;

    @FXML
    private ListView<String> ProdutosN;

    @FXML
    private ListView<String> QtdStock;

    @FXML
    private ComboBox<String> cmbMarca;

    @FXML
    private TextField pesquisa;

    @FXML
    private Label nome;

    @FXML
    private Label lblEmail;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Connection conn = criarConexao();
        String sqlCommand = "SELECT NOME_PRODUTO, MARCA_PRODUTO, PRECO_VENDA_PRODUTO, QTD_STOCK_PRODUTO FROM PRODUTOS";
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
        try {
            while (rs.next()) {
                String marcaProduto = rs.getString("MARCA_PRODUTO");
                cmbMarca.getItems().add(marcaProduto);
                ProdutosN.getItems().addAll(rs.getString("NOME_PRODUTO"));
                MarcaP.getItems().addAll(rs.getString("MARCA_PRODUTO"));
                PreçoP.getItems().addAll(String.valueOf(Float.valueOf(rs.getString("PRECO_VENDA_PRODUTO"))));
                QtdStock.getItems().addAll(String.valueOf(Integer.valueOf(rs.getString("QTD_STOCK_PRODUTO"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        cmbMarca.setOnAction(event -> {
            String selectedMarca = cmbMarca.getValue();
            ProdutosN.getItems().clear();
            MarcaP.getItems().clear();
            PreçoP.getItems().clear();
            QtdStock.getItems().clear();

            String produtosPorMarcaQuery = "SELECT NOME_PRODUTO, MARCA_PRODUTO, PRECO_VENDA_PRODUTO, QTD_STOCK_PRODUTO FROM PRODUTOS WHERE MARCA_PRODUTO = ?";
            try {
                PreparedStatement produtosPorMarcaStmt = conn.prepareStatement(produtosPorMarcaQuery);
                produtosPorMarcaStmt.setString(1, selectedMarca);
                ResultSet produtosPorMarcaResult = produtosPorMarcaStmt.executeQuery();
                while (produtosPorMarcaResult.next()) {
                    String nomeProduto = produtosPorMarcaResult.getString("NOME_PRODUTO");
                    ProdutosN.getItems().add(nomeProduto);
                    MarcaP.getItems().add(produtosPorMarcaResult.getString("MARCA_PRODUTO"));
                    PreçoP.getItems().add(String.valueOf(Float.valueOf(produtosPorMarcaResult.getString("PRECO_VENDA_PRODUTO"))));
                    QtdStock.getItems().add(String.valueOf(Integer.valueOf(produtosPorMarcaResult.getString("QTD_STOCK_PRODUTO"))));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblEmail.setText(email);
    }

    public void btnPesquisar(ActionEvent actionEvent) {
        Connection conn = criarConexao();
        String sqlCommand = "SELECT NOME_PRODUTO, MARCA_PRODUTO, PRECO_VENDA_PRODUTO, QTD_STOCK_PRODUTO FROM PRODUTOS WHERE NOME_PRODUTO LIKE ?";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sqlCommand);
            pst.setString(1, pesquisa.getText());
            ResultSet rs = pst.executeQuery();

            if (!rs.next()) {
                Alert alertProdutoNaoEncontrado = new Alert(Alert.AlertType.ERROR);
                alertProdutoNaoEncontrado.setTitle("Erro!");
                alertProdutoNaoEncontrado.setHeaderText("Produto não encontrado!");
                alertProdutoNaoEncontrado.show();
            } else {
                do {
                    ProdutosN.getItems().clear();
                    MarcaP.getItems().clear();
                    PreçoP.getItems().clear();
                    QtdStock.getItems().clear();

                    String nomeProduto = rs.getString("NOME_PRODUTO");
                    String marcaProduto = rs.getString("MARCA_PRODUTO");
                    float precoProduto = rs.getFloat("PRECO_VENDA_PRODUTO");
                    int qtdEstoque = rs.getInt("QTD_STOCK_PRODUTO");

                    ProdutosN.getItems().add(nomeProduto);
                    MarcaP.getItems().add(marcaProduto);
                    PreçoP.getItems().add(String.valueOf(precoProduto));
                    QtdStock.getItems().add(String.valueOf(qtdEstoque));
                } while (rs.next());
            }
        } catch (SQLException e) {
            Alert alertErro = new Alert(Alert.AlertType.ERROR);
            alertErro.setTitle("Erro!");
            alertErro.setHeaderText("Erro ao executar a consulta!");
            alertErro.setContentText(e.getMessage());
            alertErro.show();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                conn.close();
            } catch (SQLException e) {
            }
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
}
