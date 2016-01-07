package pizzaStore;

public class NYPizzaIngredientFactory implements PizzaIngredientFactory {
    ThinCrustDough dough;
    MarinaraSauce sauce;
    ReggianoCheese cheese;
    FreshClams clams;
    
    public NYPizzaIngredientFactory() {
        dough = new ThinCrustDough();
        sauce = new MarinaraSauce();
        cheese = new ReggianoCheese();
        clams = new FreshClams();
    }

    @Override
    public Dough createDough() {
        return dough;
    }

    @Override
    public Sauce createSauce() {
        return sauce;
    }

    @Override
    public Cheese createCheese() {
        return cheese;
    }

    @Override
    public void createVeggies() {}

    @Override
    public void createPepperoni() {}

    @Override
    public Clams createClam() {
        return clams;
    }
}
