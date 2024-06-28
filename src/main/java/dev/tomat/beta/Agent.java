package dev.tomat.beta;

import dev.tomat.beta.transformers.SharedConstantsRedirectTransformer;
import net.minecraft.SharedConstants;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String args, Instrumentation inst) {
        sharedMain(args, inst);
    }

    public static void agentmain(String args, Instrumentation inst) {
        sharedMain(args, inst);
    }

    private static void sharedMain(String ignoredArgs, Instrumentation inst) {
        SharedConstants.class.getName(); // Load the class
        inst.addTransformer(new SharedConstantsRedirectTransformer());
    }
}
