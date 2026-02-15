package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.entities.ReponseReclamation;
import tn.esprit.services.ServiceReponseReclamation;

import java.sql.SQLException;
import java.util.Date;

public class ModifierReponseReclamationController {

    @FXML
    private TextField idField;

    @FXML
    private TextArea messageField;

    @FXML
    private TextField idReclamationField;

    private ServiceReponseReclamation service = new ServiceReponseReclamation();

    @FXML
    private void modifier() {

        if (idField.getText().isEmpty()
                || messageField.getText().isEmpty()
                || idReclamationField.getText().isEmpty()) {

            showAlert("Erreur", "Tous les champs sont obligatoires.");
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText());
            int idRec = Integer.parseInt(idReclamationField.getText());

            ReponseReclamation r =
                    new ReponseReclamation(
                            id,
                            messageField.getText(),
                            new Date(),
                            idRec
                    );

            service.modifier(r);

            showAlert("Succès", "Réponse modifiée avec succès.");

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Les ID doivent être numériques.");
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
