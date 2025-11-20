package Ui;

import domain.*;
import enums.DuckType;
import event.Event;
import event.RaceEvent;
import service.ServiceFlock;
import service.ServiceUser;
import service.ServiceEvent;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private final ServiceUser serviceUser;
    private final ServiceFlock serviceFlock;
    private final ServiceEvent serviceEvent;
    private final Scanner scanner = new Scanner(System.in);

    public Ui(ServiceUser serviceUser, ServiceFlock serviceFlock, ServiceEvent serviceEvent) {
        this.serviceEvent = serviceEvent;
        this.serviceUser = serviceUser;
        this.serviceFlock = serviceFlock;
    }

    public void run() {
        showMenu();
        while (true) {
            System.out.print("Alege o optiune: ");
            String option = scanner.nextLine();
            switch (option) {
                case "1" -> addUser();
                case "2" -> removeUser();
                case "3" -> showAllUsers();
                case "4" -> addFlock();
                case "5" -> showAllFlocks();
                case "6" -> addDuckToFlock();
                case "7" -> addFriend();
                case "8" -> removeFriend();
                case "9" -> numberOfCommunities();
                case "10" -> largestCommunity();
                case "11"-> addEvent();
                case "12"-> startEvent();
                case "13"->removeEvent();
                case "14"->{
                    List<Event> events=serviceEvent.getAllEvents();
                    for(Event event:events){
                        System.out.println(event);
                    }
                }
                case "0" -> {
                    System.out.println("Salut!");
                    return;
                }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }


    private void showMenu() {
        System.out.println("1. Adauga utilizator");
        System.out.println("2. Sterge utilizator");
        System.out.println("3. Afiseaza toti utilizatorii");
        System.out.println("4. Adauga flock");
        System.out.println("5. Afiseaza toate flock-urile");
        System.out.println("6. Adauga rata intr-un flock");
        System.out.println("7. Adauga prieten");
        System.out.println("8. Sterge prieten");
        System.out.println("9. Numarul de comunitati");
        System.out.println("10. Dimensiunea celei mai mari comunitati");
        System.out.println("11.Adauga event");
        System.out.println("12.Porneste event");
        System.out.println("13.Sterge event");
        System.out.println("14.Afiseaza toate eventurile");
        System.out.println("0. Iesire");
    }

    private void addUser() {
        try {
            System.out.print("Tip utilizator (person/duck): ");
            String type = scanner.nextLine().trim().toLowerCase();
            System.out.print("Id: ");
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Parola: ");
            String password = scanner.nextLine();

            if (type.equals("person")) {
                System.out.print("Nume: ");
                String name = scanner.nextLine();
                System.out.print("Prenume: ");
                String surname = scanner.nextLine();
                System.out.print("Data nasterii: ");
                String dob = scanner.nextLine();
                System.out.print("Ocupatie: ");
                String occupation = scanner.nextLine();
                System.out.print("Nivel empatie (0-100): ");
                int empathy = Integer.parseInt(scanner.nextLine());
                Person person = new Person(id, username, email, password, name, surname, occupation, dob, empathy);
                serviceUser.addUser(person);
                System.out.println("Persoana adaugata cu succes!");
            } else if (type.equals("duck")) {
                System.out.print("Tip rata (FLYING/SWIMMING/FLYING_AND_SWIMMING): ");
                DuckType duckType = DuckType.valueOf(scanner.nextLine().trim().toUpperCase());
                System.out.print("Viteza: ");
                double speed = Double.parseDouble(scanner.nextLine());
                System.out.print("Rezistenta: ");
                double rez = Double.parseDouble(scanner.nextLine());
                Duck duck = new Duck(id, username, email, password, duckType, speed, rez);
                System.out.print("Id flock(optional,introdu 0 daca nu vrei): ");
                long flockId = Long.parseLong(scanner.nextLine());
                serviceUser.addUser(duck);
                System.out.println("Rata adaugata cu succes!");
                if(flockId != 0){
                    Flock flock = serviceFlock.getFlockById(flockId);
                    serviceFlock.addDuckToFlock(flock, duck);
                }
            }
        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private void removeUser() {
        System.out.print("Introdu ID-ul utilizatorului de sters: ");
        Long id = Long.parseLong(scanner.nextLine());
        try {
            serviceUser.removeUser(id);
            System.out.println("Utilizator sters cu succes!");
        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private void showAllUsers() {
        List<User> users = serviceUser.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void addFlock() {
        System.out.print("ID Flock: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Nume Flock: ");
        String name = scanner.nextLine();
        Flock flock = new Flock(id, name);
        try {
            serviceFlock.addFlock(flock);
            System.out.println("Flock adaugat cu succes!");
        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private void showAllFlocks() {
        List<Flock> flocks = serviceFlock.getAllFlocks();
        for (Flock flock : flocks) {
            System.out.println(flock);
        }
    }

    private void addDuckToFlock() {
        System.out.print("ID Duck: ");
        Long duckId = Long.parseLong(scanner.nextLine());
        System.out.print("ID Flock: ");
        Long flockId = Long.parseLong(scanner.nextLine());
        try {
            serviceFlock.addDuckToFlock(serviceFlock.getFlockById(flockId),(Duck) serviceUser.getUserById(duckId));
            System.out.println("Duck adaugata in flock cu succes!");
        }catch(Exception e){
            System.out.println("Eroare: " + e.getMessage());
        }

    }

    private void addFriend() {
        System.out.print("ID utilizator: ");
        Long id1 = Long.parseLong(scanner.nextLine());
        System.out.print("ID prieten: ");
        Long id2 = Long.parseLong(scanner.nextLine());
        try {
            serviceUser.addFriend(id1, id2);
            System.out.println("Prietenie adaugata cu succes!");
        } catch(Exception e){
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private void removeFriend() {
        System.out.print("ID utilizator: ");
        Long id1 = Long.parseLong(scanner.nextLine());
        System.out.print("ID prieten: ");
        Long id2 = Long.parseLong(scanner.nextLine());
        try {
            serviceUser.removeFriend(id1, id2);
            System.out.println("Prietenie stearsa cu succes!");
        } catch(Exception e){
            System.out.println("Eroare: " + e.getMessage());
        }
    }
    private void numberOfCommunities() {
        try {
            int nr = serviceUser.getNumberOfCommunities();
            System.out.println("Numarul de comunitati este: " + nr);
        } catch(Exception e){
            System.out.println("Eroare: " + e.getMessage());
        }
    }
    private void largestCommunity() {
        try {
            int size = serviceUser.getBiggestCommunitySize();
            System.out.println("Dimensiunea celei mai mari comunitati este: " + size);
        } catch(Exception e){
            System.out.println("Eroare: " + e.getMessage());
        }
    }
    private void addEvent(){
        System.out.print("ID Event: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Tip Event(avem numai RaceEvent momentan): ");
        String type= scanner.nextLine().trim().toLowerCase();
        if(type.equals("raceevent")){
            System.out.print("Numar participanti M: ");
            int M=Integer.parseInt(scanner.nextLine());
            Event event=new RaceEvent(id, serviceUser.getAllSwimmingDucks(), M);
            try {
                serviceEvent.addEvent(event);
                System.out.println("Event adaugat cu succes!");
            } catch (Exception e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }else{
            System.out.println("Tip event invalid!");
        }

    }
    public void removeEvent(){
        System.out.print("ID Event de sters: ");
        Long id = Long.parseLong(scanner.nextLine());
        try {
            serviceEvent.removeEvent(id);
            System.out.println("Event sters cu succes!");
        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }
    public void startEvent(){
        System.out.print("ID Event de pornit: ");
        Long id = Long.parseLong(scanner.nextLine());
        try {
            Event event=serviceEvent.getEventById(id);
            if(event==null){
                System.out.println("Eventul cu ID-ul "+id+" nu exista!");
                return;
            }
            if(event instanceof RaceEvent){
                ((RaceEvent) event).startRace();
                System.out.println("Event pornit cu succes!");
            }else{
                System.out.println("Eventul cu ID-ul "+id+" nu este de tip RaceEvent!");
            }
        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

}

