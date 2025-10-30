package ui;

import domain.*;
import enums.DuckType;
import repo.RepoUser;
import service.ServiceUser;

import java.util.Scanner;

public class Ui {
    private final ServiceUser service;
    private final Scanner scanner = new Scanner(System.in);

    public Ui(ServiceUser service) {
        this.service = service;
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
                case "0" -> {
                    System.out.println("Ceau!");
                    return;
                }
                default -> System.out.println("Optiune invalida!");
            }
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
            } else if (type.equals("duck")) {
                System.out.print("Tip rata (FLYING / SWIMMING / FLYING_AND_SWIMMING): ");
                DuckType duckType = DuckType.valueOf(scanner.nextLine().trim().toUpperCase());
                System.out.print("Viteza: ");
                double speed = Double.parseDouble(scanner.nextLine());
                System.out.print("Rezistenta: ");
                double rez = Double.parseDouble(scanner.nextLine());
                Duck duck = new Duck(id, username, email, password, duckType, speed, rez,null);
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

    public static void main(String[] args) {
        RepoUser repo = new RepoUser("src/data/date");
        ServiceUser service = new ServiceUser(repo);
        Ui ui = new Ui(service);
        ui.run();
    }
}
