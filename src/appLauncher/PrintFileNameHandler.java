package appLauncher;

import java.nio.file.Path;

public class PrintFileNameHandler implements IDirectoryEventHandler {

	@Override
	public void performAction(Path file) {
		System.out.printf("File: %s%n", file.getFileName().toString());
	}
}
