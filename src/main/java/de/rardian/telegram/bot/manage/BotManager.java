package de.rardian.telegram.bot.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import de.rardian.telegram.bot.castle.CastleBot;
import de.rardian.telegram.bot.castle.model.Castle;
import de.rardian.telegram.bot.castle.model.Inhabitant;
import de.rardian.telegram.bot.castle.model.ResourceDepot;
import de.rardian.telegram.bot.domain.ResourceDepotRepository;
import de.rardian.telegram.bot.model.InhabitantRepository;
import de.rardian.telegram.bot.model.User;
import de.rardian.telegram.bot.model.UserRepository;

//@SpringBootApplication
@Configuration 
@EnableAutoConfiguration 
@EntityScan(basePackageClasses = { User.class, Inhabitant.class, ResourceDepot.class })
@ComponentScan(basePackages = "de.rardian.telegram.bot")
@EnableJpaRepositories(basePackageClasses = { UserRepository.class, InhabitantRepository.class,
		ResourceDepotRepository.class })
// @RunWith(SpringJUnit4ClassRunner.class)
public class BotManager {

	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	public static void main(String[] args) {
		SpringApplication.run(BotManager.class);
	}

	// @Bean
	// public Object myBean(Collection<Command> commands) {
	//
	// }

	@Bean
	public UpdatesRetriever startBot(UserManager userManager) {
		final CastleBot bot = new CastleBot();
		beanFactory.autowireBean(bot);
		final Castle castle = new Castle(bot, beanFactory);

		castle.setUserManager(userManager);

		bot.setCastle(castle);
		bot.setUserManager(userManager);

		final UpdatesRetriever retriever = new UpdatesRetriever().forBot(bot);
		retriever.startGettingUpdates();

		return retriever;
	}

	// @Bean
	// public UserManager createUserManager(UserRepository userRepository,
	// InhabitantRepository inhabitantRepository) {
	// UserManager userManager = new UserManager();
	// userManager.setUserRepository(userRepository);
	// userManager.setInhabitantRepository(inhabitantRepository);
	// return userManager;
	// }
	
}
