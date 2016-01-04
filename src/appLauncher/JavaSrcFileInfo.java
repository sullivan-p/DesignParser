package appLauncher;

public class JavaSrcFileInfo implements ILaunchableFileInfo {
	private String fileExtension = "java";
	private String defaultOpenCommand = "emacs";
	
	@Override
	public String getFileExtension() {
		return fileExtension;
	}

	@Override
	public String getDefaultOpenCommand() {
		return defaultOpenCommand;
	}
}
