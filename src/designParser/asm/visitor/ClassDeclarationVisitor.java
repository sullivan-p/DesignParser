package designParser.asm.visitor;

import java.util.ArrayList;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IClass;
import designParser.model.api.ICustomObject;
import designParser.model.api.IEnum;
import designParser.model.api.IInterface;
import designParser.model.api.IDesignModel;

public class ClassDeclarationVisitor extends ModelBuilderClassVisitor {
	private IDesignModel model;

	// The current class, interface, or enum that the visitor is visiting.
	private ICustomObject currentEntity;

	public ClassDeclarationVisitor(int api, IDesignModel model) {
		super(api);
		this.model = model;
	}

	@Override
	public IDesignModel getModel() {
		return model;
	}

	@Override
	public ICustomObject getCurrentEntity() {
		return currentEntity;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

		// Convert names from ASM format to Java fully qualified names.
		name = AsmProcessData.qualifiedToUnqualifiedName(
		        AsmProcessData.convertAsmToJavaName(name));
		String javaName, unqualifiedName;
		for (int i = 0; i < interfaces.length; ++i) {
		    javaName = AsmProcessData.convertAsmToJavaName(interfaces[i]);
		    unqualifiedName = AsmProcessData.qualifiedToUnqualifiedName(javaName);
			interfaces[i] = unqualifiedName;
		}
		if (superName != null) {
            javaName = AsmProcessData.convertAsmToJavaName(superName);
            unqualifiedName = AsmProcessData.qualifiedToUnqualifiedName(javaName);
			superName = unqualifiedName;
		}

		// The models of the interfaces that the current entity extends or
		// implements.
		ArrayList<IInterface> interfaceModels = addInterfacesToModel(interfaces);

		// Determine whether the entity being visited is a class, interface, or
		// enum, and handle the visit appropriately.
		if (AsmProcessData.isInterface(access)) {
			handleInterfaceVisit(name, interfaceModels);
		} else if (AsmProcessData.isEnum(access)) {
			handleEnumVisit(name, interfaceModels);
		} else {
			handleClassVisit(name, !AsmProcessData.isAbstract(access), superName, interfaceModels);
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}

	private void handleClassVisit(String name, boolean isConcrete, String superName,
			ArrayList<IInterface> interfaceModels) {
		IClass classModel;
		if (!model.hasClassModel(name)) {
			model.addNewClassModel(name, isConcrete);
		}
		classModel = model.getClassModel(name);

		if (superName != null) {
			if (!model.hasClassModel(superName)) {
				// Note: This makes the assumption that all superclasses are
				// concrete. This may be overwritten if the class is visited
				// later and more information becomes available.
				model.addNewClassModel(superName, true);
			}
			IClass superClassModel = model.getClassModel(superName);
			classModel.setExtendedClass(superClassModel);
		}
	
		classModel.setInterfaces(interfaceModels);
		classModel.setIsConcrete(isConcrete);
		currentEntity = classModel;
	}

	private void handleInterfaceVisit(String name, ArrayList<IInterface> interfaceModels) {
		IInterface interfaceModel;
		if (!model.hasInterfaceModel(name)) {
			model.addNewInterfaceModel(name);
		}
		interfaceModel = model.getInterfaceModel(name);

		interfaceModel.setExtendedInterfaces(interfaceModels);
		currentEntity = interfaceModel;
	}

	private void handleEnumVisit(String name, ArrayList<IInterface> interfaceModels) {
		IEnum enumModel;
		if (!model.hasEnumModel(name)) {
			model.addNewEnumModel(name);
		}
		enumModel = model.getEnumModel(name);

		enumModel.setInterfaces(interfaceModels);
		currentEntity = enumModel;
	}

	/**
	 * Read the string of interface names, and add a model for each interface to
	 * the IModel if such an interface model does not already exist.
	 * 
	 * @param interfaces
	 *            List of fully qualified names of interfaces
	 * @return A list of the interface models in the IModel that match the given
	 *         interface names
	 */
	private ArrayList<IInterface> addInterfacesToModel(String[] interfaceNames) {
		ArrayList<IInterface> interfaceModels = new ArrayList<IInterface>();
		for (String name : interfaceNames) {
			IInterface interfaceModel;
			if (!model.hasInterfaceModel(name)) {
				model.addNewInterfaceModel(name);
			}
			interfaceModel = model.getInterfaceModel(name);
			interfaceModels.add(interfaceModel);
		}
		return interfaceModels;
	}
}