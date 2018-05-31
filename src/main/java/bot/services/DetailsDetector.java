package bot.services;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class DetailsDetector extends TelegramLongPollingBot {

	@Override
	public String getBotUsername() {
		return "DetailsDetectorBot";
	}

	@Override
	public String getBotToken() {
		return "611087370:AAFzNWbftyE0vo6jmmBcN6C2wcj_z6xyt08";
	}

	@Override
	public void onUpdateReceived(final Update update) {
		long chatId = 0;
		if (update.hasMessage())
			chatId = update.getMessage().getChatId();
		try {
			String details = detect(update.getMessage());
			execute(new SendMessage().setChatId(chatId).setText(details));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private static String detect(final Message message) {
		return "Hai scritto: " + message.getText();
	}

}
