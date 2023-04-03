package com.example.projetdejava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu {

    @FXML
    private Button logout;
    private Button Clients;
    @FXML
    private Button cancel ;


    public void userLogOut(ActionEvent event) throws IOException {
        HelloApplication h = new HelloApplication();
        h.changeScene("hello-view.fxml");

    }
    public void cancelButtonOnAction(ActionEvent e){
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void clientButton(ActionEvent event) throws IOException {
        HelloApplication h = new HelloApplication();
        h.changeScene("Client.fxml");

    }
    public void produitButton(ActionEvent event) throws IOException {
        HelloApplication h = new HelloApplication();
        h.changeScene("Produits.fxml");

    }
    public void commandeButton(ActionEvent event) throws IOException {
        HelloApplication h = new HelloApplication();
        h.changeScene("commandes.fxml");

    }
    public void creditButton(ActionEvent event) throws IOException {
        HelloApplication h = new HelloApplication();
        h.changeScene("Credit.fxml");

    }
}