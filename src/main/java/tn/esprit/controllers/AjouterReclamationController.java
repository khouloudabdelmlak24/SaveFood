package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ServiceReclamation;

public class AjouterReclamationController {

    @FXML
    private TextField typeField;
    @FXML
    private TextField sujetField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Label messageLabel;
    @FXML
    private Button btnAjouter;

    private ServiceReclamation service = new ServiceReclamation();

    @FXML
    private void ajouterReclamation() {
        String type = typeField.getText().trim();
        String sujet = sujetField.getText().trim();
        String description = descriptionField.getText().trim();

        // Contrôle de saisie simple
        if (type.isEmpty()) {
            messageLabel.setText("Le champ Type est obligatoire !");
            typeField.requestFocus();
            return;
        }

        if (sujet.isEmpty()) {
            messageLabel.setText("Le champ Sujet est obligatoire !");
            sujetField.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            messageLabel.setText("Le champ Description est obligatoire !");
            descriptionField.requestFocus();
            return;
        }

        if (sujet.length() < 5) {
            messageLabel.setText("Le Sujet doit contenir au moins 5 caractères !");
            sujetField.requestFocus();
            return;
        }

        if (description.length() < 10) {
            messageLabel.setText("La Description doit contenir au moins 10 caractères !");
            descriptionField.requestFocus();
            return;
        }

        try {
            Reclamation r = new Reclamation(0, type, sujet, description, "En attente", 1); // idUser = 1 pour test
            service.ajouter(r);
            messageLabel.setText("Réclamation ajoutée avec succès !");

            // Reset des champs
            typeField.clear();
            sujetField.clear();
            descriptionField.clear();
        } catch (Exception e) {
            messageLabel.setText("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
