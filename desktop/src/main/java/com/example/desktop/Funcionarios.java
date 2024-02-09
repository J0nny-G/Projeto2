package com.example.desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

public class Funcionarios implements Initializable {
    @FXML
    private Label nome = null;

    @FXML
    private Label lblemail = null;

    @FXML
    private ListView<String> idListarFuncE;

    @FXML
    private ListView<String> idListarFuncN;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = criarConexao();
        String sqlCommand = "SELECT NOME_FUNCIONARIO, EMAIL_FUNCIONARIO FROM FUNCIONARIO";
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
                idListarFuncN.getItems().addAll(rs.getString("NOME_FUNCIONARIO"));
                idListarFuncE.getItems().addAll(rs.getString("EMAIL_FUNCIONARIO"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        LoginController loginController = new LoginController();
        String username = String.valueOf(loginController.getInstance().getUsername());
        nome.setText(username);
        String email = loginController.getInstance().getEmail();
        lblemail.setText(email);
    }

    public void btnCriar(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Criar_FuncionarioAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Principal");
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
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

    public void btnRemover(ActionEvent actionEvent) {
        int selectedIndex = idListarFuncN.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            String selectedFuncionario = idListarFuncN.getItems().get(selectedIndex);

            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirmação de remoção");
            confirmDialog.setHeaderText("Confirma remover o funcionário?");
            confirmDialog.setContentText("Deseja remover o funcionário selecionado?");
            Optional<ButtonType> result = confirmDialog.showAndWait();

            if (result.get() == ButtonType.OK) {
                Connection conn = criarConexao();
                String sqlCommand = "DELETE FROM FUNCIONARIO WHERE NOME_FUNCIONARIO = ?";
                try {
                    PreparedStatement pst = conn.prepareStatement(sqlCommand);
                    pst.setString(1, selectedFuncionario);
                    int affectedRows = pst.executeUpdate();
                    if (affectedRows > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sucesso");
                        alert.setHeaderText("Funcionário removido com sucesso");
                        alert.show();
                        idListarFuncN.getItems().remove(selectedIndex);

                        if (selectedIndex < idListarFuncE.getItems().size()) {
                            idListarFuncE.getItems().remove(selectedIndex);
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText("Não foi possível remover o funcionário");
                        alert.show();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum funcionário selecionado");
            alert.setContentText("Por favor, selecione um funcionário para remover");
            alert.show();
        }
    }
}
