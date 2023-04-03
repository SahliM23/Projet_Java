package com.example.projetdejava ;
import com.example.projetdejava.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogIn {

    public LogIn() {

    }

    @FXML
    private Button button;
    @FXML
    private Label wrongLogIn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button cancelButton;
    @FXML
    private Button identifier;

    public void cancelButtonOnAction(ActionEvent e){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void userLogIn(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        checkLogin();

    }
    public void identifierButton(ActionEvent event) throws IOException {
        HelloApplication h = new HelloApplication();
        h.changeScene("Signup.fxml");

    }

    private void checkLogin() throws IOException, SQLException, ClassNotFoundException {
        HelloApplication m = new HelloApplication();
        if(username.getText().isBlank() == false && password.getText().isBlank() == false) {
            validateLogin();

        } else {
            wrongLogIn.setText("S'il vous plait entrer tes informations.");
        }

    }
    public void validateLogin() throws SQLException, ClassNotFoundException {
        HelloApplication m = new HelloApplication();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT count(1) FROM Users WHERE username ='" +username.getText()+ "' AND password = '" +password.getText()+ "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()){
                if(queryResult.getInt(1 )==1){
                    wrongLogIn.setText("succés!");

                    m.changeScene("afterLogin.fxml");

                }else{
                    wrongLogIn.setText("Echec de connexion, Répètez autre fois.");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}