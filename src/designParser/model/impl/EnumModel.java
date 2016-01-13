package designParser.model.impl;

import java.util.List;

import designParser.model.api.IField;
import designParser.model.api.IMethod;

public class EnumModel extends ObjectModel {

	public EnumModel(String name) {
		super(name);
	}
	
	public EnumModel(String name, List<IField> fields, List<IMethod> methods) {
        super(name, fields, methods);
	}
}
