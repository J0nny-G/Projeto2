package com.example.desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.desktop.DB.criarConexao;

public class CriarVendaFuncionario implements Initializable {
    @FXML
    private Label nome;

    @FXML
    private Label lblEmail;

    @FXML
    private ListView<String> carrinho;

    @FXML
    private ComboBox<String> cmbCliente;

    @FXML
    private ComboBox<String> cmbProduto;


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

    public void btnVoltar(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ListarVendaFuncionario.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblEmail.setText(email);

        Connection conn = criarConexao();
        String sqlClientes = "SELECT ID_CLIENTE, NOME FROM CLIENTES";
        PreparedStatement pstClientes = null;
        try {
            pstClientes = conn.prepareStatement(sqlClientes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rsClientes = null;
        try {
            rsClientes = pstClientes.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                if (!rsClientes.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String fornecedorid;
            String fornecedor;
            try {
                fornecedorid = rsClientes.getString("ID_Cliente");
                fornecedor = rsClientes.getString("NOME");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String item = fornecedorid + "-" + fornecedor;
            cmbCliente.getItems().addAll(item);
        }

        String sqlProdutos = "SELECT ID_PRODUTO, NOME_PRODUTO FROM PRODUTOS";
        PreparedStatement pstProdutos = null;
        try {
            pstProdutos = conn.prepareStatement(sqlProdutos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rsProdutos = null;
        try {
            rsProdutos = pstProdutos.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                if (!rsProdutos.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String produtoid;
            String produto;
            try {
                produtoid = rsProdutos.getString("ID_PRODUTO");
                produto = rsProdutos.getString("NOME_PRODUTO");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String item = produtoid + "-" + produto;
            cmbProduto.getItems().addAll(item);
        }
    }

    public void btnAdicionar(ActionEvent actionEvent) {
        String produtoSelecionado = cmbProduto.getValue();

        if (produtoSelecionado != null) {
            carrinho.getItems().add(produtoSelecionado);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Produto adicionado");
            alert.setContentText("O produto foi adicionado ao carrinho.");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhum produto selecionado");
            alert.setContentText("Por favor, selecione um produto para adicionar ao carrinho.");
            alert.showAndWait();
        }
    }

    public void btnConfirmar(ActionEvent actionEvent) {
        String vendaSelecionada = cmbCliente.getValue();

        if (vendaSelecionada != null && !carrinho.getItems().isEmpty()) {
            Connection conn = criarConexao();
            try {
                LoginController loginController = new LoginController();

                String sqlCommandVendas = "INSERT INTO vendas (ID_CLIENTE, ID_FUNCIONARIO, DATA_VENDA, VALOR_TOTAL_VENDA) VALUES (?, ?, ?, ?)";
                PreparedStatement pstVendas = conn.prepareStatement(sqlCommandVendas, Statement.RETURN_GENERATED_KEYS);
                pstVendas.setString(1, vendaSelecionada.split("-")[0]);
                pstVendas.setString(2, String.valueOf(loginController.getInstance().getId()));
                pstVendas.setString(3, String.valueOf(new Date(System.currentTimeMillis())));
                pstVendas.setString(4, String.valueOf(calcularValorTotalVenda()));
                pstVendas.executeUpdate();

                ResultSet generatedKeys = pstVendas.getGeneratedKeys();
                int idVenda = -1;
                if (generatedKeys.next()) {
                    idVenda = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID da venda gerado.");
                }

                // Limpa o carrinho
                carrinho.getItems().clear();

                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmação de venda");
                confirmAlert.setHeaderText("Deseja emitir uma fatura?");
                confirmAlert.setContentText("Selecione uma opção.");
                ButtonType simButton = new ButtonType("Sim");
                ButtonType naoButton = new ButtonType("Não");
                confirmAlert.getButtonTypes().setAll(simButton, naoButton);
                Optional<ButtonType> confirmResult = confirmAlert.showAndWait();

                if (confirmResult.isPresent() && confirmResult.get() == simButton) {
                    TextInputDialog nifDialog = new TextInputDialog();
                    nifDialog.setTitle("Informação de fatura");
                    nifDialog.setHeaderText("Digite o NIF do cliente:");
                    nifDialog.setContentText("NIF:");
                    Optional<String> nifResult = nifDialog.showAndWait();

                    if (nifResult.isPresent() && !nifResult.get().isEmpty()) {
                        String sqlCommandFatura = "INSERT INTO fatura (ID_CLIENTE, ID_VENDA, NIF) VALUES (?, ?, ?)";
                        PreparedStatement pstFatura = conn.prepareStatement(sqlCommandFatura);
                        pstFatura.setString(1, vendaSelecionada.split("-")[0]);
                        pstFatura.setInt(2, idVenda);
                        pstFatura.setString(3, nifResult.get());
                        pstFatura.executeUpdate();
                        pstFatura.close();


                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Sucesso");
                        successAlert.setHeaderText("Venda efetuada e fatura emitida");
                        successAlert.setContentText("Venda concluída e fatura emitida com sucesso.");
                        successAlert.showAndWait();

                        imprimirFatura(idVenda);

                    } else {
                        // Exibe mensagem de erro caso o NIF não tenha sido fornecido
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erro");
                        errorAlert.setHeaderText("NIF inválido");
                        errorAlert.setContentText("Por favor, digite um NIF válido.");
                        errorAlert.showAndWait();
                    }
                } else {
                    // Exibe mensagem de sucesso sem fatura
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Sucesso");
                    successAlert.setHeaderText("Venda efetuada");
                    successAlert.setContentText("Venda concluída com sucesso.");
                    successAlert.showAndWait();
                }

                pstVendas.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Exibe mensagem de erro em caso de falha na execução do SQL
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erro");
                errorAlert.setHeaderText("Erro na venda");
                errorAlert.setContentText("Ocorreu um erro durante a venda. Por favor, tente novamente.");
                errorAlert.showAndWait();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Exibe mensagem de erro se não houver cliente selecionado ou carrinho vazio
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erro");
            errorAlert.setHeaderText("Erro na venda");
            errorAlert.setContentText("Selecione um cliente e adicione itens ao carrinho antes de confirmar a venda.");
            errorAlert.showAndWait();
        }
    }

    private void imprimirFatura(int idVenda) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        Printer printer = Printer.getDefaultPrinter();

        if (printerJob != null && printer != null) {
            printerJob.setPrinter(printer);

            boolean showDialog = printerJob.showPrintDialog(null);
            if (showDialog) {
                Node faturaNode = criarFaturaNode(idVenda);
                if (faturaNode != null) {
                    double scaleX = printerJob.getJobSettings().getPageLayout().getPrintableWidth() / faturaNode.getBoundsInParent().getWidth();
                    double scaleY = printerJob.getJobSettings().getPageLayout().getPrintableHeight() / faturaNode.getBoundsInParent().getHeight();
                    Scale scale = new Scale(scaleX, scaleY);
                    faturaNode.getTransforms().add(scale);

                    boolean success = printerJob.printPage(faturaNode);
                    if (success) {
                        printerJob.endJob();
                        // Exibe uma mensagem de sucesso após a impressão
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Impressão bem-sucedida");
                        successAlert.setHeaderText("A fatura foi impressa com sucesso.");
                        successAlert.showAndWait();
                    } else {
                        // Exibe uma mensagem de erro em caso de falha na impressão
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erro na impressão");
                        errorAlert.setHeaderText("Ocorreu um erro durante a impressão da fatura.");
                        errorAlert.setContentText("Por favor, verifique a configuração da impressora e tente novamente.");
                        errorAlert.showAndWait();
                    }
                } else {
                    // Exibe uma mensagem de erro caso o nó da fatura seja nulo
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erro na impressão");
                    errorAlert.setHeaderText("Ocorreu um erro ao gerar o conteúdo da fatura.");
                    errorAlert.setContentText("Por favor, tente novamente.");
                    errorAlert.showAndWait();
                }
            }
        } else {
            // Exibe uma mensagem de erro caso a impressora não esteja disponível
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erro na impressão");
            errorAlert.setHeaderText("Nenhuma impressora encontrada.");
            errorAlert.setContentText("Por favor, verifique as configurações da impressora e tente novamente.");
            errorAlert.showAndWait();
        }
    }


    private Node criarFaturaNode(int idVenda) {
        return null;
    }

    private class FaturaPrintable implements Printable {
        private int idVenda;

        public FaturaPrintable(int idVenda) {
            this.idVenda = idVenda;
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            // Renderize o conteúdo da fatura usando o objeto Graphics2D
            // Você pode usar os métodos e propriedades do Graphics2D para desenhar o conteúdo da fatura

            return PAGE_EXISTS;
        }
    }

    private double calcularValorTotalVenda() throws SQLException {
        double valorTotal = 0.0;

        for (String produto : carrinho.getItems()) {
            String idProduto = produto.split("-")[0];

            double valorItem = obterValorDoProduto(idProduto);
            int quantidade = 1;

            valorTotal += valorItem * quantidade;
        }

        return valorTotal;
    }

    private float obterValorDoProduto(String idProduto) {
        try {
            Connection conn = criarConexao();
            String sqlProdutos = "SELECT ID_PRODUTO, PRECO_VENDA_PRODUTO FROM PRODUTOS";
            PreparedStatement pstProdutos = conn.prepareStatement(sqlProdutos);
            ResultSet rsProdutos = pstProdutos.executeQuery();

            float preco = 0;
            while (rsProdutos.next()) {
                if (rsProdutos.getString("ID_PRODUTO").equals(idProduto)) {
                    preco = rsProdutos.getFloat("PRECO_VENDA_PRODUTO");
                }
            }

            rsProdutos.close();
            pstProdutos.close();
            conn.close();

            return preco;
        } catch (SQLException e) {
            System.out.println("Erro ao obter valor do produto: " + e.getMessage());
            throw new RuntimeException("Falha ao obter valor do produto.", e);
        }
    }



}
