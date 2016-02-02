package de.rardian.telegram.bot.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	public List<User> findByLastName(String lastName);
}