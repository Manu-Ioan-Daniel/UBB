package Ui;

import domain.User;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import observer.Observer;
import service.ServiceMessage;
import service.ServiceUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChatWindow implements Observer {

    private final ServiceUser serviceUser;
    private final ServiceMessage serviceMessage;
    private final Long currentUserId;
    private final Map<Long, ListView<String>> openChats = new HashMap<>();

    public ChatWindow(ServiceUser serviceUser, ServiceMessage serviceMessage, Long currentUserId) {
        this.serviceUser = serviceUser;
        this.serviceMessage = serviceMessage;
        this.currentUserId = currentUserId;
    }

    public void show() {
        Stage stage = new Stage();
        BorderPane root = new BorderPane();


        ListView<String> userList = new ListView<>();
        List<String> usernames = serviceUser.getAllUsers().stream()
                .filter(u -> !u.getId().equals(currentUserId))
                .map(u -> u.getUsername())
                .toList();
        userList.setItems(FXCollections.observableArrayList(usernames));
        userList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        TextField messageField = new TextField();
        Button sendBtn = new Button("Send");
        HBox bottomBox = new HBox(5, messageField, sendBtn);

        root.setLeft(userList);
        root.setBottom(bottomBox);

        sendBtn.setOnAction(e -> {
            List<String> selectedUsernames = userList.getSelectionModel().getSelectedItems();
            if (!selectedUsernames.isEmpty() && !messageField.getText().isBlank()) {
                List<Long> toIds = selectedUsernames.stream()
                        .map(serviceUser::getUserByUsername)
                        .map(User::getId)
                        .collect(Collectors.toList());
                serviceMessage.sendMessage(currentUserId, toIds, messageField.getText());
                messageField.clear();
            }
        });


        userList.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                String selectedUsername = userList.getSelectionModel().getSelectedItem();
                if(selectedUsername != null) {
                    Long otherUserId = serviceUser.getUserByUsername(selectedUsername).getId();
                    showChatHistory(otherUserId, selectedUsername);
                }
            }
        });

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Chat");
        stage.show();
    }

    private void showChatHistory(Long otherUserId, String otherUsername) {
        Stage historyStage = new Stage();
        ListView<String> historyList = new ListView<>();

        serviceMessage.getConversation(currentUserId, otherUserId).forEach(m -> {
            String from = m.getFromId().equals(currentUserId) ? "You" : otherUsername;
            historyList.getItems().add(from + ": " + m.getMessage());
        });
        openChats.put(otherUserId, historyList);
        Scene scene = new Scene(historyList, 400, 300);
        historyStage.setScene(scene);
        historyStage.setTitle("Chat with " + otherUsername);
        historyStage.show();
        historyStage.setOnCloseRequest(e-> openChats.remove(otherUserId));
    }
    @Override
    public void update() {
        for(Long otherUserId : openChats.keySet()) {
            updateChatHistory(otherUserId, openChats.get(otherUserId));
        }
    }
    private void updateChatHistory(Long otherUserId, ListView<String> historyList) {
        historyList.getItems().clear();
        String otherUsername = serviceUser.getUserById(otherUserId).getUsername();
        serviceMessage.getConversation(currentUserId, otherUserId).forEach(m -> {
            String from = m.getFromId().equals(currentUserId) ? "You" : otherUsername;
            historyList.getItems().add(from + ": " + m.getMessage());
        });
    }
}
