package tn.esprit.test;

import tn.esprit.entities.ReponseReclamation;
import tn.esprit.services.ServiceReponseReclamation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            // ===== Service =====
            ServiceReponseReclamation srr = new ServiceReponseReclamation();

            // ===== AJOUTER =====
            ReponseReclamation r = new ReponseReclamation();

            r.setMessage("Nous avons bien reçu votre réclamation");
            r.setDateReponse(new Date(System.currentTimeMillis()));
            r.setIdReclamation(1); // ⚠ Mets un ID existant dans la table reclamation

            srr.ajouter(r);
            System.out.println("Réponse ajoutée ✅");


            // ===== AFFICHER =====
            System.out.println("\n--- Liste des réponses ---");

            List<ReponseReclamation> list = srr.afficher();

            for (ReponseReclamation rep : list) {
                System.out.println(
                        rep.getId() + " | " +
                                rep.getMessage() + " | " +
                                rep.getDateReponse() + " | Reclamation: " +
                                rep.getIdReclamation()
                );
            }


            // ===== MODIFIER =====
            if (!list.isEmpty()) {

                ReponseReclamation first = list.get(0);

                first.setMessage("Votre problème a été traité");

                srr.modifier(first);

                System.out.println("\nRéponse modifiée ✅");
            }


            // ===== RECHERCHE =====
            System.out.println("\n--- Recherche 'traité' ---");

            srr.rechercherParMessage("traité")
                    .forEach(rep -> System.out.println(rep.getMessage()));


            // ===== TRI =====
            System.out.println("\n--- Tri par date ---");

            srr.trierParDate()
                    .forEach(rep ->
                            System.out.println(rep.getDateReponse() + " | " + rep.getMessage())
                    );


            // ===== SUPPRIMER (optionnel) =====
            /*
            if (!list.isEmpty()) {
                srr.supprimer(list.get(0).getId());
                System.out.println("\nRéponse supprimée ✅");
            }
            */

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }

    }
}
