package designParser.umlGen.impl;

import java.util.ArrayList;
import java.util.List;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IDesignModel;
import designParser.umlGen.api.IUmlGenerator;
import designParser.umlGen.api.UmlModelVisitor;

public class UmlGenerator implements IUmlGenerator {
    private String modelName;
	private IDesignModel model;	
	private StringBuilder stringBuilder;
	private List<UmlModelVisitor> visitorList;

	public UmlGenerator(String modelName, IDesignModel model, String[] objNamesToModel) {
	    this.modelName = modelName;
		this.model = model;
		stringBuilder = new StringBuilder();
		
		List<String> objs = new ArrayList<String>();
		for (String objName : objNamesToModel) {
		    objs.add(AsmProcessData.qualifiedToUnqualifiedName(objName));
		}
		
		visitorList = new ArrayList<UmlModelVisitor>();
		visitorList.add(new UmlObjVisitor());
		visitorList.add(new UmlInheritanceVisitor(objs));
		visitorList.add(new UmlAssociationVisitor(objs));
	}



	@Override
	public String getUmlMarkup() {
	    
	    stringBuilder.append("digraph " + modelName + "{\n");
	    stringBuilder.append("rankdir=BT;\n");
	    
	    // Make a series of passes over the model to generate the markup.
	    for (UmlModelVisitor v : visitorList) {
	        model.accept(v);
	        stringBuilder.append(v.getUmlMarkup());
	    }
	    
	    stringBuilder.append("}");
		return stringBuilder.toString();
	}
}
