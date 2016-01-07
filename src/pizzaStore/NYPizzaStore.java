package pizzaStore;

public class NYPizzaStore {
    private Dough dough;
    private Sauce sauce;
    private Cheese cheese;
    private Clams clams;
    
    PizzaIngredientFactory ingredientFactory;

    public NYPizzaStore() {
        ingredientFactory = new NYPizzaIngredientFactory();
    }
    
    public void createPizza() {
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
        clams = ingredientFactory.createClam();
    }

}
