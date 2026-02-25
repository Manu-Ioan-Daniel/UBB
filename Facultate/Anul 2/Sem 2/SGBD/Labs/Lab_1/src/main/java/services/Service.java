package services;

import models.Materie;
import repos.Repository;

import java.util.List;

public class Service {
    private final Repository<Long, Materie> repoMaterii;

    public Service(Repository<Long, Materie> repoMaterii) {
        this.repoMaterii = repoMaterii;
    }

    public List<Materie> getAllMaterii(){
        return repoMaterii.findAll();
    }
}
