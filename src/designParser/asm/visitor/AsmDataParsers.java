package designParser.asm.visitor;

public class AsmDataParsers {
	boolean isEnum(int opcode) {
		return false;
	}

	boolean isAbstract(int opcode) {
		return false;
	}

	String convertAsmToJavaName(String asmName) {
		return asmName;
	}

	String[] extractClassNames(String signature) {
		return null;
	}
}
