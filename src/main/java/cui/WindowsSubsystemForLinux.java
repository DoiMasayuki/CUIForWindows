package cui;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import cui.parser.CommandLineLexer;
import cui.parser.Token;

public class WindowsSubsystemForLinux {
	
	final private static String WSL = new String("wsl");
	private CommandPrompt cmd = new CommandPrompt();
	
	public String execute(final String linuxCommand) {
		final String wslCommand = new String(this.convertCommandForLinux(linuxCommand));
		return cmd.execute(wslCommand);
	}
	
	private String convertCommandForLinux(String command) {
		System.out.println("----------");
		System.out.println(command);
		System.out.println("----------");
		CommandLineLexer lexer = new CommandLineLexer();
//		List<String> tokens = this.splitSpace(command);
		StringBuilder commandLine = new StringBuilder();
		StringBuilder cmd = new StringBuilder();
		for (Token token : lexer.lexicalize(command)) {
			if (token.isCommandSpliteLiteral()) {
				commandLine.append(cmd.toString());
				cmd = new StringBuilder();
				continue;
			}
			commandLine.append(token.toString());
			
			// if (token == null) continue;
			// if (this.isCommandSplitter(token)) {
			
		}
		
//		return this.convertCommandPerPile(commands);
		return commandLine.toString();
		
	}
	
	private boolean isCommandSplitter(String token) {
		Pattern p = Pattern.compile("\\|\\||\\||&&|;");
		// if (token)
		return false;
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
		List<String> string = this.splitSpace(cmd);
		for (int i = 0; i < string.size(); i++) {
			String str = string.get(i);
			if (i == 0 && str.equals(WSL)) {
				continue;
			}
			if (isIncludeDrivePath(str)) {
				str = "/mnt/" + Character.toLowerCase(str.charAt(0)) + str.substring(2);
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
	
	private List<String> splitCommand(String command) {
		Pattern p = Pattern.compile("\\|\\||\\||&&|;");
		for (String str : p.split(command)) {
			System.out.println("split : " + str);
		}
		
		return Arrays.asList(p.split(command));
	}
	
	private List<String> splitSpace(String command) {
		return Arrays.asList(command.split(" "));
	}
	
}
