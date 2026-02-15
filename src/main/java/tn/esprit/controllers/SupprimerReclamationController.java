package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.services.ServiceReclamation;

import java.sql.SQLException;

public class SupprimerReclamationController {

    @FXML
    private TextField idField;

    private ServiceReclamation service = new ServiceReclamation();

    @FXML
    private void supprimer() {

        if (idField.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez entrer un ID.");
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText());
            service.supprimer(id);

            showAlert("Succès", "Réclamation supprimée avec succès.");
            idField.clear();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "ID invalide.");
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
