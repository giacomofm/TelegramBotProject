package bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import bot.services.DetailsDetector;
import bot.services.JekOrologio;
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
			botsApi.registerBot(new DetailsDetector());
			System.out.println("DetailsDetector inizializzato");
		} catch (final TelegramApiException e) {
			e.printStackTrace();
		}
		System.out.println("Bot partiti");
	}

}
