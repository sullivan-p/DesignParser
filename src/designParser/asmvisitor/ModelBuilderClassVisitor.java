package designParser.asmvisitor;

import org.objectweb.asm.ClassVisitor;

import designParser.model.api.*;

public abstract class ModelBuilderClassVisitor extends ClassVisitor {
	public ModelBuilderClassVisitor(int api) {
		super(api);
	}

	public ModelBuilderClassVisitor(int api, ClassVisitor decorated) {
		super(api, decorated);
	}
	
	public abstract IModel getModel();
}
