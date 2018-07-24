package cui;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class WindowsSubsystemForLinuxTest {
	
	private WindowsSubsystemForLinux wsl = new WindowsSubsystemForLinux();
	
	@Test
	public void execute() {
		assertEquals("a\n", wsl.execute("echo a"));
	}
	
	/*
	 * @Test public void executePwd() { WindowsSubsystemForLinux wsl = new
	 * WindowsSubsystemForLinux(); String pwd = new
	 * File(".").getAbsoluteFile().getParent().toString(); System.out.println(pwd);
	 * assertEquals(pwd + "\n", wsl.execute("pwd")); }
	 */
	
	@Test
	public void convertCommandForLinuxTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = WindowsSubsystemForLinux.class.getDeclaredMethod("convertCommandForLinux", String.class);
		method.setAccessible(true);
		assertEquals((String) method.invoke(wsl, "ls | xargs cat"), "wsl ls | wsl xargs cat");
		
	}
	
	@Test
	public void convertDriveCommandTest2() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = WindowsSubsystemForLinux.class.getDeclaredMethod("convertCommandForLinux", String.class);
		method.setAccessible(true);
		assertEquals((String) method.invoke(wsl, "D:"), "wsl cd /mnt/d");
	}
	
	@Test
	public void convertDriveCommandTest3() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = WindowsSubsystemForLinux.class.getDeclaredMethod("convertCommandForLinux", String.class);
		method.setAccessible(true);
		
		assertEquals((String) method.invoke(wsl, "ls D:\\test"), "wsl ls /mnt/d/test");
	}
	
	@Test
	public void convertDriveCommandTest3of1() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = WindowsSubsystemForLinux.class.getDeclaredMethod("convertCommandForLinux", String.class);
		method.setAccessible(true);
		
		assertEquals((String) method.invoke(wsl, "ls D:\\TeSt"), "wsl ls /mnt/d/TeSt");
	}
	
	@Test
	public void convertDriveCommandTest4() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = WindowsSubsystemForLinux.class.getDeclaredMethod("convertCommandForLinux", String.class);
		method.setAccessible(true);
		assertEquals((String) method.invoke(wsl, "wsl ls"), "wsl ls");
	}
	
	@Test
	public void convertDriveCommandTest5() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = WindowsSubsystemForLinux.class.getDeclaredMethod("convertCommandForLinux", String.class);
		method.setAccessible(true);
		assertEquals((String) method.invoke(wsl, "cd .. && ls"), "wsl cd .. && wsl ls");
	}
	
}
