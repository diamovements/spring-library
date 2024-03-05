package lib.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lib.models.Book;

import java.util.List;

@Entity
@Table(name="firstdb.person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_person")
    private int id_person;
    @NotEmpty(message = "Введите имя")
    @Column(name="name")
    @Size(min=2, max=100, message="Имя должно быть от 2 до 100 символов")
    private String fullName;

    @Column(name="birth")
    @NotEmpty
    private int birthdate;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(int id_person, String fullName, int birthdate) {
        this.id_person = id_person;
        this.fullName = fullName;
        this.birthdate = birthdate;
    }
    public Person() {};

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(int birthdate) {
        this.birthdate = birthdate;
    }
    public List<Book> getBooks() {
        return books;
    }
}
