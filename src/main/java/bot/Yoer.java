package bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Yoer extends TelegramLongPollingBot {

	public static final String YO = "yo";

	@Override
	public String getBotUsername() {
		return "YoerBot";
	}

	@Override
	public String getBotToken() {
		return "568627206:AAGOwDRsevB7L2nvVCIP19u84HF9b-Dxa-g";
	}

	@Override
	public void onUpdateReceived(final Update update) {
		if (update.hasMessage()) {
			long chat_id = update.getMessage().getChatId();
			SendMessage message = new SendMessage().setChatId(chat_id).setText(YO);
			try {
				execute(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

}
