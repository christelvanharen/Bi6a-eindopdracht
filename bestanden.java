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
                hashmap();
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
    public static void hashmap() throws NullPointerException {
        String bestand = "variant_summary.txt.gz";
        // Een gezipt bestand inlezen en splitten op de tab en er een
        // Hashmap van maken.
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(bestand))));
            HashMap<String, ziekte_variant> variantHashMap =
                    new HashMap<>();
            String line;
            String firstline = reader.readLine();
            firstline = null;

            while ((line = reader.readLine()) != null) {
                String[] plek = line.split("\t");

                variantHashMap.put((plek[18] + plek[31] + plek[32] + plek[33]),
                        new ziekte_variant(Integer.parseInt(plek[0]), plek[1],
                                Integer.parseInt(plek[31]), Integer.parseInt(plek[7]),
                                Integer.parseInt(plek[3]), plek[32],
                                plek[33], plek[13], plek[18]));
//                System.out.println(variantHashMap);
            }
            System.out.println("\nHet bestand " + bestand + " is " +
                    "correct ingelezen.");
            SNP_bestanden(variantHashMap);
            reader.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("\n* Het bestand " + bestand + " is " +
                    "incorrect ingelezen. *");
            e.printStackTrace();
            System.exit(0);
        }
    }


    public static void SNP_bestanden(HashMap<String, ziekte_variant> variantHashMap) throws NullPointerException {
        System.out.println("\n* Er worden alleen 23andme " +
                "bestanden van ouders geaccepteerd! *\n");

        try {
            // Het bestand van ouder 1 inlezen
            System.out.println("Selecteer een bestand voor ouder 1.");
            File ouder1 = new File(Objects.requireNonNull(bestanden_kiezen()));
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
                System.exit(0);
            }

            // Het bestand van ouder 2 inlezen
            System.out.println("\nSelecteer een bestand voor ouder 2.");
            File ouder2 = new File(Objects.requireNonNull(bestanden_kiezen()));
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
                System.exit(0);
            }

            // Als 2 keer hetzelfde bestand wordt opgegeven, dan
            // stopt het programma.
            if (ouder1.equals(ouder2)){
                System.out.println("\n* Er wordt 2 keer hetzelfde " +
                        "23andme bestand gebruikt. Probeer het " +
                        "opnieuw en selecteer 2 verschillende 23andme" +
                        " bestanden. *");
                System.exit(0);
            }
            // Bestanden kunnen nu geanalyseerd worden
            else {
                System.out.println("\nDe bestanden worden nu " +
                        "geanalyseerd...");
                HashMap<String, String[]> hashmap_ouder1 =
                        Objects.requireNonNull(bestanden_ouders(ouder1.getAbsolutePath()));
                HashMap<String, String[]> hashmap_ouder2 =
                        Objects.requireNonNull(bestanden_ouders(ouder2.getAbsolutePath()));

//                System.out.println("Hashmap ouder 1 komt overeen met " +
//                        "ouder 2: " + hashmap_ouder1.keySet().retainAll(hashmap_ouder2.keySet()));
//                System.out.println("Hashmap ouder 2 komt overeen met " +
//                        "ouder 1: " + hashmap_ouder2.keySet().retainAll(hashmap_ouder1.keySet()));

                if (hashmap_ouder1.isEmpty() && hashmap_ouder2.isEmpty()) {
                    System.out.println("Er zijn geen mutaties " +
                            "gevonden. Er is niks aan de hand.");
                    System.exit(0);
                } else {
                    System.out.println("Er zijn mutaties gevonden. Er" +
                            " wordt nu verder onderzoek gedaan...");
                    verder_onderzoek(file_ouder1, hashmap_ouder1, file_ouder2,
                            hashmap_ouder2, variantHashMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verder_onderzoek(String file_ouder1, HashMap<String,
            String[]> hashmap_ouder1, String file_ouder2, HashMap<String,
            String[]> hashmap_ouder2, HashMap<String, ziekte_variant> variantHashMap){
        try {
            FileWriter bestand_schrijven = new FileWriter(file_ouder1 +
                    "_vergeleken_met_" + file_ouder2 + ".txt");
            bestand_schrijven.write("Ouder 1 en ouder 2 worden met elkaar " +
                    "vergeleken:"+ "\n" + "Ouder 1: " + file_ouder1 +
                    "\n" + "Ouder 2: " + file_ouder2 + "\n");


            for (Map.Entry<String, String[]> e : hashmap_ouder1.entrySet()) {
                String[] data_ouder1 = e.getValue();
//                System.out.println(key + "\t" + Arrays.toString(value));

                String ouder_info = data_ouder1[1] + data_ouder1[2] + data_ouder1[3];

                if (variantHashMap.containsKey(ouder_info)){
                    ziekte_variant overeenkomst = variantHashMap.get(ouder_info);
//                    System.out.println(overeenkomst);

                    String[] data_ouder2 = null;
                    data_ouder2 = hashmap_ouder2.get(data_ouder1[0]);
//                    System.out.println(data_ouder2);

                    bestand_schrijven.write(data_ouder1[0] + "\t" + (overeenkomst.getReferenceAllele() + overeenkomst.getAlternativeAllele()) + "\t" +
                            overeenkomst.getChromosome() + "\t" + data_ouder1[3] + "\t" + data_ouder2[3] + "\t" +
                            file_ouder1.split("\\.")[0] + "\t" + file_ouder2.split("\\.")[0] + "\n");
                }
            }

            System.out.println("Het bestand is aangemaakt. " + file_ouder1
                    + " is vergeleken met " + file_ouder2);
            bestand_schrijven.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String[]> bestanden_ouders(String bestand) {
        HashMap<String, String[]> ouders = new HashMap<>();
        String line;
        String[] regel;
        try {
            Scanner scanner = new Scanner(new File(bestand));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (!line.startsWith("#")){
                    regel = line.split("\t");
                    ouders.put(regel[0], regel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ouders;
    }

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

class ziekte_variant implements Comparable<ziekte_variant> {

    private int alleleID;
    private String type;
    private int position;
    private int pathogenicity;
    private int geneID;
    private String alternativeAllele;
    private String disease;
    private String referenceAllele;
    private String chromosome;

    public ziekte_variant(int alleleID, String type, int position,
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

    public int compareTo (ziekte_variant a) {
        return this.getChromosome().compareTo(a.getChromosome());
    }

    public String toString (){
        return "Chromosome: " + chromosome + "\t" + "Position: " + position +
                "\t" + "Type: " + type + "\t" + "Pathogeniciteit: " + pathogenicity
                + "\t" + "geneID: " + geneID + "\t" + "AlternativeAllele: " +
                alternativeAllele + "\t" + "disease: " + disease + "\t" +
                "referenceAllele: " + referenceAllele;
    }
}
