package com.ereed.libraryproject.controllers;

import com.ereed.libraryproject.dao.BookDAO;
import com.ereed.libraryproject.dao.PersonDAO;
import com.ereed.libraryproject.models.Book;
import com.ereed.libraryproject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    //   private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        //      this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }

    /*-------------------- страница со списком книг ------------------------*/

    @GetMapping()
    public String index(Model model){
        // получение всех книг из DAO и передача их на отображение в представление
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    /*-------------------- страница конкретной книги ------------------------*/

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        // получение одной книги из DAO и передача их на отображение в представление
        model.addAttribute("result", bookDAO.merge(id));
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", personDAO.index());
        return "books/show";
    }

    /*--------------- страница с формой для создания книги --------------------*/

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    /*-------------------------- добавление книги ------------------------------*/

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {


        //  bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/new";

        // Добавление человека в БД
        bookDAO.save(book);
        return "redirect:/books";
    }

    /*-------------------------- редактирование книги ---------------------------*/

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        //      bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    /*------------------------------ удаление книги ------------------------------*/

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    /*----------------------- назначение пользователя книги -----------------------*/

    @PatchMapping("/{id}/add")
    public String assignBook(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookDAO.assignPerson(person, id);
        return "redirect:/books";
    }

    /*----------------------- освобождение пользователя книги -----------------------*/

    @PatchMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id){
        bookDAO.deletePerson(id);
        return "redirect:/books";
    }
}