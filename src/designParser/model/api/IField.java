package designParser.model.api;

import designParser.model.impl.AccessLevel;

public interface IField extends IModelComponent {
    public String getTypeName();
    public String getSignature();
    public AccessLevel getAccessLevel();
}
