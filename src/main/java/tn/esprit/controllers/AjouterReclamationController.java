package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ServiceReclamation;

public class AjouterReclamationController {

    @FXML
    private TextField typeField, sujetField, idUserField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ComboBox<String> statutCombo;
    @FXML
    private Button ajouterBtn;
    @FXML
    private Label messageLabel;

    private ServiceReclamation service = new ServiceReclamation();

    @FXML
    public void initialize() {
        // Ajouter l'action du bouton
        ajouterBtn.setOnAction(e -> ajouterReclamation());
    }

    private void ajouterReclamation() {
        String type = typeField.getText().trim();
        String sujet = sujetField.getText().trim();
        String description = descriptionField.getText().trim();
        String statut = statutCombo.getValue();
        String idUserStr = idUserField.getText().trim();

        // Contrôle de saisie simple
        if(type.isEmpty() || sujet.isEmpty() || description.isEmpty() || statut == null || idUserStr.isEmpty()) {
            messageLabel.setText("Tous les champs sont obligatoires !");
            return;
        }

        int idUser;
        try {
            idUser = Integer.parseInt(idUserStr);
        } catch (NumberFormatException ex) {
            messageLabel.setText("ID Utilisateur doit être un nombre !");
            return;
        }

        // Création et ajout de la réclamation
        Reclamation r = new Reclamation(0, type, sujet, description, statut, idUser);
        try {
            service.ajouter(r);
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Réclamation ajoutée avec succès !");
            clearFields();
        } catch (Exception ex) {
            messageLabel.setText("Erreur : " + ex.getMessage());
        }
    }

    private void clearFields() {
        typeField.clear();
        sujetField.clear();
        descriptionField.clear();
        statutCombo.getSelectionModel().clearSelection();
        idUserField.clear();
    }
}
