package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ServiceReclamation;

import java.sql.SQLException;
import java.util.List;

public class AfficherReclamationController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox cardContainer;

    private ServiceReclamation service = new ServiceReclamation();

    // ================= INITIALISATION =================
    @FXML
    public void initialize() {
        loadData();
    }

    // ================= CHARGER DONNÉES =================
    private void loadData() {
        cardContainer.getChildren().clear();

        try {
            List<Reclamation> list = service.afficher();
            for (Reclamation r : list) {
                cardContainer.getChildren().add(createCard(r));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des données.");
            e.printStackTrace();
        }
    }

    // ================= CREATION CARD =================
    private VBox createCard(Reclamation r) {

        VBox card = new VBox(10);
        card.setStyle("-fx-background-color:white;" +
                "-fx-padding:20;" +
                "-fx-background-radius:15;" +
                "-fx-effect:dropshadow(gaussian, rgba(0,0,0,0.1),10,0,0,5);");

        Label type = new Label("Type : " + r.getType());
        type.setStyle("-fx-font-weight:bold; -fx-text-fill:#3498db; -fx-font-size:14px;");

        Label sujet = new Label("Sujet : " + r.getSujet());
        Label description = new Label("Description : " + r.getDescription());
        Label statut = new Label("Statut : " + r.getStatut());

        Button deleteBtn = new Button("Supprimer");
        deleteBtn.setStyle("-fx-background-color:#e74c3c;" +
                "-fx-text-fill:white;" +
                "-fx-background-radius:20;" +
                "-fx-padding:6 15 6 15;" +
                "-fx-cursor:hand;");

        deleteBtn.setOnAction(e -> {
            try {
                service.supprimer(r.getId());
                showAlert("Succès", "Réclamation supprimée avec succès.");
                loadData();
            } catch (SQLException ex) {
                showAlert("Erreur", "Erreur lors de la suppression.");
                ex.printStackTrace();
            }
        });

        card.getChildren().addAll(type, sujet, description, statut, deleteBtn);

        return card;
    }

    // ================= RECHERCHE =================
    @FXML
    private void rechercher() {
        String keyword = searchField.getText();

        if (keyword == null || keyword.isEmpty()) {
            loadData();
            return;
        }

        cardContainer.getChildren().clear();

        try {
            List<Reclamation> result = service.rechercherParSujet(keyword);
            for (Reclamation r : result) {
                cardContainer.getChildren().add(createCard(r));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la recherche.");
            e.printStackTrace();
        }
    }

    // ================= TRI PAR TYPE =================
    @FXML
    private void trierType() {
        cardContainer.getChildren().clear();

        try {
            List<Reclamation> list = service.trierParType();
            for (Reclamation r : list) {
                cardContainer.getChildren().add(createCard(r));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du tri par type.");
            e.printStackTrace();
        }
    }

    // ================= TRI PAR STATUT =================
    @FXML
    private void trierStatut() {
        cardContainer.getChildren().clear();

        try {
            List<Reclamation> list = service.trierParStatut();
            for (Reclamation r : list) {
                cardContainer.getChildren().add(createCard(r));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du tri par statut.");
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
