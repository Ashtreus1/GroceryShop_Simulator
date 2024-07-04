import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class GrocerySectionUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static GrocerySectionUI currentInstance;
    private Map<String, Item[]> sections;
    private ShoppingCart cart;
    private JComboBox<String> sectionComboBox;
    private JEditorPane itemListEditorPane;
    private JTextField itemNumberField;
    private JTextField quantityField;
    private JButton addButton;
    public JButton showCartButton;
    private JButton logoutButton;

    public GrocerySectionUI(Map<String, Item[]> sections, ShoppingCart cart) {

        this.sections = sections;
        this.cart = cart;

        setTitle("Grocery Section");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Font titleFont = new Font("Arial", Font.BOLD, 18);
        Font textFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new FlowLayout());
        JLabel sectionLabel = new JLabel("Select Section:");
        sectionLabel.setFont(titleFont);
        sectionPanel.add(sectionLabel);

        sectionComboBox = new JComboBox<>();
        sectionComboBox.setFont(textFont);
        for (String sectionName : sections.keySet()) {
            sectionComboBox.addItem(sectionName);
        }
        sectionPanel.add(sectionComboBox);
        add(sectionPanel, BorderLayout.NORTH);

        itemListEditorPane = new JEditorPane();
        itemListEditorPane.setContentType("text/html");
        itemListEditorPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(itemListEditorPane);
        add(scrollPane, BorderLayout.CENTER);

        JPanel itemSelectionPanel = new JPanel();
        itemSelectionPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel itemNumberLabel = new JLabel("Item Number:");
        itemNumberLabel.setFont(textFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        itemSelectionPanel.add(itemNumberLabel, gbc);

        itemNumberField = new JTextField();
        itemNumberField.setFont(textFont);
        gbc.gridx = 1;
        itemSelectionPanel.add(itemNumberField, gbc);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(textFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        itemSelectionPanel.add(quantityLabel, gbc);

        quantityField = new JTextField();
        quantityField.setFont(textFont);
        gbc.gridx = 1;
        itemSelectionPanel.add(quantityField, gbc);

        addButton = new JButton("Add to Cart");
        addButton.setFont(buttonFont);
        addButton.setForeground(Color.BLACK);
        addButton.setOpaque(true);
        gbc.gridx = 0;
        gbc.gridy = 2;
        itemSelectionPanel.add(addButton, gbc);

        showCartButton = new JButton("Show Cart");
        showCartButton.setFont(buttonFont);
        showCartButton.setForeground(Color.BLACK);
        showCartButton.setOpaque(true);
        showCartButton.setVisible(true);
        showCartButton.setEnabled(false);
        gbc.gridx = 1;
        itemSelectionPanel.add(showCartButton, gbc);

        logoutButton = new JButton("Log Out");
        logoutButton.setFont(buttonFont);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setOpaque(true);
        logoutButton.setPreferredSize(new Dimension(addButton.getPreferredSize().width + showCartButton.getPreferredSize().width + 20, addButton.getPreferredSize().height));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        itemSelectionPanel.add(logoutButton, gbc);

        add(itemSelectionPanel, BorderLayout.SOUTH);

        sectionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItemList();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToCart();
            }
        });

        showCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCartContents();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        updateItemList();
    }

    public static JFrame browseSections(Map<String, Item[]> sections, ShoppingCart cart) {
        if (currentInstance != null) {
            currentInstance.dispose();
        }
        currentInstance = new GrocerySectionUI(sections, cart);
        currentInstance.setVisible(true);
        return currentInstance;
    }

    private void updateItemList() {
        String selectedSection = (String) sectionComboBox.getSelectedItem();
        Item[] items = sections.get(selectedSection);

        if (items == null) {
            itemListEditorPane.setText("No items in this section.");
            return;
        }

        StringBuilder itemList = new StringBuilder("<html><head>");
        itemList.append("<style>");
        itemList.append("table { width: 100%; border-collapse: collapse; }");
        itemList.append("th, td { padding: 8px; text-align: center; font-weight: bold; }");
        itemList.append("th { background-color: #f2f2f2; }");
        itemList.append("tr:nth-child(even) { background-color: #f2f2f2; }");
        itemList.append("h2 { font-family: monospace; font-size: 24px;");
        itemList.append("</style>");
        itemList.append("</head><body>");
        itemList.append("<h2>").append(selectedSection).append("</h2>");
        itemList.append("<table>");

        for (int i = 0; i < items.length; i++) {
            if(items[i].getStocks() == 0) {
            	itemList.append("<tr style='background-color: gray;'>");
                itemList.append("<td>").append("Item ").append(i + 1);
                itemList.append("<td><img src='").append(items[i].getImagePath()).append("' width='150' height='150'/></td>");
                itemList.append("<td>").append(items[i].getName()).append("</td>");
                itemList.append("<td>").append(items[i].getPrice()).append("</td>"); 
                itemList.append("<td style='color: red;'>").append("Stocks: "+items[i].getStocks()).append("</td>"); 
                itemList.append("</tr>");
            }else {
            	itemList.append("<tr>");
                itemList.append("<td>").append("Item ").append(i + 1);
                itemList.append("<td><img src='").append(items[i].getImagePath()).append("' width='150' height='150'/></td>");
                itemList.append("<td>").append(items[i].getName()).append("</td>");
                itemList.append("<td>").append(items[i].getPrice()).append("</td>"); 
                itemList.append("<td>").append("Stocks: "+items[i].getStocks()).append("</td>"); 
                itemList.append("</tr>");
            }
        }
        itemList.append("</table>");
        itemList.append("</body></html>");

        itemListEditorPane.setContentType("text/html");
        itemListEditorPane.setText(itemList.toString());
    }


    private void addItemToCart() {
        String selectedSection = (String) sectionComboBox.getSelectedItem();
        Item[] items = sections.get(selectedSection);

        try {

            int itemChoice = Integer.parseInt(itemNumberField.getText());

            if (itemChoice >= 1 && itemChoice <= items.length) {

                Item selectedItem = items[itemChoice - 1];
                int quantity = Integer.parseInt(quantityField.getText());

                if (selectedItem.getStocks() >= quantity) {
                    cart.addItem(selectedItem, quantity);
                    selectedItem.setStocks(selectedItem.getStocks() - quantity); 
                    updateItemList(); 
                    JOptionPane.showMessageDialog(this, selectedItem.getName() + " added to the cart.");
                    itemNumberField.setText(null);
                    quantityField.setText(null);
                    showCartButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient stock for " + selectedItem.getName());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid item number. Please try again.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.");
        }
    }

    private void showCartContents() {
        cart.showCart();
    }

    private void logout() {
        int response = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to log out?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            dispose();
            Main.createMainFrame();
        }
    }
}
