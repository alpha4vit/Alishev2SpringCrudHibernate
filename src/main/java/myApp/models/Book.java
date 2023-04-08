package myApp.models;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty( message = "Имя не может быть пустым")
    private String name;

    @Max(value = 2023,message = "Год выпуска не может быть больше настоящего")
    @Column(name = "publishment")
    private int publishment;

    @NotEmpty( message = "ФИО автора не может быть пустым")
    @Column(name = "author")
    private String author;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date taken_at;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Transient
    private Boolean delayed;

    public Book() {}

    public Book(String name, int publishment, Person person) {
        this.name = name;
        this.publishment = publishment;
        this.person = person;
        this.delayed = false;
    }

    public Boolean getDelayed() {
        return delayed;
    }

    public void setDelayed(Boolean delayed) {
        this.delayed = delayed;
    }

    public Date getTaken_at() {
        return taken_at;
    }

    public void setTaken_at(Date taken_at) {
        this.taken_at = taken_at;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPublishment() {
        return publishment;
    }

    public void setPublishment(int publishment) {
        this.publishment = publishment;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
