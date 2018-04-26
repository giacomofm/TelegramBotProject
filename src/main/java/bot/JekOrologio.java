package bot;

import java.time.Duration;
import java.time.LocalTime;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import bot.util.BotUtil;

public class JekOrologio extends TelegramLongPollingBot {

	public static final String ASKTIMECOMMAND = "porcodio";

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
			final long chat_id = update.getMessage().getChatId();
			final SendMessage message = new SendMessage().setChatId(chat_id).setText(getTime());
			try {
				execute(message.enableMarkdown(true));
			} catch (final TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getTime() {
		final StringBuilder sb = new StringBuilder("*");
		final Duration between = Duration.between(BotUtil.now(), end_time);
		sb.append(hours(between.getSeconds())).append(" E ").append(minutes(between.getSeconds())).append("*");
		return sb.toString();
	}

	private static String minutes(final long seconds) {
		long minutes = 0;
		if (seconds > 0)
			minutes = Math.abs((seconds / 60) % 60);
		return minutes != 1 ? minutes + " MINUTI" : minutes + " MINUTO";
	}

	private static String hours(final long seconds) {
		long hours = 0;
		if (seconds > 0)
			hours = Math.abs((seconds / (60 * 60)) % 60);
		return hours != 1 ? hours + " ORE" : hours + " ORA";
	}

}
