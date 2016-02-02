package de.rardian.telegram.bot.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import de.rardian.telegram.bot.castle.CastleBot;
import de.rardian.telegram.bot.model.User;
import de.rardian.telegram.bot.model.UserRepository;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EntityScan(basePackageClasses=User.class)
public class BotManager {

	public static void main(String[] args) {
		SpringApplication.run(BotManager.class);
//		new BotManager().start();
	}

	@Bean
	public UpdatesRetriever startBot(UserManager userManager) {
		final CastleBot bot = new CastleBot();
		bot.setUserManager(userManager);
		
		final UpdatesRetriever retriever = new UpdatesRetriever().forBot(bot);
		retriever.startGettingUpdates();
		
		return retriever;
	}
	
	@Bean 
	public UserManager createUserManager(UserRepository userRepository) {
		UserManager userManager = new UserManager();
		userManager.setUserRepository(userRepository);
		return userManager;
	}
}
