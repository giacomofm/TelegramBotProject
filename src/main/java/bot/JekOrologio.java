package bot;

import java.time.LocalTime;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import bot.util.BotUtil;

public class JekOrologio extends TelegramLongPollingBot {

	public static final String ASKTIMECOMMAND = "/porcodio";

	private static final LocalTime end_time = LocalTime.of(18, 0);

	@Override
	public String getBotUsername() {
		return "JekOrologioBot";
	}

	@Override
	public String getBotToken() {
		return "555689661:AAHs7lWaHnQefPekuXCUcQKTPa9KSxNwKME";
	}

	@Override
	public void onUpdateReceived(final Update update) {
		if (BotUtil.checkMessage(update.getMessage(), ASKTIMECOMMAND)) {
			long chat_id = update.getMessage().getChatId();
			SendMessage message = new SendMessage().setChatId(chat_id).setText(getTime());
			try {
				execute(message.enableMarkdown(true));
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getTime() {
		StringBuilder sb = new StringBuilder("*");
		sb.append(hours()).append(" E ").append(minutes()).append("*");
		return sb.toString();
	}

	private static String hours() {
		int hours = 0;
		if (BotUtil.now().isBefore(end_time)) {
			hours = end_time.getHour() - BotUtil.now().getHour();
		}
		return hours != 1 ? hours + " ORE" : hours + " ORA";
	}

	private static String minutes() {
		int minutes = 0;
		if (BotUtil.now().isBefore(end_time)) {
			minutes = 60 - BotUtil.now().getMinute();
		}
		return minutes != 1 ? minutes + " MINUTI" : minutes + " MINUTO";
	}

}
