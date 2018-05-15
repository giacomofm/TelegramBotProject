package bot.commons.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.telegram.telegrambots.api.objects.Message;

import bot.commons.BotConstants;

public class BotUtil {

	public static Boolean checkMessage(final Message message, final String txt) {
		return message != null && message.hasText() && message.getText().contains(txt);
	}

	public static LocalTime timeNow() {
		return LocalTime.now(BotConstants.ZONE_ID);
	}

	public static LocalDateTime dateTimeNow() {
		return LocalDateTime.now(BotConstants.ZONE_ID);
	}

	public static void write(final Long chatId, final Path path) {
		String string = chatId.toString() + '\n';
		try {
			Files.write(path, string.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Long> read(final Path path) {
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines.stream().map(Long::parseLong).collect(Collectors.toList());
	}

	public static void remove(final Long chatId, final Path path) {
		try {
			List<String> lines = Files.readAllLines(path);
			lines.removeIf(s -> s.contains(chatId.toString()));
			// TODO: rimuovere la singola riga o aggiornare l'intero elenco?
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
