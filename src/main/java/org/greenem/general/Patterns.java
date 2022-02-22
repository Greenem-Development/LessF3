package org.greenem.general;

import java.util.regex.Pattern;

public class Patterns {
    public static final Pattern version = Pattern.compile("Minecraft\\s.+\\s\\(.+\\)");
    //public static final Pattern xyz = Pattern.compile("XYZ:\\s[0-z]+");
    public static final Pattern fps1 = Pattern.compile("([0-9]+\\sfps)"); //^
    public static final Pattern fps2 = Pattern.compile("([0-9]+\\/[0-9]+\\sfps)");
    //public static final Pattern fpsPart = Pattern.compile("[0-9]+\\sfps");
    public static final Pattern entitiesPart = Pattern.compile("E:\\s[0-9]+/[0-9]+");
}
