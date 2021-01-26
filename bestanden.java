import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

class bestanden {

    public static void main(String[] args) {
        md5checker();
        arraylist();
        SNP_bestanden();
//        info_bestand();
    }

    /**
     * Functie om de MD5 te runnen via de terminal
     */
    public static void md5checker() {
        try {
            String md5sum = "md5sum variant_summary.txt.gz";
            String cat = "cat variant_summary.txt.gz.md5";
            Process proc_md5 = Runtime.getRuntime().exec(md5sum);
            Process proc_cat = Runtime.getRuntime().exec(cat);
            BufferedReader reader_md5 = new BufferedReader(new InputStreamReader(proc_md5.getInputStream()));
            BufferedReader reader_cat = new BufferedReader(new InputStreamReader(proc_cat.getInputStream()));
            String line_md5;
            String line_cat;
            System.out.println("Controle of de bestanden " +
                    "goed zijn overgekomen door middel van MD5:");
            while ((line_md5 = reader_md5.readLine()) != null) {
                System.out.println(line_md5);
            }
            while ((line_cat = reader_cat.readLine()) != null) {
                System.out.println(line_cat);
            }
            proc_md5.waitFor();
            proc_cat.waitFor();

            // Functie om te kijken of de md5 overeenkomen of niet
            // Hier moet nog aan gewerkt worden

//            ArrayList<String> gz = new ArrayList<>();
//            Scanner scannergz = new Scanner(line_cat);
//            while (scannergz.hasNextLine()) {
//                String data_gz = scannergz.nextLine();
//                gz.add(data_gz);
//                System.out.println(gz);
//            }


        } catch (IOException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static void arraylist() throws NullPointerException {
        String bestand = "variant_summary.txt.gz";
        // Een gezipt bestand inlezen en splitten op de tab
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(bestand))));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] plek = line.split("\t");

                // De juiste informatie uit de lijst halen
                String chromosome = plek[18];
                String position = plek[31];
                String alleleID = plek[0];
                String type = plek[1];
                String pathogenicity = plek[7];
                String geneID = plek[3];
                String alternateAllele = plek[32];
                String referenceAllele = plek[33];
                String disease = plek[13];

                // De informatie toevoegen aan een ArrayList
                ArrayList<String> alle_info = new ArrayList<>();

                alle_info.add(chromosome);
                alle_info.add(position);
                alle_info.add(alleleID);
                alle_info.add(type);
                alle_info.add(pathogenicity);
                alle_info.add(geneID);
                alle_info.add(alternateAllele);
                alle_info.add(referenceAllele);
                alle_info.add(disease);

//                System.out.println(alle_info);
            }
            System.out.println("\nHet bestand " + bestand + " is " +
                    "correct ingelezen.");
            reader.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("\nHet bestand " + bestand + " is " +
                    "incorrect ingelezen.");
            e.printStackTrace();
        }
    }
    public static void SNP_bestanden() throws NullPointerException {
        System.out.println("\n* Er worden alleen 23andme " +
                "bestanden van ouders geaccepteerd! *\n");
//        ArrayList<String> lijst_ouder1 = new ArrayList<>();
//        ArrayList<String> lijst_ouder2 = new ArrayList<>();
        try {
            // Het bestand van ouder 1 inlezen
            System.out.println("Selecteer een bestand voor ouder 1.");
            File ouder1 = new File(bestanden_kiezen());
            Scanner scanner1 = new Scanner(ouder1);
            while (scanner1.hasNextLine()) {
                String data_ouder1 = scanner1.nextLine();
//                System.out.println(data_ouder1);

            }
            String file_ouder1 = ouder1.getName();
            System.out.println("Het geselecteerde bestand van ouder 1" +
                    " is: " + file_ouder1);

            // Controleren of er ook echt een 23andme bestand is
            // ingevoerd
            String ouder1_ID = file_ouder1.split("\\.")[1];
            if (ouder1_ID.equals("23andme")) {
                System.out.println("Het bestand van ouder 1 is " +
                        "inderdaad een 23andme bestand.");
            } else {
                System.out.println("Dit bestand van ouder 1 is geen " +
                        "23andme bestand. Probeer het opnieuw.");
            }


            // Het bestand van ouder 2 inlezen
            System.out.println("\nSelecteer een bestand voor ouder 2.");
            File ouder2 = new File(bestanden_kiezen());
            Scanner scanner2 = new Scanner(ouder1);
            while (scanner2.hasNextLine()) {
                String data_ouder2 = scanner2.nextLine();
//                System.out.println(data_ouder2);
            }
            String file_ouder2 = ouder2.getName();
            System.out.println("Het geselecteerde bestand van ouder 2" +
                    " is: " + file_ouder2);

            // Controleren of er ook echt een 23andme bestand is
            // ingevoerd
            String ouder2_ID = file_ouder2.split("\\.")[1];
            if (ouder2_ID.equals("23andme")) {
                System.out.println("Het bestand van ouder 2 is " +
                        "inderdaad een 23andme bestand.");
            } else {
                System.out.println("Het bestand van ouder 2 is geen " +
                        "23andme bestand. Probeer het opnieuw.");
            }

            // Kijken of het bestand niet vaker wordt gebruikt dan 1
            // keer.
            if (ouder1.equals(ouder2)){
                System.out.println("\nEr wordt 2 keer hetzelfde " +
                        "23andme bestand gebruikt. Probeer het " +
                        "opnieuw en selecteer 2 verschillende 23andme" +
                        " bestanden.");
            } else if ((ouder2_ID.equals("23andme")) || ((ouder1_ID.equals("23andme")))) {
                System.out.println("\nEen van de bestanden is geen " +
                        "23andme bestand. Probeer het opnieuw.");
            } else {  // Bestanden kunnen nu geanalyseerd worden
                System.out.println("\nDe bestanden worden nu " +
                        "geanalyseerd...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private static void ziekte_zoeken(String ouder1_id, String ouder2_id,
//                                      ArrayList<String> arrayList_ouder1, ArrayList<String> arrayList_ouder2) {
//
//    }


    public static String bestanden_kiezen() {
        JFileChooser filechooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = filechooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = filechooser.getSelectedFile();
            String filelocation = selectedFile.getAbsolutePath();
            return filelocation;
        } else if (returnValue == JFileChooser.CANCEL_OPTION || returnValue == JFileChooser.ABORT) {
            System.out.println("Het programma is afgebroken, probeer " +
                    "het opnieuw.");
        } else {
            System.out.println("Het bestand kan niet worden " +
                    "geaccepteerd. Probeer het opnieuw.");
            bestanden_kiezen();
        }
        return null;
    }
//    public static void info_bestand() bestanden_arraylist (String pathwway) throws IOException {
//        System.out.println(lijst);
//    }
}

class variant_zoeken extends bestanden{

    private int alleleID;
    private String type;
    private int position;
    private int pathogenicity;
    private int geneID;
    private String alternativeAllele;
    private String disease;
    private String referenceAllele;
    private String chromosome;



}

