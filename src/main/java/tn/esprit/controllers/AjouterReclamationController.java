package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.*;

import tn.esprit.entities.Reclamation;
import tn.esprit.services.ServiceReclamation;

import java.io.IOException;

public class AjouterReclamationController {

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private TextField sujetField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Label messageLabel;

    private ServiceReclamation service = new ServiceReclamation();

    // ================= INITIALISATION =================
    @FXML
    public void initialize() {

        typeCombo.getItems().addAll(
                "Livraison",
                "Paiement",
                "Qualité produit",
                "Application",
                "Autre"
        );
    }

    // ================= AJOUTER =================
    @FXML
    private void ajouterReclamation(ActionEvent event) {

        String type = typeCombo.getValue();
        String sujet = sujetField.getText().trim();
        String description = descriptionField.getText().trim();

        if (type == null) {
            messageLabel.setText("Veuillez choisir un type !");
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
            Reclamation r = new Reclamation(0, type, sujet, description, "En attente", 1);
            service.ajouter(r);

            messageLabel.setText("Réclamation ajoutée avec succès !");

            typeCombo.setValue(null);
            sujetField.clear();
            descriptionField.clear();

        } catch (Exception e) {
            messageLabel.setText("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ================= OUVRIR PAGE AFFICHAGE =================
    @FXML
    private void ouvrirAfficherReclamation(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            messageLabel.setText("Impossible d'ouvrir la page Afficher.");
            e.printStackTrace();
        }
    }
}
