package designParser.asm.visitor;

import java.util.Set;

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
		Set<String> typeNames = AsmProcessData.getTypeNamesFromDescriptor(typeDescriptor);
        AccessLevel accessLevel = AsmProcessData.getAccessLevel(access);
        String fieldSig = getFieldSignature(name, accessLevel, typeDescriptor);
		IField fieldModel = new FieldModel(name, fieldSig, typeNames, accessLevel);
		this.getCurrentEntity().getFields().add(fieldModel);
		
		return toDecorate;
	}

	@Override
	public ModelBuilderClassVisitor getDecoratedVisitor() {
		return decoratedVisitor;
	};
	
    private String getFieldSignature(String name, AccessLevel al, String descriptor) {
        String prettyTypeName = AsmProcessData.getPrettyTypeNames(descriptor);      
        return al.toUmlString() + " " + prettyTypeName + " " + name;
    }
}