package designParser.umlGen.api;

import designParser.model.impl.EmptyModelVisitor;

public abstract class UmlModelVisitor extends EmptyModelVisitor {
	public abstract String getUmlMarkup();
}
