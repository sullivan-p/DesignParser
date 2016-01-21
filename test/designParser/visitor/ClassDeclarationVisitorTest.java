package designParser.visitor;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import designParser.asm.visitor.ClassDeclarationVisitor;
import designParser.asm.visitor.ClassFieldVisitor;
import designParser.asm.visitor.ClassMethodVisitor;
import designParser.asm.visitor.ModelBuilderClassVisitor;
import designParser.markupGen.api.IUmlGenerator;
import designParser.markupGen.impl.UmlGenerator;
import designParser.model.api.IDesignModel;
import designParser.model.impl.DesignModel;

public class ClassDeclarationVisitorTest {

	private final static String[] OBJECT_NAMES = { "appLauncherSltn.AppLauncherApplication",
			"appLauncherSltn.ApplicationLauncher", "appLauncherSltn.DataFileRunner",
			"appLauncherSltn.DirectoryChangeLogger", "appLauncherSltn.DirectoryEvent",
			"appLauncherSltn.DirectoryMonitorService", "appLauncherSltn.ExecutableFileRunner",
			"appLauncherSltn.IApplicationLauncher", "appLauncherSltn.IDirectoryListener",
			"appLauncherSltn.IDirectoryMonitorService", "appLauncherSltn.ProcessRunner"

	};

	@Test
	public void test() throws IOException {
		String res = "digraph AppLauncher{rankdir=BT;AppLauncherApplication [shape = \"record\",label = \"{AppLauncherApplication|+ String IN_OUT_DIR\\l+ String LOG_FILE\\l|+ AppLauncherApplication()\\l+ void main(String[])\\l}\"];";
		IDesignModel designModel = new DesignModel(OBJECT_NAMES);
		for (String className : OBJECT_NAMES) {
			ClassReader reader = new ClassReader(className);

			ModelBuilderClassVisitor decVisitor;
			decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, designModel);
			ModelBuilderClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor);
			ModelBuilderClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			designModel = methodVisitor.getModel();
		}

		IUmlGenerator umlGenerator = new UmlGenerator("AppLauncher", designModel, OBJECT_NAMES);
		
		System.out.println(umlGenerator.getUmlMarkup());
		assertEquals(res, (umlGenerator.getUmlMarkup().substring(0, res.length()+5).replace("\n", "")));
		//+5 because of the escape (\) characters we had to use
	}
}
