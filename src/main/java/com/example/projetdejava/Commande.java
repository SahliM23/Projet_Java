package com.example.projetdejava;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Commande {
    private final StringProperty ID ;
    private final StringProperty Client;
    private final StringProperty Produits;
    private final StringProperty  Quantite;
    private final StringProperty  Prix_total;

    public Commande()
    {
        ID = new SimpleStringProperty(this, "ID");
        Client = new SimpleStringProperty(this, "Client");
        Produits= new SimpleStringProperty(this, "Produits");
        Quantite= new SimpleStringProperty(this, "Quantite");
       Prix_total= new SimpleStringProperty(this, "Prix_total");
    }


    public StringProperty idProperty() { return ID; }
    public String getId() { return ID.get(); }
    public void setId(String newId) { ID.set(newId); }

    public StringProperty nameProperty() { return Client; }
    public String getName() { return Client.get(); }
    public void setName(String newName) { Client.set(newName); }

    public StringProperty mobileProperty() { return Produits; }
    public String getMobile() { return Produits.get(); }
    public void setMobile(String newMobile) { Produits.set(newMobile); }

    public StringProperty prenomProperty() { return Quantite; }
    public String getCourse() { return Quantite.get(); }
    public void setCourse(String newCourse) { Quantite.set(newCourse); }
    public StringProperty prixProperty() { return Prix_total; }
    public String getPrix() { return Prix_total.get(); }
    public void setPrix(String newCourse) { Prix_total.set(newCourse); }
}
