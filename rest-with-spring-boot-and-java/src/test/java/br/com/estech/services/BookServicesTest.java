package br.com.estech.services;

import br.com.estech.data.dto.BookDTO;
import br.com.estech.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.estech.unitetests.mapper.mocks.MockBook;
import br.com.estech.repository.BookRepository;
import br.com.estech.model.Book;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

        assertTrue(fourBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")));

        assertTrue(fourBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")));

        assertTrue(fourBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        Long noExistentId = 999L;
        when(repository.findById(noExistentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(noExistentId));
    }

    @Test
    void create() {

        LocalDateTime fixedDate = LocalDateTime.of(2024, 10, 15, 10, 30, 0);

        Book book = input.mockBookEntity(1);
        book.setId(1L);
        book.setLaunchDate(fixedDate);

        BookDTO dto = input.mockBookDTO(1);
        dto.setLaunchDate(fixedDate);

        when(repository.save(any(Book.class))).thenReturn(book);

        var oneBook = service.create(dto);

        assertNotNull(oneBook);
        assertNotNull(oneBook.getId());
        assertNotNull(oneBook.getLinks());

        assertTrue(oneBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(oneBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")));

        assertTrue(oneBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")));

        assertTrue(oneBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")));

        assertTrue(oneBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Author Test1", oneBook.getAuthor());
        assertEquals("Title Test1", oneBook.getTitle());
        assertEquals(fixedDate, oneBook.getLaunchDate());
        assertEquals(26D, oneBook.getPrice().doubleValue(), 0.001);

        verify(repository, times(1)).save(any(Book.class));
    }

    @Test
    void update() {
        LocalDateTime fixedDate = LocalDateTime.of(2024, 10, 15, 10, 30, 0);

        Book book = input.mockBookEntity(1);
        book.setId(1L);
        book.setLaunchDate(fixedDate);

        BookDTO dto = input.mockBookDTO(1);
        dto.setLaunchDate(fixedDate);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn((book));

        var oneBook = service.update(dto);

        assertNotNull(oneBook);
        assertNotNull(oneBook.getId());
        assertNotNull(oneBook.getLinks());

        assertTrue(oneBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")));

        assertTrue(oneBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")));

        assertTrue(oneBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")));

        assertTrue(oneBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("PUT")));

        assertTrue(oneBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")));

        assertEquals("Author Test1", oneBook.getAuthor());
        assertEquals("Title Test1", oneBook.getTitle());
        assertEquals(fixedDate, oneBook.getLaunchDate());
        assertEquals(26D, oneBook.getPrice().doubleValue(), 0.001);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Book.class));
    }

    @Test
    void findAll() {
        LocalDateTime fixedDate = LocalDateTime.of(2024, 10, 15, 10, 30, 0);
        List<Book> list = input.mockBookEntityList();
        for (Book book : list) {
            book.setLaunchDate(fixedDate);
        }

        when(repository.findAll()).thenReturn(list);

        var book = service.findAll();

        assertNotNull(book);
        assertEquals(14, book.size());

        var oneBook = book.get(1);

        assertNotNull(oneBook);
        assertNotNull(oneBook.getId());
        assertNotNull(oneBook.getLinks());

        assertTrue(oneBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")));

        assertEquals("Author Test1", oneBook.getAuthor());
        assertEquals("Title Test1", oneBook.getTitle());
        assertEquals(fixedDate, oneBook.getLaunchDate());
        assertEquals(26D, oneBook.getPrice().doubleValue(), 0.001);

        var fourBook = book.get(4);

        assertNotNull(fourBook);
        assertNotNull(fourBook.getId());
        assertNotNull(fourBook.getLinks());

        assertTrue(fourBook.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/4")
                        && link.getType().equals("GET")));

        assertEquals("Author Test4", fourBook.getAuthor());
        assertEquals("Title Test4", fourBook.getTitle());
        assertEquals(fixedDate, fourBook.getLaunchDate());
        assertEquals(29D, fourBook.getPrice().doubleValue(), 0.001);

        verify(repository, times(1)).findAll();
    }

    @Test
    void delete() {
        Book book = input.mockBookEntity(1);
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(repository);

    }

}
