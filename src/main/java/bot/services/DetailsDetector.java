package bot.services;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.Video;
import org.telegram.telegrambots.api.objects.stickers.Sticker;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.vdurmont.emoji.EmojiParser;

public class DetailsDetector extends TelegramLongPollingBot {

	private static final Character end_line = '\n';

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
		StringBuilder sb = new StringBuilder("");
		if (message.hasText())
			detectText(sb, message.getText());

		if (message.hasEntities())
			message.getEntities().stream().forEach(e -> detectEntity(sb, e));

		if (message.getSticker() != null)
			detectSticker(sb, message.getSticker());

		if (message.hasPhoto())
			message.getPhoto().stream().forEach(p -> detectPhoto(sb, p));

		if (message.hasVideo())
			detectVideo(sb, message.getVideo());

		if (message.hasDocument())
			detectDocument(sb, message.getDocument());

		if (sb.length() > 0)
			return sb.toString();
		return "nothing detectable";
	}

	private static void detectText(final StringBuilder sb, final String text) {
		sb.append("-- message text --").append(end_line);
		sb.append(EmojiParser.parseToAliases(text)).append(end_line);
	}

	private static void detectEntity(final StringBuilder sb, final MessageEntity entity) {
		sb.append("-- entity info --").append(end_line);
		sb.append("entity type: ").append(entity.getType()).append(end_line);
		sb.append("entity text: ").append(entity.getText()).append(end_line);
	}

	private static void detectSticker(final StringBuilder sb, final Sticker sticker) {
		sb.append("-- sticker info --").append(end_line);
		sb.append("sticker set: ").append(sticker.getSetName()).append(end_line);
		sb.append("file id: ").append(sticker.getFileId()).append(end_line);
		sb.append("file size: ").append(sticker.getFileSize()).append(end_line);
		sb.append("file height: ").append(sticker.getHeight()).append(end_line);
		sb.append("file width: ").append(sticker.getWidth()).append(end_line);
		sb.append("emoji: ").append(EmojiParser.parseToAliases(sticker.getEmoji())).append(end_line);
	}

	private static void detectPhoto(final StringBuilder sb, final PhotoSize photo) {
		sb.append("-- photo info --").append(end_line);
		sb.append("file id: ").append(photo.getFileId()).append(end_line);
		sb.append("file size: ").append(photo.getFileSize()).append(end_line);
		if (photo.hasFilePath())
			sb.append("file path: ").append(photo.getFilePath()).append(end_line);
		sb.append("photo height: ").append(photo.getHeight()).append(end_line);
		sb.append("photo width: ").append(photo.getWidth()).append(end_line);
	}

	private static void detectVideo(final StringBuilder sb, final Video video) {
		sb.append("-- video info --").append(end_line);
		sb.append("file id: ").append(video.getFileId()).append(end_line);
		sb.append("file size: ").append(video.getFileSize()).append(end_line);
		sb.append("file mime type: ").append(video.getMimeType()).append(end_line);
		sb.append("video height: ").append(video.getHeight()).append(end_line);
		sb.append("video width: ").append(video.getWidth()).append(end_line);
		sb.append("video duration: ").append(video.getDuration()).append(" seconds").append(end_line);
	}

	private static void detectDocument(final StringBuilder sb, final Document document) {
		sb.append("-- document info --").append(end_line);
		sb.append("file id: ").append(document.getFileId()).append(end_line);
		sb.append("file name: ").append(document.getFileName()).append(end_line);
		sb.append("file size: ").append(document.getFileSize()).append(end_line);
		sb.append("file mime type: ").append(document.getMimeType()).append(end_line);
	}
}
