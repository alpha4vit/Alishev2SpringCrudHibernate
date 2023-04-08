package myApp.services;

import myApp.models.Book;
import myApp.models.Person;
import myApp.repositories.BookRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.StyledEditorKit;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll(Integer num, Integer booksPerPage, Boolean sorted) {
        if (num != null && booksPerPage != null && sorted != null)
            return bookRepository.findAll(PageRequest.of(num, booksPerPage, Sort.by("publisment"))).getContent();
        else if (sorted != null)
            return bookRepository.findAll(Sort.by("publishment"));
        else if (num != null &&booksPerPage != null)
            return bookRepository.findAll(PageRequest.of(num ,booksPerPage)).getContent();
        else
            return bookRepository.findAll();
    }

    public Book getById(int id){
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(int id){
        bookRepository.deleteById(id);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updBook){
        updBook.setId(id);
        bookRepository.save(updBook);
    }

    @Transactional
    public void selectPerson(Person person, int id){
        Book book = bookRepository.findById(id).orElse(null);
        book.setPerson(person);
        book.setTaken_at(new Date());
        bookRepository.save(book);
    }

    @Transactional
    public void release(int id){
        Book book = bookRepository.getOne(id);
        book.setPerson(null);
        book.setTaken_at(null);
    }

    @Transactional
    public List<Book> getBookByStart(String start){
        List<Book> books = bookRepository.findByNameStartingWith(start);
        return books;
    }

}
