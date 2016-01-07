package designParser.umlGen.impl;

import java.util.ArrayList;
import java.util.List;

import designParser.model.api.IDesignModel;
import designParser.umlGen.api.IUmlGenerator;
import designParser.umlGen.api.UmlModelVisitor;

public class UmlGenerator implements IUmlGenerator {
    private String modelName;
	private StringBuilder stringBuilder;
	private IDesignModel model;
	private List<UmlModelVisitor> visitorList;

	public UmlGenerator(String modelName, IDesignModel model) {
	    this.modelName = modelName;
		this.model = model;
		stringBuilder = new StringBuilder();
		visitorList = new ArrayList<UmlModelVisitor>();
		visitorList.add(new UmlObjVisitor());
		visitorList.add(new UmlInheritanceVisitor());
		//visitorList.add(new UmlAssociationVisitor());
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
