package br.com.estech.integrationtests.controllers.withxml;

import br.com.estech.config.TestConfigs;
import br.com.estech.integrationtests.dto.PersonDTO;
import br.com.estech.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerXmlTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static XmlMapper xmlMapper;

  private static PersonDTO person;

  @BeforeAll
  static void setUp() {
    xmlMapper = new XmlMapper();
    xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
        .setBasePath("/api/person/v1")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    person = new PersonDTO();
  }

  @Test
  @Order(1)
  void createTest() throws JsonProcessingException {
    mockPerson();

    var content = given(specification)
        .contentType(MediaType.APPLICATION_XML_VALUE)
        .accept(MediaType.APPLICATION_XML_VALUE)
        .body(xmlMapper.writeValueAsString(person))
        .when()
        .post()
        .then()
        .statusCode(200)
        .contentType(MediaType.APPLICATION_XML_VALUE)
        .extract()
        .body()
        .asString();

    PersonDTO createdPerson = xmlMapper.readValue(content, PersonDTO.class);
    person = createdPerson;

    assertNotNull(createdPerson.getId());
    assertTrue(createdPerson.getId() > 0);
    assertEquals("Linus", createdPerson.getFirstName());
    assertEquals("Torvalds", createdPerson.getLastName());
    assertEquals("Helsinki - Finland", createdPerson.getAddress());
    assertEquals("Male", createdPerson.getGender());
    assertTrue(createdPerson.getEnabled());
  }

  @Test
  @Order(2)
  void updateTest() throws JsonProcessingException {
    person.setLastName("Benedict Torvalds");

    var content = given(specification)
        .contentType(MediaType.APPLICATION_XML_VALUE)
        .accept(MediaType.APPLICATION_XML_VALUE)
        .body(xmlMapper.writeValueAsString(person))
        .when()
        .put()
        .then()
        .statusCode(200)
        .contentType(MediaType.APPLICATION_XML_VALUE)
        .extract()
        .body()
        .asString();

    PersonDTO updatedPerson = xmlMapper.readValue(content, PersonDTO.class);
    assertNotNull(updatedPerson.getId());
    assertTrue(updatedPerson.getId() > 0);
    assertEquals("Linus", updatedPerson.getFirstName());
    assertEquals("Benedict Torvalds", updatedPerson.getLastName());
    assertEquals("Helsinki - Finland", updatedPerson.getAddress());
    assertEquals("Male", updatedPerson.getGender());
    assertTrue(updatedPerson.getEnabled());
    person = updatedPerson;
  }

  @Test
  @Order(3)
  void findByIdTest() throws JsonProcessingException {
    var content = given(specification)
        .contentType(MediaType.APPLICATION_XML_VALUE)
        .accept(MediaType.APPLICATION_XML_VALUE)
        .pathParam("id", person.getId())
        .when()
        .get("{id}")
        .then()
        .statusCode(200)
        .contentType(MediaType.APPLICATION_XML_VALUE)
        .extract()
        .body()
        .asString();

    PersonDTO foundPerson = xmlMapper.readValue(content, PersonDTO.class);
    assertNotNull(foundPerson.getId());
    assertEquals(person.getId(), foundPerson.getId());
    assertEquals("Linus", foundPerson.getFirstName());
    assertEquals("Benedict Torvalds", foundPerson.getLastName());
    assertEquals("Helsinki - Finland", foundPerson.getAddress());
    assertEquals("Male", foundPerson.getGender());
    assertTrue(foundPerson.getEnabled());
    person = foundPerson;
  }

  @Test
  @Order(4)
  void disableTest() throws JsonProcessingException {
    var content = given(specification)
        .contentType(MediaType.APPLICATION_XML_VALUE)
        .accept(MediaType.APPLICATION_XML_VALUE)
        .pathParam("id", person.getId())
        .when()
        .patch("{id}")
        .then()
        .statusCode(200)
        .contentType(MediaType.APPLICATION_XML_VALUE)
        .extract()
        .body()
        .asString();

    PersonDTO disabledPerson = xmlMapper.readValue(content, PersonDTO.class);
    assertNotNull(disabledPerson.getId());
    assertTrue(disabledPerson.getId() > 0);
    assertEquals("Linus", disabledPerson.getFirstName());
    assertEquals("Benedict Torvalds", disabledPerson.getLastName());
    assertEquals("Helsinki - Finland", disabledPerson.getAddress());
    assertEquals("Male", disabledPerson.getGender());
    assertFalse(disabledPerson.getEnabled());
    person = disabledPerson;
  }

  @Test
  @Order(5)
  void deleteTest() {
    given(specification)
        .pathParam("id", person.getId())
        .when()
        .delete("{id}")
        .then()
        .statusCode(204);
    given(specification)
        .contentType(MediaType.APPLICATION_XML_VALUE)
        .accept(MediaType.APPLICATION_XML_VALUE)
        .pathParam("id", person.getId())
        .when()
        .get("{id}")
        .then()
        .statusCode(404);
  }

  @Test
  @Order(6)
  void findAllTest() throws JsonProcessingException {
    var content = given(specification)
        .contentType(MediaType.APPLICATION_XML_VALUE)
        .accept(MediaType.APPLICATION_XML_VALUE)
        .when()
        .get()
        .then()
        .statusCode(200)
        .contentType(MediaType.APPLICATION_XML_VALUE)
        .extract()
        .body()
        .asString();

    // A resposta agora é paginada (PagedModel<EntityModel<PersonDTO>>),
    // então validamos o conteúdo pelo XML em vez de desserializar como lista.
    assertTrue(content.contains("<PagedModel>"));
    assertTrue(content.contains("<content>"));
    assertTrue(content.contains("<firstName>"));
    assertTrue(content.contains("<rel>findAll</rel>"));
    assertTrue(content.contains("<rel>self</rel>"));
  }

  private void mockPerson() {
    person.setFirstName("Linus");
    person.setLastName("Torvalds");
    person.setAddress("Helsinki - Finland");
    person.setGender("Male");
    person.setEnabled(true);
  }
}
