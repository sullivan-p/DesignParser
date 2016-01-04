package appLauncher;

public class TxtFileInfo implements ILaunchableFileInfo {
	private String fileExtension = "txt";
	private String defaultOpenCommand = "gedit";
	
	@Override
	public String getFileExtension() {
		return fileExtension;
	}

	@Override
	public String getDefaultOpenCommand() {
		return defaultOpenCommand;
	}
}
