package com.ereed.libraryproject.dao;

import com.ereed.libraryproject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("select * from Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("select * from Person where id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("insert into Person(name, year_of_birth) values (?, ?)",
                person.getName(), person.getYearOfBirth());
    }

    public void update(int id, Person person){
        jdbcTemplate.update("update Person set name=?, year_of_birth=? where id=?",
                person.getName(), person.getYearOfBirth(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("delete from Person where id=?", id);
    }

    public Person merge(int id){
        return jdbcTemplate.query("select Person.id, Person.name from Person JOIN Book ON Person.id = Book.person_id where Book.id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

}