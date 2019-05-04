package fr.xebia.circlecidemo.controller;

import fr.xebia.circlecidemo.domain.Book;
import fr.xebia.circlecidemo.repository.AuthorRepository;
import fr.xebia.circlecidemo.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    public void should_fail_to_get_book_if_is_not_exist() {
        // given
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> bookController.one(id));

        // then
        assertThat(thrown).isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Could not find book " + id);
    }

    @Test
    public void should_fail_to_create_or_update_book_if_author_is_not_exist() {
        // given
        Book newBook = new Book();
        Long authorId = 1L;
        Long bookId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> bookController.replaceBook(newBook, authorId, bookId));

        // then
        assertThat(thrown).isInstanceOf(AuthorNotFoundException.class)
                .hasMessageContaining("Could not find author " + authorId);
    }
}
