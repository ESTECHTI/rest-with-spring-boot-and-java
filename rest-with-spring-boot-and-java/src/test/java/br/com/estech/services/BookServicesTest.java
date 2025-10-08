package br.com.estech.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import br.com.estech.unitetests.mapper.mocks.MockBook;
import br.com.estech.repository.BookRepository;
import br.com.estech.model.Book;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

  MockBook input;

  @InjectMocks
  private BookServices service;

  @Mock
  BookRepository repository;

  @BeforeEach
  void setup() {
    input = new MockBook();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findById() {
    Book book = input.mockBookEntity(1);
    book.setId(1L);
    when(repository.findById(1L)).thenReturn(Optional.of(book));
    var fourBook = service.findById(1L);

    assertNotNull(fourBook);
    assertNotNull(fourBook.getId());
    assertNotNull(fourBook.getLinks());

    assertTrue(fourBook.getLinks().stream()
        .anyMatch(link -> link.getRel().value().equals("self")
            && link.getHref().endsWith("/api/book/v1/1")
            && link.getType().equals("GET")));

    assertTrue(fourBook.getLinks().stream()
        .anyMatch(link -> link.getRel().value().equals("findAll")
            && link.getHref().endsWith("/api/book/v1")
            && link.getType().equals("GET")));
  }
}
