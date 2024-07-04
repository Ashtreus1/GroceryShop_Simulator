import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String fullName;
    private String contactNo;
    private String password;

    public User(String username, String fullName, String contactNo, String password) {
        this.username = username;
        this.fullName = fullName;
        this.contactNo = contactNo;
        this.password = password;
    }
    
    public String getPassword() {
    	return password;
    }

    public static User register() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);
        JLabel fullNameLabel = new JLabel("Full Name:");
        JTextField fullNameField = new JTextField(15);
        JLabel contactNoLabel = new JLabel("Contact Number:");
        JTextField contactNoField = new JTextField(15);
        
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField(15);

        panel.add(usernameLabel, gbc);
        gbc.gridx++;
        panel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(fullNameLabel, gbc);
        gbc.gridx++;
        panel.add(fullNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(contactNoLabel, gbc);
        gbc.gridx++;
        panel.add(contactNoField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridx++;
        panel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(confirmPasswordLabel, gbc);
        gbc.gridx++;
        panel.add(confirmPasswordField, gbc);

        int result = JOptionPane.showConfirmDialog(null, panel, "Register", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String fullName = fullNameField.getText();
            String contactNo = contactNoField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || fullName.isEmpty() || contactNo.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled. Please try again.");
                return null;
            }
            
            if(password.length() < 8) {
            	JOptionPane.showMessageDialog(null, "Password must be 8 characters above.");
            	return null;
            }

            if (password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Registration successful!");
                User newUser = new User(username, fullName, contactNo, password);
                saveAccount(newUser);
                return newUser;
            } else {
                JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
            }
        }
        return null;
    }

    public static User login() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);

        panel.add(usernameLabel, gbc);
        gbc.gridx++;
        panel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridx++;
        panel.add(passwordField, gbc);

        int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled. Please try again.");
                return null;
            }

            List<User> savedUsers = loadUsersFromFile();
            for (User savedUser : savedUsers) {
                if (username.equals(savedUser.username) && password.equals(savedUser.password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    return savedUser;
                }
            }
            JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
        }
        return null;
    }

    private static void saveAccount(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt", true))) {
            writer.write(user.username + "," + user.fullName + "," + user.contactNo + "," + user.password);
            writer.newLine(); 
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save user account: " + e.getMessage());
        }
    }

    private static List<User> loadUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No loaded saved accounts\n" + e.getMessage() + "\n\nTip\nTry to register account again.");
        }
        return users;
    }
   
    
}