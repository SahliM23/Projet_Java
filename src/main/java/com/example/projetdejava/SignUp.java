package com.example.projetdejava;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUp implements Initializable {
    @FXML
    private TextField Adresse;

    @FXML
    private TextField age;

    @FXML
    private Button button;

    @FXML
    private Button identifier;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private Label wrongLogIn;

    @FXML
    void identifierButtonOnAction(ActionEvent event) {
        String stname, Age, adresse, Password;
        stname = name.getText();
        Age = age.getText();
        adresse = Adresse.getText();
        Password = password.getText();
        if (name.getText().isEmpty() || password.getText().isEmpty() || age.getText().isEmpty() || Adresse.getText().isEmpty()) {
            wrongLogIn.setText("S'il vous plait entrer tes informations.");
        } else {
            try {
                pst = con.prepareStatement("INSERT INTO users(username,Age,Adresse,password)values(?,?,?,?)");
                pst.setString(1, stname);
                pst.setString(2, Age);
                pst.setString(3, adresse);
                pst.setString(4, Password);
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Compte créer avec succés");

                alert.setHeaderText("Compte créer avec succés");
                alert.setContentText("Bienvenue Chez Essalaf!");

                alert.showAndWait();


                name.setText("");
                age.setText("");
                Adresse.setText("");
                password.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(Clientcontroller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;



    public void Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/javaproject","root","");
        } catch (ClassNotFoundException ex) {

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void userLogOut(ActionEvent event) throws IOException {
        HelloApplication h = new HelloApplication();
        h.changeScene("hello-view.fxml");

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connect();
    }
}
