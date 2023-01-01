package org.lmrl.utils;

public class JsonUtils {
    public static String unescapeString(String s) {
        final StringBuilder b = new StringBuilder();
        final int n = s.length();
        for (int i = 0; i < n; ) {
            final char c = s.charAt(i++);
            if (c == '\\' && i < n) {
                final char e = s.charAt(i++);
                switch (e) {
                    case 'u' -> {
                        if (i < n - 3) {
                            try {
                                b.append((char) Integer.parseInt(
                                        "" + s.charAt(i++) + s.charAt(i++) + s.charAt(i++) + s.charAt(i++), 16));
                                continue;
                            } catch (NumberFormatException nfe) {
                                i -= 4;
                            }
                        }
                        b.append("\\u");
                    }
                    case '\\' -> b.append('\\');
                    case '\'' -> b.append('\'');
                    case '\"' -> b.append('"');
                    case 'r' -> b.append('\r');
                    case 'f' -> b.append('\f');
                    case 't' -> b.append('\t');
                    case 'n' -> b.append('\n');
                    case 'b' -> b.append('\b');
                    default -> b.append(c).append(e);
                }
            } else {
                b.append(c);
            }
        }
        return b.toString();
    }

    public static String escapeString(String str) {
        StringBuilder sb = new StringBuilder("\"");
        for (char c : str.toCharArray())
            sb.append(switch (c) {
                case '\\', '"', '/' -> "\\" + c;
                case '\b' -> "\\b";
                case '\t' -> "\\t";
                case '\n' -> "\\n";
                case '\f' -> "\\f";
                case '\r' -> "\\r";
                default -> c < ' ' ? String.format("\\u%04x", c) : c;
            });
        return sb.append('"').toString();
    }
}
