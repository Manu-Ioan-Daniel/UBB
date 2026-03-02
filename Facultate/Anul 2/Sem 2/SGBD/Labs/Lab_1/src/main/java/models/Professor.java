package models;

public class Professor extends Entity<Long> {
    private final String name;
    private final Integer age;
    private final String email;
    private final Long materieId;

    public Professor(String name, Integer age, String email, Long materieId) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.materieId = materieId;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Long getMaterieId() {
        return materieId;
    }
}
