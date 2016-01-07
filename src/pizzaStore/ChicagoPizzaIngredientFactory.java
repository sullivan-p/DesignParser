package pizzaStore;

public class ChicagoPizzaIngredientFactory implements PizzaIngredientFactory {
    ThickCrustDough dough;
    PlumTomatoSauce sauce;
    MozzarellaCheese cheese;
    FrozenClams clams;
    
    public ChicagoPizzaIngredientFactory() {
        dough = new ThickCrustDough();
        sauce = new PlumTomatoSauce();
        cheese = new MozzarellaCheese();
        clams = new FrozenClams();
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
