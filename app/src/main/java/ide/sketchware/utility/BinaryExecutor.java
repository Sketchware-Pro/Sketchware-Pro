package ide.sketchware.utility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BinaryExecutor {

    private final ProcessBuilder mProcess = new ProcessBuilder();
    private final StringWriter mWriter = new StringWriter();

    public void setCommands(ArrayList<String> arrayList) {
        mProcess.command(arrayList);
    }

    public String execute() {
        Process process = null;
        try {
            mProcess.redirectErrorStream(true);
            process = mProcess.start();
            try (Scanner scanner = new Scanner(process.getInputStream())) {
                while (scanner.hasNextLine()) {
                    mWriter.append(scanner.nextLine());
                    mWriter.append(System.lineSeparator());
                }
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace(new PrintWriter(mWriter));
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return mWriter.toString();
    }

    public String getLog() {
        return mWriter.toString();
    }
}