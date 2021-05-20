package mod.hey.studios.code;

public class ResHelper {

    public static boolean isInASD = false;

    public static int select_all() {
        return isInASD ? 0x7F0704B2 : 0x7f070024;
    }

    public static int cut() {
        return isInASD ? 0x7F0704B0 : 0x7f070021;
    }

    public static int copy() {
        return isInASD ? 0x7F0704B1 : 0x7f07021a;
    }

    public static int paste() {
        return isInASD ? 0x7F07021B : 0x7f07021c;
    }
}