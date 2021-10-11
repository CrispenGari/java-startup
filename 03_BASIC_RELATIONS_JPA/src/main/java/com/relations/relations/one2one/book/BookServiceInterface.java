package com.relations.relations.one2one.book;

import java.util.Collection;

public interface BookServiceInterface {
    Book createBook(Book book);
    Book updateBook(Book book);
    Boolean deleteBook(Long id);
    Book getBook(Long id);
    Collection<Book> allBooks();
}
