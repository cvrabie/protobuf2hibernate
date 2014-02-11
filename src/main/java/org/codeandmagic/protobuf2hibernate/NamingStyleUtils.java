package org.codeandmagic.protobuf2hibernate;

/**
 * <p>Providing variable naming conversions.</p>
 * <p>Styles can be found at <a href="https://developers.google.com/protocol-buffers/docs/style">Protobuf Guide</a>.</p>
 */
public class NamingStyleUtils {
    /**
     * <p>Translating java style name into cpp style name.</p>
     *
     * @param javaName java style name
     * @return cpp style name
     */
    public static String java2cpp(String javaName) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < javaName.length(); ++i) {
            char c = javaName.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('_');
                c = Character.toLowerCase(c);
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
