package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ServiceReclamation;

import java.util.List;

public class ModifierReclamationController {

    @FXML private TextField searchSujetField, typeField, sujetField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<String> statutCombo;
    @FXML private Button loadBtn, modifierBtn;
    @FXML private Label messageLabel;

    private ServiceReclamation service = new ServiceReclamation();
    private Reclamation current;

    @FXML
    public void initialize() {
        // initialisation si besoin
        loadBtn.setOnAction(e -> loadReclamation());
    }

    // ==============================
    // CHARGER UNE RÉCLAMATION
    // ==============================
    @FXML
    public void loadReclamation() {
        String sujet = searchSujetField.getText().trim();
        if(sujet.isEmpty()) {
            messageLabel.setText("Entrez un sujet pour charger !");
            return;
        }
        try {
            List<Reclamation> list = service.rechercherParSujet(sujet);
            if(list.isEmpty()) {
                messageLabel.setText("Aucune réclamation trouvée !");
                return;
            }
            current = list.get(0);
            typeField.setText(current.getType());
            sujetField.setText(current.getSujet());
            descriptionField.setText(current.getDescription());
            statutCombo.setValue(current.getStatut());
            messageLabel.setText("");
        } catch (Exception ex) {
            messageLabel.setText("Erreur : " + ex.getMessage());
        }
    }

    // ==============================
    // MODIFIER UNE RÉCLAMATION
    // ==============================
    @FXML
    public void modifierReclamation(ActionEvent event) {
        if(current == null) {
            messageLabel.setText("Chargez d'abord une réclamation !");
            return;
        }

        String type = typeField.getText().trim();
        String sujet = sujetField.getText().trim();
        String desc = descriptionField.getText().trim();
        String statut = statutCombo.getValue();

        if(type.isEmpty() || sujet.isEmpty() || desc.isEmpty() || statut == null) {
            messageLabel.setText("Tous les champs sont obligatoires !");
            return;
        }

        current.setType(type);
        current.setSujet(sujet);
        current.setDescription(desc);
        current.setStatut(statut);

        try {
            service.modifier(current);
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Réclamation modifiée avec succès !");
        } catch (Exception ex) {
            messageLabel.setText("Erreur : " + ex.getMessage());
        }
    }

    // ==============================
    // SUPPRIMER UNE RÉCLAMATION
    // ==============================
    @FXML
    public void supprimerReclamation(ActionEvent event) {
        if(current == null) {
            messageLabel.setText("Chargez d'abord une réclamation !");
            return;
        }

        try {
            service.supprimer(current.getId());
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Réclamation supprimée avec succès !");

            // vider les champs
            typeField.clear();
            sujetField.clear();
            descriptionField.clear();
            statutCombo.setValue(null);
            current = null;

        } catch (Exception ex) {
            messageLabel.setText("Erreur : " + ex.getMessage());
        }
    }
}
