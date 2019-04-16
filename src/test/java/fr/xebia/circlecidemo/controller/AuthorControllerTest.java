package fr.xebia.circlecidemo.controller;

import fr.xebia.circlecidemo.repository.AuthorRepository;
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
public class AuthorControllerTest {

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    public void should_fail_to_get_author_if_is_not_exist() {
        // given
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> authorController.one(id));

        // then
        assertThat(thrown).isInstanceOf(AuthorNotFoundException.class)
                .hasMessageContaining("Could not find author " + id);
    }
}
