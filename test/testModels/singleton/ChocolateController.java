package testModels.singleton;
 
public class ChocolateController {
	public static void main(String args[]) {
		ChocolateBoiler boiler = ChocolateBoiler.getInstance();
		boiler.fill();
		boiler.boil();
		boiler.drain();

		// will return the existing instance
		@SuppressWarnings("unused")
        ChocolateBoiler boiler2 = ChocolateBoiler.getInstance();
	}
}
