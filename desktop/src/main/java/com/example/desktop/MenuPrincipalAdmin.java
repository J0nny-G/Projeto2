package com.example.desktop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.desktop.DB.criarConexao;

public class MenuPrincipalAdmin implements Initializable {
    @FXML
    private Label nome = null;

    @FXML
    private Label lblemail = null;

    @FXML
    private Label BalancoSe;

    @FXML
    private Label Balancodi;

    @FXML
    private Rectangle btnFornecedores;

    @FXML
    private Label encomendasPendetnes;

    @FXML
    private Label restock;

    public MenuPrincipalAdmin() {
    }

    @FXML
    void btnSair(MouseEvent mouseEvent) {
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
    public void btnClientes(MouseEvent mouseEvent) {
        MenusAdmin menusAdmin = new MenusAdmin();
        menusAdmin.MenuClientes(mouseEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblemail.setText(email);
        System.out.println(username);

        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        double mediaVendas = calcularMediaVendasDiarias();
        String mediaFormatado = decimalFormat.format(mediaVendas);
        Balancodi.setText(mediaFormatado);

        double balancoSemanal = calcularMediaBalancoSemanal();
        String balancoFormatado = decimalFormat.format(balancoSemanal);
        BalancoSe.setText(balancoFormatado);

        int numeroPendentes = 0;
        try {
            numeroPendentes = obterNumeroEncomendasPendentes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        encomendasPendetnes.setText(String.valueOf(numeroPendentes));

        String produtoLimiteStock = null;
        try {
            produtoLimiteStock = obterProdutoLimiteStock();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (produtoLimiteStock != null) {
            restock.setText(produtoLimiteStock);
        } else {
            restock.setText("Nenhum produto");
        }

    }


    public double calcularMediaVendasDiarias() {
        double mediaVendas = 0.0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = criarConexao();

            // Obter a data atual
            LocalDate dataAtual = LocalDate.now();
            String dataFormatada = dataAtual.format(DateTimeFormatter.ISO_DATE);

            String sql = "SELECT AVG(valor_total_venda) AS media FROM vendas WHERE data_venda = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, dataFormatada);
            rs = stmt.executeQuery();

            if (rs.next()) {
                mediaVendas = rs.getDouble("media");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return mediaVendas;
    }

    public double calcularMediaBalancoSemanal() {
        double balancoSemanal = 0.0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = criarConexao();


            LocalDate dataAtual = LocalDate.now();
            String dataFormatada = dataAtual.format(DateTimeFormatter.ISO_DATE);

            LocalDate dataSeteDiasAtras = dataAtual.minusDays(7);
            String dataSeteDiasAtrasFormatada = dataSeteDiasAtras.format(DateTimeFormatter.ISO_DATE);


            String sql = "SELECT AVG(valor_total_venda) AS balanco FROM vendas WHERE data_venda >= ? AND data_venda <= ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, dataSeteDiasAtrasFormatada);
            stmt.setString(2, dataFormatada);
            rs = stmt.executeQuery();


            if (rs.next()) {
                balancoSemanal = rs.getDouble("balanco");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return balancoSemanal;
    }

    public int obterNumeroEncomendasPendentes() throws SQLException {
        Connection conn = criarConexao();

        String sql = "SELECT COUNT(*) FROM PEDIDO_COMPRA WHERE STATUS_PEDIDO_COMPRA = 'Pendente'";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int numeroEncomendasPendentes = rs.getInt(1);

        return numeroEncomendasPendentes;
    }

    public String obterProdutoLimiteStock() throws SQLException {
        Connection conn = criarConexao();

        String sql = "SELECT NOME_PRODUTO FROM PRODUTOS WHERE QTD_STOCK_PRODUTO < QTD_MIN_STOCK";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String nomeProduto = rs.getString("NOME_PRODUTO");
            return nomeProduto;
        } else {
            return null;
        }
    }

}
