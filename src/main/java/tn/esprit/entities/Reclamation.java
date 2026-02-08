package tn.esprit.entities;

public class Reclamation {

    private int id;
    private String type;
    private String sujet;
    private String description;
    private String statut;
    private int idUser;

    public Reclamation() {
    }

    public Reclamation(int id, String type, String sujet, String description, String statut, int idUser) {
        this.id = id;
        this.type = type;
        this.sujet = sujet;
        this.description = description;
        this.statut = statut;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSujet() {
        return sujet;
    }

    public String getDescription() {
        return description;
    }

    public String getStatut() {
        return statut;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", sujet='" + sujet + '\'' +
                ", description='" + description + '\'' +
                ", statut='" + statut + '\'' +
                ", idUser=" + idUser +
                '}';
    }
}
