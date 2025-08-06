package mod.hey.studios.project;

//6.3.0
public class ProjectTracker {
    /*
     * tracks the current project's sc_id
     * this value might be unreliable in some cases,
     * so use it only if you can't access sc_id using any other way.
     */
    public static String SC_ID;

    public static void setScId(String a) {
        SC_ID = a;

        //tested and working
        /*FileUtil.writeFile(
            new File(
                Environment.getExternalStorageDirectory(),
                ".sketchware/debug.txt"
            ).getAbsolutePath(),
            SC_ID
        );*/
    }
}