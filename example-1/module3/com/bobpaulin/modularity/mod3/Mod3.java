package com.bobpaulin.modularity.mod3;

import java.io.File;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleDescriptor;
import java.lang.reflect.Layer;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ServiceLoader;

public class Mod3 {
    
    private static final Path MLIB_DIR = Paths.get("mods");
    
    public static void main(String[] args) throws Exception {
        Layer bootLayer = Layer.boot();

        ModuleFinder finder = ModuleFinder.of(MLIB_DIR);

        Configuration cf = Configuration.resolve(finder,
                bootLayer.configuration(),
                ModuleFinder.empty(),
                "module2");
        cf = cf.bind();
        
     // choose a class loader
        ModuleClassLoader loader = new ModuleClassLoader(cf);

        // reify the configuration as a Layer
        Layer layer = Layer.create(cf, bootLayer, mn -> loader);

        
     // invoke application main method
        Class<?> c = layer.findLoader("module2").loadClass("com.bobpaulin.modularity.mod2.Mod2");
        Mod3.class.getModule().addReads(c.getModule());
        Method mainMethod = c.getMethod("main", String[].class);

        // set TCCL as that is the EE thing to do
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(loader);
            mainMethod.invoke(null, (Object)new String[0]);
        } finally {
            Thread.currentThread().setContextClassLoader(tccl);
        }

    }
}