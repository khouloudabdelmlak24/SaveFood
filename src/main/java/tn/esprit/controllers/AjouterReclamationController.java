package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ServiceReclamation;

public class AjouterReclamationController {

    @FXML
    private TextField tfType;
    @FXML
    private TextField tfSujet;
    @FXML
    private TextArea taDescription;
    @FXML
    private Button btnAjouter;
    @FXML
    private Label lblMessage;

    private ServiceReclamation service = new ServiceReclamation();

    @FXML
    private void ajouterReclamation() {
        String type = tfType.getText().trim();
        String sujet = tfSujet.getText().trim();
        String description = taDescription.getText().trim();

        // Contrôle de saisie simple
        if(type.isEmpty() || sujet.isEmpty() || description.isEmpty()) {
            lblMessage.setText("Tous les champs sont obligatoires !");
            return;
        }

        try {
            Reclamation r = new Reclamation(0, type, sujet, description, "En attente", 1); // idUser = 1 pour test
            service.ajouter(r);
            lblMessage.setText("Réclamation ajoutée avec succès !");

            // Reset des champs
            tfType.clear();
            tfSujet.clear();
            taDescription.clear();
        } catch (Exception e) {
            lblMessage.setText("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
