package dev.tomat.beta.transformers;

import dev.tomat.beta.stub.SharedConstants;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class SharedConstantsRedirectTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println(className);
        if (!className.equals("net/minecraft/SharedConstants")) {
            return classfileBuffer;
        }

        Class stubClass = SharedConstants.class;
        String stubClassName = stubClass.getName().replace('.', '/') + ".class";
        InputStream stubStream = stubClass.getClassLoader().getResourceAsStream(stubClassName);
        try {
            byte[] stubBytes = new byte[stubStream.available()];
            stubStream.read(stubBytes);

            // process with asm to rename the class
            ClassReader reader = new ClassReader(stubBytes);
            ClassNode node = new ClassNode();
            reader.accept(node, 0);

            System.out.println(node.name);
            node.name = "net/minecraft/SharedConstants";
            System.out.println(node.name);

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            node.accept(writer);
            return writer.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to read SharedConstants stub class", e);
        }
    }
}
