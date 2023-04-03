package com.example.projetdejava;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
    private final StringProperty ID ;
    private final StringProperty Nom;
    private final StringProperty Prenom;
    private final StringProperty  Telephone;

    public Client()
    {
        ID = new SimpleStringProperty(this, "ID");
        Nom = new SimpleStringProperty(this, "Nom");
        Prenom = new SimpleStringProperty(this, "Prenom");
        Telephone= new SimpleStringProperty(this, "Telephone");
    }


    public StringProperty idProperty() { return ID; }
    public String getId() { return ID.get(); }
    public void setId(String newId) { ID.set(newId); }

    public StringProperty nameProperty() { return Nom; }
    public String getName() { return Nom.get(); }
    public void setName(String newName) { Nom.set(newName); }

    public StringProperty mobileProperty() { return Prenom; }
    public String getMobile() { return Prenom.get(); }
    public void setMobile(String newMobile) { Prenom.set(newMobile); }

    public StringProperty prenomProperty() { return Telephone; }
    public String getCourse() { return Telephone.get(); }
    public void setCourse(String newCourse) { Telephone.set(newCourse); }
}
