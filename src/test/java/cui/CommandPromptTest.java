package cui;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class CommandPromptTest {
	
	@Test
	public void testExecuteEcho() {
		CommandPrompt cmd = new CommandPrompt();
		assertEquals("a\n", cmd.execute("echo a"));
	}
	
	@Test
	public void testExecutePwd() {
		CommandPrompt cmd = new CommandPrompt();
		String pwd = new File(".").getAbsoluteFile().getParent().toString();
		assertEquals(pwd + "\n", cmd.execute("@cd"));
	}
	
}
