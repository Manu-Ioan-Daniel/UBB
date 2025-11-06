package ui;
import domain.*;
import enums.DuckType;
import events.Event;
import events.RaceEvent;
import repo.RepoCard;
import repo.RepoEvent;
import repo.RepoUser;
import service.ServiceCard;
import service.ServiceEvent;
import service.ServiceUser;

import javax.smartcardio.Card;
import java.util.Scanner;

public class Ui {
    private final ServiceUser service;
    private final ServiceCard serviceCard;
    private final Scanner scanner = new Scanner(System.in);
    private final ServiceEvent serviceEvent;

    public Ui(ServiceUser service,ServiceCard serviceCard,ServiceEvent serviceEvent) {
        this.service = service;
        this.serviceCard=serviceCard;
        this.serviceEvent=serviceEvent;
    }

    public void run() {
        while (true) {
            showMenu();
            System.out.print("Alege o optiune: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> addUser();
                case "2" -> removeUser();
                case "3" -> addFriend();
                case "4" -> removeFriend();
                case "5" -> showCommunities();
                case "6" -> showBiggestCommunity();
                case "7" -> showAllUsers();
                case "8" ->addCard();
                case "9" ->afiseazaCarduri();
                case "10"->addEvent();
                case "11"->incepeEveniment();
                case "0" -> {
                    System.out.println("Ceau!");
                    return;
                }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }

    private void incepeEveniment() {
        try {
            serviceEvent.runEvent();
        }catch(Exception e){
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private void showMenu() {
        System.out.println("1. Adauga utilizator");
        System.out.println("2. Sterge utilizator");
        System.out.println("3. Adauga prietenie");
        System.out.println("4. Sterge prietenie");
        System.out.println("5. Afiseaza numarul de comunitati");
        System.out.println("6. Afiseaza cea mai mare comunitate (ca marime)");
        System.out.println("7. Afiseaza toti utilizatorii");
        System.out.println("8. Adauga card");
        System.out.println("9. Afiseaza toate cardurile si membrii lor");
        System.out.println("10. Adauga eveniment");
        System.out.println("11. Incepe eveniment");
        System.out.println("0. Iesire");
    }

    private void addUser() {
        System.out.print("Tip utilizator(person/duck): ");
        String type = scanner.nextLine().trim().toLowerCase();
        if(!type.equals("person") && !type.equals("duck")){
            System.out.println("Tip utilizator invalid!");
            return;
        }
        System.out.print("Id: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Parola: ");
        String password = scanner.nextLine();
        try {
            if (type.equals("person")) {
                System.out.print("Nume: ");
                String name = scanner.nextLine();
                System.out.print("Prenume: ");
                String surname = scanner.nextLine();
                System.out.print("Data nasterii: ");
                String dob = scanner.nextLine();
                System.out.print("Ocupatie: ");
                String occupation = scanner.nextLine();
                System.out.print("Nivel empatie (0-10): ");
                int empathy = Integer.parseInt(scanner.nextLine());
                Person person = new Person(id, username, email, password, name, surname, dob, occupation, empathy);
                service.addUser(person);
                System.out.println("Persoana adaugata cu succes!");
            } else {
                System.out.print("Tip rata (FLYING / SWIMMING / FLYING_AND_SWIMMING): ");
                DuckType duckType = DuckType.valueOf(scanner.nextLine().trim().toUpperCase());
                System.out.print("Viteza: ");
                double speed = Double.parseDouble(scanner.nextLine());
                System.out.print("Rezistenta: ");
                double rez = Double.parseDouble(scanner.nextLine());
                Duck duck = switch (duckType) {
                    case FLYING -> new FlyingDuck(id, username, email, password, speed, rez);
                    case SWIMMING -> new SwimmingDuck(id, username, email, password, speed, rez);
                    case FLYING_AND_SWIMMING -> null;
                };
                assert duck != null;
                System.out.print("Id card: ");
                Long cardId=Long.parseLong(scanner.nextLine());
                duck.setFlock(serviceCard.getFlockById(cardId));
                service.addUser(duck);
                System.out.println("Rata adaugata cu succes!");
            }
        } catch (Exception e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private void removeUser() {
        System.out.print("Introdu ID-ul utilizatorului de sters: ");
        Long id = Long.parseLong(scanner.nextLine());
        try{
            service.removeUser(id);
            System.out.println("Utilizator sters cu succes!");
        }catch(Exception e){
            System.out.println("Eroare: " + e.getMessage());
        }
    }
    void afiseazaCarduri(){
        for(Flock<? extends Duck> flock:serviceCard.getAll()){
            System.out.println(flock);
        }
    }
    private void addFriend() {
        System.out.print("ID utilizator: ");
        Long id1 = Long.parseLong(scanner.nextLine());
        System.out.print("ID prieten: ");
        Long id2 = Long.parseLong(scanner.nextLine());
        try{
            service.addFriend(id1,id2);
            System.out.println("Prietenie adaugata cu succes!");
        }catch(Exception e){
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private void removeFriend() {
        System.out.print("ID utilizator: ");
        Long id1 = Long.parseLong(scanner.nextLine());
        System.out.print("ID prieten: ");
        Long id2 = Long.parseLong(scanner.nextLine());
        try{
            service.removeFriend(id1, id2);
            System.out.println("Prietenie stearsa cu succes!");
        }catch(Exception e){
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private void showCommunities() {
        int count = service.getNumberOfCommunities();
        System.out.println("Numar de comunitati: " + count);
    }

    private void showBiggestCommunity() {
        int size = service.biggestCommunitySize();
        System.out.println("Cea mai mare comunitate are " + size + " membri!");
    }
    private void showAllUsers(){
        for(User user:service.getAllUsers()){
            System.out.println(user);
        }
    }
    private void addCard(){
        System.out.print("ID Card: ");
        Long id=Long.parseLong(scanner.nextLine());
        System.out.print("Tip Card (Duck/FlyingDuck/SwimmingDuck): ");
        String type=scanner.nextLine().trim().toUpperCase();
        System.out.print("Nume Card: ");
        String name=scanner.nextLine().trim().toUpperCase();
        try{
            serviceCard.addCard(id,name,type);
        }catch(Exception e){
            System.out.println("Eroare: " + e.getMessage());
        }
    }
    private void addEvent(){
        System.out.print("ID Eveniment: ");
        Long id=Long.parseLong(scanner.nextLine());
        System.out.print("Nume Eveniment: ");
        String name=scanner.nextLine().trim();
        System.out.print("Introdu M: ");
        int M=Integer.parseInt(scanner.nextLine());
        RaceEvent event= new RaceEvent(id,name,M);
        try{
            serviceEvent.addEvent(event);
            System.out.println("Eveniment adaugat cu succes!");
        }catch(Exception e){
            System.out.println("Eroare: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        RepoUser repo = new RepoUser("src/data/date");
        ServiceUser service = new ServiceUser(repo);
        ServiceCard serviceCard=new ServiceCard(repo,new RepoCard());
        RepoEvent repoEvent=new RepoEvent();
        ServiceEvent serviceEvent=new ServiceEvent(repoEvent,repo);
        Ui ui = new Ui(service,serviceCard,serviceEvent);
        ui.run();
    }
}
