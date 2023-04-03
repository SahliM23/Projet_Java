package com.example.projetdejava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Produitcontroller implements Initializable {
    @FXML
    private TableColumn<Produit, String> IDColmn;

    @FXML
    private TableColumn<Produit, String> MobileColmn;

    @FXML
    private TableColumn<Produit, String> NameColmn;

    @FXML
    private TableColumn<Produit, String> PrenomColmn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Produit> table;

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
                pst = con.prepareStatement("INSERT INTO produits(Nom,Prix,Quantite)values(?,?,?)");
                pst.setString(1, stname);
                pst.setString(2, mobile);
                pst.setString(3, prenom);
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajouter Produit");

                alert.setHeaderText("Ajouter Produit");
                alert.setContentText("Ajouter avec succées!");
                alert.showAndWait();

                table();

                txtName.setText("");
                txtMobile.setText("");
                txtPrenom.setText("");
                txtName.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(Produitcontroller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void table()
    {
        Connect();
        ObservableList<Produit> produit = FXCollections.observableArrayList();
        try
        {
            pst = con.prepareStatement("SELECT ID,Nom,Prix,Quantite FROM produits");
            ResultSet rs = pst.executeQuery();
            {
                while (rs.next())
                {
                    Produit st = new Produit();
                    st.setId(rs.getString("ID"));
                    st.setName(rs.getString("Nom"));
                    st.setMobile(rs.getString("Prix"));
                    st.setCourse(rs.getString("Quantite"));
                    produit.add(st);
                }
            }
            table.setItems(produit);
            IDColmn.setCellValueFactory(f -> f.getValue().idProperty());
            NameColmn.setCellValueFactory(f -> f.getValue().nameProperty());
            MobileColmn.setCellValueFactory(f -> f.getValue().mobileProperty());
            PrenomColmn.setCellValueFactory(f -> f.getValue().prenomProperty());



        }

        catch (SQLException ex)
        {
            Logger.getLogger(Produitcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory( tv -> {
            TableRow<Produit> myRow = new TableRow<>();
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
            pst = con.prepareStatement("delete from produits where id = ? ");
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
            Logger.getLogger(Produitcontroller.class.getName()).log(Level.SEVERE, null, ex);
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
            pst = con.prepareStatement("update produits set Nom = ?,Prix = ? ,Quantite = ? where id = ? ");
            pst.setString(1, stname);
            pst.setString(2, mobile);
            pst.setString(3, prenom);
            pst.setInt(4, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modifier Produit");

            alert.setHeaderText("Modifier Produit");
            alert.setContentText("Modifier avec succés!");

            alert.showAndWait();
            table();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Produitcontroller.class.getName()).log(Level.SEVERE, null, ex);
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
    public void searchProduit() {
        txt_search.setOnKeyReleased(e -> {
            if (txt_search.getText().equals("")) {
                table();
            } else {
                String sql = "SELECT * FROM produits WHERE Nom Like '%" + txt_search.getText() + "%'";
                ObservableList<Produit> produit = FXCollections.observableArrayList();
                try (PreparedStatement pst = con.prepareStatement(sql);
                     ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Produit st = new Produit();
                        st.setId(rs.getString("ID"));
                        st.setName(rs.getString("Nom"));
                        st.setMobile(rs.getString("Prix"));
                        st.setCourse(rs.getString("Quantite"));
                        produit.add(st);
                    }
                    table.setItems(produit);
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
        searchProduit();

    }
}
