/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jerem.database.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe retourne une connexion à une base de données MySQL Elle
 * implémente de la pattern Singleton
 *
 * @author Administrateur
 */
public class databaseConnection {

    /**
     * Variable destinée à stocker l'instance de la connexion
     */
    private static Connection instance;

    /**
     * Constructeur private our pouvoir instancier la classe depuis l'extérieur
     */
    private databaseConnection() {

    }//fin du constructeur

    /**
     * Retourne un objet de type Connection
     *
     * @return
     */
    public static Connection getInstance() throws SQLException {

        FileInputStream fis = null;
        try {
            //Instantation d'un objet Properties qui contiendra la configuration
            Properties config = new Properties();
            //Ouverture du fichiers qui contient les
            fis = new FileInputStream("./config/app.properties");

            //Chargement des données du fichier dans l'object Properies
            config.load(fis);
            fis.close();

            // Récupération des informations de configuration dans des variables
            String dbHost = config.getProperty("db.host", "localhost");
            String dbName = config.getProperty("dbname", "bibliotheque");
            String dbUser = config.getProperty("db.user", "root");
            String dbPass = config.getProperty("db.pass", "");
            // Si l'instance est nulle on instancie ne nouvelle connexion
            if (instance == null) {
                instance = DriverManager.getConnection(
                        "jdbc:mysql://" + dbHost + "/" + dbName,
                        dbUser,
                        dbPass
                );
            }
        } catch (IOException ex) {
            Logger.getLogger(databaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(databaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }

            return instance;
        }
    }
}
