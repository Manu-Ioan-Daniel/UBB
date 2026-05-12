package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "profesori")
@Where(clause = "is_deleted = false")
public class Professor extends models.Entity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "materie_id")
    private Long materieId;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    public Professor() {

    }

    public Professor(String name, Integer age, String email, Long materieId) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.materieId = materieId;
    }

    public Professor(String name, Integer age, String email, String phone, Long materieId) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public Long getMaterieId() {
        return materieId;
    }

    public Integer getVersion() { return version; }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMaterieId(Long materieId) {
        this.materieId = materieId;
    }

    public void softDelete(String username) {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = username;
    }

    public void restore() {
        this.isDeleted = false;
        this.deletedAt = null;
        this.deletedBy = null;
    }
}
