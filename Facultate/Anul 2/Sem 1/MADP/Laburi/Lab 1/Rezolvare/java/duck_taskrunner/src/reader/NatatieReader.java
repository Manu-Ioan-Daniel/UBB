package reader;

import model.NatatieData;
import model.Rata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NatatieReader {

    public static NatatieData readData(String f){
        File file = new File("src/data/"+f);
        try(Scanner scanner = new Scanner(file)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            Rata[] rate = new Rata[n];
            for(int i=0;i<n;i++){
                rate[i]=new Rata();
                rate[i].setViteza(scanner.nextInt());
            }
            for(int i=0;i<n;i++){
                rate[i].setRezistenta(scanner.nextInt());
            }
            int[] distante = new int[m];
            for(int i=0;i<m;i++){
                distante[i] = scanner.nextInt();
            }
            return new NatatieData(n,m,rate,distante);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return null;
        }
    }
}
