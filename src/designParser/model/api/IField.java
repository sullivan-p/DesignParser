package designParser.model.api;

import designParser.model.impl.AccessLevel;

public interface IField extends IVariable {
    public AccessLevel getAccessLevel();
}
