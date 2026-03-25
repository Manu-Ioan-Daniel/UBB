package main;

import javafx.application.Application;
import javafx.stage.Stage;
import repos.MaterieDBRepo;
import repos.NoteDBRepo;
import repos.ProfessorDBRepo;
import repos.StudRepo;
import services.Service;
import utils.StageManager;
import validator.ProfessorValidator;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        StageManager.showMainWindow(new Service(new MaterieDBRepo(),new StudRepo(),new ProfessorDBRepo(),new NoteDBRepo(), new ProfessorValidator()),new StageManager());
    }
}
