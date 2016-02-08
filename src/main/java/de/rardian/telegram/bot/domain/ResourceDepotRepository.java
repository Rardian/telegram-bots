package de.rardian.telegram.bot.domain;

import org.springframework.stereotype.Repository;

import de.rardian.telegram.bot.castle.model.ResourceDepot;
import de.rardian.telegram.bot.castle.model.ResourcesManager.TYPE;

@Repository
public interface ResourceDepotRepository extends BaseRepository<ResourceDepot, TYPE> {
}