package testModels.singleton;

/**
 * A class that is not a singleton because it has a public constructor.
 */
public class AlmostSingleton {
    private static AlmostSingleton instance;
    
    private AlmostSingleton() {}
    
    public AlmostSingleton(int x) {}
    
    public static AlmostSingleton getInstance() {
        if (instance == null) {
            instance = new AlmostSingleton();
        }
        return instance;
    }
}
