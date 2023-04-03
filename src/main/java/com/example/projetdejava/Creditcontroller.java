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

public class Creditcontroller implements Initializable {
    @FXML
    private TableColumn<Commande, String> IDColmn;

    @FXML
    private TableColumn<Commande, String> MobileColmn;

    @FXML
    private TableColumn<Commande, String> NameColmn;

    @FXML
    private TableColumn<Commande, String> PrenomColmn;

    @FXML
    private TableColumn<Commande, String> PrixColmn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Commande> table;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrenom;
    @FXML
    private Button cancel ;
    @FXML
    private TextField txtPrix;
    @FXML
    private TextField txt_search;
    @FXML
    private Label wrongLogIn;



    @FXML
    void Add(ActionEvent event) {
        String stname, mobile, prenom, prix_total;
        stname = txtName.getText();
        mobile = txtMobile.getText();
        prenom = txtPrenom.getText();
        prix_total = txtPrix.getText();
        if (txtName.getText().isEmpty() || txtMobile.getText().isEmpty() || txtPrenom.getText().isEmpty() || txtPrix.getText().isEmpty()) {
            wrongLogIn.setText("S'il vous plait entrer tous les informations.");
        } else {
            try {
                pst = con.prepareStatement("INSERT INTO credits(Client,Produits,Quantite,Prix_Credit)values(?,?,?,?)");
                pst.setString(1, stname);
                pst.setString(2, mobile);
                pst.setString(3, prenom);
                pst.setString(4, prix_total);
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajouter un crédit");

                alert.setHeaderText("Ajouter un crédit");
                alert.setContentText("Ajouter avec succés");

                alert.showAndWait();

                table();

                txtName.setText("");
                txtMobile.setText("");
                txtPrenom.setText("");
                txtPrix.setText("");
                txtName.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(Creditcontroller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void table()
    {
        Connect();
        ObservableList<Commande> commande = FXCollections.observableArrayList();
        try
        {
            pst = con.prepareStatement("SELECT ID_Credit,Client,Produits,Quantite,Prix_Credit FROM credits");
            ResultSet rs = pst.executeQuery();
            {
                while (rs.next())
                {
                    Commande st = new Commande();
                    st.setId(rs.getString("ID_Credit"));
                    st.setName(rs.getString("Client"));
                    st.setMobile(rs.getString("Produits"));
                    st.setCourse(rs.getString("Quantite"));
                    st.setPrix(rs.getString("Prix_Credit"));
                    commande.add(st);
                }
            }
            table.setItems(commande);
            IDColmn.setCellValueFactory(f -> f.getValue().idProperty());
            NameColmn.setCellValueFactory(f -> f.getValue().nameProperty());
            MobileColmn.setCellValueFactory(f -> f.getValue().mobileProperty());
            PrenomColmn.setCellValueFactory(f -> f.getValue().prenomProperty());
            PrixColmn.setCellValueFactory(f -> f.getValue().prixProperty());



        }

        catch (SQLException ex)
        {
            Logger.getLogger(Creditcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setRowFactory( tv -> {
            TableRow<Commande> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  table.getSelectionModel().getSelectedIndex();
                    id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                    txtName.setText(table.getItems().get(myIndex).getName());
                    txtMobile.setText(table.getItems().get(myIndex).getMobile());
                    txtPrenom.setText(table.getItems().get(myIndex).getCourse());
                    txtPrix.setText(table.getItems().get(myIndex).getPrix());



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
            pst = con.prepareStatement("delete from credits where ID_Credit = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Crédit");

            alert.setHeaderText("Crédit");
            alert.setContentText("Suppression avec succés!");

            alert.showAndWait();
            table();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Creditcontroller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void Update(ActionEvent event) {
        String stname,mobile,prenom,prix_total;

        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));

        stname = txtName.getText();
        mobile = txtMobile.getText();
        prenom = txtPrenom.getText();
        prix_total = txtPrix.getText();
        try
        {
            pst = con.prepareStatement("update credits set Client = ?,Produits = ? ,Quantite = ? ,Prix_Credit = ? where ID_Credit = ? ");
            pst.setString(1, stname);
            pst.setString(2, mobile);
            pst.setString(3, prenom);
            pst.setString(4, prix_total);
            pst.setInt(5, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modifier un crédit");

            alert.setHeaderText("Modifier un crédit");
            alert.setContentText("Modifier ave succés!");

            alert.showAndWait();
            table();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Creditcontroller.class.getName()).log(Level.SEVERE, null, ex);
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
    public void searchClient() {
        txt_search.setOnKeyReleased(e -> {
            if (txt_search.getText().equals("")) {
                table();
            } else {
                String sql = "SELECT * FROM credits WHERE Client Like '%" + txt_search.getText() + "%'";
                ObservableList<Commande> commande = FXCollections.observableArrayList();
                try (PreparedStatement pst = con.prepareStatement(sql);
                     ResultSet rs = pst.executeQuery()) {
                    while (rs.next())
                    {
                        Commande st = new Commande();
                        st.setId(rs.getString("ID_Credit"));
                        st.setName(rs.getString("Client"));
                        st.setMobile(rs.getString("Produits"));
                        st.setCourse(rs.getString("Quantite"));
                        st.setPrix(rs.getString("Prix_Credit"));
                        commande.add(st);
                    }
                    table.setItems(commande);
                    IDColmn.setCellValueFactory(f -> f.getValue().idProperty());
                    NameColmn.setCellValueFactory(f -> f.getValue().nameProperty());
                    MobileColmn.setCellValueFactory(f -> f.getValue().mobileProperty());
                    PrenomColmn.setCellValueFactory(f -> f.getValue().prenomProperty());
                    PrixColmn.setCellValueFactory(f -> f.getValue().prixProperty());
                } catch (SQLException ex) {
                    Logger.getLogger(Clientcontroller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    public void userLogOut(ActionEvent event) throws IOException {
        HelloApplication h = new HelloApplication();
        h.changeScene("afterLogin.fxml");

    }
    public void cancelButtonOnAction(ActionEvent e){
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
 


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connect();
        table();
        searchClient();

    }
}
