package br.com.estech.unittests.services;

import br.com.estech.data.dto.BookDTO;
import br.com.estech.exception.ResourceNotFoundException;
import br.com.estech.services.BookServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.estech.unittests.mapper.mocks.MockBook;
import br.com.estech.repository.BookRepository;
import br.com.estech.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

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

        @Mock
        PagedResourcesAssembler<BookDTO> assembler;

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
                                                && link.getHref().endsWith("/api/book/v1?page=0&size=12&direction=asc")
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
                                                && link.getHref().endsWith("/api/book/v1?page=0&size=12&direction=asc")
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
                                                && link.getHref().endsWith("/api/book/v1?page=0&size=12&direction=asc")
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

                Pageable pageable = PageRequest.of(0, 10);
                Page<Book> page = new PageImpl<>(list, pageable, list.size());

                when(repository.findAll(pageable)).thenReturn(page);

                // Monta um PagedModel simulado retornado pelo assembler
                List<EntityModel<BookDTO>> dtoModels = list.stream().map(entity -> {
                        BookDTO dto = new BookDTO();
                        dto.setId(entity.getId());
                        dto.setAuthor(entity.getAuthor());
                        dto.setLaunchDate(entity.getLaunchDate());
                        dto.setPrice(entity.getPrice());
                        dto.setTitle(entity.getTitle());
                        return EntityModel.of(dto);
                }).toList();

                PagedModel<EntityModel<BookDTO>> pagedModel = PagedModel.of(dtoModels,
                                new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements()));

                when(assembler.toModel(any(Page.class), any(Link.class))).thenReturn(pagedModel);

                var result = service.findAll(pageable);

                assertNotNull(result);
                assertEquals(list.size(), result.getContent().size());

                EntityModel<BookDTO> firstModel = result.getContent().stream().findFirst().orElseThrow();
                BookDTO firstDto = firstModel.getContent();

                assertNotNull(firstDto);
                assertEquals("Author Test0", firstDto.getAuthor());

                verify(repository, times(1)).findAll(pageable);
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
