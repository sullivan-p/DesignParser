package designParser.model.api;

import java.util.Collection;

import designParser.model.impl.AccessLevel;

public interface IMethod extends IModelComponent {
    public AccessLevel getAccessLevel();
    public Collection<String> getReferencedTypeNames();
    public String getMethodSignature();
}