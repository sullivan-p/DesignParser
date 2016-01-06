package designParser.asm.visitor;

import org.objectweb.asm.ClassVisitor;

import designParser.model.api.IDesignModel;
import designParser.model.api.IObject;

public abstract class ClassVisitorDecorator extends ModelBuilderClassVisitor{
	public ClassVisitorDecorator(int api, ClassVisitor decorated) {
		super(api, decorated);
	}

	public abstract ModelBuilderClassVisitor getDecoratedVisitor();

	public IDesignModel getModel() {
		return this.getDecoratedVisitor().getModel();
	}
	
	public IObject getCurrentEntity() {
	    return this.getDecoratedVisitor().getCurrentEntity();
	}
}
