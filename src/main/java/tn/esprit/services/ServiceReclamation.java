package tn.esprit.services;

import tn.esprit.entities.Reclamation;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceReclamation {

    private Connection connection;

    public ServiceReclamation() {
        connection = MyDataBase.getInstance().getMyConnection();
    }

    // Ajouter une réclamation
    public void ajouter(Reclamation r) throws SQLException {
        String sql = "INSERT INTO reclamation(type, sujet, description, statut, id_user) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, r.getType());
        ps.setString(2, r.getSujet());
        ps.setString(3, r.getDescription());
        ps.setString(4, r.getStatut());
        ps.setInt(5, r.getIdUser());
        ps.executeUpdate();
        System.out.println("Réclamation ajoutée !");
    }

    // Modifier une réclamation
    public void modifier(Reclamation r) throws SQLException {
        String sql = "UPDATE reclamation SET type=?, sujet=?, description=?, statut=?, id_user=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, r.getType());
        ps.setString(2, r.getSujet());
        ps.setString(3, r.getDescription());
        ps.setString(4, r.getStatut());
        ps.setInt(5, r.getIdUser());
        ps.setInt(6, r.getId());
        ps.executeUpdate();
        System.out.println("Réclamation modifiée !");
    }

    // Supprimer une réclamation par ID
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM reclamation WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Réclamation supprimée !");
    }

    // Supprimer par type et sujet
    public void supprimerParTypeEtSujet(String type, String sujet) throws SQLException {
        String sql = "DELETE FROM reclamation WHERE type = ? AND sujet = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, type);
        ps.setString(2, sujet);

        int rows = ps.executeUpdate();
        if (rows > 0) {
            System.out.println("Réclamation supprimée avec succès !");
        } else {
            System.out.println("Aucune réclamation trouvée avec ces critères.");
        }
    }

    // Afficher toutes les réclamations
    public List<Reclamation> afficher() throws SQLException {
        List<Reclamation> list = new ArrayList<>();
        String sql = "SELECT * FROM reclamation";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Reclamation r = new Reclamation();
            r.setId(rs.getInt("id"));
            r.setType(rs.getString("type"));
            r.setSujet(rs.getString("sujet"));
            r.setDescription(rs.getString("description"));
            r.setStatut(rs.getString("statut"));
            r.setIdUser(rs.getInt("id_user"));
            list.add(r);
        }
        return list;
    }

    // Recherche par sujet
    public List<Reclamation> rechercherParSujet(String keyword) throws SQLException {
        return afficher().stream()
                .filter(r -> r.getSujet().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Tri par type
    public List<Reclamation> trierParType() throws SQLException {
        return afficher().stream()
                .sorted((r1, r2) -> r1.getType().compareToIgnoreCase(r2.getType()))
                .collect(Collectors.toList());
    }

    // Tri par statut
    public List<Reclamation> trierParStatut() throws SQLException {
        return afficher().stream()
                .sorted((r1, r2) -> r1.getStatut().compareToIgnoreCase(r2.getStatut()))
                .collect(Collectors.toList());
    }
}
