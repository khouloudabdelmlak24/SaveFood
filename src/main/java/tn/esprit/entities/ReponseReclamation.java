package tn.esprit.entities;

import java.util.Date;

public class ReponseReclamation {
    private int id;
    private String message;
    private Date dateReponse;
    private int idReclamation;

    public ReponseReclamation() {
    }

    public ReponseReclamation(int id, String message, Date dateReponse, int idReclamation) {
        this.id = id;
        this.message = message;
        this.dateReponse = dateReponse;
        this.idReclamation = idReclamation;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public java.sql.Date getDateReponse() {
        return new java.sql.Date(dateReponse.getTime());
    }


    public int getIdReclamation() {
        return idReclamation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateReponse(Date dateReponse) {
        this.dateReponse = dateReponse;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    @Override
    public String toString() {
        return "ReponseReclamation{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", dateReponse=" + dateReponse +
                ", idReclamation=" + idReclamation +
                '}';
    }
}
