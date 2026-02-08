package tn.esprit.services;

import java.sql.SQLException;
import java.util.List;

public interface IService <T> {
    //CRUD Basique
    void ajouter (T t) throws SQLException;
    void modifier (T t) throws SQLException;
    void supprimer (T t) throws SQLException;
    List<T> afficher () throws SQLException;
    //Recherche par motcle
    List<T> rechercher (String motCle) throws SQLException;

    //Tri selon un critere specifique
    List <T> trierPar(String critere , boolean asc) throws SQLException;

}
