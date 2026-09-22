package br.com.estechti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.estechti.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {}
