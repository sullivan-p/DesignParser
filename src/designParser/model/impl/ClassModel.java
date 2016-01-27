package designParser.model.impl;

public class ClassModel extends AbstractObjectModel {
	protected boolean isConcrete;

	public ClassModel(String name, boolean isConcrete) {
		super(name);
		this.isConcrete = isConcrete;
	}

	public boolean getIsConcrete() {
		return isConcrete;
	}
	
	public void setIsConcrete(boolean isConcrete) {
	    this.isConcrete = isConcrete;
	}
}
