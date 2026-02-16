package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.services.ServiceReponseReclamation;

import java.sql.SQLException;

public class SupprimerReponseReclamationController {

    @FXML
    private TextField idReclamationField;

    @FXML
    private TextField messageField;

    private ServiceReponseReclamation service = new ServiceReponseReclamation();

    @FXML
    private void supprimer() {

        if (idReclamationField.getText().isEmpty() || messageField.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            int idReclamation = Integer.parseInt(idReclamationField.getText());
            String message = messageField.getText();

            // ✅ Appel correct pour supprimer
            service.supprimerParIdReclamationEtMessage(idReclamation, message);

            showAlert("Succès", "Réponse supprimée avec succès.");
            idReclamationField.clear();
            messageField.clear();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "ID de réclamation invalide.");
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la suppression.");
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
