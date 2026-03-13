package br.com.estech.integrationtests.controllers.withyaml;

import br.com.estech.config.TestConfigs;
import br.com.estech.integrationtests.dto.BookDTO;
import br.com.estech.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerYamlTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static YAMLMapper yamlMapper;

  private static BookDTO book;

  @BeforeAll
  static void setUp() {
    yamlMapper = new YAMLMapper();
    yamlMapper.findAndRegisterModules();
    yamlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
        .setConfig(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig()
            .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
        .setBasePath("/api/book/v1")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    book = new BookDTO();
  }

  @Test
  @Order(1)
  void createTest() throws JsonProcessingException {
    mockBook();

    var content = given(specification)
        .contentType(MediaType.APPLICATION_YAML_VALUE)
        .accept(MediaType.APPLICATION_YAML_VALUE)
        .body(yamlMapper.writeValueAsString(book))
        .when()
        .post()
        .then()
        .statusCode(200)
        .contentType(MediaType.APPLICATION_YAML_VALUE)
        .extract()
        .body()
        .asString();

    BookDTO createdBook = yamlMapper.readValue(content, BookDTO.class);
    book = createdBook;

    assertNotNull(createdBook.getId());
    assertTrue(createdBook.getId() > 0);
    assertEquals("Robert C. Martin", createdBook.getAuthor());
    assertEquals("Clean Code", createdBook.getTitle());
    assertEquals(new BigDecimal("77.00"), createdBook.getPrice());
    assertNotNull(createdBook.getLaunchDate());
  }

  @Test
  @Order(2)
  void updateTest() throws JsonProcessingException {
    book.setTitle("Clean Code - A Handbook of Agile Software Craftsmanship");
    book.setPrice(new BigDecimal("89.00"));

    var content = given(specification)
        .contentType(MediaType.APPLICATION_YAML_VALUE)
        .accept(MediaType.APPLICATION_YAML_VALUE)
        .body(yamlMapper.writeValueAsString(book))
        .when()
        .put()
        .then()
        .statusCode(200)
        .contentType(MediaType.APPLICATION_YAML_VALUE)
        .extract()
        .body()
        .asString();

    BookDTO updatedBook = yamlMapper.readValue(content, BookDTO.class);
    book = updatedBook;

    assertNotNull(updatedBook.getId());
    assertTrue(updatedBook.getId() > 0);
    assertEquals("Robert C. Martin", updatedBook.getAuthor());
    assertEquals("Clean Code - A Handbook of Agile Software Craftsmanship", updatedBook.getTitle());
    assertEquals(new BigDecimal("89.00"), updatedBook.getPrice());
  }

  @Test
  @Order(3)
  void findByIdTest() throws JsonProcessingException {

    var content = given(specification)
        .contentType(MediaType.APPLICATION_YAML_VALUE)
        .accept(MediaType.APPLICATION_YAML_VALUE)
        .pathParam("id", book.getId())
        .when()
        .get("{id}")
        .then()
        .statusCode(200)
        .contentType(MediaType.APPLICATION_YAML_VALUE)
        .extract()
        .body()
        .asString();

    BookDTO foundBook = yamlMapper.readValue(content, BookDTO.class);

    assertNotNull(foundBook.getId());
    assertEquals(book.getId(), foundBook.getId());
    assertEquals("Robert C. Martin", foundBook.getAuthor());
    assertEquals("Clean Code - A Handbook of Agile Software Craftsmanship", foundBook.getTitle());
    assertEquals(new BigDecimal("89.00"), foundBook.getPrice());
  }

  @Test
  @Order(4)
  void deleteTest() {

    given(specification)
        .pathParam("id", book.getId())
        .when()
        .delete("{id}")
        .then()
        .statusCode(204);
  }

  @Test
  @Order(5)
  void findAllTest() throws JsonProcessingException {

    var content = given(specification)
        .accept(MediaType.APPLICATION_YAML_VALUE)
        .when()
        .get()
        .then()
        .statusCode(200)
        .contentType(MediaType.APPLICATION_YAML_VALUE)
        .extract()
        .body()
        .asString();

    List<BookDTO> books = yamlMapper.readValue(content, new TypeReference<List<BookDTO>>() {
    });

    assertFalse(books.isEmpty());

    BookDTO firstBook = books.get(0);
    assertNotNull(firstBook.getId());
    assertNotNull(firstBook.getAuthor());
    assertNotNull(firstBook.getTitle());
    assertNotNull(firstBook.getPrice());
    assertNotNull(firstBook.getLaunchDate());
  }

  private void mockBook() {
    book.setAuthor("Robert C. Martin");
    book.setTitle("Clean Code");
    book.setPrice(new BigDecimal("77.00"));
    book.setLaunchDate(LocalDateTime.now());
  }
}