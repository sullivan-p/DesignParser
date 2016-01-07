package pizzaStore;

public interface PizzaIngredientFactory {
    public Dough createDough();
    public Sauce createSauce();
    public Cheese createCheese();
    public void createVeggies();
    public void createPepperoni();
    public Clams createClam();
}
