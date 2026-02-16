package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.entities.ReponseReclamation;
import tn.esprit.services.ServiceReponseReclamation;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficherReponseController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox cardContainer;

    private ServiceReponseReclamation service = new ServiceReponseReclamation();

    @FXML
    public void initialize() {
        loadData();
    }

    private void loadData() {
        cardContainer.getChildren().clear();
        try {
            List<ReponseReclamation> list = service.afficher();
            for (ReponseReclamation r : list) {
                cardContainer.getChildren().add(createCard(r));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des réponses.");
            e.printStackTrace();
        }
    }

    private VBox createCard(ReponseReclamation r) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color:white; -fx-padding:20; -fx-background-radius:20;" +
                "-fx-border-radius:20; -fx-border-color:#A5C89E; -fx-border-width:2;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,5);");

        Label message = new Label("Message : " + r.getMessage());
        message.setStyle("-fx-font-weight:bold; -fx-text-fill:#36656B; -fx-font-size:14px;");

        Label date = new Label("Date : " + r.getDateReponse());
        Label idReclamation = new Label("Réclamation ID : " + r.getIdReclamation());

        Button editBtn = new Button("Modifier");
        editBtn.setStyle("-fx-background-color:#3498db; -fx-text-fill:white; -fx-background-radius:15; -fx-padding:6 15 6 15;");
        editBtn.setOnAction(e -> showEditDialog(r));

        Button deleteBtn = new Button("Supprimer");
        deleteBtn.setStyle("-fx-background-color:#e74c3c; -fx-text-fill:white; -fx-background-radius:15; -fx-padding:6 15 6 15;");
        deleteBtn.setOnAction(e -> {
            try {
                service.supprimer(r.getId());
                showAlert("Succès", "Réponse supprimée !");
                loadData();
            } catch (SQLException ex) {
                showAlert("Erreur", "Erreur lors de la suppression.");
                ex.printStackTrace();
            }
        });

        HBox buttonBox = new HBox(10, editBtn, deleteBtn);
        card.getChildren().addAll(message, date, idReclamation, buttonBox);

        return card;
    }

    private void showEditDialog(ReponseReclamation r) {
        Dialog<ReponseReclamation> dialog = new Dialog<>();
        dialog.setTitle("Modifier Réponse");

        Label msgLabel = new Label("Message : ");
        TextArea msgField = new TextArea(r.getMessage());
        Label dateLabel = new Label("Date : ");
        TextField dateField = new TextField(r.getDateReponse().toString());
        Label idRecLabel = new Label("Réclamation ID : ");
        TextField idRecField = new TextField(String.valueOf(r.getIdReclamation()));

        VBox content = new VBox(10, msgLabel, msgField, dateLabel, dateField, idRecLabel, idRecField);
        dialog.getDialogPane().setContent(content);

        ButtonType okButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                r.setMessage(msgField.getText());
                r.setDateReponse(java.sql.Date.valueOf(dateField.getText()));
                r.setIdReclamation(Integer.parseInt(idRecField.getText()));
                return r;
            }
            return null;
        });

        Optional<ReponseReclamation> result = dialog.showAndWait();
        result.ifPresent(updated -> {
            try {
                service.modifier(updated);
                showAlert("Succès", "Réponse modifiée !");
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
            List<ReponseReclamation> result = service.rechercherParMessage(keyword);
            for (ReponseReclamation r : result) {
                cardContainer.getChildren().add(createCard(r));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la recherche.");
            e.printStackTrace();
        }
    }

    @FXML
    private void trierDate() {
        cardContainer.getChildren().clear();
        try {
            List<ReponseReclamation> list = service.trierParDate();
            for (ReponseReclamation r : list) {
                cardContainer.getChildren().add(createCard(r));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du tri par date.");
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
