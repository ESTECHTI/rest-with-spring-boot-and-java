package com.br.estech.unittests.mapper;

import br.com.estech.data.dto.PersonDTO;
import br.com.estech.model.Person;
import com.br.estech.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static br.com.estech.mapper.ObjectMapper.parseListObjects;
import static br.com.estech.mapper.ObjectMapper.parseObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectMapperTests {
    MockPerson inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToDTOTest() {
        PersonDTO output = parseObject(inputObject.mockEntity(), PersonDTO.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("FirstName Test0", output.getFirstName());
        assertEquals("LastName Test0", output.getLastName());
        assertEquals("Address Test0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        List<PersonDTO> outputlist = parseListObjects(inputObject.mockEntityList(), PersonDTO.class);
        PersonDTO outputZero = outputlist.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("FirstName Test0", outputZero.getFirstName());
        assertEquals("LastName Test0", outputZero.getLastName());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());

        PersonDTO outputSeven = outputlist.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("FirstName Test7", outputSeven.getFirstName());
        assertEquals("LastName Test7", outputSeven.getLastName());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());

        PersonDTO outputTwelve = outputlist.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("FirstName Test12", outputTwelve.getFirstName());
        assertEquals("LastName Test12", outputTwelve.getLastName());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }

    @Test
    public void parseDTOToEntityTest() {
        Person output = parseObject(inputObject.mockDTO(), Person.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("FirstName Test0", output.getFirstName());
        assertEquals("LastName Test0", output.getLastName());
        assertEquals("Address Test0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseDTOListToEntityListTest() {
        List<PersonDTO> outputList = parseListObjects(inputObject.mockDTOList(), PersonDTO.class);
        PersonDTO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("FirstName Test0", outputZero.getFirstName());
        assertEquals("LastName Test0", outputZero.getLastName());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());

        PersonDTO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("FirstName Test7", outputSeven.getFirstName());
        assertEquals("LastName Test7", outputSeven.getLastName());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());

        PersonDTO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("FirstName Test12", outputTwelve.getFirstName());
        assertEquals("LastName Test12", outputTwelve.getLastName());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }

}
