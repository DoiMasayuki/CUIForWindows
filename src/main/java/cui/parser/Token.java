package cui.parser;

public class Token {
	
	private String token = new String();
	
	public final static String SEMICOLON = new String(";");
	public final static String AND = new String("&&");
	public final static String OR = new String("||");
	public final static String PIPE = new String("|");
	
	public Token(String token) {
		this.token = token;
	}
	
	public String toString() {
		return this.token;
	}
	
	public boolean isSemicolonLiteral() {
		if (this.token.equals(SEMICOLON)) return true;
		return false;
	}
	
	public boolean isANDLiteral() {
		if (this.token.equals(AND)) return true;
		return false;
	}
	
	public boolean isORLiteral() {
		if (this.token.equals(OR)) return true;
		return false;
	}
	
	public boolean isPipeLiteral() {
		if (this.token.equals(PIPE)) return true;
		return false;
	}
	
	public boolean isCommandSpliteLiteral() {
		if (this.isANDLiteral()) return true;
		if (this.isORLiteral()) return true;
		if (this.isPipeLiteral()) return true;
		return false;
	}
	
}
