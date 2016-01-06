package designParser.asm.visitor;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import designParser.model.api.IField;
import designParser.model.impl.FieldModel;

public class ClassFieldVisitor extends ClassVisitorDecorator {
	ModelBuilderClassVisitor decoratedVisitor;

	public ClassFieldVisitor(int api, ModelBuilderClassVisitor decorated) {
		super(api, decorated);
		decoratedVisitor = decorated;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		
		String type = Type.getType(desc).getClassName();
		// TODO: delete the line below
		System.out.println(" " + type + " " + name);
		// TODO: add this field to your internal representation of the current
		// class.
		// What is a good way to know what the current class is?
		
		return toDecorate;
	}

	@Override
	public ModelBuilderClassVisitor getDecoratedVisitor() {
		return decoratedVisitor;
	};
}