package designParser.markupGen.impl;

import designParser.visitor.impl.TypesVisitor;

public abstract class UmlModelVisitor extends TypesVisitor {
	public abstract String getUmlMarkup();
}
