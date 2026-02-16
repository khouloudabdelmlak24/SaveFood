package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ServiceReclamation;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficherReclamationController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox cardContainer;

    private ServiceReclamation service = new ServiceReclamation();

    @FXML
    public void initialize() {
        loadData();
    }

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

    private VBox createCard(Reclamation r) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color:white; -fx-padding:20; -fx-background-radius:15; " +
                "-fx-effect:dropshadow(gaussian, rgba(0,0,0,0.1),10,0,0,5);");

        Label type = new Label("Type : " + r.getType());
        type.setStyle("-fx-font-weight:bold; -fx-text-fill:#3498db; -fx-font-size:14px;");

        Label sujet = new Label("Sujet : " + r.getSujet());
        Label description = new Label("Description : " + r.getDescription());
        Label statut = new Label("Statut : " + r.getStatut());
        Label idUser = new Label("ID User : " + r.getIdUser());

        // ===================== BOUTONS =====================
        Button deleteBtn = new Button("Supprimer");
        deleteBtn.setStyle("-fx-background-color:#e74c3c; -fx-text-fill:white; -fx-background-radius:20; -fx-padding:6 15 6 15;");
        deleteBtn.setOnAction(e -> {
            try {
                service.supprimer(r.getId());
                showAlert("Succès", "Réclamation supprimée !");
                loadData();
            } catch (SQLException ex) {
                showAlert("Erreur", "Erreur lors de la suppression.");
                ex.printStackTrace();
            }
        });

        Button editBtn = new Button("Modifier");
        editBtn.setStyle("-fx-background-color:#36656B; -fx-text-fill:white; -fx-background-radius:20; -fx-padding:6 15 6 15;");
        editBtn.setOnAction(e -> {
            showEditDialog(r);
        });

        HBox buttonBox = new HBox(10, editBtn, deleteBtn);

        card.getChildren().addAll(type, sujet, description, statut, idUser, buttonBox);

        return card;
    }

    // ================= EDIT DIALOG =================
    private void showEditDialog(Reclamation r) {
        Dialog<Reclamation> dialog = new Dialog<>();
        dialog.setTitle("Modifier Réclamation");

        Label typeLabel = new Label("Type : ");
        TextField typeField = new TextField(r.getType());
        Label sujetLabel = new Label("Sujet : ");
        TextField sujetField = new TextField(r.getSujet());
        Label descLabel = new Label("Description : ");
        TextArea descField = new TextArea(r.getDescription());
        Label statutLabel = new Label("Statut : ");
        TextField statutField = new TextField(r.getStatut());
        Label idUserLabel = new Label("ID User : ");
        TextField idUserField = new TextField(String.valueOf(r.getIdUser()));

        VBox content = new VBox(10, typeLabel, typeField, sujetLabel, sujetField, descLabel, descField,
                statutLabel, statutField, idUserLabel, idUserField);
        dialog.getDialogPane().setContent(content);

        ButtonType okButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                r.setType(typeField.getText());
                r.setSujet(sujetField.getText());
                r.setDescription(descField.getText());
                r.setStatut(statutField.getText());
                r.setIdUser(Integer.parseInt(idUserField.getText()));
                return r;
            }
            return null;
        });

        Optional<Reclamation> result = dialog.showAndWait();
        result.ifPresent(updated -> {
            try {
                service.modifier(updated);
                showAlert("Succès", "Réclamation modifiée !");
                loadData();
            } catch (SQLException ex) {
                showAlert("Erreur", "Erreur lors de la modification.");
                ex.printStackTrace();
            }
        });
    }

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
