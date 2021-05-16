package mod.hey.studios.util;

import java.io.OutputStream;
import java.io.PrintStream;
import mod.agus.jcoderz.lib.FileUtil;

public class SystemLogPrinter 
{

	private static final String PATH =
    FileUtil.getExternalStorageDir().concat("/.sketchware/debug.txt");


	public static void start() {

        //reset
		FileUtil.writeFile(PATH, "");

		PrintStream ps = new PrintStream(new OutputStream() {
			String cache;

			@Override
			public void write(int b) {
		        if (cache == null) cache = "";

		        if (((char) b) == '\n')
                {
					//write each line printed to the specified path
					FileUtil.writeFile(PATH,
                        FileUtil.readFile(PATH) + "\n" + cache);

					cache = "";
				}
                else
                {
					cache += (char) b;
				}
		    }
		});

        System.setOut(ps);
        System.setErr(ps);
        
     }
        
}


        
                

