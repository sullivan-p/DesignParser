package designParser.asm.visitor;

import org.objectweb.asm.ClassVisitor;

import designParser.model.api.IModel;

public abstract class ClassVisitorDecorator extends ModelBuilderClassVisitor{
	public ClassVisitorDecorator(int api, ClassVisitor decorated) {
		super(api, decorated);
	}

	public abstract ModelBuilderClassVisitor getDecoratedVisitor();

	public IModel getModel() {
		return this.getDecoratedVisitor().getModel();
	}
}
