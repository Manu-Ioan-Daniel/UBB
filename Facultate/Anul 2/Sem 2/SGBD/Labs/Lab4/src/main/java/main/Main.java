package main;

import javafx.application.Application;
import javafx.stage.Stage;
import repos.*;
import services.Service;
import utils.StageManager;
import validator.ProfessorValidator;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        StageManager.showMainWindow(new Service(new MaterieDBRepoORM(),new StudRepoORM(),new ProfessorDBRepoORM(),new NoteDBRepoORM(), new ProfessorValidator()),new StageManager());
    }
}

