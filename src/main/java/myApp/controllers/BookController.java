package myApp.controllers;

import myApp.models.Book;
import myApp.models.Person;
import myApp.models.RequestParamert;
import myApp.services.BookService;
import myApp.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String showAllBooks(@RequestParam(value = "page", required = false) Integer num, @RequestParam(value = "books_per_page", required = false) Integer books_per_page,@RequestParam(value = "sort_by_year", required = false) Boolean sorted, Model model){
        model.addAttribute("books", bookService.getAll(num, books_per_page, sorted));
        return "books/showAll";
    }

    @GetMapping("/{id}")
    public String showBookInfo(@PathVariable("id") int id, Model model){
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("person", new Person());
        System.out.println(book.getPerson());
        if (book.getPerson() != null)
            model.addAttribute("owner", book.getPerson());
        else
            model.addAttribute("people", personService.getAllPerson());
        return "books/showBookInfo";
    }

    @PatchMapping("/{id}/selectPerson")
    public String selectPerson(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookService.selectPerson(person, id);
        return String.format("redirect:/books/%d", id);
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookService.release(id);
        return String.format("redirect:/books/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.deleteById(id);
        return "redirect:/books";
        }

        @GetMapping("/new")
        public String newPage(@ModelAttribute("book")Book book){
            return "books/new";
        }

        @PostMapping("/create")
        public String createNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/new";
        bookService.save(book);
        return "redirect:/books";
        }

        @GetMapping("/{id}/edit")
        public String editPage(@PathVariable("id") int id,Model model){
            model.addAttribute("book", bookService.getById(id));
            return "books/editPage";
        }

        @PatchMapping("/{id}/update")
        public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,BindingResult bindingResult){
            if (bindingResult.hasErrors())
                return "books/editPage";
            bookService.update(id, book);
            return String.format("redirect:/books/%d", id);
        }

        @GetMapping("/search")
        public String searchPage(Model model){
            model.addAttribute("search", new RequestParamert(""));
            model.addAttribute("books", new ArrayList<Book>());
            return "books/search";
        }

        @PostMapping("/search")
    public String findBooksByName(@ModelAttribute("search") RequestParamert search, Model model){
        model.addAttribute("books", bookService.getBookByStart(search.getS()));
        return "books/search";
    }

}
