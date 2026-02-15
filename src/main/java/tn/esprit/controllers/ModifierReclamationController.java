package tn.esprit.controllers;

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
        loadBtn.setOnAction(e -> loadReclamation());
        modifierBtn.setOnAction(e -> modifierReclamation());
    }

    private void loadReclamation() {
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

    private void modifierReclamation() {
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
}
