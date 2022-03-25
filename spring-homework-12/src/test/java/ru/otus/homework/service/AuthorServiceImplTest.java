package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application.yaml")
@DisplayName("Сервис AuthorService должен ")
@SpringBootTest
public class AuthorServiceImplTest {

   /* @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorServiceImpl authorService;


    @DisplayName("получать автора книги по его id")
    @Test
    public void shouldReturnAuthorById(){
        given(authorRepository.findById(1L)).willReturn(Optional.of(new Author(1,"test")));
        Author actualAuthor = authorService.getAuthorById(1);
        assertThat(actualAuthor).isNotNull();
    }

    @DisplayName("получать всех авторов")
    @Test
    public void shouldReturnAllAuthors(){
        List<Author> expectedAuthorList = Arrays.asList(
                new Author(1,"testAuthor1"),
                new Author(2,"testAuthor2"));
        given(authorRepository.findAll()).willReturn(expectedAuthorList);
        List<Author> actualAuthorList = authorService.getAllAuthors();
        assertThat(actualAuthorList.equals(expectedAuthorList));
    }

    @DisplayName("получать ошибку при запросе на несуществующего автора")
    @Test
    public void shouldThrowAuthorException(){
        Throwable exception = assertThrows(AuthorException.class,()->{
            given(authorRepository.findById(2L)).willReturn(Optional.empty());
            authorService.getAuthorById(2);
        });
        assertEquals("Author with id [2] not found",exception.getMessage());
    }*/
}
