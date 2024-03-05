package lib.controllers;


import jakarta.validation.Valid;
import lib.dao.PeopleDAO;
import lib.models.Person;
import lib.services.PeopleService;
import lib.util.PersonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PeopleDAO peopleDAO;
    private final PersonValidation personValidation;

    @Autowired
    public PeopleController(PeopleService peopleService, PeopleDAO peopleDAO, PersonValidation personValidation) {
        this.peopleService = peopleService;
        this.peopleDAO = peopleDAO;
        this.personValidation = personValidation;
    }
    @GetMapping()
    public String index(Model model) {
        List<Person> people = peopleService.findAll();
        if (people.isEmpty()) {
            System.out.println("error");
        }
        model.addAttribute("people", people);
        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("book", peopleService.getBooksByPersonId(id));

        return "people/show";
    }
    //newUser (return form with new user)
    @GetMapping("/new")
    public String newUser(@ModelAttribute("person") Person person) {
        return "people/new_user";
    }


    //create (create user from main page)
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidation.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new_user";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    //edit (edit existing user)
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    //update (updates full list of people)
    //binding result - validation from spring
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "people/edit";
        return "redirect:/people";
    }

    //delete (deletes user)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        return "redirect:/people";
    }
}
