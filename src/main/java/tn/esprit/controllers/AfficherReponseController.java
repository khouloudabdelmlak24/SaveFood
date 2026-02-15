package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;

import tn.esprit.entities.ReponseReclamation;
import tn.esprit.services.ServiceReponseReclamation;

import java.sql.SQLException;
import java.util.List;

public class AfficherReponseController {

    @FXML
    private VBox cardContainer;

    @FXML
    private TextField searchField;

    private ServiceReponseReclamation service = new ServiceReponseReclamation();

    @FXML
    public void initialize() {
        afficherToutes();
    }

    private void afficherToutes() {
        try {
            afficherCards(service.afficher());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherCards(List<ReponseReclamation> list) {

        cardContainer.getChildren().clear();

        for (ReponseReclamation r : list) {

            VBox card = new VBox(8);
            card.setStyle("-fx-background-color: white; -fx-padding:15; -fx-background-radius:15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1),10,0,0,4);");

            Label message = new Label("Message : " + r.getMessage());
            Label date = new Label("Date : " + r.getDateReponse());
            Label reclamation = new Label("Réclamation ID : " + r.getIdReclamation());

            card.getChildren().addAll(message, date, reclamation);

            cardContainer.getChildren().add(card);
        }
    }

    @FXML
    void rechercher() {
        try {
            afficherCards(service.rechercherParMessage(searchField.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void trierDate() {
        try {
            afficherCards(service.trierParDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
