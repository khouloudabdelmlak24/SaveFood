package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.services.ServiceReclamation;

import java.sql.SQLException;

public class SupprimerReclamationController {

    @FXML
    private TextField typeField;

    @FXML
    private TextField sujetField;

    private ServiceReclamation service = new ServiceReclamation();

    @FXML
    private void supprimer() {

        // Vérification que les champs ne sont pas vides
        if (typeField.getText().isEmpty() || sujetField.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez entrer le type et le sujet de la réclamation.");
            return;
        }

        try {
            String type = typeField.getText();
            String sujet = sujetField.getText();

            service.supprimerParTypeEtSujet(type, sujet);

            showAlert("Succès", "Réclamation supprimée avec succès.");
            typeField.clear();
            sujetField.clear();

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
