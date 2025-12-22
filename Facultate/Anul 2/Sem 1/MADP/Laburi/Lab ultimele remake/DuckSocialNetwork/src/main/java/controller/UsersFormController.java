package controller;

import domain.Duck;
import domain.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.UserModel;
import utils.alerts.Alert;
import utils.observer.Observer;

import java.io.IOException;


public class UsersFormController implements Observer {
    @FXML
    private BorderPane root;

    @FXML
    private Button addUserBtn;

    @FXML
    private Button friendsBtn;

    @FXML
    private Button signoutBtn;

    @FXML
    private Button deleteUserBtn;

    @FXML
    private Button chatBtn;

    @FXML
    private Label duckCount;

    @FXML
    private Label peopleCount;

    @FXML
    private Label userCount;

    @FXML
    private Label usernameLabel;

    @FXML
    private TableView<User> usersTableView;

    @FXML
    private TableColumn<User,String> usernameColumn;

    @FXML
    private TableColumn<User,String> emailColumn;

    @FXML
    private TableColumn<User,String> friendsColumn;

    @FXML
    private TableColumn<User,String> typeColumn;

    @FXML
    private Pagination pagination;

    private UserModel userModel;
    private Stage addUserStage;
    private Stage signoutAlert;
    private Stage deleteErrorAlert;

    private final static int USERS_PER_PAGE = 3;


    public void initData(String username, UserModel userModel) {
        usernameLabel.setText(username);
        this.userModel = userModel;
        userModel.addObserver(this);
        initUsersTable();
        loadLabelInfo();
        initPagination();
    }

    private void initPagination() {
        loadPaginationPageCount();
        pagination.setPageFactory((index)->{
            refreshUsersTable();
            return new VBox();
        });
    }

    private void initUsersTable() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        //TODO: friends column
        typeColumn.setCellValueFactory(cell->
                new SimpleStringProperty(cell.getValue() instanceof Duck ? "duck" : "person"));
    }

    public void loadLabelInfo(){
        userCount.setText(Integer.toString(userModel.getTotalUsers()));
        duckCount.setText(Integer.toString(userModel.getTotalDucks()));
        peopleCount.setText(Integer.toString(userModel.getTotalPeople()));
    }

    public void loadPaginationPageCount(){
        int totalUsers = userModel.getTotalUsers();
        int pageCount = totalUsers/USERS_PER_PAGE + (totalUsers % USERS_PER_PAGE == 0 ? 0 : 1);
        pagination.setPageCount(pageCount);
    }

    public void signout(){

        userModel.removeObserver(this);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginWindow.fxml"));
        Parent newRoot;
        try{
            newRoot = fxmlLoader.load();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        LoginController controller = fxmlLoader.getController();
        controller.setUserModel(userModel);

        Scene scene = new Scene(newRoot);

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @Override
    public void update(){
        loadLabelInfo();
        loadPaginationPageCount();
        refreshUsersTable();
    }
    private void refreshUsersTable(){
        int pageIndex = pagination.getCurrentPageIndex();
        usersTableView.setItems(userModel.findUsersFromPage(pageIndex, USERS_PER_PAGE));
        if(!usersTableView.getSortOrder().contains(usernameColumn))
            usersTableView.getSortOrder().add(usernameColumn);
        usersTableView.sort();
    }

    @FXML
    public void handleSignout(ActionEvent e){
        if(signoutAlert != null){
            signoutAlert.toFront();
            return;
        }
        signoutAlert = Alert.confirmationAlert(this::signout);
        signoutAlert.setOnHidden(ev->signoutAlert=null);
        signoutAlert.show();

    }

    @FXML
    public void handleAdd(ActionEvent e){
        FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("/view/addUserForm.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
        }catch(IOException ex) {
            throw new RuntimeException(ex);
        }

        AddUserFormController controller = fxmlLoader.getController();
        controller.setUserModel(userModel);

        Scene scene = new Scene(root);

        if(addUserStage!=null){
            addUserStage.close();
            return;
        }
        addUserStage = new Stage();
        addUserStage.setScene(scene);
        addUserStage.initStyle(StageStyle.UNDECORATED);
        addUserStage.setOnHidden(ev->addUserStage=null);
        addUserStage.show();
    }

    public void handleDelete(ActionEvent e){
        User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if(selectedUser == null){
            if(deleteErrorAlert!=null){
                deleteErrorAlert.close();
            }
            deleteErrorAlert = Alert.errorAlert("You did not select anything!");
            deleteErrorAlert.setOnHidden(ev->deleteErrorAlert=null);
            deleteErrorAlert.show();
            return;
        }
        if(selectedUser.getUsername().equals(usernameLabel.getText())){
            if(deleteErrorAlert!=null){
                deleteErrorAlert.close();
            }
            deleteErrorAlert = Alert.errorAlert("You cannot delete yourself!");
            deleteErrorAlert.setOnHidden(ev->deleteErrorAlert=null);
            deleteErrorAlert.show();
            return;
        }
        userModel.delete(selectedUser.getId());
    }

}
