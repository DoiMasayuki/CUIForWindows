package cui;

import java.util.Arrays;
import java.util.List;

public class WindowsSubsystemForLinux {
	
	final private static String WSL = new String("wsl");
	private CommandPrompt cmd = new CommandPrompt();
	
	public String execute(final String linuxCommand) {
		final String wslCommand = new String(this.convertCommandForLinux(linuxCommand));
		return cmd.execute(wslCommand);
	}
	
	private String convertCommandForLinux(String command) {
		List<String> commands = this.splitCommandPipe(command);
		return this.convertCommandPerPile(commands);
		
	}
	
	private String convertCommandPerPile(List<String> commands) {
		String wslCommand = new String();
		for (int i = 0; i < commands.size(); i++) {
			final String cmd = commands.get(i);
			wslCommand += this.convertCommand(cmd);
			if (this.isLastCommand(i, commands)) break;
			wslCommand += " | ";
		}
		return wslCommand;
	}
	
	private boolean isLastCommand(int i, List<String> commands) {
		return i == commands.size() - 1;
	}
	
	private String convertCommand(String cmd) {
		cmd = this.removeSideSpace(cmd);
		if (isDriveSelectCommand(cmd)) {
			cmd = "cd /mnt/" + cmd.toLowerCase().replace(":", "");
		}
		
		String newCmd = new String();
		List<String> string = this.splitCommandSpace(cmd);
		for (int i = 0; i < string.size(); i++) {
			String str = string.get(i);
			if (i == 0 && str.equals(WSL)) {
				continue;
			}
			if (isIncludeDrivePath(str)) {
				str = "/mnt/" + str.toLowerCase().replace(":", "");
			}
			newCmd += str;
			if (i == string.size() - 1) break;
			newCmd += " ";
		}
		newCmd = newCmd.replace("\\", "/");
		return WSL + " " + newCmd;
	}
	
	private boolean isIncludeDrivePath(String str) {
		if (str.length() > 1) {
			if (str.charAt(1) == ':') {
				return true;
			}
		}
		return false;
	}
	
	private boolean isDriveSelectCommand(String cmd) {
		return cmd.length() == 2 && cmd.endsWith(":");
	}
	
	private String removeSideSpace(String cmd) {
		cmd = cmd.replaceAll("^ +", "");
		cmd = cmd.replaceAll(" +$", "");
		return cmd;
	}
	
	private List<String> splitCommandPipe(String command) {
		return Arrays.asList(command.split("\\|"));
	}
	
	private List<String> splitCommandSpace(String command) {
		return Arrays.asList(command.split(" "));
	}
	
}
