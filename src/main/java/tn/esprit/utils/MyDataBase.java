package tn.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {

    private static MyDataBase instance;  // Singleton
    private Connection myConnection;

    private final String URL = "jdbc:mysql://localhost:3306/savefood";
    private final String USER = "root";
    private final String PASSWORD = "";

    // Constructeur privé
    private MyDataBase() {
        try {
            myConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Erreur DB: " + e.getMessage());
        }
    }

    // Retourne la connexion
    public Connection getMyConnection() {
        return myConnection;
    }

    // Retourne l'instance singleton
    public static MyDataBase getInstance() {
        if (instance == null) {
            instance = new MyDataBase(); // ⚠ Assigner correctement ici
        }
        return instance;
    }
}
