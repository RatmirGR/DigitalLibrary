package com.ereed.libraryproject.controllers;

import com.ereed.libraryproject.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.ereed.libraryproject.dao.PersonDAO;
import com.ereed.libraryproject.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    /*-------------------- страница со списком пользователей ------------------------*/

    @GetMapping()
    public String index(Model model){
        // получение всех людей из DAO и передача их на отображение в представление
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    /*-------------------- страница конкретного пользователя ------------------------*/

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        // получение одного человека из DAO и передача их на отображение в представление
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books", personDAO.getBooksByPersonId(id));
        return "people/show";
    }

    /*--------------- страница с формой для создания пользователя --------------------*/

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    /*-------------------------- добавление пользователя ------------------------------*/

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        // Добавление человека в БД
        personDAO.save(person);
        return "redirect:/people";
    }

    /*-------------------------- редактирование пользователя ---------------------------*/

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    /*------------------------------ удаление пользователя ------------------------------*/

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
