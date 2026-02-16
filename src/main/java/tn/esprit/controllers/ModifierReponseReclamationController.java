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

    @FXML
    private Label messageLabel;

    @FXML
    private Button btnModifier;

    private ServiceReponseReclamation service = new ServiceReponseReclamation();

    @FXML
    private void modifier() {

        String idStr = idField.getText().trim();
        String message = messageField.getText().trim();
        String idRecStr = idReclamationField.getText().trim();

        // ==============================
        // CONTROLE ID
        // ==============================
        if (idStr.isEmpty()) {
            messageLabel.setText("Le champ ID est obligatoire !");
            idField.requestFocus();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            messageLabel.setText("ID doit être un nombre !");
            idField.requestFocus();
            return;
        }

        // ==============================
        // CONTROLE MESSAGE
        // ==============================
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

        // ==============================
        // CONTROLE ID RECLAMATION
        // ==============================
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

        // ==============================
        // MODIFICATION
        // ==============================
        try {

            ReponseReclamation r =
                    new ReponseReclamation(
                            id,
                            message,
                            new Date(),
                            idRec
                    );

            service.modifier(r);

            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Réponse modifiée avec succès !");

        } catch (SQLException e) {
            messageLabel.setText("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
