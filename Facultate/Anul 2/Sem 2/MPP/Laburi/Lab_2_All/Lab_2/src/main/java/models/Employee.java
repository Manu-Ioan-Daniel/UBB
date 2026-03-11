package models;

public class Employee extends Entity<Long>{

    private final String username;
    private final String password;
    private final String name;
    private final String office;

    public Employee(String username, String password, String name, String office) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.office = office;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getOffice() {
        return office;
    }
}
