package jdbc.types;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import static jdbc.utils.Utils.toLowerCase;

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


    private static final Map<String, Integer> javaTypeMap = new HashMap<>();

    static {
        javaTypeMap.put(OBJECT, Types.JAVA_OBJECT);
        javaTypeMap.put(STRING, Types.VARCHAR);
        javaTypeMap.put(LONG, Types.BIGINT);
        javaTypeMap.put(DOUBLE, Types.DOUBLE);
        javaTypeMap.put(BOOLEAN, Types.BOOLEAN);
        javaTypeMap.put(BINARY, Types.BINARY);
        javaTypeMap.put(ARRAY, Types.ARRAY);
        javaTypeMap.put(MAP, Types.JAVA_OBJECT);
    }
    
    private static final Map<String, String> typeNameMap = new HashMap<>();

    static {
        typeNameMap.put(OBJECT, "java.lang.Object");
        typeNameMap.put(STRING, "java.lang.String");
        typeNameMap.put(LONG, "java.lang.Long");
        typeNameMap.put(DOUBLE, "java.lang.Double");
        typeNameMap.put(BOOLEAN, "java.lang.Boolean");
        typeNameMap.put(BINARY, "[B");
        typeNameMap.put(ARRAY, "java.util.List");
        typeNameMap.put(MAP, "java.util.Map");
    }


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
