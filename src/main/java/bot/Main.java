package bot;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import bot.services.JekOrologio;
import bot.services.Pausatron;
import bot.services.Yoer;

/**
 * <ol>
 * <li>inizializzo il contesto delle api</li>
 * <li>istanzio le Telegram Bots Api</li>
 * <li>registro i bot</li>
 * </ol>
 * 
 * @author GiacomoFM
 */
public class Main {

	public static void main(final String[] args) {
		ApiContextInitializer.init();
		try {
			final TelegramBotsApi botsApi = new TelegramBotsApi();
			botsApi.registerBot(new Yoer());
			System.out.println("Yoer inizializzato");
			botsApi.registerBot(new JekOrologio());
			System.out.println("JekOrologio inizializzato");
			botsApi.registerBot(new Pausatron());
			System.out.println("Pausatron inizializzato");
			startQuartzJob();
		} catch (final TelegramApiException | SchedulerException e) {
			e.printStackTrace();
		}
		System.out.println("Bot partiti");
	}

	private static void startQuartzJob() throws SchedulerException {
		final SchedulerFactory schedFact = new StdSchedulerFactory();
		final Scheduler sched = schedFact.getScheduler();
		sched.start();

		Pausatron.schedule(sched);

		System.out.println("Quartz partito");
	}

}
