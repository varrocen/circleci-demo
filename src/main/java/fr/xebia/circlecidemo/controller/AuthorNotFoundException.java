package fr.xebia.circlecidemo.controller;

class AuthorNotFoundException extends RuntimeException {

    AuthorNotFoundException(Long id) {
        super("Could not find author " + id);
    }
}
