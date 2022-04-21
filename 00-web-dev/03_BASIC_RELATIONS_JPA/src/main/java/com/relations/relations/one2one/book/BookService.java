package com.relations.relations.one2one.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
public class BookService implements BookServiceInterface{
    private final BookRepository repository;
    @Override
    public Book createBook(Book book) {
        return this.repository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return this.repository.save(book);
    }

    @Override
    public Boolean deleteBook(Long id) {
        this.repository.deleteById(id);
        return  true;
    }

    @Override
    public Book getBook(Long id) {
        return this.repository.findById(id).orElseThrow(()-> new RuntimeException("error"));
    }

    @Override
    public Collection<Book> allBooks() {
        return this.repository.findAll();
    }
}
