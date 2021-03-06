package designParser.asm.visitor;

import org.objectweb.asm.ClassVisitor;

import designParser.model.api.IDesignModel;

public abstract class ClassVisitorDecorator extends ModelBuilderClassVisitor{
	public ClassVisitorDecorator(int api, ClassVisitor decorated) {
		super(api, decorated);
	}

	public abstract ModelBuilderClassVisitor getDecoratedVisitor();

	public IDesignModel getModel() {
		return this.getDecoratedVisitor().getModel();
	}
	
	public String getCurrentObjectName() {
	    return this.getDecoratedVisitor().getCurrentObjectName();
	}
}
