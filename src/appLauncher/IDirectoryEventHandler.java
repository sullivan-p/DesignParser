package appLauncher;

import java.nio.file.Path;

public interface IDirectoryEventHandler {
	public void performAction(Path file);
}
