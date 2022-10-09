package com.example.library.repository;

import com.example.library.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

//    @Query("SELECT a.surname FROM Author a")
//    @Query("SELECT a.surname " +
//            "FROM Author a " +
//            "JOIN Book b ON b.id = 2 " +
//            "GROUP BY a.surname ")
//    String authorSurname();


//    @Query("SELECT a.surname " +
//            "FROM Author a " +
//            "JOIN Book b ON b.id = 1 " +
//            "GROUP BY a.surname ")
}