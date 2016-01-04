package appLauncher;

public class HtmlFileInfo implements ILaunchableFileInfo {
	private String fileExtension = "html";
	private String defaultOpenCommand = "xdg-open";
	
	@Override
	public String getFileExtension() {
		return fileExtension;
	}

	@Override
	public String getDefaultOpenCommand() {
		return defaultOpenCommand;
	}
}
