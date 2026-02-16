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

    @FXML
    private Label messageLabel;

    @FXML
    private Button btnAjouter;

    private ServiceReponseReclamation service = new ServiceReponseReclamation();

    @FXML
    private void ajouter() {

        String message = messageField.getText().trim();
        String idRecStr = idReclamationField.getText().trim();

        // Contrôle de saisie
        if (message.isEmpty()) {
            messageLabel.setText("Le champ Message est obligatoire !");
            messageField.requestFocus();
            return;
        }

        if (message.length() < 5) {
            messageLabel.setText("Le Message doit contenir au moins 5 caractères !");
            messageField.requestFocus();
            return;
        }

        if (idRecStr.isEmpty()) {
            messageLabel.setText("Le champ ID Réclamation est obligatoire !");
            idReclamationField.requestFocus();
            return;
        }

        int idRec;
        try {
            idRec = Integer.parseInt(idRecStr);
        } catch (NumberFormatException e) {
            messageLabel.setText("ID Réclamation doit être un nombre !");
            idReclamationField.requestFocus();
            return;
        }

        try {
            ReponseReclamation r =
                    new ReponseReclamation(
                            0,
                            message,
                            new Date(),
                            idRec
                    );

            service.ajouter(r);

            messageLabel.setText("Réponse ajoutée avec succès !");
            messageField.clear();
            idReclamationField.clear();

        } catch (SQLException e) {
            messageLabel.setText("Erreur lors de l'ajout : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
