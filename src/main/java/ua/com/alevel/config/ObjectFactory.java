package ua.com.alevel.config;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ObjectFactory {

    private static ObjectFactory instance;
    private final Reflections scanner;

    private ObjectFactory() {
        scanner = new Reflections("ua.com.alevel");
    }

    public static ObjectFactory getInstance() {
        if (instance == null) {
            instance = new ObjectFactory();
        }
        return instance;
    }

    public <T> T getImplClass(Class<T> aClass) {
        Set<Class<? extends T>> implClasses = scanner.getSubTypesOf(aClass);
        for (Class<? extends T> implClass : implClasses) {
            try {
                if (!implClass.isAnnotationPresent(Deprecated.class)) {
                    return implClass.getDeclaredConstructor().newInstance();
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("class not found");
    }
}
