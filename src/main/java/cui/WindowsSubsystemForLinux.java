package cui;

import java.util.Arrays;
import java.util.List;

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
		CommandLineLexer lexer = new CommandLineLexer();
		StringBuilder commandLine = new StringBuilder();
		StringBuilder cmd = new StringBuilder();
		for (Token token : lexer.lexicalize(command)) {
			if (token.isCommandSpliteLiteral()) {
				commandLine.append(cmd.toString());
				if (token.isSemicolonLiteral()) commandLine.append(" " + Token.SEMICOLON + " ");
				if (token.isANDLiteral()) commandLine.append(" " + Token.AND + " ");
				if (token.isORLiteral()) commandLine.append(" " + Token.OR + " ");
				if (token.isPipeLiteral()) commandLine.append(" " + Token.PIPE + " ");
				cmd = new StringBuilder();
				continue;
			}
			commandLine.append(this.convertCommand(token.toString()));
			
		}
		return commandLine.toString();
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
				str = "/mnt/" + Character.toLowerCase(str.charAt(0)) + str.replace(":", "\\").substring(1);
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
	
	private List<String> splitSpace(String command) {
		return Arrays.asList(command.split(" "));
	}
	
}
