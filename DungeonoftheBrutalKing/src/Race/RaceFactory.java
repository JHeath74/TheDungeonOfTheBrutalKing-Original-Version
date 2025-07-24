
package Race;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class RaceFactory {
    public static String[] getClassNamesInPackage(String packageName, Class<?> referenceClass) {
        String path = packageName.replace('.', '/');
        List<String> result = new ArrayList<>();

        CodeSource src = referenceClass.getProtectionDomain().getCodeSource();
        if (src != null) {
            URL jar = src.getLocation();
            try (JarInputStream jis = new JarInputStream(jar.openStream())) {
                JarEntry entry;
                while ((entry = jis.getNextJarEntry()) != null) {
                    String name = entry.getName();
                    if (name.startsWith(path) && name.endsWith(".class") && !entry.isDirectory()) {
                        String className = name.substring(path.length() + 1, name.length() - 6); // Remove .class
                        result.add(className);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toArray(new String[0]);
    }
}
