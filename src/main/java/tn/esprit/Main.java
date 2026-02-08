package tn.esprit.test;

import tn.esprit.entities.Reclamation;
import tn.esprit.entities.ReponseReclamation;
import tn.esprit.services.ServiceReclamation;
import tn.esprit.services.ServiceReponseReclamation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // ====== SERVICES ======
            ServiceReclamation sr = new ServiceReclamation();
            ServiceReponseReclamation srr = new ServiceReponseReclamation();

            // ====== AJOUTER UNE RÉCLAMATION ======
            Reclamation r1 = new Reclamation();
            r1.setType("Qualité");
            r1.setSujet("Plat froid");
            r1.setDescription("Le plat était froid à la livraison");
            r1.setStatut("En cours");
            r1.setIdUser(1);
            sr.ajouter(r1);
            System.out.println("Réclamation ajoutée ✅");

            // ====== AJOUTER UNE RÉPONSE ======
            ReponseReclamation rep1 = new ReponseReclamation();
            rep1.setMessage("Merci pour votre retour, nous allons régler le problème");
            rep1.setDateReponse(new Date(System.currentTimeMillis()));
            rep1.setIdReclamation(r1.getId()); // Lier à la réclamation ajoutée
            srr.ajouter(rep1);
            System.out.println("Réponse ajoutée ✅");

            // ====== AFFICHER ======
            System.out.println("\n--- Liste des réclamations ---");
            List<Reclamation> reclamations = sr.afficher();
            reclamations.forEach(rc ->
                    System.out.println(rc.getId() + " | " + rc.getType() + " | " + rc.getSujet() + " | " + rc.getStatut()));

            System.out.println("\n--- Liste des réponses ---");
            List<ReponseReclamation> reponses = srr.afficher();
            reponses.forEach(rep ->
                    System.out.println(rep.getId() + " | " + rep.getMessage() + " | " + rep.getDateReponse() + " | Réclamation ID: " + rep.getIdReclamation()));

            // ====== MODIFIER ======
            r1.setStatut("Résolu");
            sr.modifier(r1);
            rep1.setMessage("Problème traité et résolu");
            srr.modifier(rep1);
            System.out.println("\nModification effectuée ✅");

            // ====== RECHERCHE ======
            System.out.println("\n--- Recherche réclamations contenant 'plat' ---");
            sr.rechercherParSujet("plat").forEach(rc -> System.out.println(rc.getSujet()));

            System.out.println("\n--- Recherche réponses contenant 'merci' ---");
            srr.rechercherParMessage("merci").forEach(rep -> System.out.println(rep.getMessage()));

            // ====== TRI ======
            System.out.println("\n--- Réclamations triées par type ---");
            sr.trierParType().forEach(rc -> System.out.println(rc.getType() + " | " + rc.getSujet()));

            System.out.println("\n--- Réponses triées par date ---");
            srr.trierParDate().forEach(rep -> System.out.println(rep.getDateReponse() + " | " + rep.getMessage()));

            // ====== SUPPRIMER (optionnel) ======
            // sr.supprimer(r1.getId());
            // srr.supprimer(rep1.getId());
            // System.out.println("\nSuppression effectuée ✅");

        } catch (SQLException e) {
            System.out.println("Erreur SQL: " + e.getMessage());
        }
    }
}
