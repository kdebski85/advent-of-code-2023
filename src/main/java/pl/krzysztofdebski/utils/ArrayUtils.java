package pl.krzysztofdebski.utils;

public class ArrayUtils {

    public static String toString(final char[][] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char[] chars : array) {
            for (char aChar : chars) {
                stringBuilder.append(aChar);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static String toString(final boolean[][] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (boolean[] chars : array) {
            for (boolean aChar : chars) {
                stringBuilder.append(aChar ? '1' : '0');
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
