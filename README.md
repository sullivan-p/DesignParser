# Design Parser

## Design

The current design of the project can be described in three main sections, the byte code parser, the design model, and the UML generator.

### 1. Byte Code Parser

The project makes use of the ASM library to parse Java byte code.  The `ModelBuilderClassVisitor` extends the ASM library's ClassVisitor.  A `ModelBuilderClassVisitor` can be decorated by `ClassVisitorDecorator` objects. As the `ModelBuilderClassVisitor` traverses ASM's representation of the byte code, it generates a model of the code.

### 2. Design Model

As the byte code is parsed, a model of the design is built.  This model includes objects from a hierarchy of model components, which implement the `IModelComponent` interface.  The type hierarchy of model components is intended to capture various concepts of the Java language and variuos relations between those concepts. Object models, for instance, are composed of method and field models. The hierarchy of object models includes models for classes, interfaces, and enums.  Certain object models can reference other object models to show extension or implementation.  

The design model and model components implement the `ITraversable` interface so that an `IModelVisitor` can be guided through the structure of a design model.

### 3. UML Generator
The Design Parser uses an `IUmlGenerator` to generate a  markup file that describes the UML diagram for the design model.  Specifically, the markup can be used as input to GraphViz to generate an image of the UML diagram.

The UML generator contains a list of `UmlModelVisitor` objects.  Each visitor generates markup for the UML as it traverses the design model. Each visitor generates markup describing different concepts. The `UmlObjVisitor` generates a box for each object that is being modeled, the `UmlInheritanceVisitor` generates arrows between boxes to indicate inheritance and implementation, and the `UmlAssociationVisitor` generates arrows between boxes to indicate association. The UML diagram is built as these visitors are used to make a series of passes over the design model.

## Contributions

Patrick and Navine both made contributions to the code base and made updates to the UML diagram. Navine worked on the tests. Patrick worked on the high level design of the application.
(See the commit log for more specific contributions.)

## Instructions

To use the Design Parser, open the project in Eclipse. Move a package containing the code you want to parse into the project. 

Next, open the DesignParser.java file in the designParser.app package.  Modify the `OBJECT_NAMES` variable so that it includes the qualified names of all objects that you want to model in the UML diagram.

Run DesignParser.java as Java Application.  The application will output the markup for the UML diagram to the console.  Copy, paste, and save this text to file, and pass the file to GraphViz to generate the UML diagram.
