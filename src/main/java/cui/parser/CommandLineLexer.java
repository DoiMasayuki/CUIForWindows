package cui.parser;

import java.util.ArrayList;

public class CommandLineLexer {
	
	private ArrayList<Token> tokens = new ArrayList<>();
	
	public ArrayList<Token> lexicalize(final String command) {
		boolean tmpEscape = false;
		String token = new String();
		String tmp = new String();
		for (String str : command.split("")) {
			if (str.equals("\\")) {
				tmpEscape = true;
				continue;
			}
			if (tmpEscape) {
				System.out.println("aa");
			}
			tmpEscape = false;
			if (str.equals(Token.SEMICOLON)) {
				appendToken(token);
				appendToken(Token.SEMICOLON);
				token = new String();
				continue;
			}
			
			if (isAndAnd(tmp, str)) {
				appendToken(token);
				appendToken(Token.AND);
				token = new String();
				tmp = new String();
				continue;
			}
			
			if (isOR(tmp, str)) {
				appendToken(token);
				appendToken(Token.OR);
				token = new String();
				tmp = new String();
				continue;
			}
			if (isPipe(tmp, str)) {
				appendToken(token);
				appendToken(Token.PIPE);
				token = new String();
				tmp = new String();
				continue;
			}
			
			if (str.equals("&") || str.equals("|")) {
				tmp = str;
				continue;
			}
			if (!tmp.isEmpty()) {
				token += tmp;
				tmp = new String();
			}
			token += str;
		}
		appendToken(token);
		return tokens;
	}
	
	private boolean isPipe(String tmp, String str) {
		if (tmp.equals("|") && !str.equals("|")) return true;
		return false;
	}
	
	private boolean isOR(String tmp, String str) {
		if (tmp.equals("|") && str.equals("|")) return true;
		return false;
	}
	
	private boolean isAndAnd(String tmp, String str) {
		if (tmp.equals("&") && str.equals("&")) return true;
		return false;
	}
	
	private void appendToken(String token) {
		token = this.removeSideSpace(token);
		tokens.add(new Token(token));
	}
	
	private String removeSideSpace(String token) {
		token = token.replaceAll("^ +", "");
		token = token.replaceAll(" +$", "");
		return token;
	}
}
