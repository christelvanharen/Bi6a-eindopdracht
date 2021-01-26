import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MD5Checksum {

    public static void main(String[] args) throws IOException {
        try {
            String md5sum = "md5sum variant_summary.txt.gz";
            String cat = "cat variant_summary.txt.gz.md5";
            Process proc_md5 = Runtime.getRuntime().exec(md5sum);
            Process proc_cat = Runtime.getRuntime().exec(cat);
            BufferedReader reader_md5 = new BufferedReader(new InputStreamReader(proc_md5.getInputStream()));
            BufferedReader reader_cat = new BufferedReader(new InputStreamReader(proc_cat.getInputStream()));
            String line_md5 = "";
            String line_cat = "";
            while ((line_md5 = reader_md5.readLine()) != null) {
                System.out.println(line_md5);
            }
            while ((line_cat = reader_cat.readLine()) != null) {
                System.out.println(line_cat);
            }
            proc_md5.waitFor();
            proc_cat.waitFor();

        } catch (IOException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }

        ArrayList<String> lijst = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "variant_summary.txt"));
            String line = reader.readLine();
            while (line != null) {
//                System.out.println(line);
                line = reader.readLine();
            }
            System.out.println("Het bestand is ingelezen");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        BufferedReader reader;
//        try {
//            String bestand = "variant_summary.txt";
//            reader = new BufferedReader(new FileReader(bestand));
//            String line = reader.readLine();
//            while (line != null) {
//                System.out.println(line);
//                // read next line
//                line = reader.readLine();
//            }
//            System.out.println("Het bestand is ingelezen.");
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}

