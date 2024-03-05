package lib.models;


import java.util.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="firstdb.book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_book")
    private int id_book;

    @NotEmpty(message = "Введите имя")
    @Size(min=2, max=100, message="Имя должно быть длиной от 2 до 100 букв")
    @Column(name="author")
    private String author;
    @NotEmpty
    @Size(min=2, max=100, message="Название должно быть длиной от 2 до 100 символов")
    @Column(name="title")
    private String title;
    @NotEmpty
    @Column(name="year")
    @Min(value=1800, message = "Год должен быть не менее 1800")
    private int year;

    @Transient
    private boolean expired;

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    @Column(name="taken_at")
    @Temporal(TemporalType.DATE)
    private Date takenAt;

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @ManyToOne
    @JoinColumn(name="id_person", referencedColumnName = "id_person")
    private Person owner;

    public Book(String author, String title, int year) {

        this.author = author;
        this.title = title;
        this.year = year;

    }
    public Book() {};
    public String toString() {

        return title + ", " + author + ", " + year;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public void setExpired(boolean expired) {
        this.expired=expired;
    }
}
