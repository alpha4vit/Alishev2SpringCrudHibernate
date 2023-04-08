package myApp.controllers;

import myApp.models.Book;
import myApp.models.Person;
import myApp.services.PersonService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("")
    public String showAllPeople(Model model){
        model.addAttribute("people", personService.getAllPerson());
        return "people/showAll";
    }

    @GetMapping("/{id}")
    public String showPersonalInfo(@PathVariable("id") int id, Model model){
        model.addAttribute("person",personService.getOneById(id));
        model.addAttribute("books", personService.getBooks(id));

        return "people/showOne";
    }

    @DeleteMapping ("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id){
        personService.deleteById(id);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("person") Person person){
        return "people/createPerson";
    }

    @PostMapping()
    public String newPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "people/createPerson";
        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.getOneById(id));
        return "people/editPage";
    }

    @PatchMapping("/{id}/editSubmit")
    public String update(@ModelAttribute("person") @Valid Person person, @PathVariable("id") int id, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "people/editPage";
        personService.update(id, person);
        return String.format("redirect:/people/ %d", id);
    }



}
