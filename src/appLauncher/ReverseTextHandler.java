package appLauncher;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReverseTextHandler implements IDirectoryEventHandler {

	@Override
	public void performAction(Path file) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(file)) {
			String line;
			if ((line = reader.readLine()) != null) {
				sb.append(reverseString(line));
			}
			while ((line = reader.readLine()) != null) {
				sb.append(System.lineSeparator());
				sb.append(reverseString(line));
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
			return;
		}
		
		System.out.print(sb.toString());
	}
	
	private static String reverseString(String s) {
		char[] chars = s.toCharArray();
		char[] reversedChars = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			reversedChars[i] = chars[s.length()-1-i];
		}
		return String.valueOf(reversedChars);
	}
}
