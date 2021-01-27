/**
 * @author: Christel van Haren
 * Blok 6, java informatica tentamen
 * 22-1-2021 / 29-1-2021
 */

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

class bestanden {

    public static void main(String[] args) {
        md5checker();
        arraylist();
        SNP_bestanden();
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

            if ((Objects.equals(line_md5, line_cat))) {
                System.out.println("De MD5 hashcodes komen overeen. " +
                        "De bestanden zijn juist overgekomen.");
            } else {
                System.out.println("* De MD5 hashcodes komen niet " +
                        "overeen. De bestanden zijn niet juist " +
                        "overgekomen. *");
            }
        } catch (IOException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws NullPointerException
     */
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
            System.out.println("\n* Het bestand " + bestand + " is " +
                    "incorrect ingelezen. *");
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws NullPointerException
     */
    public static void SNP_bestanden() throws NullPointerException {
        System.out.println("\n* Er worden alleen 23andme " +
                "bestanden van ouders geaccepteerd! *\n");
        ArrayList<String> lijst_ouder1 = new ArrayList<>();
        ArrayList<String> lijst_ouder2 = new ArrayList<>();

        try {
            // Het bestand van ouder 1 inlezen
            System.out.println("Selecteer een bestand voor ouder 1.");
            File ouder1 = new File(Objects.requireNonNull(bestanden_kiezen()));
            Scanner scanner1 = new Scanner(ouder1);
            String data_ouder1 = null;
            String data1 = null;

            while (scanner1.hasNextLine()) {
                data_ouder1 = scanner1.nextLine();
                if (!data_ouder1.startsWith("#")){
                    data1 = Arrays.toString(data_ouder1.split("\t"));
                    lijst_ouder1.add(data1);

                }
            }
//            System.out.println(lijst_ouder1);
            String file_ouder1 = ouder1.getName();
            System.out.println("Het geselecteerde bestand van ouder 1" +
                    " is: " + file_ouder1);

            // Controleren of er ook echt een 23andme bestand is
            // ingevoerd bij ouder 1
            String ouder1_ID = file_ouder1.split("\\.")[1];
            if (ouder1_ID.equals("23andme")) {
                System.out.println("Het bestand van ouder 1 is " +
                        "inderdaad een 23andme bestand.");
            } else {
                System.out.println("* Dit bestand van ouder 1 is geen" +
                        " 23andme bestand. Probeer het opnieuw. *");
            }


            // Het bestand van ouder 2 inlezen
            System.out.println("\nSelecteer een bestand voor ouder 2.");
            File ouder2 = new File(Objects.requireNonNull(bestanden_kiezen()));
            Scanner scanner2 = new Scanner(ouder2);
            String data_ouder2 = null;
            String data2 = null;

            while (scanner2.hasNextLine()) {
                data_ouder2 = scanner2.nextLine();
                if (!data_ouder2.startsWith("#")){
                    data2 = Arrays.toString(data_ouder2.split("\t"));
                    lijst_ouder2.add(data2);
                }
            }
//            System.out.println(lijst_ouder2);
            String file_ouder2 = ouder2.getName();
            System.out.println("Het geselecteerde bestand van ouder 2" +
                    " is: " + file_ouder2);
            
            // Controleren of er ook echt een 23andme bestand is
            // ingevoerd bij ouder 2
            String ouder2_ID = file_ouder2.split("\\.")[1];
            if (ouder2_ID.equals("23andme")) {
                System.out.println("Het bestand van ouder 2 is " +
                        "inderdaad een 23andme bestand.");
            } else {
                System.out.println("* Het bestand van ouder 2 is geen" +
                        " 23andme bestand. Probeer het opnieuw. *");
            }

            // Kijken of het bestand niet vaker wordt gebruikt dan 1
            // keer.
            if (ouder1.equals(ouder2)){
                System.out.println("\n* Er wordt 2 keer hetzelfde " +
                        "23andme bestand gebruikt. Probeer het " +
                        "opnieuw en selecteer 2 verschillende 23andme" +
                        " bestanden. *");
            }
//            else if ((ouder2_ID.equals("23andme")) || ((ouder1_ID.equals("23andme")))) {
//                System.out.println("\nEen van de bestanden is geen " +
//                        "23andme bestand. Probeer het opnieuw.");
//            }
            else {  // Bestanden kunnen nu geanalyseerd worden
                System.out.println("\nDe bestanden worden nu " +
                        "geanalyseerd...");

//                System.out.println("Ouder 1:");
                String nieuwe_rsid1 = null;
                String nieuwe_nt1 = null;
//                String info_ouder1 = null;
                for (String lijst1 : lijst_ouder1) {
//                    System.out.println(lijst1);
                    String rsid1 = lijst1.split(",")[0];
                    String nt_1 = lijst1.split(",")[3];
                    nieuwe_rsid1 = rsid1.replaceAll("\\[", "");
                    nieuwe_nt1 = nt_1.replaceAll("\\]", "");
//                    info_ouder1 = "RSID: " + nieuwe_rsid1 + "\n" +
//                            "Nucleotide die tot ziekte kan " +
//                            "leiden:" + nieuwe_nt1;
//                    System.out.println(info_ouder1);
                }
                
//                System.out.println("Ouder 2:");
                String nieuwe_rsid2 = null;
                String nieuwe_nt2 = null;
//                String info_ouder2 = null;
                for (String lijst2 : lijst_ouder2) {
//                        System.out.println(lijst2);
                    String rsid2 = lijst2.split(",")[0];
                    String nt_2 = lijst2.split(",")[3];
                    nieuwe_rsid2 = rsid2.replaceAll("\\[", "");
                    nieuwe_nt2 = nt_2.replaceAll("\\]", "");
//                    info_ouder2 = "RSID: " + nieuwe_rsid2 + "\n" +
//                            "Nucleotide die tot ziekte kan " +
//                            "leiden:" + nieuwe_nt2;
//                    System.out.println(info_ouder2);
                }

                nieuwe_rsid1.compareTo(nieuwe_rsid2);
                System.out.println("RSID: " + nieuwe_rsid2 + "\t" +
                        "nucleotides: " + nieuwe_nt1 + "&" + nieuwe_nt2);
//                System.out.println(nieuwe_rsid1);

                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @return
     */
    public static String bestanden_kiezen() {
        JFileChooser bestand_kiezen =
                new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int uitgekozen_bestand = bestand_kiezen.showOpenDialog(null);

        if (uitgekozen_bestand == JFileChooser.APPROVE_OPTION) {
            File bestand = bestand_kiezen.getSelectedFile();
            String locatie_bestand = bestand.getAbsolutePath();
            return locatie_bestand;
        } else if (uitgekozen_bestand == JFileChooser.CANCEL_OPTION || uitgekozen_bestand == JFileChooser.ABORT) {
            System.out.println("Het programma is afgebroken, probeer " +
                    "het opnieuw.");
        } else {
            System.out.println("Het bestand kan niet worden " +
                    "geaccepteerd. Probeer het opnieuw.");
            bestanden_kiezen();
        }
        return null;
    }
}

class alle_info implements Comparable<alle_info> {

    private int alleleID;
    private String type;
    private int position;
    private int pathogenicity;
    private int geneID;
    private String alternativeAllele;
    private String disease;
    private String referenceAllele;
    private String chromosome;

    public alle_info(int alleleID, String type, int position,
                          int pathogenicity, int geneID,
                          String alternativeAllele, String disease,
                          String referenceAllele, String chromosome){
        this.alleleID = alleleID;
        this.type = type;
        this.position = position;
        this.pathogenicity = pathogenicity;
        this.geneID = geneID;
        this.alternativeAllele = alternativeAllele;
        this.referenceAllele = referenceAllele;
        this.disease = disease;
        this.chromosome = chromosome;
    }

    public int getAlleleID(){
        return alleleID;
    }

    public void setAlleleID(int alleleID){
        this.alleleID = alleleID;
    }

    public String getType() {
        return type;
    }

    public void setType (String type){
        this.type = type;
    }

    public int getPosition(){
        return position;
    }

    public void setPosition (int position){
        this.position = position;
    }

    public int getPathogenicity(){
        return pathogenicity;
    }

    public void setPathogenicity (int pathogenicity){
        this.pathogenicity = pathogenicity;
    }

    public int getGeneID(){
        return geneID;
    }

    public void setGeneID (int geneID){
        this.geneID = geneID;
    }

    public String getAlternativeAllele(){
        return alternativeAllele;
    }

    public void setAlternativeAllele (String alternativeAllele){
        this.alternativeAllele = alternativeAllele;
    }

    public String getDisease(){
        return disease;
    }

    public void setDisease (String disease){
        this.disease = disease;
    }

    public String getReferenceAllele(){
        return referenceAllele;
    }

    public void setReferenceAllele (String referenceAllele){
        this.referenceAllele = referenceAllele;
    }

    public String getChromosome(){
        return chromosome;
    }

    public void setChromosome (String chromosome){
        this.chromosome = chromosome;
    }

    public int compareTo (alle_info a) {
        return this.getChromosome().compareTo(a.getChromosome());
    }

    public String toString (){
        return "Chromosome: " + chromosome + "Position: " + position + "Type: " + type + "Pathogeniciteit: " + pathogenicity +
                "geneID: " + geneID + "AlternativeAllele: " + alternativeAllele + "disease: " + disease +
                "referenceAllele: " + referenceAllele;
    }

}

