package designParser.model.impl;

import java.util.List;

import designParser.model.api.IField;
import designParser.model.api.IMethod;

public class ClassModel extends ObjectModel {
	private boolean isConcrete;

	public ClassModel(String name, boolean isConcrete) {
		super(name);
		this.isConcrete = isConcrete;
	}

	public ClassModel(String name, List<IField> fields, List<IMethod> methods, boolean isConcrete) {
		super(name, fields, methods);
        this.isConcrete = isConcrete;
	}

	public boolean getIsConcrete() {
		return isConcrete;
	}

	public void setIsConcrete(boolean isConcrete) {
		this.isConcrete = isConcrete;
	}
}
