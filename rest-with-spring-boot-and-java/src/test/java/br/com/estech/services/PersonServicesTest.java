package br.com.estech.services;

import br.com.estech.data.dto.PersonDTO;
import br.com.estech.exception.RequiredObjectIsNullException;
import br.com.estech.model.Person;
import br.com.estech.repository.PersonRepository;
import com.br.estech.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

        MockPerson input;

        @InjectMocks
        private PersonServices service;

        @Mock
        PersonRepository repository;

        @BeforeEach
        void setUp() {
                input = new MockPerson();
                MockitoAnnotations.openMocks(this);
        }

        @Test
        void findById() {
                Person person = input.mockEntity(1);
                person.setId(1L);
                when(repository.findById(1L)).thenReturn(Optional.of(person));
                var fourPerson = service.findById(1L);

                assertNotNull(fourPerson);
                assertNotNull(fourPerson.getId());
                assertNotNull(fourPerson.getLinks());

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("self")
                                                && link.getHref().endsWith("/api/person/v1/1")
                                                && link.getType().equals("GET")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("findAll")
                                                && link.getHref().endsWith("/api/person/v1")
                                                && link.getType().equals("GET")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("create")
                                                && link.getHref().endsWith("/api/person/v1")
                                                && link.getType().equals("POST")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("update")
                                                && link.getHref().endsWith("/api/person/v1")
                                                && link.getType().equals("PUT")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("delete")
                                                && link.getHref().endsWith("/api/person/v1/1")
                                                && link.getType().equals("DELETE")));

                assertEquals("Address Test1", fourPerson.getAddress());
                assertEquals("FirstName Test1", fourPerson.getFirstName());
                assertEquals("LastName Test1", fourPerson.getLastName());
                assertEquals("Female", fourPerson.getGender());

        }

        @Test
        void create() {
                Person person = input.mockEntity(1);
                Person persisted = person;
                persisted.setId(1L);

                PersonDTO dto = input.mockDTO(1);

                when(repository.findById(1L)).thenReturn(Optional.of(person));
                when(repository.save(person)).thenReturn((persisted));

                var fourPerson = service.update(dto);

                assertNotNull(fourPerson);
                assertNotNull(fourPerson.getId());
                assertNotNull(fourPerson.getLinks());

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("self")
                                                && link.getHref().endsWith("/api/person/v1/1")
                                                && link.getType().equals("GET")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("findAll")
                                                && link.getHref().endsWith("/api/person/v1")
                                                && link.getType().equals("GET")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("create")
                                                && link.getHref().endsWith("/api/person/v1")
                                                && link.getType().equals("POST")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("update")
                                                && link.getHref().endsWith("/api/person/v1")
                                                && link.getType().equals("PUT")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("delete")
                                                && link.getHref().endsWith("/api/person/v1/1")
                                                && link.getType().equals("DELETE")));

                assertEquals("Address Test1", fourPerson.getAddress());
                assertEquals("FirstName Test1", fourPerson.getFirstName());
                assertEquals("LastName Test1", fourPerson.getLastName());
                assertEquals("Female", fourPerson.getGender());
        }

        @Test
        void testCreateWithNullPerson() {
                Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
                        service.create(null);
                });

                String expectedMessage = "It is not allowed to persisted a null object!";
                String actualMessage = exception.getMessage();

                assertTrue(actualMessage.contains(expectedMessage));
        }

        @Test
        void update() {
                Person person = input.mockEntity(1);
                Person persisted = person;
                persisted.setId(1L);

                PersonDTO dto = input.mockDTO(1);

                when(repository.save(person)).thenReturn((persisted));

                var fourPerson = service.create(dto);

                assertNotNull(fourPerson);
                assertNotNull(fourPerson.getId());
                assertNotNull(fourPerson.getLinks());

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("self")
                                                && link.getHref().endsWith("/api/person/v1/1")
                                                && link.getType().equals("GET")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("findAll")
                                                && link.getHref().endsWith("/api/person/v1")
                                                && link.getType().equals("GET")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("create")
                                                && link.getHref().endsWith("/api/person/v1")
                                                && link.getType().equals("POST")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("update")
                                                && link.getHref().endsWith("/api/person/v1")
                                                && link.getType().equals("PUT")));

                assertNotNull(fourPerson.getLinks().stream()
                                .anyMatch(link -> link.getRel().value().equals("delete")
                                                && link.getHref().endsWith("/api/person/v1/1")
                                                && link.getType().equals("DELETE")));

                assertEquals("Address Test1", fourPerson.getAddress());
                assertEquals("FirstName Test1", fourPerson.getFirstName());
                assertEquals("LastName Test1", fourPerson.getLastName());
                assertEquals("Female", fourPerson.getGender());
        }

        @Test
        void testUpdateWithNullPerson() {
                Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
                        service.update(null);
                });

                String expectedMessage = "It is not allowed to persisted a null object!";
                String actualMessage = exception.getMessage();

                assertTrue(actualMessage.contains(expectedMessage));
        }

        @Test
        void delete() {
                Person person = input.mockEntity(1);
                person.setId(1L);
                when(repository.findById(1L)).thenReturn(Optional.of(person));
                service.delete(1L);
                verify(repository, times(1)).findById(anyLong());
                verify(repository, times(1)).delete(any(Person.class));
                verifyNoMoreInteractions(repository);
        }

        @Test
        void findAll() {
            List<Person> list = input.mockEntityList();
            when(repository.findAll()).thenReturn(list);
            List<PersonDTO> people = service.findAll();

            assertNotNull(people);
            assertEquals(14, people.size());

            var personOne = people.get(1);

            assertNotNull(personOne);
            assertNotNull(personOne.getId());
            assertNotNull(personOne.getLinks());

            assertNotNull(personOne.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("self")
                            && link.getHref().endsWith("/api/person/v1/1")
                            && link.getType().equals("GET")));

            assertNotNull(personOne.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("findAll")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("GET")));

            assertNotNull(personOne.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("create")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("POST")));

            assertNotNull(personOne.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("update")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("PUT")));

            assertNotNull(personOne.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("delete")
                            && link.getHref().endsWith("/api/person/v1/1")
                            && link.getType().equals("DELETE")));

            assertEquals("Address Test1", personOne.getAddress());
            assertEquals("FirstName Test1", personOne.getFirstName());
            assertEquals("LastName Test1", personOne.getLastName());
            assertEquals("Female", personOne.getGender());

            var fourPerson = people.get(4);

            assertNotNull(fourPerson);
            assertNotNull(fourPerson.getId());
            assertNotNull(fourPerson.getLinks());

            assertNotNull(fourPerson.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("self")
                            && link.getHref().endsWith("/api/person/v1/4")
                            && link.getType().equals("GET")));

            assertNotNull(fourPerson.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("findAll")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("GET")));

            assertNotNull(fourPerson.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("create")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("POST")));

            assertNotNull(fourPerson.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("update")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("PUT")));

            assertNotNull(fourPerson.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("delete")
                            && link.getHref().endsWith("/api/person/v1/4")
                            && link.getType().equals("DELETE")));

            assertEquals("Address Test4", fourPerson.getAddress());
            assertEquals("FirstName Test4", fourPerson.getFirstName());
            assertEquals("LastName Test4", fourPerson.getLastName());
            assertEquals("Male", fourPerson.getGender());

            var sevenPerson = people.get(7);

            assertNotNull(sevenPerson);
            assertNotNull(sevenPerson.getId());
            assertNotNull(sevenPerson.getLinks());

            assertNotNull(sevenPerson.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("self")
                            && link.getHref().endsWith("/api/person/v1/7")
                            && link.getType().equals("GET")));

            assertNotNull(sevenPerson.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("findAll")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("GET")));

            assertNotNull(sevenPerson.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("create")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("POST")));

            assertNotNull(sevenPerson.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("update")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("PUT")));

            assertNotNull(sevenPerson.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("delete")
                            && link.getHref().endsWith("/api/person/v1/7")
                            && link.getType().equals("DELETE")));

            assertEquals("Address Test7", sevenPerson.getAddress());
            assertEquals("FirstName Test7", sevenPerson.getFirstName());
            assertEquals("LastName Test7", sevenPerson.getLastName());
            assertEquals("Female", sevenPerson.getGender());
        }
}