package com.progra.pc2.repositories;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.progra.pc2.entities.Person;

public interface TributoRepository extends Repository<Person,Integer>{
	
	void save(Person model);
	
	List<Person> findAll();
}
