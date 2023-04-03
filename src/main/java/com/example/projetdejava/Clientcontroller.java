package com.example.projetdejava;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class Clientcontroller implements Initializable {
    @FXML
    private TableColumn<Client, String> IDColmn;

    @FXML
    private TableColumn<Client, String> MobileColmn;

    @FXML
    private TableColumn<Client, String> NameColmn;

    @FXML
    private TableColumn<Client, String> PrenomColmn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Client> table;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrenom;
    @FXML
    private Button cancel ;
    @FXML
    private TextField txt_search;
    @FXML
    private Label wrongLogIn;


    @FXML
    void Add(ActionEvent event) {
        String stname, mobile, prenom;
        stname = txtName.getText();
        mobile = txtMobile.getText();
        prenom = txtPrenom.getText();
        if (txtName.getText().isEmpty() || txtMobile.getText().isEmpty() || txtPrenom.getText().isEmpty()) {
            wrongLogIn.setText("S'il vous plait entrer tous les informations.");
        } else {
            try {
                pst = con.prepareStatement("INSERT INTO clients(Nom,Prix_Credit,Telephone)values(?,?,?)");
                pst.setString(1, stname);
                pst.setString(2, mobile);
                pst.setString(3, prenom);
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajouter Client");

                alert.setHeaderText("Ajouter Client");
                alert.setContentText("Ajouter avec succées!");

                alert.showAndWait();

                table();

                txtName.setText("");
                txtMobile.setText("");
                txtPrenom.setText("");
                txtName.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(Clientcontroller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void table()
    {
        Connect();
        ObservableList<Client> client = FXCollections.observableArrayList();
        try
        {
            pst = con.prepareStatement("SELECT ID,Nom,Prix_Credit,Telephone FROM clients");
            ResultSet rs = pst.executeQuery();
            {
                while (rs.next())
                {
                    Client st = new Client();
                    st.setId(rs.getString("ID"));
                    st.setName(rs.getString("Nom"));
                    st.setMobile(rs.getString("Prix_Credit"));
                    st.setCourse(rs.getString("Telephone"));
                    client.add(st);
                }
            }
            table.setItems(client);
            IDColmn.setCellValueFactory(f -> f.getValue().idProperty());
            NameColmn.setCellValueFactory(f -> f.getValue().nameProperty());
            MobileColmn.setCellValueFactory(f -> f.getValue().mobileProperty());
            PrenomColmn.setCellValueFactory(f -> f.getValue().prenomProperty());



        }

        catch (SQLException ex)
        {
            Logger.getLogger(Clientcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory( tv -> {
            TableRow<Client> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  table.getSelectionModel().getSelectedIndex();
                    id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                    txtName.setText(table.getItems().get(myIndex).getName());
                    txtMobile.setText(table.getItems().get(myIndex).getMobile());
                    txtPrenom.setText(table.getItems().get(myIndex).getCourse());



                }
            });
            return myRow;
        });


    }

    @FXML
    void Delete(ActionEvent event) {
        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));


        try
        {
            pst = con.prepareStatement("delete from clients where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Supprimer Client");

            alert.setHeaderText("Supprimer Client");
            alert.setContentText("Suppression avec succées!");

            alert.showAndWait();
            table();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Clientcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void Update(ActionEvent event) {
        String stname,mobile,prenom;

        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));

        stname = txtName.getText();
        mobile = txtMobile.getText();
        prenom = txtPrenom.getText();
        try
        {
            pst = con.prepareStatement("update clients set Nom = ?,Prix_Credit = ? ,Telephone = ? where id = ? ");
            pst.setString(1, stname);
            pst.setString(2, mobile);
            pst.setString(3, prenom);
            pst.setInt(4, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modifier Client");

            alert.setHeaderText("Modifier Client");
            alert.setContentText("Modifier avec succés!");

            alert.showAndWait();
            table();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Clientcontroller.class.getName()).log(Level.SEVERE, null, ex);
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
        h.changeScene("afterLogin.fxml");

    }
    public void cancelButtonOnAction(ActionEvent e){
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    public void searchClient() {
        txt_search.setOnKeyReleased(e -> {
            if (txt_search.getText().equals("")) {
                table();
            } else {
                String sql = "SELECT * FROM clients WHERE Nom Like '%" + txt_search.getText() + "%'";
                ObservableList<Client> client = FXCollections.observableArrayList();
                try (PreparedStatement pst = con.prepareStatement(sql);
                     ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Client st = new Client();
                        st.setId(rs.getString("ID"));
                        st.setName(rs.getString("Nom"));
                        st.setMobile(rs.getString("Prix_Credit"));
                        st.setCourse(rs.getString("Telephone"));
                        client.add(st);
                    }
                    table.setItems(client);
                    IDColmn.setCellValueFactory(f -> f.getValue().idProperty());
                    NameColmn.setCellValueFactory(f -> f.getValue().nameProperty());
                    MobileColmn.setCellValueFactory(f -> f.getValue().mobileProperty());
                    PrenomColmn.setCellValueFactory(f -> f.getValue().prenomProperty());
                } catch (SQLException ex) {
                    Logger.getLogger(Clientcontroller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connect();
        table();
        searchClient();

    }
}
