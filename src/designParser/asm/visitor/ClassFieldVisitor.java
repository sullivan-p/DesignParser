package designParser.asm.visitor;

import java.util.HashSet;

import org.objectweb.asm.FieldVisitor;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IField;
import designParser.model.impl.AccessLevel;
import designParser.model.impl.FieldModel;

public class ClassFieldVisitor extends ClassVisitorDecorator {
	ModelBuilderClassVisitor decoratedVisitor;

	public ClassFieldVisitor(int api, ModelBuilderClassVisitor decorated) {
		super(api, decorated);
		decoratedVisitor = decorated;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		
		String typeDescriptor = (signature != null) ? signature : desc;
		HashSet<String> typeNames = AsmProcessData.getTypeNamesFromDescriptor(typeDescriptor);
        AccessLevel accessLevel = AsmProcessData.getAccessLevel(access);
		IField fieldModel = new FieldModel(name, typeNames, accessLevel);
		this.getCurrentEntity().getFields().add(fieldModel);
		
		return toDecorate;
	}

	@Override
	public ModelBuilderClassVisitor getDecoratedVisitor() {
		return decoratedVisitor;
	};
}