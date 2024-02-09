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
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.desktop.DB.criarConexao;

public class MenuPrincipalFuncionario implements Initializable {

    @FXML
    private Label nome;

    @FXML
    private Label lblEmail;

    @FXML
    private Label vendasSemanal;

    @FXML
    private Label numerovendas;

    @FXML
    private Label valordiario;

    @FXML
    private Label valorsemanal;

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
        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblEmail.setText(email);

        MenuPrincipalAdmin menuPrincipalAdmin = new MenuPrincipalAdmin();

        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        double mediaVendas = menuPrincipalAdmin.calcularMediaVendasDiarias();
        String mediaFormatado = decimalFormat.format(mediaVendas);
        valordiario.setText(mediaFormatado);

        double balancoSemanal = menuPrincipalAdmin.calcularMediaBalancoSemanal();
        String balancoFormatado = decimalFormat.format(balancoSemanal);
        valorsemanal.setText(balancoFormatado);

        int nvendas = 0;
        try {
            nvendas = calcularNumeroVendasDiarias();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        numerovendas.setText(String.valueOf(nvendas));

        int nvendassemanal = calcularNumeroVendasSemanal();
        vendasSemanal.setText(String.valueOf(nvendassemanal));

    }

    public int calcularNumeroVendasDiarias() throws SQLException {
        Connection conn = criarConexao();

        // Obter a data atual
        LocalDate dataAtual = LocalDate.now();

        // Formatar a data no formato adequado para consulta SQL
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = dataAtual.format(formatter);

        String sql = "SELECT COUNT(*) FROM vendas WHERE data_venda = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, dataFormatada);
        ResultSet rs = stmt.executeQuery();


        int numeroVendas = 0;
        if (rs.next()) {
            numeroVendas = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        conn.close();

        return numeroVendas;
    }

    public int calcularNumeroVendasSemanal() {
        int numeroVendasSemanal = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = criarConexao();

            // Obter a data atual
            LocalDate dataAtual = LocalDate.now();

            // Calcular a data uma semana atrás
            LocalDate dataUmaSemanaAtras = dataAtual.minusWeeks(1);

            String sql = "SELECT COUNT(*) AS total_vendas FROM vendas WHERE data_venda >= ? AND data_venda <= ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, dataUmaSemanaAtras.toString());
            stmt.setString(2, dataAtual.toString());
            rs = stmt.executeQuery();

            if (rs.next()) {
                numeroVendasSemanal = rs.getInt("total_vendas");
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

        return numeroVendasSemanal;
    }

}
