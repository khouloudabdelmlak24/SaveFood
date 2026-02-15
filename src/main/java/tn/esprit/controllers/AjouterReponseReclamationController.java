package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.entities.ReponseReclamation;
import tn.esprit.services.ServiceReponseReclamation;

import java.sql.SQLException;
import java.util.Date;

public class AjouterReponseReclamationController {

    @FXML
    private TextArea messageField;

    @FXML
    private TextField idReclamationField;

    private ServiceReponseReclamation service = new ServiceReponseReclamation();

    @FXML
    private void ajouter() {

        if (messageField.getText().isEmpty()
                || idReclamationField.getText().isEmpty()) {

            showAlert("Erreur", "Tous les champs sont obligatoires.");
            return;
        }

        try {
            int idRec = Integer.parseInt(idReclamationField.getText());

            ReponseReclamation r =
                    new ReponseReclamation(
                            0,
                            messageField.getText(),
                            new Date(),
                            idRec
                    );

            service.ajouter(r);

            showAlert("Succès", "Réponse ajoutée avec succès.");
            messageField.clear();
            idReclamationField.clear();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "ID Réclamation doit être un nombre.");
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
