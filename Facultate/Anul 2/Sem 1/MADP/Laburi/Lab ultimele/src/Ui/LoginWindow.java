package Ui;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import service.ServiceMessage;
import service.ServiceUser;


public class LoginWindow{
    private final ServiceUser serviceUser;
    private final ServiceMessage serviceMessage;
    public LoginWindow(ServiceUser serviceUser, ServiceMessage serviceMessage) {
        this.serviceUser = serviceUser;
        this.serviceMessage = serviceMessage;
    }
    public void start(Stage stage) {
        stage.setTitle("Login");
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
        grid.add(loginButton, 1, 2);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            try {
                boolean success = login(username, password);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
                    stage.close();
                    UiJavaFx mainUI = new UiJavaFx(serviceUser, serviceUser.getUserByUsername(username),serviceMessage);
                    Stage mainStage = new Stage();
                    mainUI.show(mainStage);

                } else {
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Login Failed", ex.getMessage());
            }
        });

        Scene scene = new Scene(grid, 350, 200);
        stage.setScene(scene);
        stage.show();
    }
    private boolean login(String username, String password) {
        return serviceUser.authenticateUser(username, password);
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
