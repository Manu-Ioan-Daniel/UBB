package Ui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import observer.Observer;
import service.ServiceFriendRequest;
import service.ServiceUser;
import utils.InformationAlert;

import java.util.List;

public class FriendRequestWindow implements Observer {
    private final ServiceFriendRequest serviceFriendRequest;
    private final ServiceUser serviceUser;
    private ListView<String> friendRequestList;
    private final Long currentId;
    FriendRequestWindow(ServiceFriendRequest serviceFriendRequest, ServiceUser serviceUser,Long currentId){
        this.serviceFriendRequest = serviceFriendRequest;
        this.serviceUser=serviceUser;
        this.currentId = currentId;
    }
    public void show(){
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        init();
        root.setLeft(friendRequestList);
        root.setBottom(createButtonsBox());
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }
    private void init(){
        initializeFriendRequestList();
    }
    private void initializeFriendRequestList(){
        friendRequestList = new ListView<>();
        List<String> requests = serviceFriendRequest.getUserFriendRequests(currentId).stream()
                .filter(friendRequest -> friendRequest.getStatus().equals("pending"))
                .map(friendRequest -> serviceUser.getUserById(friendRequest.getFrom()).getUsername())
                .toList();
        friendRequestList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        friendRequestList.setItems(FXCollections.observableList(requests));
    }
    private void refreshFriendRequestList(){
        List<String> requests = serviceFriendRequest.getUserFriendRequests(currentId).stream()
                .filter(friendRequest -> friendRequest.getStatus().equals("pending"))
                .map(friendRequest -> serviceUser.getUserById(friendRequest.getFrom()).getUsername())
                .toList();
        friendRequestList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        friendRequestList.setItems(FXCollections.observableList(requests));
    }

    private HBox createButtonsBox(){
        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(e->acceptFriendRequest());
        Button denyButton = new Button("Deny");
        denyButton.setOnAction(e-> denyFriendRequest());

        HBox buttonBox = new HBox(10,acceptButton,denyButton);
        buttonBox.setPadding(new Insets(10));
        return buttonBox;
    }

    private void acceptFriendRequest() {
        try {
            List<String> usernames = List.copyOf(friendRequestList.getSelectionModel().getSelectedItems());
            String currentUsername = serviceUser.getUserById(currentId).getUsername();
            for (String username : usernames) {
                serviceUser.addFriend(username, currentUsername);
                serviceUser.addFriend(currentUsername, username);
                serviceFriendRequest.changeFriendRequestStatus(serviceUser.getUserByUsername(username).getId(), serviceUser.getUserByUsername(currentUsername).getId(), "accepted");
            }
            refreshFriendRequestList();
        }catch(Exception e){
            InformationAlert.alert(e.getMessage());
        }
    }

    private void denyFriendRequest() {
        try {
            List<Long> selectedIds = friendRequestList.getSelectionModel().getSelectedItems().stream()
                    .map(username -> serviceUser.getUserByUsername(username).getId())
                    .toList();
            for (Long fromId : selectedIds) {
                serviceFriendRequest.changeFriendRequestStatus(fromId, currentId, "denied");
            }
            refreshFriendRequestList();
        }catch(Exception e){
            InformationAlert.alert(e.getMessage());
        }
    }
    @Override
    public void update(){
        refreshFriendRequestList();
    }

}
