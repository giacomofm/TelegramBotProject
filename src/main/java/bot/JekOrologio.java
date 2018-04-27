package bot;

import java.time.Duration;
import java.time.LocalTime;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import bot.util.BotUtil;

public class JekOrologio extends TelegramLongPollingBot {

	public static final String ASK_END_TIME_COMMAND = "porcodio";
	public static final String ASK_LUNCH_TIME_COMMAND = "diocane";

	private static final LocalTime end_time = LocalTime.of(18, 0);
	private static final LocalTime lunch_time = LocalTime.of(13, 0);

	private static final String lunch_time_additional_text = "_AL PRANZO_";

	@Override
	public String getBotUsername() {
		return "JekOrologioBot";
	}

	@Override
	public String getBotToken() {
		return "555689661:AAHs7lWaHnQefPekuXCUcQKTPa9KSxNwKME";
	}

	private void send(final SendMessage message) {
		try {
			execute(message.enableMarkdown(true));
		} catch (final TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpdateReceived(final Update update) {
		long chatId = 0;
		if (update.hasMessage())
			chatId = update.getMessage().getChatId();

		if (BotUtil.checkMessage(update.getMessage(), ASK_END_TIME_COMMAND)) {
			send(new SendMessage().setChatId(chatId).setText(getTime(end_time)));
		}

		if (BotUtil.checkMessage(update.getMessage(), ASK_LUNCH_TIME_COMMAND)) {
			send(new SendMessage().setChatId(chatId).setText(getTime(lunch_time)));
			send(new SendMessage().setChatId(chatId).setText(lunch_time_additional_text));
		}
	}

	private static String getTime(final LocalTime endTime) {
		final StringBuilder sb = new StringBuilder("*");
		final Duration between = Duration.between(BotUtil.now(), endTime);
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
