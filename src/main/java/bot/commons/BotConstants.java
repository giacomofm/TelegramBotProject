package bot.commons;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;

public interface BotConstants {

	ZoneId ZONE_ID = ZoneId.of("Europe/Rome");

	Path PAUSATRON_RESOURCE_FILE_PATH = Paths.get("src/main/resources/PausatronArchive.txt");

}
