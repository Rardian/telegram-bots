package de.rardian.telegram.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.rardian.telegram.bot.castle.CastleBot;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.manage.UpdatesRetriever;
import de.rardian.telegram.bot.manage.UserManager;
import de.rardian.telegram.bot.model.InhabitantRepository;
import de.rardian.telegram.bot.model.UserRepository;

@SpringBootApplication

public class ApplicationConfiguration {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfiguration.class, args);
	}

	@Bean
	public UpdatesRetriever startBot(UserManager userManager) {
		final CastleBot bot = new CastleBot();
		final Castle castle = new Castle(bot);

		castle.setUserManager(userManager);

		bot.setCastle(castle);
		bot.setUserManager(userManager);

		final UpdatesRetriever retriever = new UpdatesRetriever().forBot(bot);
		retriever.startGettingUpdates();

		return retriever;
	}
	

	@Bean
	public UserManager createUserManager(UserRepository userRepository, InhabitantRepository inhabitantRepository) {
		UserManager userManager = new UserManager();
		userManager.setUserRepository(userRepository);
		userManager.setInhabitantRepository(inhabitantRepository);
		return userManager;
	}
}
