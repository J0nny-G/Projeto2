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

public class Encomendas implements Initializable {
    @FXML
    private Label nome;

    @FXML
    private Label lblemail;

    @FXML
    private ListView<String> idListarDE;

    @FXML
    private ListView<String> idListarEE;

    @FXML
    private ListView<String> idListarESE;

    @FXML
    private ListView<String> idListarFE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblemail.setText(email);

        Connection conn = criarConexao();
        String sqlCommand = "SELECT PC.ID_PEDIDO_COMPRA, F.NOME_FORNECEDOR , PC.STATUS_PEDIDO_COMPRA, PC.DATA_PEDIDO_COMPRA FROM PEDIDO_COMPRA PC JOIN FORNECEDORES F ON PC.ID_FORNECEDOR = F.ID_FORNECEDOR";
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

        while (true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                idListarEE.getItems().addAll(rs.getString("ID_PEDIDO_COMPRA"));
                idListarFE.getItems().addAll(rs.getString("NOME_FORNECEDOR"));
                idListarESE.getItems().addAll(rs.getString("STATUS_PEDIDO_COMPRA"));
                idListarDE.getItems().addAll(rs.getString("DATA_PEDIDO_COMPRA"));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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

    public void btnEncomenda(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("FazerEncomendaAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Principal");
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void btnEstado(ActionEvent actionEvent) {
        String encomendaSelecionada = idListarEE.getSelectionModel().getSelectedItem();

        if (encomendaSelecionada != null) {
            ObservableList<String> estados = FXCollections.observableArrayList("Pendente", "Em andamento", "Concluído");

            ChoiceDialog<String> dialog = new ChoiceDialog<>(estados.get(0), estados);
            dialog.setTitle("Editar Estado");
            dialog.setHeaderText("Selecione o novo estado");
            dialog.setContentText("Estado:");

            dialog.showAndWait().ifPresent(novoEstado -> {
                try {
                    Connection conn = criarConexao();
                    String sqlCommand = "UPDATE PEDIDO_COMPRA SET STATUS_PEDIDO_COMPRA = ? WHERE ID_PEDIDO_COMPRA = ?";
                    PreparedStatement pst = conn.prepareStatement(sqlCommand);
                    pst.setString(1, novoEstado);
                    pst.setString(2, encomendaSelecionada);
                    pst.executeUpdate();
                    pst.close();
                    conn.close();

                    idListarESE.getItems().set(idListarEE.getSelectionModel().getSelectedIndex(), novoEstado);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhuma encomenda selecionada");
            alert.setContentText("Por favor, selecione uma encomenda para editar o estado.");
            alert.showAndWait();
        }
    }
    public void btnRemover(ActionEvent actionEvent) {
        String encomendaSelecionada = idListarEE.getSelectionModel().getSelectedItem();

        if (encomendaSelecionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de remoção de encomenda");
            alert.setHeaderText("Confirma remover encomenda?");
            alert.setContentText("Deseja remover a encomenda selecionada?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    Connection conn = criarConexao();
                    String sqlCommand = "DELETE FROM PEDIDO_COMPRA WHERE ID_PEDIDO_COMPRA = ?";
                    PreparedStatement pst = conn.prepareStatement(sqlCommand);
                    pst.setString(1, encomendaSelecionada);
                    pst.executeUpdate();
                    pst.close();
                    conn.close();

                    idListarEE.getItems().remove(encomendaSelecionada);
                    idListarFE.getItems().remove(idListarEE.getSelectionModel().getSelectedIndex());
                    idListarESE.getItems().remove(idListarEE.getSelectionModel().getSelectedIndex());
                    idListarDE.getItems().remove(idListarEE.getSelectionModel().getSelectedIndex());

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhuma encomenda selecionada");
            alert.setContentText("Por favor, selecione uma encomenda para remover.");
            alert.showAndWait();
        }
    }
}
