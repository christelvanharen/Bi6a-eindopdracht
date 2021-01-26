import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.HashMap;
import java.util.Objects;

class bestanden {

    public static void main(String[] args) {
        md5checker();
        hashmap();
        SNP_bestanden();
//        info_bestand();
    }


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
            System.out.println("Controle of de bestanden overeenkomen" +
                    " met behulp van MD5:");
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
    }
    public static void hashmap() throws NullPointerException {
        HashMap<String, variant_zoeken> zoekenHashMap = new HashMap();
        try {
            String bestand = "/home/christel/IdeaProjects/Bi6a-eindopdracht/variant_summary.txt";
            BufferedReader reader =
                    new BufferedReader(new FileReader(bestand));
            String line;
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                String[] plek = line.split("\t");
//                zoekenHashMap.put((plek[18]+plek[30]),
//                        new variant_zoeken());
            }
            System.out.println("Het bestand " + bestand + " is " +
                    "correct ingelezen.");

            reader.close();
        } catch (IOException e) {
            System.out.println("Het bestand is " +
                    "niet correct ingelezen.");
            e.printStackTrace();
        }
    }
    public static void SNP_bestanden(){
        System.out.println("\n" + "Selecteer alleen 23andme " +
                "bestanden!");
        try {
            System.out.println("Selecteer een bestand voor ouder 1:");
            File ouder1 =
                    new File(Objects.requireNonNull(fileSelecter()));
            String file_ouder1 = ouder1.getName();
            System.out.println("Het geselecteerde bestand van ouder 1" +
                    " is: " + file_ouder1);
            String ouder1_ID = file_ouder1.split("\\.")[0];

            System.out.println("Selecteer een bestand voor ouder 2:");
            File ouder2 =
                    new File(Objects.requireNonNull(fileSelecter()));
            String file_ouder2 = ouder2.getName();
            System.out.println("Het geselecteerde bestand van ouder 2" +
                    " is: " + file_ouder2);
            String ouder2_ID = file_ouder2.split("\\.")[0];

            if (ouder1.equals(ouder2)){
                System.out.println("Er is 2 keer hetzelfde bestand " +
                        "gebruikt. Probeer het opnieuw en selecteer 2" +
                        " verschillende bestanden.");
            } else {
                System.out.println("De bestanden worden nu " +
                        "geanalyseerd.");
//                ArrayList<String> hashmap_ouder1 =
//                        (ArrayList<String>) Objects.requireNonNull(bestand_hashmap(ouder1.getAbsolutePath()));
//                ArrayList<String> hashmap_ouder2 =
//                        (ArrayList<String>) Objects.requireNonNull(bestand_hashmap(ouder2.getAbsolutePath()));
//                if (hashmap_ouder1.isEmpty() && hashmap_ouder2.isEmpty()){
//                    System.out.println("Er zijn geen overeenkomstige " +
//                            "mutaties gevonden");
//                } else {
//                    System.out.println("Er zijn overeenkomstige " +
//                            "mutaties gevonden");
////                    ziekte_zoeken(ouder1_ID, ouder2_ID,
////                            hashmap_ouder1, hashmap_ouder2);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static void ziekte_zoeken(String ouder1_id, String ouder2_id,
//                                      ArrayList<String> arrayList_ouder1, ArrayList<String> arrayList_ouder2) {
//
//    }

    private static Object bestand_hashmap(String absolutePath) {
        System.out.println("boe");
        return null;
    }

    public static String fileSelecter() {
        JFileChooser filechooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = filechooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = filechooser.getSelectedFile();
            String filelocation = selectedFile.getAbsolutePath();
            return filelocation;

        } else if (returnValue == JFileChooser.CANCEL_OPTION || returnValue == JFileChooser.ABORT) {
            System.out.println("Het programma is afgebroken, probeer " +
                    "het opnieuw.");
            System.exit(0);

        } else {
            System.out.println("Het bestand kan niet worden " +
                    "geaccepteerd. Probeer het opnieuw.");
            fileSelecter();
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

//    public int getAlleleID() {
//
//    }


}

