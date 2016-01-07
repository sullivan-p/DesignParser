package designParser.model.impl;

import java.util.List;

import designParser.model.api.IDesignModel;
import designParser.model.api.IUmlGenerator;
import designParser.model.visitor.UmlAssociationVisitor;
import designParser.model.visitor.UmlInheritanceVisitor;
import designParser.model.visitor.UmlModelVisitor;
import designParser.model.visitor.UmlObjVisitor;

public class UmlGenerator implements IUmlGenerator {
	private StringBuilder stringBuilder;
	private IDesignModel model;
	private List<UmlModelVisitor> visitorList;

	UmlGenerator(StringBuilder stringBuilder, IDesignModel model) {
		this.stringBuilder = stringBuilder;
		this.model = model;
		visitorList.add(new UmlObjVisitor());
		visitorList.add(new UmlInheritanceVisitor());
		visitorList.add(new UmlAssociationVisitor());
	}

	@Override
	public String getUmlMarkup(IDesignModel model) {
		// TODO Auto-generated method stub
		return null;
	}

}
