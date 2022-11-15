package com.ereed.libraryproject.dao;

import com.ereed.libraryproject.models.Book;
import com.ereed.libraryproject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("select * from Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id){
        return jdbcTemplate.query("select * from Book where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save(Book book){
        jdbcTemplate.update("insert into Book(name, author, year_of_created, check_status) values (?, ?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear_of_created(), book.isCheck_status());
    }

    public void update(int id, Book book){
        jdbcTemplate.update("update Book set name=?, author=?, year_of_created=?, check_status=? where id=?",
                book.getName(), book.getAuthor(), book.getYear_of_created(), book.isCheck_status(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("delete from Book where id=?", id);
    }

    public Person merge(int id){
        return jdbcTemplate.query("select Person.id, Person.name, Person.count_books from Person JOIN Book ON Person.id = Book.person_id where Book.id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void assignPerson(Person person, int id){
        jdbcTemplate.update("update Book set person_id=?, check_status=? where id=?", person.getId(), true, id);
        jdbcTemplate.update("update Person set count_books = count_books+1 where id=?", person.getId());
    }

    public void deletePerson(int id){
        jdbcTemplate.update("update Person set count_books = count_books-1\n" +
                "from (select Person.id as person_id from Person join Book ON Person.id = Book.person_id where Book.id=?)\n" +
                "as Subquery where Person.id = Subquery.person_id", id);
        jdbcTemplate.update("update Book set person_id=?, check_status=? where id=?", null, false, id);
    }
}