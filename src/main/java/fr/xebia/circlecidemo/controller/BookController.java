package fr.xebia.circlecidemo.controller;

import fr.xebia.circlecidemo.domain.Author;
import fr.xebia.circlecidemo.domain.Book;
import fr.xebia.circlecidemo.repository.AuthorRepository;
import fr.xebia.circlecidemo.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/books")
    List<Book> all() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    Book one(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @GetMapping("/authors/{authorId}/books")
    List<Book> getBooksByAuthor(@PathVariable Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    @PutMapping("/authors/{authorId}/books/{bookId}")
    Book replaceBook(@RequestBody Book newBook, @PathVariable Long authorId, @PathVariable Long bookId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        return bookRepository.findById(bookId)
            .map(book -> {
                book.setTitle(newBook.getTitle());
                book.setPublicationDate(newBook.getPublicationDate());
                book.setAuthor(author);
                return bookRepository.save(book);
            })
            .orElseGet(() -> {
                newBook.setId(bookId);
                newBook.setAuthor(author);
                return bookRepository.save(newBook);
            });
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}
