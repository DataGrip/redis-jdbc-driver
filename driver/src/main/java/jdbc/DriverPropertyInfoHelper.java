package jdbc;

import java.sql.DriverPropertyInfo;
import java.util.ArrayList;

public class DriverPropertyInfoHelper {
    public static final String USER = "user";
    public static final String PASSWORD = "password";

    public static DriverPropertyInfo[] getPropertyInfo() {
        ArrayList<DriverPropertyInfo> propInfos = new ArrayList<>();
        addPropInfo(propInfos, USER, "", "Username.", null);
        addPropInfo(propInfos, PASSWORD, "", "Password.", null);
        return propInfos.toArray(new DriverPropertyInfo[0]);
    }

    private static void addPropInfo(final ArrayList<DriverPropertyInfo> propInfos, final String propName,
                                    final String defaultVal, final String description, final String[] choices) {
        DriverPropertyInfo newProp = new DriverPropertyInfo(propName, defaultVal);
        newProp.description = description;
        if (choices != null) {
            newProp.choices = choices;
        }
        propInfos.add(newProp);
    }
}
