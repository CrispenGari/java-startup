package com.relations.relations.one2one.book;
import com.relations.relations.one2one.picture.Picture;
import com.relations.relations.one2one.picture.PictureService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final PictureService pictureService;

    @PostMapping(path = "/create/{pictureId}")
    public  ResponseEntity<Book> createBook(@RequestBody Book book, @PathVariable("pictureId") Long pictureId){
        Picture picture = this.pictureService.getPicture(pictureId);
        if(picture.getId() == null){
            throw  new RuntimeException("the picture not found.");
        }
        System.out.println("creating a new book!!!");
        book.setPicture(picture);
        return ResponseEntity.status(201).body(this.bookService.createBook(book));
    }

    @PostMapping(path = "/create/picture")
    public  ResponseEntity<Book> createBookWithAPicture(@RequestBody BookPictureInput input){
        Picture picture = new Picture(input.getPicture().getUrl());
        Book book = input.getBook();
        book.setPicture(picture);
        return ResponseEntity.status(201).body(this.bookService.createBook(book));
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable("bookId") Long bookId, @RequestBody Book book){
        Book book1 = this.bookService.getBook(bookId);
        book1.setTitle(book.getTitle());
        return  ResponseEntity.status(204).body(this.bookService.updateBook(book1));
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable("bookId") Long bookId){
        return ResponseEntity.status(204).body(this.bookService.deleteBook(bookId));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable("bookId") Long bookId){

        System.out.println(this.bookService.getBook(bookId));
        System.out.println("book picture ---------");
        System.out.println(this.bookService.getBook(bookId).getPicture());
        return ResponseEntity.status(200).body(this.bookService.getBook(bookId));
    }

    @GetMapping("/books")
    public ResponseEntity<Collection<Book>> getBooks(){
        return ResponseEntity.status(200).body(this.bookService.allBooks());
    }
}


@Data
class BookPictureInput{
    private Book book;
    private Picture picture;
}