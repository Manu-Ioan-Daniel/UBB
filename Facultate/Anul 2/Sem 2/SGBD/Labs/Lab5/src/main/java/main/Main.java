package main;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import repos.MaterieDBRepoORM;
import repos.NoteDBRepoORM;
import repos.ProfessorDBRepoORM;
import repos.StudRepoORM;
import services.Service;
import utils.HibernateStats;
import utils.LiquibaseManager;
import utils.OptimisticLockingDemo;
import utils.StageManager;
import validator.ProfessorValidator;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            try {
                LiquibaseManager.initiateMigrations();
            } catch (Exception e) {
                HibernateStats.log("Liquibase", "Migration/init failed: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Migration Error");
                alert.setHeaderText("Database migration failed");
                alert.setContentText("Liquibase failed to apply migrations. Check logs and ensure the database is available. Error: " + e.getMessage());
                alert.showAndWait();
            }

            try {
                OptimisticLockingDemo.runDemo();
            } catch (Exception e) {
                HibernateStats.log("OptimisticLockDemo", "Demo failed: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Demo Warning");
                alert.setHeaderText("Optimistic Locking Demo failed");
                alert.setContentText("Demo encountered an error: " + e.getMessage());
                alert.showAndWait();
            }

            Service service = new Service(
                    new MaterieDBRepoORM(),
                    new StudRepoORM(),
                    new ProfessorDBRepoORM(),
                    new NoteDBRepoORM(),
                    new ProfessorValidator()
            );

            StageManager stageManager = new StageManager();
            stageManager.openLoginWindow(service);

        } catch (Exception e) {
            HibernateStats.log("ApplicationStart", "Unexpected error in start(): " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Startup Error");
            alert.setHeaderText("Application failed to start");
            alert.setContentText("An unexpected error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
