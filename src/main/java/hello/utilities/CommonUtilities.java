package hello.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by yangyu on 14/10/17.
 */
public class CommonUtilities {

    public static void runPythonCode(String path) throws Exception {
        Process p = Runtime.getRuntime().exec("python " + path);

        String s = null;
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

//        ProcessBuilder pb = new ProcessBuilder("python", path);
//        Process p = pb.start();
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        String line;
//        while ((line = in.readLine()) != null) {
//            System.out.println(line);
//        }
    }

}
