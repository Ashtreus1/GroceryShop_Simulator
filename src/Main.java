import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        createMainFrame();
    }

    public static void createMainFrame() {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel message = new JLabel("Welcome to the Grocery Shop!");
        message.setFont(new Font("Arial", Font.BOLD, 20));
        message.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(message, gbc);

        gbc.gridy = 1;

        String[] options = {"Login", "Register", "Exit"};
        for (String option : options) {
            JButton button = new JButton(option);
            button.setPreferredSize(new Dimension(150, 40)); 
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String choice = ((JButton) e.getSource()).getText();
                    if (choice.equals("Register")) {
                    	User.register();
                    } else if (choice.equals("Login")) {
                        User savedUser = User.login();
                        if (savedUser != null) {
                            frame.dispose();
                            ShoppingCart cart = new ShoppingCart();
                            GrocerySection.browseSections(cart);
                        }
                    } else if (choice.equals("Exit")) {
                        System.exit(0);
                    }
                }
            });
            panel.add(button, gbc);
            gbc.gridy++;
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
