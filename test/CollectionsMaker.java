import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import designParser.asm.visitor.ClassDeclarationVisitor;
import designParser.asm.visitor.ClassFieldVisitor;
import designParser.asm.visitor.ClassMethodVisitor;
import designParser.asm.visitor.ModelBuilderClassVisitor;

public class CollectionsMaker {

	// public static void main(String[] args) throws IOException {
	// ClassReader reader = new
	// ClassReader("java.util.Collections.shuffle");
	// ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,
	// null);
	// ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5,
	// (ModelBuilderClassVisitor) decVisitor);
	// ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5,
	// (ModelBuilderClassVisitor) fieldVisitor);
	// // TODO: ... More visitors / decorators
	// reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
	// }

	public static void main(String[] argv) throws Exception {
		ArrayList<String> obj = new ArrayList<String>();
		obj.add("A");
		obj.add("E");
		obj.add("I");
		obj.add("O");
		obj.add("U");

		Collections.shuffle(obj);
		System.out.println(obj);
		// Collections.shuffle(obj);
	}
}