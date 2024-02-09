package com.example.desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
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

public class ProdutosAdmin implements Initializable {
    @FXML
    private Label nome = null;

    @FXML
    private Label lblemail = null;

    @FXML
    private ListView<String> MarcaP;

    @FXML
    private ListView<Float> PreçoP;

    @FXML
    private ListView<String> ProdutosN;

    @FXML
    private ListView<Integer> QtdStock;

    @FXML
    private ComboBox<String> cmbMarca;

    @FXML
    private TextField pesquisa;

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

    public void btnCriar(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Criar_ProdutoAdmin.fxml"));
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
                PreçoP.getItems().addAll(Float.valueOf(rs.getString("PRECO_VENDA_PRODUTO")));
                QtdStock.getItems().addAll(Integer.valueOf(rs.getString("QTD_STOCK_PRODUTO")));
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
                        PreçoP.getItems().add(Float.valueOf(produtosPorMarcaResult.getString("PRECO_VENDA_PRODUTO")));
                        QtdStock.getItems().add(Integer.valueOf(produtosPorMarcaResult.getString("QTD_STOCK_PRODUTO")));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        });
        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblemail.setText(email);
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
                    PreçoP.getItems().add(precoProduto);
                    QtdStock.getItems().add(qtdEstoque);
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

    public void btnRemover(ActionEvent actionEvent) {
        int selectedIndex = ProdutosN.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            String selectedProduct = ProdutosN.getItems().get(selectedIndex);

            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirmação de remoção");
            confirmDialog.setHeaderText("Confirma remover o produto?");
            confirmDialog.setContentText("Deseja remover o produto selecionado?");
            Optional<ButtonType> result = confirmDialog.showAndWait();

            if (result.get() == ButtonType.OK) {
                Connection conn = criarConexao();
                String sqlCommand = "DELETE FROM PRODUTOS WHERE NOME_PRODUTO = ?";
                try {
                    PreparedStatement pst = conn.prepareStatement(sqlCommand);
                    pst.setString(1, selectedProduct);
                    int affectedRows = pst.executeUpdate();
                    if (affectedRows > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sucesso");
                        alert.setHeaderText("Produto removido com sucesso");
                        alert.show();
                        ProdutosN.getItems().remove(selectedIndex);
                        MarcaP.getItems().remove(selectedIndex);
                        PreçoP.getItems().remove(selectedIndex);
                        QtdStock.getItems().remove(selectedIndex);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText("Não foi possível remover o produto");
                        alert.show();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum produto selecionado");
            alert.setContentText("Por favor, selecione um produto para remover");
            alert.show();
        }
    }
}

