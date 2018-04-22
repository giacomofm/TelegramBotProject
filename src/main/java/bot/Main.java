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

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi botsApi = new TelegramBotsApi();
		try {
			botsApi.registerBot(new Yoer());
			System.out.println("Yoer inizializzato");
			botsApi.registerBot(new JekOrologio());
			System.out.println("JekOrologio inizializzato");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		System.out.println("Bot partiti");
	}

}
