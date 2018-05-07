package bot.services;

import static bot.commons.utils.BotUtil.checkMessage;
import static bot.commons.utils.BotUtil.dateTimeNow;
import static bot.commons.utils.BotUtil.timeNow;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class JekOrologio extends TelegramLongPollingBot {

	/**
	 * porcodio - (to 18:00) Informa su quanto manca alle 18:00
	 */
	public static final String ASK_END_TIME_COMMAND = "porcodio";
	/**
	 * diocane - (to 13:00) Informa su quanto manca alle 13:00
	 */
	public static final String ASK_LUNCH_TIME_COMMAND = "diocane";
	/**
	 * madonnavacca - (to 18:00 ven) Informa su quante ore mancano alle 18:00 di venerdi
	 */
	public static final String ASK_WEEKEND_TIME_COMMAND = "madonnavacca";

	private static final LocalTime end_time = LocalTime.of(18, 0);
	private static final LocalTime lunch_time = LocalTime.of(13, 0);

	private static final int seconds_to_weekend = 60 * 10;

	private static final String lunch_time_additional_text = " _AL PRANSO_";
	private static final String weekend_additional_text = " _AL WEEKEND_";
	private static final String in_weekend_text = "GODITI IL WEEKEND";

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

		if (checkMessage(update.getMessage(), ASK_END_TIME_COMMAND)) {
			send(new SendMessage().setChatId(chatId).setText(getTime(end_time)));
		}

		if (checkMessage(update.getMessage(), ASK_LUNCH_TIME_COMMAND)) {
			send(new SendMessage().setChatId(chatId).setText(getTime(lunch_time) + lunch_time_additional_text));
		}

		if (checkMessage(update.getMessage(), ASK_WEEKEND_TIME_COMMAND)) {
			send(new SendMessage().setChatId(chatId).setText(getWeekEndTime()));
		}
	}

	private static String getTime(final LocalTime endTime) {
		final StringBuilder sb = new StringBuilder("*");
		final Duration between = Duration.between(timeNow(), endTime);
		sb.append(hours(between.getSeconds())).append(" E ").append(minutes(between.getSeconds())).append("*");
		return sb.toString();
	}

	private static String minutes(final long seconds) {
		long minutes = 0;
		if (seconds > 0) {
			final long hours = Math.abs(seconds / (60 * 60));
			minutes = Math.abs((seconds - (hours * 60 * 60)) / 60);
		}
		return minutes != 1 ? minutes + " MINUTI" : minutes + " MINUTO";
	}

	private static String hours(final long seconds) {
		long hours = 0;
		if (seconds > 0)
			hours = Math.abs(seconds / (60 * 60));
		return hours != 1 ? hours + " ORE" : hours + " ORA";
	}

	private static String getWeekEndTime() {
		final StringBuilder sb = new StringBuilder("*");
		final Duration between = Duration.between(dateTimeNow(), firstFriday());
		if (between.getSeconds() > seconds_to_weekend)
			sb.append(hours(between.getSeconds())).append("*").append(weekend_additional_text);
		else
			sb.append(in_weekend_text).append("*");
		return sb.toString();
	}

	private static Temporal firstFriday() {
		switch (dateTimeNow().getDayOfWeek()) {
		case SATURDAY:
		case SUNDAY:
			return dateTimeNow();
		default:
			return dateTimeNow().with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)).withHour(18).withMinute(0);
		}
	}

}
