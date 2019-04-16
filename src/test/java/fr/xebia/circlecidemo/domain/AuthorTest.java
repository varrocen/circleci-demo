package fr.xebia.circlecidemo.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorTest {

    @Test
    public void should_get_name_with_first_name_and_last_name() {
        // given
        Author author = new Author("Terry", "Pratchett");

        // when
        String name = author.getName();

        // then
        assertThat(name).isEqualTo("Terry Pratchett");
    }
}
