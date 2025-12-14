package service;

import domain.Message;
import domain.ReplyMessage;
import observer.Observable;
import repo.DatabaseMessageRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceMessage extends Observable {

    private final DatabaseMessageRepository messageRepo;

    public ServiceMessage(DatabaseMessageRepository repo) {
        this.messageRepo = repo;
    }

    // Folosim Long fromId și List<Long> toIds
    public void sendMessage(Long fromId, List<Long> toIds, String text) {
        Message m = new Message(fromId, toIds, text, LocalDateTime.now());
        messageRepo.save(m);
        this.notifyObservers();
    }

    public void replyMessage(Long fromId, List<Long> toIds, String text, Long replyToId) {
        ReplyMessage rm = new ReplyMessage(fromId, toIds, text, LocalDateTime.now(), replyToId);
        messageRepo.save(rm);
        this.notifyObservers();
    }

    public List<Message> getConversation(Long userId1, Long userId2) {
        return messageRepo.getConversation(userId1, userId2);
    }
    public Message findMessageById(Long messageId) {
        return messageRepo.findMessageById(messageId);
    }
}
