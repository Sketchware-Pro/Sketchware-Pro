package mod.hey.studios.code;

public class ResHelper {
    public static boolean isInASD = false;

    public static int select_all() {
        return isInASD ? 2131166386 : 2131165220;
    }

    public static int cut() {
        return isInASD ? 2131166384 : 2131165217;
    }

    public static int copy() {
        return isInASD ? 2131166385 : 2131165722;
    }

    public static int paste() {
        return isInASD ? 2131165723 : 2131165724;
    }
}
