package repo;
import domain.Duck;
import domain.Flock;
import errors.RepoError;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepoCard {
    private List<Flock<? extends Duck>> flocks;
    private final String filePath="src/data/dateCard";
    public RepoCard(){
        flocks=new ArrayList<>();
    }
    public void addFlock(Flock<? extends Duck> flock){
        if(flocks.contains(flock)){
            throw new RepoError("Flock already exists!");
        }
        flocks.add(flock);
    }
    public void readDataFromFile(RepoUser repoUser){
        flocks.clear();
        try(BufferedReader br=new BufferedReader(new FileReader(filePath))){
            String line;
            while((line=br.readLine())!=null){
                String[] parts=line.split(",");
                Long id=Long.parseLong(parts[0]);
                String flockName=parts[1];
                List<Duck> members=new ArrayList<>();
                for(int i=2;i<parts.length;i++) {
                    Long duckId = Long.parseLong(parts[i]);
                    Duck duck = (Duck) repoUser.getUserById(duckId);
                    if (duck != null) {
                        members.add(duck);
                    }
                }
                Flock<Duck> flock=new Flock<>(id,flockName,members);
                for(Duck duck:members){
                    duck.setFlock(flock);
                }
                flocks.add(flock);
            }

        } catch (IOException e) {
            throw new RepoError("Error reading file: "+e.getMessage());
        }

    }
    public void writeDataToFile(){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(filePath))){
            for(Flock<? extends Duck> flock:flocks){
                StringBuilder sb=new StringBuilder();
                sb.append(flock.getId()).append(",").append(flock.getFlockName());
                for(Duck duck:flock.getMembers()){
                    sb.append(",").append(duck.getId());
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RepoError("Error writing to file: "+e.getMessage());
        }
    }
    public List<Flock<? extends Duck>> getAll(){
        return flocks;
    }


    public Flock<? extends Duck> getFlockById(Long cardId) {
        for(Flock<? extends Duck> flock:flocks){
            if(flock.getId().equals(cardId)){
                return flock;
            }
        }
        return null;
    }
}
