package main;

import javafx.application.Application;
import javafx.stage.Stage;
import repos.MaterieRepo;
import repos.NoteRepo;
import repos.ProfessorRepo;
import repos.StudRepo;
import services.Service;
import utils.StageManager;
import validator.ProfessorValidator;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        StageManager.showMainWindow(new Service(new MaterieRepo(),new StudRepo(),new ProfessorRepo(),new NoteRepo(), new ProfessorValidator()));
    }
}
