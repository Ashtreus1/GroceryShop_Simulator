import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class GrocerySection {
    public static Map<String, Item[]> sections = new HashMap<>();

    static {
        sections.put("Fruits and Vegetables", new Item[]{
            new Item("Apple", 20.50, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/apple.jpg", 10),
            new Item("Banana", 8.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/banana.jpg", 10),
            new Item("Tomato", 150.75, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/tomato.jpg", 10),
            new Item("Potato", 90.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/potato.jpg", 10),
            new Item("Strawberry", 206.50, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/strawberry.jpg", 10)
        });
        sections.put("Frozen Meats", new Item[]{
            new Item("Fish", 250.75, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/fish.jpeg", 10),
            new Item("Pork Meat", 170.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/pork.jpg", 10),
            new Item("Chicken", 180.50, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/chicken.jpg", 10),
            new Item("Beef Meat", 500.25, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/beef.jpeg", 10)
        });
        sections.put("Canned/Jarred Goods", new Item[]{
            new Item("555 Tuna", 25.25, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/555tuna.jpg", 10),
            new Item("Argentina Corned Beef", 30.75, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/argentina.jpg", 10),
            new Item("Maling", 53.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/maling.jpg", 10),
            new Item("Silver Swan", 15.75, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/silverSwan.jpg", 10),
            new Item("UFC Ketchup", 11.50, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/ketchup.jpg", 10),
            new Item("Wow Ulam", 23.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/wowUlam.jpg", 10),
            new Item("Spam", 109.50, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/spam.jpg", 10),
            new Item("San Marino", 30.75, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/sanMarino.jpg", 10)
        });
        sections.put("Dairy and Baking Goods", new Item[]{
            new Item("Butter", 35.25, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/butter.jpg", 10),
            new Item("Star Margarine", 30.75, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/star_margarine.jpg", 10),
            new Item("Flour", 40.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/flour.jpg", 10)
        });
        sections.put("Beverage", new Item[]{
            new Item("Gin", 42.75, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/gin.jpg", 10),
            new Item("Zest-o", 12.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/zest-o.jpg", 10),
            new Item("Vita Milk", 35.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/vita_milk.jpg", 10),
            new Item("Cobra", 24.50, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/cobra.jpg", 10),
            new Item("Bearbrand Sterilized", 38.25, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/bear_brand.jpg", 10),
            new Item("Red Horse", 110.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/red_horse.jpg", 10)
        });
        sections.put("Personal Care and Cleaners", new Item[]{
            new Item("Tissue", 11.50, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/tissue.jpg", 10),
            new Item("Joy", 10.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/joy.jpg", 10),
            new Item("Safeguard", 19.75, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/safe_guard.jpg", 10),
            new Item("Mr. Clean (bar)", 27.25, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/mr_clean.jpg", 10),
            new Item("Downy", 15.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/downy.jpg", 10),
            new Item("Tender Care (powder)", 21.00, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/tender_care.jpg", 10),
            new Item("Modess", 47.25, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/modess.jpg", 10),
            new Item("Gatsby", 50.75, "file:///home/keiru/eclipse-workspace/Grocery%20Shop/src/pics/gatsby.jpg", 10)
        });
    }

    public static void browseSections(ShoppingCart cart) {
    	GrocerySectionUI.browseSections(sections, cart);
    }
    
    public static void exitBrowseSection(ShoppingCart cart) {
    	JFrame frame = GrocerySectionUI.browseSections(sections, cart);
    	
    	frame.dispose();
    }
}
