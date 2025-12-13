package Ui;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.Main;
import service.ServiceUser;
import utils.UserLogins;


public class LoginWindow{
    private static ServiceUser serviceUser;
    public void start(Stage stage) {
        stage.setTitle("Login");
        init();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        Label messageLabel = new Label();
        grid.add(loginButton, 1, 2);
        grid.add(messageLabel, 1, 3);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            try {
                boolean success = login(username, password);
                if (success) {
                    if(UserLogins.getLoggedInUsers().contains(username)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Login Info");
                        alert.setHeaderText(null);
                        alert.setContentText("User " + username + " is already logged in.");
                        alert.showAndWait();
                        return;
                    }

                    UserLogins.getLoggedInUsers().add(username);

                    UiJavaFx mainUI = new UiJavaFx(serviceUser, serviceUser.getUserByUsername(username));
                    Stage mainStage = new Stage();
                    mainUI.show(mainStage);
                    mainStage.setOnCloseRequest(ev -> {
                        UserLogins.getLoggedInUsers().remove(username);
                    });

                    usernameField.clear();
                    passwordField.clear();
                    messageLabel.setText("Login successful! New window opened.");

                } else {
                    messageLabel.setText("Invalid username or password.");
                }
            } catch (Exception ex) {
                messageLabel.setText("Error: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(grid, 350, 200);
        stage.setScene(scene);
        stage.show();
    }
    public void init() {
        serviceUser = Main.getService();
    }
    private boolean login(String username, String password) {
        return serviceUser.authenticateUser(username, password);
    }
}
