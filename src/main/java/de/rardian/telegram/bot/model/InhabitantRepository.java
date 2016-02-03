package de.rardian.telegram.bot.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import de.rardian.telegram.bot.castle.model.Inhabitant;

@Transactional
public interface InhabitantRepository extends CrudRepository<Inhabitant, Long> {

	public List<Inhabitant> findByName(String name);

	public Inhabitant findByUser(User user);
}