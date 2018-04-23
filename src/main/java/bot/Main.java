package bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

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

	// private static final String externalUrl = "https://gfm-telegram-bot.herokuapp.com:80";
	// private static final String internalUrl = "https://127.0.0.1:" + (System.getenv("PORT") != null ? System.getenv("PORT") : "5000");

	public static void main(final String[] args) {
		ApiContextInitializer.init();
		try {
			final TelegramBotsApi botsApi = new TelegramBotsApi();// new TelegramBotsApi(externalUrl, internalUrl);
			botsApi.registerBot(new Yoer());
			System.out.println("Yoer inizializzato");
			botsApi.registerBot(new JekOrologio());
			System.out.println("JekOrologio inizializzato");
		} catch (final TelegramApiException e) {
			e.printStackTrace();
		}
		System.out.println("Bot partiti");
	}

}
