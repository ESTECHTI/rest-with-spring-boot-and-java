package br.com.estech.data.dto;

import br.com.estech.model.Person;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Objects;

@Relation(collectionRelation = "people")
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    private Boolean enabled;

    public PersonDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass())
            return false;
        if (!super.equals(object))
            return false;
        PersonDTO personDTO = (PersonDTO) object;
        return java.util.Objects.equals(getId(), personDTO.getId())
                && java.util.Objects.equals(getFirstName(), personDTO.getFirstName())
                && java.util.Objects.equals(getLastName(), personDTO.getLastName())
                && java.util.Objects.equals(getAddress(), personDTO.getAddress())
                && java.util.Objects.equals(getGender(), personDTO.getGender())
                && java.util.Objects.equals(getEnabled(), personDTO.getEnabled());
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getFirstName(), getLastName(), getAddress(), getGender(),
                getEnabled());
    }
}
