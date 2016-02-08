package designParser.manual.test;

import java.io.IOException;

import designParser.framework.api.DesignParserFramework;
import designParser.framework.impl.DesignParserUmlGenerator;

public class DesignParserUmlTest {
    public final static String CONFIG = 
        "designParser.framework.impl.DesignParserUmlGenerator " +
        "designParser.framework.api.DesignParserFramework " +
        "designParser.asm.visitor.ClassFieldVisitor " +
        "designParser.asm.visitor.ClassMethodVisitor " +
        "designParser.asm.visitor.MethodCodeVisitor " +
        "designParser.asm.visitor.ClassVisitorDecorator " +
        "designParser.asm.visitor.ModelBuilderClassVisitor " +
        "designParser.asm.visitor.ClassDeclarationVisitor " +
        "designParser.asm.util.AsmProcessData " +
        "designParser.visitor.impl.TypesVisitor " +
        "designParser.visitor.api.IVisitor " +
        "designParser.visitor.api.IModelVisitor " +
        "designParser.markupGen.impl.UmlModelVisitor " +
        "designParser.markupGen.impl.SdModelVisitor " +
        "designParser.markupGen.impl.UmlObjVisitor " +
        "designParser.markupGen.impl.UmlHierarchyVisitor " +
        "designParser.markupGen.impl.UmlDependencyVisitor " +
        "designParser.markupGen.impl.SdMessageVisitor " +
        "designParser.markupGen.impl.SdGenerator " +
        "designParser.markupGen.impl.SdObject " +
        "designParser.markupGen.impl.UmlGenerator " +
        "designParser.markupGen.impl.SdObjectVisitor " +
        "designParser.markupGen.api.MarkupGenerator " +
        "designParser.markupGen.util.UmlProcessString " +
        "designParser.markupGen.util.UmlArrowMarkup " +
        "designParser.app.DesignParser " +
        "designParser.model.impl.MethodModel " +
        "designParser.model.impl.AdapterModel " +
        "designParser.model.impl.PrimitiveCharDataType " +
        "designParser.model.impl.AdapteeModel " +
        "designParser.model.impl.ObjectModelDecorator " +
        "designParser.model.impl.PrimitiveLongDataType " +
        "designParser.model.impl.DecoratesRelation " +
        "designParser.model.impl.AbstractObjectRelation " +
        "designParser.model.impl.ExtendsRelation " +
        "designParser.model.impl.ReferencesRelation " +
        "designParser.model.impl.PrimitiveByteDataType " +
        "designParser.model.impl.DesignModel " +
        "designParser.model.impl.AssociatesWithRelation " +
        "designParser.model.impl.PrimitiveBooleanDataType " +
        "designParser.model.impl.ImplementsRelation " +
        "designParser.model.impl.PrimitiveIntDataType " +
        "designParser.model.impl.EnumModel " +
        "designParser.model.impl.FieldModel " +
        "designParser.model.impl.DecoratorComponentModel " +
        "designParser.model.impl.PrimitiveDoubleDataType " +
        "designParser.model.impl.PrimitiveFloatDataType " +
        "designParser.model.impl.AbstractHierarchyRelation " +
        "designParser.model.impl.InterfaceModel " +
        "designParser.model.impl.ClassModel " +
        "designParser.model.impl.AccessLevel " +
        "designParser.model.impl.AdaptsRelation " +
        "designParser.model.impl.AdapterTargetModel " +
        "designParser.model.impl.DecoratorModel " +
        "designParser.model.impl.PrimitiveShortDataType " +
        "designParser.model.impl.SingletonModel " +
        "designParser.model.impl.AbstractDependencyRelation " +
        "designParser.model.impl.AbstractObjectModel " +
        "designParser.model.impl.ArrayModel " +
        "designParser.model.impl.DepRltnDecorator " +
        "designParser.model.api.IObject " +
        "designParser.model.api.IField " +
        "designParser.model.api.PrimitiveDataType " +
        "designParser.model.api.IDataType " +
        "designParser.model.api.IObjectRelation " +
        "designParser.model.api.IMethod " +
        "designParser.model.api.ITraversable " +
        "designParser.model.api.IDesignModel " +
        "designParser.model.api.IDependencyRelation " +
        "designParser.model.api.IModelComponent " +
        "designParser.patternDet.impl.SingletonDetector " +
        "designParser.patternDet.impl.SubclassDecoratorDetector " +
        "designParser.patternDet.impl.AdapterDetector " +
        "designParser.patternDet.impl.DecoratorDetector " +
        "designParser.patternDet.api.PatternDetector ";
    
    public static void main(String[] args) throws IOException {
        DesignParserFramework dp = new DesignParserUmlGenerator();
        String markup = dp.process(CONFIG);
        System.out.println(markup);
    }
}
