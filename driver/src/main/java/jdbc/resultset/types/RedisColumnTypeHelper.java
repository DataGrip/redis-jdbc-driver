package jdbc.resultset.types;

import java.sql.Types;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RedisColumnTypeHelper {
    
    private RedisColumnTypeHelper() {
    }
    
    private static final Map<String, Integer> javaTypeMap = new HashMap<>() {{
        put("null", Types.NULL);
        put("string", Types.VARCHAR);
        put("long", Types.BIGINT);
        put("double", Types.DOUBLE);
        put("boolean", Types.BOOLEAN);
        put("array", Types.ARRAY);
        put("map", Types.JAVA_OBJECT);
        put("binary", Types.BINARY);
    }};
    
    private static final Map<String, String> typeNameMap = new HashMap<>() {{
        put("null", "java.lang.Object");
        put("string", "java.lang.String");
        put("long", "java.lang.Long");
        put("double", "java.lang.Double");
        put("boolean", "java.lang.Boolean");
        put("array", "java.util.List");
        put("map", "java.util.Map");
        put("binary", "[B");
    }};

    private static String toLowerCase(String value) {
        return value.toLowerCase(Locale.ENGLISH);
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
