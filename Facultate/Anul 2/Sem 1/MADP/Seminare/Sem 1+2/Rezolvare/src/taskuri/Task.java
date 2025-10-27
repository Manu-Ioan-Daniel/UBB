package taskuri;

import java.util.Objects;

public abstract class Task {
    private String taskID;
    private String descriere;

    public Task(String taskID, String descriere) {
        this.taskID = taskID;
        this.descriere = descriere;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
    public abstract void execute();
    @Override
    public String toString(){
        return "taskuri.Task ID: " + taskID + ", Descriere: " + descriere;
    }
    public boolean equals(Object obj) {
        if(obj==null || obj.getClass()!=this.getClass()){
            return false;
        }
        Task other = (Task) obj;
        return this.taskID.equals(other.taskID) && this.descriere.equals(other.descriere);
    }
    @Override
    public int hashCode() {
        return Objects.hash(taskID, descriere);
    }

}
