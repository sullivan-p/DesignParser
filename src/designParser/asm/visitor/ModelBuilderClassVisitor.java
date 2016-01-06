package designParser.asm.visitor;

import org.objectweb.asm.ClassVisitor;

import designParser.model.api.*;

public abstract class ModelBuilderClassVisitor extends ClassVisitor {
	public ModelBuilderClassVisitor(int api) {
		super(api);
	}

	public ModelBuilderClassVisitor(int api, ClassVisitor decorated) {
		super(api, decorated);
	}
	
	public abstract IDesignModel getModel();
	
	/**
	 * Return the current class, interface, or enum that the visitor is 
	 * visiting.
	 */
	public abstract IObject getCurrentEntity();
}
