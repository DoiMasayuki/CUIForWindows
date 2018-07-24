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
		if(this.token.equals(SEMICOLON)) return true;
		return false;
	}
	
	public boolean isCommandSpliteLiteral() {
		if(this.isSemicolonLiteral()) return true;
		return false;
	}
	
}
