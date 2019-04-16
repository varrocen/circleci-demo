package fr.xebia.circlecidemo.controller;

import fr.xebia.circlecidemo.domain.Author;
import fr.xebia.circlecidemo.repository.AuthorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;

    AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/authors")
    List<Author> all() {
        return authorRepository.findAll();
    }

    @PostMapping("/authors")
    Author newAuthor(@RequestBody Author newAuthor) {
        return authorRepository.save(newAuthor);
    }

    @GetMapping("/authors/{id}")
    Author one(@PathVariable Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @PutMapping("/authors/{id}")
    Author replaceAuthor(@RequestBody Author newAuthor, @PathVariable Long id) {
        return authorRepository.findById(id)
            .map(author -> {
                author.setFirstName(newAuthor.getFirstName());
                author.setLastName(newAuthor.getLastName());
                return authorRepository.save(author);
            })
            .orElseGet(() -> {
                newAuthor.setId(id);
                return authorRepository.save(newAuthor);
            });
    }

    @DeleteMapping("/authors/{id}")
    void deleteAuthor(@PathVariable Long id) {
        authorRepository.deleteById(id);
    }
}
