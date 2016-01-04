package appLauncher;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class OpenFileHandler implements IDirectoryEventHandler {
	
	// The child processes of the process that uses OpenFileHandler.
	private List<Process> processes;
	
	// Mapping of file extensions to information about the file type.
	private HashMap<String, ILaunchableFileInfo> supportedFileInfo; 
	
	public OpenFileHandler(List<Process> processes) {
		this.processes = processes;
		ILaunchableFileInfo txtFileInfo = new TxtFileInfo();
		ILaunchableFileInfo htmlFileInfo = new HtmlFileInfo();
		ILaunchableFileInfo javaFileInfo = new JavaSrcFileInfo();
		supportedFileInfo = new HashMap<String, ILaunchableFileInfo>();
		supportedFileInfo.put(txtFileInfo.getFileExtension(), txtFileInfo);
		supportedFileInfo.put(htmlFileInfo.getFileExtension(), htmlFileInfo);
		supportedFileInfo.put(javaFileInfo.getFileExtension(), javaFileInfo);
	}

	@Override
	public void performAction(Path file) {		
		String fileName = file.getFileName().toString();
		String fileExt = getExtensionFromFileName(fileName);
		
		if (!supportedFileInfo.containsKey(fileExt)) {
			System.err.format("No support available for: %s...%n", file);
			return;
		} 
		String command = supportedFileInfo.get(fileExt).getDefaultOpenCommand();
		String arg = file.toString();
		
		// Run the application if support is available
		try {
			System.out.format("Launching %s ...%n", command);
			ProcessBuilder processBuilder = new ProcessBuilder(command, arg);
			
			// Start and add the process to the processes list
			Process process = processBuilder.start();
			this.processes.add(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getExtensionFromFileName(String fileName) {
		StringTokenizer st = new StringTokenizer(fileName, ".");
		String fileExt = "";
		String token = "";
		while (st.hasMoreTokens()) {
			token = st.nextToken();
			if (!st.hasMoreTokens()) {
				fileExt = token;
			}
		}
		return fileExt;
	}
}
