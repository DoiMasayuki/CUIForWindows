package cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandPrompt {
	
	final private static String CMD = new String("cmd");
	final private static String EXEC = new String("/c");
	
	public String execute(final String command) {
		ProcessBuilder pb = new ProcessBuilder(CMD, EXEC, command);
		//pb.redirectErrorStream(true);
		
		String output = new String();
		try {
			Process p = pb.start();
			output = new String(this.outputFromProcess(p));
			p.waitFor();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	private String outputFromProcess(Process p) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println(line);
				sb.append(line);
				sb.append('\n');
			}
		}
	
		return sb.toString();
	}
}
