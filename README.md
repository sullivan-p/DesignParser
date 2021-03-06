# Week 9 Update 1

Since last week, I have added a decorator pattern detector and an adapter pattern detector. The overall design has not changed significantly since last week.

![DesignParser UML](/docs/DesignParserUML-w9-1.png)

# Week 8 Update

Since last week, I have not implemented any new pattern detection. I have, however, refactored parts of the design to make the addition of new pattern detectors easier.

Previously, singleton detection was performed in the `UmlObjectVisitor`. Design pattern detection is now performed in a `PatternDetector` visitor which is passed over the design model. After the pattern detector finds a singleton pattern, it replaces the appropriate object model in the design model with a decorated object model. The decoration that wraps the object model indicates that the object is a singleton.

After the pattern detector has been passed over the design model, an additional visitor that is responsible for markup generation can output particular markup if it visits one of the design pattern decorations.

The various steps for generating the design model, detecting patterns, and generating markup are now wrapped together and performed in a `DesignParserFramework`. The DesignParserFramework class uses the Template pattern to specify the algorithm for performing these steps.    

The current state of the design is captured by the UML below. Classes that are part of a decorator pattern are green. The red subclasses of PatternDetector still need to be implemented as part of decorator and adapter pattern detection. 

![DesignParser UML](/docs/DesignParserUML-w8-1.png)

# Week 7 Update 2

Not many changes were made to the design to implement singelton detection. Various models, the `ClassMethodVisitor`, and the `MethodCodeVisitor` were modified so that method models could be marked as static or not. The `UmlObjVisitor` was also modified and is where singleton detection is performed. When the `UmlObjVisitor` is visiting a class model it checks for the following conditions:
- The class is concrete.
- The class has at least one private or protected constructor and no public constructors.
- The class has a public, static method that returns an instance of the class.
If the `UmlObjVisitor` detects a singleton it outputs markup to indicate that a class is a singleton. The UML diagram below shows the state of the design after the implementation of singleton detection.

![DesignParser UML](/docs/DesignParserUML-w7-2.png)

# Week 7 Update 1

Since the last update, I have worked on implementing the sequence diagram generation functionality. The DesignParser can now generate markup for SDEdit, although I have not checked the validity of the output. The UML diagram below shows the design of the application at the time of this writing.

![DesignParser UML](/docs/DesignParserUML-w7-1.png)

# Week 6 Update

Since last week, the design of the model visitors has been modified. The EmptyModelVisitor has been removed. Instead the model visitors use lamda expressions to set visit actions for different types of objects.

We are still working on tasks for Milestone 3, and have modified the way in which method call models are stored in the design model. Otherwise, however, there have not been major design changes.

# Milestone 3 Update

Between Milestones 2 and 3 we refactored a large portion of the codebase. As part of this refactoring, relations between object models are now represented using IRelation objects.

We have also changed the structure of the design model significantly in an attempt to make the project addhere closely to the Principle of Least Knowledge. Previously, in order to create new model components in the design model, the ASM parser was responsible for performing complicated logic to create model components and insert them into the design model. This has been changed, and the IDesign model now implements the Facade pattern and largely hides the inner workings of the model components from clients. 

We have not yet completed Milestone 3, so the design may continue to change as features for Milestone 3. However, this simplification of the design model API already seems to have made it easier to add functionality to the project.  The logic for populating the design model with objects that model method calls is far simpler than it would have been without the change.

# Milestone 2

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
