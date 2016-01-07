package designParser.model.visitor;

import java.util.List;

import designParser.model.api.IDesignModel;
import designParser.model.api.IUmlGenerator;

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
