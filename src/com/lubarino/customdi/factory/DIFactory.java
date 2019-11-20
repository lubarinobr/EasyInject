package com.lubarino.customdi.factory;

import com.lubarino.customdi.annotations.Component;
import com.lubarino.customdi.annotations.Create;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DIFactory {

    private Object instace;
    private Map<Class, Class> container = new HashMap<>();
    private Map<Class, Object> applicationScope = new HashMap<>();

    private DIFactory() {}

    public DIFactory(Object instace) {
        this.instace = instace;
    }

    public void run() throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections("");
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> implementationClass : types) {
            for (Class iFaces: implementationClass.getInterfaces()) {
                container.put(iFaces, implementationClass);
            }
        }
        inject(null);
    }

    private void inject(Object instace) throws InstantiationException, IllegalAccessException {
        Object o = instace == null ? getInstace() : instace;
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field field: declaredFields) {
            if(field.isAnnotationPresent(Create.class)) {
                Object bean = getBean(field.getType());
                field.setAccessible(true);
                field.set(o, bean);
                inject(bean);
            }
        }
    }

    public <T> Object getBean(Class<?> interfaceClass) {
        Class implementationClass = container.get(interfaceClass);
        if(applicationScope.containsKey(interfaceClass)) {
            return applicationScope.get(implementationClass);
        }
        synchronized (applicationScope) {
            try {
                T instanc = (T) implementationClass.newInstance();
                applicationScope.put(implementationClass, instanc);
                return instanc;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Object getInstace() {
        return instace;
    }
}
