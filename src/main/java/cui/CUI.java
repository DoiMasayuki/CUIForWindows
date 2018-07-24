package cui;

public class CUI {
	
	final private WindowsSubsystemForLinux wsl = new WindowsSubsystemForLinux();
	
	public String execute(final String command) {
		
		return wsl.execute(command);
	}
	
	public static void main(String[] args) {
		String command = new String();
		for (String arg : args) {
			command += arg;
			command += " ";
		}
		CUI cui = new CUI();
		cui.execute(command);
	}
	
}
