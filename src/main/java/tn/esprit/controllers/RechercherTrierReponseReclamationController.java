package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import tn.esprit.entities.ReponseReclamation;
import tn.esprit.services.ServiceReponseReclamation;

import java.sql.SQLException;
import java.util.List;

public class RechercherTrierReponseReclamationController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox cardContainer;

    private ServiceReponseReclamation service = new ServiceReponseReclamation();

    // ================= INITIALISATION =================
    @FXML
    public void initialize() {
        loadAll();
    }

    // ================= AFFICHER TOUT =================
    @FXML
    private void afficherTout() {
        loadAll();
    }

    private void loadAll() {
        cardContainer.getChildren().clear();
        try {
            List<ReponseReclamation> list = service.afficher();
            for (ReponseReclamation r : list) {
                cardContainer.getChildren().add(createCard(r));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement.");
            e.printStackTrace();
        }
    }

    // ================= CREATION CARD =================
    private VBox createCard(ReponseReclamation r) {

        VBox card = new VBox(10);
        card.setStyle("-fx-background-color:white;" +
                "-fx-padding:20;" +
                "-fx-background-radius:15;" +
                "-fx-effect:dropshadow(gaussian, rgba(0,0,0,0.1),10,0,0,5);");

        Label message = new Label("Message : " + r.getMessage());
        message.setStyle("-fx-font-weight:bold; -fx-text-fill:#3498db;");

        Label date = new Label("Date : " + r.getDateReponse());
        Label idRec = new Label("ID Réclamation : " + r.getIdReclamation());

        card.getChildren().addAll(message, date, idRec);

        return card;
    }

    // ================= RECHERCHE =================
    @FXML
    private void rechercher() {

        String keyword = searchField.getText();

        if (keyword == null || keyword.isEmpty()) {
            loadAll();
            return;
        }

        cardContainer.getChildren().clear();

        try {
            List<ReponseReclamation> result =
                    service.rechercherParMessage(keyword);

            for (ReponseReclamation r : result) {
                cardContainer.getChildren().add(createCard(r));
            }

        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la recherche.");
            e.printStackTrace();
        }
    }

    // ================= TRI PAR DATE =================
    @FXML
    private void trierDate() {

        cardContainer.getChildren().clear();

        try {
            List<ReponseReclamation> list =
                    service.trierParDate();

            for (ReponseReclamation r : list) {
                cardContainer.getChildren().add(createCard(r));
            }

        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du tri.");
            e.printStackTrace();
        }
    }

    // ================= ALERT =================
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
