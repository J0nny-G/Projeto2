package com.example.desktop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MenusAdmin {
    public void MenuFornecedores(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ListarFornecedoresAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MenuEncomendas(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ListarEncomendasAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MenuProdutos(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("produtosAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Listar Encomendas");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MenuFuncionarios(MouseEvent mouseEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("ListarFuncionariosAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Listar Encomendas");
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void MenuClientes(MouseEvent mouseEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("ListarClientesAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Listar Encomendas");
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
