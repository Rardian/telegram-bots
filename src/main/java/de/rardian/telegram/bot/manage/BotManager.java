package de.rardian.telegram.bot.manage;

import java.beans.PropertyVetoException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import de.rardian.telegram.bot.castle.CastleBot;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.model.InhabitantRepository;
import de.rardian.telegram.bot.model.User;
import de.rardian.telegram.bot.model.UserRepository;

//@SpringBootApplication
//@EnableAutoConfiguration
//@EnableJpaRepositories(basePackageClasses = { UserRepository.class, InhabitantRepository.class })
//@EntityScan(basePackageClasses = { User.class, Inhabitant.class })
public class BotManager {

	/*
	public static void main(String[] args) {
		SpringApplication.run(BotManager.class);
		//		new BotManager().start();
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
	*/
}
