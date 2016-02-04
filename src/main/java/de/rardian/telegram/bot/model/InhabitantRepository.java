package de.rardian.telegram.bot.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.rardian.telegram.bot.castle.model.Inhabitant;

@Repository
public interface InhabitantRepository extends CrudRepository<Inhabitant, Long> {

	public List<Inhabitant> findByName(String name);

	public Inhabitant findByUser(User user);
}