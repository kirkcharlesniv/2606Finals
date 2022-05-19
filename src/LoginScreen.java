import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.*;

public class LoginScreen implements ActionListener {
	
	private static JLabel usernameLabel;
	private static JLabel passwordLabel;
	private static JTextField usernameTextField;
	private static JPasswordField passwordTextField;
	private static JButton loginButton;
	private static HashMap<String, String> credentials = new HashMap<String, String>();
	private static int failedCtr = 1;
	private static JFrame loginScreenFrame;
	
	public static void main(String[] args) {
		try {
			URL url = LoginScreen.class.getResource("loginCredentials.txt");
			if(url == null) throw new FileNotFoundException();
			
			File credentialsFile = new File(url.getPath());
			System.out.println(credentialsFile.getAbsolutePath());
			
			Scanner scanner = new Scanner(credentialsFile);
			
			while(scanner.hasNextLine()) {
				credentials.put(scanner.next(), scanner.next());
			}
			
			scanner.close();
			
			System.out.println(credentials.toString());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File loginCredentials.txt is not found!", "FileNotFoundException", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch(NoSuchElementException e) {
			JOptionPane.showMessageDialog(null, "Your loginCredentials.txt is badly formatted.", "NoSuchElementException", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Exception", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		loginScreenFrame = new JFrame("Login screen");
		JPanel panel = new JPanel();
		
		loginScreenFrame.setSize(300, 200);
		loginScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loginScreenFrame.add(panel);
		panel.setLayout(null);

		usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(10, 20, 80, 25);
		panel.add(usernameLabel);

		usernameTextField = new JTextField(20);
		usernameTextField.setBounds(100, 20, 165, 25);
		panel.add(usernameTextField);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 50, 80, 25);
		panel.add(passwordLabel);

		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(100, 50, 165, 25);
		panel.add(passwordTextField);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(150 - 40, 100, 80, 25);
		loginButton.addActionListener(new LoginScreen());
		panel.add(loginButton);

		// Center the frame into the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		loginScreenFrame.setLocation(dim.width/2-loginScreenFrame.getSize().width/2, dim.height/2-loginScreenFrame.getSize().height/2);
		
		loginScreenFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		
		if(username.isBlank()) {
			JOptionPane.showMessageDialog(null, "Username is required.", "Please fill up all the forms!", JOptionPane.INFORMATION_MESSAGE);
			return;
		} else if (password.isBlank()) {
			JOptionPane.showMessageDialog(null, "Password is required.", "Please fill up all the forms!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if(!credentials.containsKey(username) || !credentials.get(username).equals(password)) {
			JOptionPane.showMessageDialog(null, "Invalid username / password. " + (failedCtr == 3 ? "Program is terminating." : (3 - failedCtr) + " more chance."), "Invalid credentials.", JOptionPane.WARNING_MESSAGE);
			if(failedCtr == 3) {
				System.exit(0);
			}
			failedCtr++;
			return;
		}
		
		loginScreenFrame.setVisible(false);
		RecordScreen recordScreen = new RecordScreen();
		
	}

}
