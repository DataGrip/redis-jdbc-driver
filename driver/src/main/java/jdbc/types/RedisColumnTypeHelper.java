package jdbc.types;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import static jdbc.Utils.toLowerCase;

public class RedisColumnTypeHelper {
    public static final String OBJECT = "object";
    public static final String STRING = "string";
    public static final String LONG = "long";
    public static final String DOUBLE = "double";
    public static final String BOOLEAN = "boolean";
    public static final String BINARY = "binary";
    public static final String ARRAY = "array";
    public static final String MAP = "map";
    
    private RedisColumnTypeHelper() {
    }
    
    private static final Map<String, Integer> javaTypeMap = new HashMap<>() {{
        put(OBJECT, Types.JAVA_OBJECT);
        put(STRING, Types.VARCHAR);
        put(LONG, Types.BIGINT);
        put(DOUBLE, Types.DOUBLE);
        put(BOOLEAN, Types.BOOLEAN);
        put(BINARY, Types.BINARY);
        put(ARRAY, Types.ARRAY);
        put(MAP, Types.JAVA_OBJECT);
    }};
    
    private static final Map<String, String> typeNameMap = new HashMap<>() {{
        put(OBJECT, "java.lang.Object");
        put(STRING, "java.lang.String");
        put(LONG, "java.lang.Long");
        put(DOUBLE, "java.lang.Double");
        put(BOOLEAN, "java.lang.Boolean");
        put(BINARY, "[B");
        put(ARRAY, "java.util.List");
        put(MAP, "java.util.Map");
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
}
