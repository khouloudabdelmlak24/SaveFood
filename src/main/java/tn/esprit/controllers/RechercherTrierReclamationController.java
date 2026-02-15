package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ServiceReclamation;

import java.sql.SQLException;
import java.util.List;

public class RechercherTrierReclamationController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox cardContainer;

    private ServiceReclamation service = new ServiceReclamation();

    @FXML
    public void initialize() {
        loadAll();
    }

    private void loadAll() {
        cardContainer.getChildren().clear();
        try {
            for (Reclamation r : service.afficher()) {
                cardContainer.getChildren().add(createCard(r));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createCard(Reclamation r) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color:white;" +
                "-fx-padding:20;" +
                "-fx-background-radius:15;" +
                "-fx-effect:dropshadow(gaussian, rgba(0,0,0,0.1),10,0,0,5);");

        Label sujet = new Label("Sujet : " + r.getSujet());
        Label type = new Label("Type : " + r.getType());
        Label statut = new Label("Statut : " + r.getStatut());

        card.getChildren().addAll(sujet, type, statut);
        return card;
    }

    @FXML
    private void rechercher() throws SQLException {
        cardContainer.getChildren().clear();
        for (Reclamation r : service.rechercherParSujet(searchField.getText())) {
            cardContainer.getChildren().add(createCard(r));
        }
    }

    @FXML
    private void trierType() throws SQLException {
        cardContainer.getChildren().clear();
        for (Reclamation r : service.trierParType()) {
            cardContainer.getChildren().add(createCard(r));
        }
    }

    @FXML
    private void trierStatut() throws SQLException {
        cardContainer.getChildren().clear();
        for (Reclamation r : service.trierParStatut()) {
            cardContainer.getChildren().add(createCard(r));
        }
    }
}
