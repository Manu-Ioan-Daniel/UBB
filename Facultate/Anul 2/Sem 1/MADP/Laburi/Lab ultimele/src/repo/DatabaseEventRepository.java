package repo;

import domain.User;
import errors.RepoError;
import event.Event;
import event.RaceEvent;
import observer.Observer;

import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseEventRepository {
    private final String url;
    private final DatabaseUserRepository userRepository;
    public DatabaseEventRepository(String url, DatabaseUserRepository userRepository) {
        this.url = url;
        this.userRepository = userRepository;
    }
    public List<Event> getAllEvents() {
        Map<Long, Event> events = new HashMap<>();
        Map<Long, List<Long>> memberMap = new HashMap<>();

        try (Connection connection = DriverManager.getConnection(url)) {

            PreparedStatement psEvents = connection.prepareStatement(
                    "SELECT e.eventId, e.eventName, e.eventType, r.M " +
                            "FROM events e " +
                            "LEFT JOIN raceEvents r ON e.eventId = r.eventId"
            );
            ResultSet rsEvents = psEvents.executeQuery();
            while (rsEvents.next()) {
                long id = rsEvents.getLong("eventId");
                String name = rsEvents.getString("eventName");
                String type = rsEvents.getString("eventType");
                int m = rsEvents.getInt("M");

                Event event;
                if (m != 0) {
                    event = new RaceEvent(id,name,m);
                } else {
                    event = new Event(id,name);
                }
                events.put(id, event);
            }

            PreparedStatement psMembers = connection.prepareStatement(
                    "SELECT eventId, userId FROM eventMembers"
            );
            ResultSet rsMembers = psMembers.executeQuery();

            while (rsMembers.next()) {
                long eid = rsMembers.getLong("eventId");
                long uid = rsMembers.getLong("userId");
                if(!memberMap.containsKey(eid)){
                    memberMap.put(eid, new ArrayList<>());
                }
                if(!memberMap.get(eid).contains(uid)) {
                    memberMap.get(eid).add(uid);
                }
            }
            for (var entry : events.entrySet()) {
                long eventId = entry.getKey();
                Event ev = entry.getValue();

                List<Long> users = memberMap.get(eventId);
                if (users == null) continue;
                for (Long uid : users) {
                    ev.subscribe(userRepository.getUserById(uid));
                }
            }

            return new ArrayList<>(events.values());

        } catch (SQLException e) {
            throw new RuntimeException("Error loading events", e);
        }
    }
    public void addEvent(Event event) {
        if(eventExists(event.getName())){
            throw new RepoError("Event with name "+event.getName()+" already exists.");
        }
        try (Connection connection = DriverManager.getConnection(url)) {

            String sqlEvent = "INSERT INTO events (eventName, eventType) VALUES (?, ?)";
            PreparedStatement psEvent = connection.prepareStatement(sqlEvent, Statement.RETURN_GENERATED_KEYS);
            psEvent.setString(1, event.getName());
            psEvent.setString(2, event instanceof RaceEvent ? "RaceEvent" : "NormalEvent");
            psEvent.executeUpdate();
            ResultSet generatedKeys = psEvent.getGeneratedKeys();
            long eventId;
            if (generatedKeys.next()) {
                eventId = generatedKeys.getLong(1);
            }else{
                throw new SQLException("Creating event failed, no ID obtained.");
            }
            if(event instanceof RaceEvent){
                String sqlRace = "INSERT INTO raceEvents (eventId, M) VALUES (?, ?)";
                PreparedStatement psRace = connection.prepareStatement(sqlRace);
                psRace.setLong(1, eventId);
                psRace.setInt(2, ((RaceEvent) event).getM());
                psRace.executeUpdate();
            }
            String sqlMember = "INSERT INTO eventMembers (eventId, userId) VALUES (?, ?)";
            PreparedStatement psMember = connection.prepareStatement(sqlMember);
            for (Observer member : event.getObservers()) {
                User user=(User) member;
                psMember.setLong(1, eventId);
                psMember.setLong(2, user.getId());
                psMember.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void removeEvent(String eventName) {
        if(!eventExists(eventName)){
            throw new RepoError("Event with name "+eventName+" does not exist.");
        }
        try (Connection connection = DriverManager.getConnection(url)) {
            String sqlDeleteEvent = "DELETE FROM events WHERE eventName = ?";
            PreparedStatement psDeleteMembers = connection.prepareStatement(sqlDeleteEvent);
            psDeleteMembers.setString(1, eventName);
            psDeleteMembers.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    public boolean eventExists(String eventName) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT 1 FROM events WHERE eventName = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, eventName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    public Event getEventById(Long eventId){
//        List<Event> events=getAllEvents();
//        for(Event event:events){
//            if(event.getId().equals(eventId)){
//                return event;
//            }
//        }
//        return null;
//    }

    public Event getEventByName(String eventName) {
        List<Event> events = getAllEvents();
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return event;
            }
        }
        return null;
    }
}