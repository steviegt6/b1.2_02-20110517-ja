package dev.tomat.beta;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String args, Instrumentation inst) {
        sharedMain(args, inst);
    }

    public static void agentmain(String args, Instrumentation inst) {
        sharedMain(args, inst);
    }

    private static void sharedMain(String ignoredArgs, Instrumentation ignoredInstrumentation) {
        net.minecraft.SharedConstants.class.getName();
    }
}
