package designParser.asmvisitor;

import java.util.Arrays;
import designParser.model.api.IModel;
import designParser.model.impl.Model;

public class ClassDeclarationVisitor extends ModelBuilderClassVisitor {
	private IModel model;

	public ClassDeclarationVisitor(int api) {
		super(api);
		model = new Model();
	}

	public ClassDeclarationVisitor(int api, IModel model) {
		super(api);
		this.model = model;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		// TODO: delete the line below
		System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		// TODO: construct an internal representation of the class for later use
		// by decorators
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public IModel getModel() {
		return model;
	}
}