package designParser.umlGen.api;

import designParser.model.impl.TypesVisitor;

public abstract class UmlModelVisitor extends TypesVisitor {
	public abstract String getUmlMarkup();
}
