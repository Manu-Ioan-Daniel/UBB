package main;

import demos.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nCe demo vrei sa rulezi:");
            System.out.println("  1. Dirty Read");
            System.out.println("  2. Non-Repeatable Read");
            System.out.println("  3. Phantom Read");
            System.out.println("  4. Lost Update");
            System.out.println("  5. Deadlock");
            System.out.println("  6. Batch Insert Performance");
            System.out.println("  7. Ruleaza toate demo-urile");
            System.out.println("  0. Iesire");
            System.out.print("Alege o optiune: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> DirtyReadDemo.run();
                case "2" -> NonRepeatableReadDemo.run();
                case "3" -> PhantomReadDemo.run();
                case "4" -> LostUpdateDemo.run();
                case "5" -> DeadlockDemo.run();
                case "6" -> BatchInsertDemo.run();
                case "7" -> {
                    DirtyReadDemo.run();
                    NonRepeatableReadDemo.run();
                    PhantomReadDemo.run();
                    LostUpdateDemo.run();
                    DeadlockDemo.run();
                    BatchInsertDemo.run();
                }
                case "0" -> {
                    System.out.println("Gata, pa");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Nu e o optiune valida.");
            }
        }
    }
}