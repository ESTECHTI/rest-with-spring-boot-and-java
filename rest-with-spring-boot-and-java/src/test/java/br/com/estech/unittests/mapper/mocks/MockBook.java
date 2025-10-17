package br.com.estech.unittests.mapper.mocks;

import br.com.estech.data.dto.BookDTO;
import br.com.estech.model.Book;

import java.util.ArrayList;
import java.util.List;

public class MockBook {

    public Book mockEntity() {
        return mockBookEntity(0);
    }

    public BookDTO mockDTO() {
        return mockBookDTO(0);
    }

    public List<Book> mockBookEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockBookEntity(i));
        }
        return books;
    }


    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockBookEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockBookDTO(i));
        }
        return books;
    }

    public Book mockBookEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setAuthor("Author Test" + number);
        book.setTitle("Title Test" + number);
        book.setLaunchDate(java.time.LocalDateTime.now());
        book.setPrice(java.math.BigDecimal.valueOf(25.0 + number));
        return book;
    }

    public BookDTO mockBookDTO(Integer number) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(number.longValue());
        bookDTO.setAuthor("Author Test" + number);
        bookDTO.setTitle("Title Test" + number);
        bookDTO.setLaunchDate(java.time.LocalDateTime.now());
        bookDTO.setPrice(java.math.BigDecimal.valueOf(25.0 + number));
        return bookDTO;
    }
}