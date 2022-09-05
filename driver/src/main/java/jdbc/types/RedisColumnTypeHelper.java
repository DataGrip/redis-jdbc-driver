package jdbc.types;

import java.sql.Types;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RedisColumnTypeHelper {
    
    private RedisColumnTypeHelper() {
    }
    
    private static final Map<String, Integer> javaTypeMap = new HashMap<>() {{
        put("map", Types.JAVA_OBJECT);
        put("object", Types.JAVA_OBJECT);
        put("array", Types.ARRAY);
        put("numeric", Types.NUMERIC);
        put("int", Types.INTEGER);
        put("integer", Types.INTEGER);
        put("long", Types.BIGINT);
        put("short", Types.INTEGER);
        put("boolean", Types.BOOLEAN);
        put("string", Types.VARCHAR);
        put("null", Types.NULL);
        put("float", Types.FLOAT);
        put("double", Types.DOUBLE);
    }};
    
    private static final Map<String, String> typeNameMap = new HashMap<>() {{
        put("map", "java.util.Map");
        put("object", "java.util.Map");
        put("array", "java.util.List");
        put("numeric", "java.lang.Long");
        put("int", "java.lang.Integer");
        put("integer", "java.lang.Integer");
        put("long", "java.lang.Long");
        put("short", "java.lang.Short");
        put("boolean", "java.lang.Boolean");
        put("string", "java.lang.String");
        put("null", "java.lang.Object");
        put("float", "java.lang.Float");
        put("double", "java.lang.Double");
    }};

    public static int getJavaType(String typeName) {
        String lower = toLowerCase(typeName);
        if (javaTypeMap.containsKey(lower)) {
            return javaTypeMap.get(lower);
        }
        throw new IllegalArgumentException("Type name is not known: " + lower);
    }

    public static String getClassName(String typeName) {
        String lower = toLowerCase(typeName);
        if (typeNameMap.containsKey(lower)) {
            return typeNameMap.get(lower);
        }
        throw new IllegalArgumentException("Type name is not known: " + lower);
    }

    private static String toLowerCase(String value) {
        return value.toLowerCase(Locale.ENGLISH);
    }
}
