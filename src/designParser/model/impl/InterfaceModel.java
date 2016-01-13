package designParser.model.impl;

import java.util.List;

import designParser.model.api.IField;
import designParser.model.api.IMethod;

public class InterfaceModel extends ObjectModel {

	public InterfaceModel(String name) {
	    super(name);
	}

	public InterfaceModel(String name, List<IField> fields, List<IMethod> methods) {
        super(name, fields, methods);
	}
}
