package models;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Table(name = "profesori")
@SQLDelete(sql = "UPDATE profesori SET is_deleted = true, deleted_at = now(), deleted_by = current_user WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Professor extends models.Entity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "materie_id")
    private Long materieId;

    @Column(name = "phone")
    private String phone;

    public Professor() {

    }

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

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setMaterieId(Long materieId) {
        this.materieId = materieId;
    }

    // New getters/setters
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

}
