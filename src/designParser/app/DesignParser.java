package designParser.app;

import java.io.IOException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import designParser.asm.visitor.ClassDeclarationVisitor;
import designParser.asm.visitor.ClassFieldVisitor;
import designParser.asm.visitor.ClassMethodVisitor;
import designParser.asm.visitor.ModelBuilderClassVisitor;
import designParser.markupGen.api.MarkupGenerator;
import designParser.markupGen.impl.SdGenerator;
import designParser.markupGen.impl.UmlGenerator;
import designParser.model.api.IDesignModel;
import designParser.model.impl.DesignModel;

public class DesignParser {
//	public final static String MODEL_NAME = "AppLauncher";
//	public final static String[] OBJECT_NAMES = { "appLauncherSltn.AppLauncherApplication",
//			"appLauncherSltn.ApplicationLauncher", "appLauncherSltn.DataFileRunner",
//			"appLauncherSltn.DirectoryChangeLogger", "appLauncherSltn.DirectoryEvent",
//			"appLauncherSltn.DirectoryMonitorService", "appLauncherSltn.ExecutableFileRunner",
//			"appLauncherSltn.IApplicationLauncher", "appLauncherSltn.IDirectoryListener",
//			"appLauncherSltn.IDirectoryMonitorService", "appLauncherSltn.ProcessRunner" };

    public final static String MODEL_NAME = "ChocolateBoiler";
    public final static String[] OBJECT_NAMES = { "headfirst.singleton.chocolate.ChocolateBoiler",
            "headfirst.singleton.chocolate.ChocolateController" };
    
//	 private final static String MODEL_NAME = "AbstractPizzaFactory";
//	 private final static String[] OBJECT_NAMES = {
//    	 "pizzaStore.Cheese",
//    	 "pizzaStore.ChicagoPizzaIngredientFactory",
//    	 "pizzaStore.Clams",
//    	 "pizzaStore.Dough",
//    	 "pizzaStore.FreshClams",
//    	 "pizzaStore.FrozenClams",
//    	 "pizzaStore.MarinaraSauce",
//    	 "pizzaStore.MozzarellaCheese",
//    	 "pizzaStore.NYPizzaIngredientFactory",
//    	 "pizzaStore.NYPizzaStore",
//    	 "pizzaStore.PizzaIngredientFactory",
//    	 "pizzaStore.PlumTomatoSauce",
//    	 "pizzaStore.ReggianoCheese",
//    	 "pizzaStore.Sauce",
//    	 "pizzaStore.ThickCrustDough",
//    	 "pizzaStore.ThinCrustDough"
//	 };

	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 *
	 * @param args:
	 *            the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		IDesignModel designModel = new DesignModel(OBJECT_NAMES);
		for (String className : OBJECT_NAMES) {
			ClassReader reader = new ClassReader(className);

			ModelBuilderClassVisitor decVisitor;
			decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, designModel);
			ModelBuilderClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor);
			ModelBuilderClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			designModel = methodVisitor.getModel();
		}

		MarkupGenerator umlGenerator = new UmlGenerator(MODEL_NAME, designModel);
		System.out.println(umlGenerator.getMarkup());

//		String mthdClassName = "DesignParser";
//		String mthdName = "main";
//		String[] mthdParamTypes = { "String[]" };
//		int callDepth = 5;
//		MarkupGenerator sdGenerator = new SdGenerator(designModel, mthdClassName, mthdName, mthdParamTypes, callDepth);
//		System.out.println(sdGenerator.getMarkup());
	}
}