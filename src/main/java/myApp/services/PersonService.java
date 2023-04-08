package myApp.services;

import myApp.models.Book;
import myApp.models.Person;
import myApp.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPerson(){
        return personRepository.findAll();
    }

    public Person getOneById(int id){
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(int id){
        personRepository.deleteById(id);
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person){
        person.setId(id);
        personRepository.save(person);
    }

    @Transactional
    public List<Book> getBooks(int id){
        Date current = new Date();
        Person person = personRepository.getOne(id);
        Hibernate.initialize(person.getBooks());
        List<Book> books = person.getBooks();
        for (Book book : books){
            if ((current.getTime() - book.getTaken_at().getTime()) / (24 * 60 * 60 * 1000) > 10)
                book.setDelayed(true);
        }
        return books;

    }

}
