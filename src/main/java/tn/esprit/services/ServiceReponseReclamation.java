package tn.esprit.services;

import tn.esprit.entities.ReponseReclamation;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceReponseReclamation {

    private Connection connection;

    public ServiceReponseReclamation() {
        connection = MyDataBase.getInstance().getMyConnection();
    }

    // Ajouter une réponse
    public void ajouter(ReponseReclamation r) throws SQLException {
        String sql = "INSERT INTO reponse_reclamation(message, date_reponse, id_reclamation) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, r.getMessage());
        ps.setDate(2, r.getDateReponse());
        ps.setInt(3, r.getIdReclamation());
        ps.executeUpdate();
        System.out.println("Réponse ajoutée !");
    }

    // Modifier une réponse
    public void modifier(ReponseReclamation r) throws SQLException {
        String sql = "UPDATE reponse_reclamation SET message=?, date_reponse=?, id_reclamation=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, r.getMessage());
        ps.setDate(2, r.getDateReponse());
        ps.setInt(3, r.getIdReclamation());
        ps.setInt(4, r.getId());
        ps.executeUpdate();
        System.out.println("Réponse modifiée !");
    }

    // Supprimer une réponse
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM reponse_reclamation WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Réponse supprimée !");
    }

    // Afficher toutes les réponses
    public List<ReponseReclamation> afficher() throws SQLException {
        List<ReponseReclamation> list = new ArrayList<>();
        String sql = "SELECT * FROM reponse_reclamation";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            ReponseReclamation r = new ReponseReclamation();
            r.setId(rs.getInt("id"));
            r.setMessage(rs.getString("message"));
            r.setDateReponse(rs.getDate("date_reponse"));
            r.setIdReclamation(rs.getInt("id_reclamation"));
            list.add(r);
        }
        return list;
    }

    // Recherche par message
    public List<ReponseReclamation> rechercherParMessage(String keyword) throws SQLException {
        return afficher().stream()
                .filter(r -> r.getMessage().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Tri par date de réponse
    public List<ReponseReclamation> trierParDate() throws SQLException {
        return afficher().stream()
                .sorted((r1, r2) -> r1.getDateReponse().compareTo(r2.getDateReponse()))
                .collect(Collectors.toList());
    }

    // Tri par idReclamation
    public List<ReponseReclamation> trierParReclamation() throws SQLException {
        return afficher().stream()
                .sorted((r1, r2) -> Integer.compare(r1.getIdReclamation(), r2.getIdReclamation()))
                .collect(Collectors.toList());
    }
}
