import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingCart {
	
    public class CartItem {
        private Item item;
        private double quantity;

        public CartItem(Item item, double quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public double getTotalPrice() {
            return item.getPrice() * quantity;
        }

        public Item getItem() {
            return item;
        }

        public double getQuantity() {
            return quantity;
        }

        @Override
        public String toString() {
            return item.getName() + " " + item.getPrice() + " x " + quantity + " = " + getTotalPrice();
        }
    }

    private List<CartItem> items;
    
    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(Item item, double quantity) {
        items.add(new CartItem(item, quantity));
    }
    
    public double calculateTotal() {
        double total = 0.0;
        for (CartItem cartItem : items) {
            total += cartItem.getTotalPrice();
        }
        return total;
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (CartItem cartItem : items) {
            totalQuantity += cartItem.getQuantity();
        }
        return totalQuantity;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
    
    private JFrame shopCart;
    
    public void showCart() {
        
    	shopCart = new JFrame();
    	shopCart.setTitle("Shopping Cart");
    	shopCart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shopCart.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        for (CartItem cartItem : items) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            JLabel itemLabel = new JLabel(cartItem.getItem().getName());
            JLabel priceLabel = new JLabel("$" + cartItem.getItem().getPrice() + " x " + cartItem.getQuantity());

            itemPanel.add(itemLabel, BorderLayout.CENTER);
            itemPanel.add(priceLabel, BorderLayout.LINE_END);
            contentPanel.add(itemPanel);
        }

        JLabel totalLabel = new JLabel("Total: $" + String.format("%.2f", calculateTotal()));
        totalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        contentPanel.add(totalLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton checkoutButton = new JButton("Proceed to Checkout");
        checkoutButton.addActionListener(e -> {
            handleCheckout(this);
            shopCart.dispose();
        });

        buttonPanel.add(checkoutButton);
        contentPanel.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        shopCart.add(scrollPane, BorderLayout.CENTER);
        shopCart.pack();
        shopCart.setLocationRelativeTo(null);
        shopCart.setVisible(true);
    }

    
    public void handleCheckout(ShoppingCart cart) {
        double total = cart.calculateTotal();
        int totalQuantity = cart.getTotalQuantity();


        	JOptionPane.showMessageDialog(null, "Your total is: $" + total);

            JPanel paymentPanel = new JPanel(new GridLayout(0, 1));
            paymentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            ButtonGroup group = new ButtonGroup();
            
            
            JRadioButton cashOption = new JRadioButton("Cash");
            JRadioButton cardOption = new JRadioButton("Credit Card");
              
            group.add(cashOption);
            group.add(cardOption);
            paymentPanel.add(cashOption);
            paymentPanel.add(cardOption);

            int result = JOptionPane.showConfirmDialog(null, paymentPanel, "Choose your payment method:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                double cash = 0.0;
                if (cashOption.isSelected()) {
                    cash = handleCashPayment(total);
                } else if (cardOption.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Payments successfully with credit card");
                }
                displayReceipt(cart, total, totalQuantity, cashOption.isSelected() ? "Cash" : "Credit Card", cash);
                cart.clear();
            }	
    }

    private static double handleCashPayment(double total) {
        double cash = 0.0;
        while (true) {
            try {
                String amountStr = JOptionPane.showInputDialog(null, "Enter cash amount:");
                if (amountStr == null) return 0.0;
                cash = Double.parseDouble(amountStr);
                if (cash >= total) {
                    double change = cash - total;
                    JOptionPane.showMessageDialog(null, "Payment successful!\nYour change is: $" + Math.round(change));
                    return cash;
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient amount. "
                    		+ "\nPlease enter an amount greater than or equal to $" + total);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input.\nPlease enter a valid amount.");
            }
        }
    }

    private void displayReceipt(ShoppingCart cart, double total, int totalQuantity, String paymentMethod, double cash) {
        StringBuilder receipt = new StringBuilder("<html><head>");
        receipt.append("<style>");
        receipt.append("table { width: 100%; border-collapse: collapse; }");
        receipt.append("th, td { padding: 8px; text-align: left; }");
        receipt.append("th { background-color: #f2f2f2; }");
        receipt.append("tr:nth-child(even) { background-color: #f2f2f2; }");
        receipt.append("</style>");
        receipt.append("</head><body>");
        receipt.append("<h2>Receipt</h2>");
        receipt.append("<table>");
        receipt.append("<tr><th>Item</th><th>Quantity</th><th>Price</th></tr>");

        for (CartItem cartItem : cart.getItems()) {
            Item item = cartItem.getItem();
            double quantity = cartItem.getQuantity();
            receipt.append("<tr><td>").append(item.getName()).append("</td><td>").append(quantity).append("</td><td>").append(item.getPrice() * quantity).append("</td></tr>");
        }


        receipt.append("<tr><td colspan='5'>&nbsp;</td></tr>");

        receipt.append("<tr><td colspan='2'>Total</td><td>").append(total).append("</td></tr>");
        receipt.append("<tr><td colspan='2'>Payment Method</td><td>").append(paymentMethod).append("</td></tr>");
        if (paymentMethod.equalsIgnoreCase("cash")) {
            receipt.append("<tr><td colspan='2'>Cash</td><td>").append(cash).append("</td></tr>");
            receipt.append("<tr><td colspan='2'>Change</td><td>").append(Math.round(cash - total)).append("</td></tr>");
        }
        receipt.append("</table>");
        receipt.append("</body></html>");

        JFrame receiptFrame = new JFrame("Receipt");
        receiptFrame.setSize(400, 600);
        receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        receiptFrame.setLocationRelativeTo(null);

        JEditorPane receiptPane = new JEditorPane("text/html", receipt.toString());
        receiptPane.setEditable(false);
        receiptFrame.add(new JScrollPane(receiptPane));

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(receiptFrame, "Do you want to make another transaction?", "Continue Shopping", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    cart.getItems().clear();
                    GrocerySection.browseSections(cart);
                    receiptFrame.dispose();
                } else {
                    receiptFrame.dispose();
                    GrocerySection.exitBrowseSection(cart);
                    Main.createMainFrame();
                }
            }
        });

        receiptFrame.add(continueButton, BorderLayout.SOUTH);

        receiptFrame.setVisible(true);
    }

}
