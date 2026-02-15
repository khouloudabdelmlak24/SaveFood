package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.services.ServiceReponseReclamation;

import java.sql.SQLException;

public class SupprimerReponseReclamationController {

    @FXML
    private TextField idField;

    private ServiceReponseReclamation service = new ServiceReponseReclamation();

    @FXML
    private void supprimer() {

        if (idField.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez entrer un ID.");
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText());
            service.supprimer(id);
            showAlert("Succès", "Réponse supprimée.");

        } catch (NumberFormatException e) {
            showAlert("Erreur", "ID invalide.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setContentText(m);
        a.showAndWait();
    }
}
