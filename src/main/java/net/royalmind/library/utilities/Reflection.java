package net.royalmind.library.utilities;

import java.lang.reflect.*;

public class Reflection
{
    public static Object getStaticValue(final String s, final String s2) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        final Field declaredField = Class.forName(s).getDeclaredField(s2);
        declaredField.setAccessible(true);
        return declaredField.get(Class.forName(s));
    }

    public static void setStaticValue(final String s, final String s2, final Object o) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        final Field declaredField = Class.forName(s).getDeclaredField(s2);
        declaredField.setAccessible(true);
        declaredField.set(declaredField.get(Class.forName(s)), o);
    }

    public static Object getInstanceValue(final Object o, final String s) throws NoSuchFieldException, IllegalAccessException {
        final Field declaredField = o.getClass().getDeclaredField(s);
        declaredField.setAccessible(true);
        return declaredField.get(o);
    }

    public static void setInstanceValue(final Object o, final String s, final Object o2) throws NoSuchFieldException, IllegalAccessException {
        final Field declaredField = o.getClass().getDeclaredField(s);
        declaredField.setAccessible(true);
        declaredField.set(o, o2);
    }
}
