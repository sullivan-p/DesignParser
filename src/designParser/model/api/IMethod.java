package designParser.model.api;

import designParser.model.impl.AccessLevel;

public interface IMethod extends IModelComponent {
    public String getSignature();
    public AccessLevel getAccessLevel();
}