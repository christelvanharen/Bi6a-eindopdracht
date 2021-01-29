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

/**
 * In deze class wordt met het gezipte bestand gewerkt en met de SNP
 * bestanden van de ouders
 */
class bestanden {

    /**
     * Hier wordt de functie md5checker aangeroepen
     * @param args ; Geeft een argument mee
     */
    public static void main(String[] args) {
        md5checker();
    }

    /**
     * Deze functie zorgt ervoor dat de MD5 hashcodes van het gezipte
     * bestand wordt aangemaakt en vergeleken wordt met het MD5 bestand.
     * @throws NullPointerException; beide bestanden mogen niet leeg
     * zijn.
     */
    public static void md5checker() throws NullPointerException {
        try {
            // Deze Strings worden gerund in de terminal
            String md5sum = "md5sum variant_summary.txt.gz";
            String cat = "cat variant_summary.txt.gz.md5";
            Process proc_md5 = Runtime.getRuntime().exec(md5sum);
            Process proc_cat = Runtime.getRuntime().exec(cat);
            BufferedReader reader_md5 = new BufferedReader(new InputStreamReader(proc_md5.getInputStream()));
            BufferedReader reader_cat = new BufferedReader(new InputStreamReader(proc_cat.getInputStream()));
            String line_md5;
            String line_cat;

            // De MD5 codes met bestand worden geprint
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

            // Kijken of de MD5 codes gelijk zijn aan elkaar
            // Deze werkt niet optimaal
            if ((Objects.equals(line_md5, line_cat))) {
                System.out.println("De MD5 hashcodes komen overeen. " +
                        "De bestanden zijn juist overgekomen.");
                // Als ze overeenkomen dan gaat het programma door
                hashmap();
            } else {
                System.out.println("* De MD5 hashcodes komen niet " +
                        "overeen. De bestanden zijn niet juist " +
                        "overgekomen. *");
                // Als ze niet overeenkomen, dan stopt het programma
                System.exit(0);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Een gezipt bestand inlezen en splitten op de tab en er een
     * HashMap van maken.
     * @throws NullPointerException; Het bestand mag niet leeg zijn.
     */
    public static void hashmap() throws NullPointerException {
        // Het gezipte bestand
        String bestand = "variant_summary.txt.gz";

        try {
            // Het gezipte bestand inlezen
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(bestand))));
            HashMap<String, ziekte_variant> variantHashMap = new HashMap<>();

            // Het bestand per regel lezen en splitten op de tab
            String regel;
            String line = reader.readLine();
            line = null;

            while ((regel = reader.readLine()) != null) {
                String[] plek = regel.split("\t");

                // De gevonden waardes toevoegen aan de HashMap
                variantHashMap.put((plek[18] + plek[31] + plek[32] + plek[33]),
                        new ziekte_variant(Integer.parseInt(plek[0]), plek[1],
                                Integer.parseInt(plek[31]),
                                Integer.parseInt(plek[7]),
                                Integer.parseInt(plek[3]), plek[33],
                                plek[13], plek[32], plek[18]));
//                System.out.println(variantHashMap);
            }
            // Als het bestand correct is ingelezen gaat hij verder
            // met het programma
            System.out.println("\n" + "Het bestand " + bestand + " is" +
                    " correct ingelezen.");
            SNP_bestanden(variantHashMap);
            reader.close();
        } catch (IOException | NumberFormatException e) {
            // Als het bestand niet correct is ingelezen, dan wordt
            // de error weergegeven en stopt het programma
            System.out.println("\n" + "* Het bestand " + bestand + " " +
                    "is incorrect ingelezen. *");
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * De bestanden van de ouders worden geopend en gecontroleerd
     * @param variantHashMap; de HashMap met de data van het
     *                      ingelezen bestand
     * @throws NullPointerException; de bestanden van de ouders mogen
     * niet leeg zijn
     */
    public static void SNP_bestanden(HashMap<String, ziekte_variant> variantHashMap) throws NullPointerException {
        System.out.println("\n" + "* Er worden alleen 23andme " +
                "bestanden van ouders geaccepteerd! *" + "\n");

        try {
            // Het bestand van ouder 1 inlezen
            System.out.println("Selecteer een bestand voor ouder 1.");
            File ouder1 = new File(Objects.requireNonNull(bestanden_kiezen()));
            // De naam van ouder 1
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
                // Als het geen 23andme bestand is dan begint het
                // programma opnieuw
                System.out.println("* Dit bestand van ouder 1 is geen" +
                        " 23andme bestand. Probeer het opnieuw. *");
                SNP_bestanden(variantHashMap);
            }

            // Het bestand van ouder 2 inlezen
            System.out.println("\n" + "Selecteer een bestand voor " +
                    "ouder 2.");
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
                // Als het geen 23andme bestand is dan begint het
                // programma opnieuw
                System.out.println("* Het bestand van ouder 2 is geen" +
                        " 23andme bestand. Probeer het opnieuw. *");
                SNP_bestanden(variantHashMap);
            }

            // Als 2 keer hetzelfde bestand wordt opgegeven, dan
            // begint het programma opnieuw
            if (file_ouder1.equals(file_ouder2)){
                System.out.println("\n" + "* Er wordt 2 keer " +
                        "hetzelfde 23andme bestand gebruikt. Probeer het " +
                        "opnieuw en selecteer 2 verschillende 23andme" +
                        " bestanden. *");
                SNP_bestanden(variantHashMap);
            }

            // Bestanden kunnen nu geanalyseerd worden
            else {
                System.out.println("\n" + "De bestanden worden nu " +
                        "geanalyseerd...");
                // Van de bestanden van de ouders worden HashMaps
                // gemaakt, voor makkelijkere vergelijking van
                // eventuele mutaties.
                HashMap<String, String[]> hashmap_ouder1 =
                        Objects.requireNonNull(bestanden_ouders(ouder1.getAbsolutePath()));
                HashMap<String, String[]> hashmap_ouder2 =
                        Objects.requireNonNull(bestanden_ouders(ouder2.getAbsolutePath()));

                // Als de bestanden niet overeenkomen dan is er niks
                // aan de hand en stopt het programma
                if (hashmap_ouder1.isEmpty() && hashmap_ouder2.isEmpty()) {
                    System.out.println("Er zijn geen mutaties " +
                            "gevonden. Er is niks aan de hand.");
                    System.exit(0);
                } else {
                    // Hier wordt er wel wat gevonden en wordt er
                    // verder onderzoek gedaan en het programma gaat
                    // verder
                    System.out.println("Er zijn mutaties gevonden. Er" +
                            " wordt nu verder onderzoek gedaan...");
                    verder_onderzoek(file_ouder1, hashmap_ouder1, file_ouder2,
                            hashmap_ouder2, variantHashMap);
                }
            }
        } catch (IOError | Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Er wordt een tekstbestand aangemaakt met informatie over de
     * mutaties die een eventueel kind kan krijgen
     * @param file_ouder1; De naam van ouder 1
     * @param hashmap_ouder1; De informatie van ouder 1 in een HashMap
     * @param file_ouder2; De naam van ouder 2
     * @param hashmap_ouder2; De informatie van ouder 2 in een HashMap
     * @param variantHashMap; De informatie van het gezipte bestand
     */
    public static void verder_onderzoek(String file_ouder1, HashMap<String,
            String[]> hashmap_ouder1, String file_ouder2, HashMap<String,
            String[]> hashmap_ouder2, HashMap<String, ziekte_variant> variantHashMap){
        try {
            // Bestand aanmaken met als titel de naam van de ouders
            FileWriter bestand_schrijven = new FileWriter(file_ouder1 +
                    "_vergeleken_met_" + file_ouder2 + ".txt");
            bestand_schrijven.write("# Ouder 1: " + file_ouder1 +
                    "\n" + "# Ouder 2: " + file_ouder2 + "\n");
            // De kopjes van de gegeven informatie
            bestand_schrijven.write("# RSID" + "\t" + "NT combinatie" + "\t" + "Chromosoom"
                    + "\t" + "NT ouder 1" + "\t" + "NT ouder 2" + "\t" + "ID ouder1 "
                    + "\t" + "ID ouder 2" + "\n");

            // De informatie uit de HashMap van ouder 1 halen
            for (Map.Entry<String, String[]> e : hashmap_ouder1.entrySet()) {
                String[] data_ouder1 = e.getValue();
//                System.out.println(key + "\t" + Arrays.toString(value));
                String ouder_info = data_ouder1[1] + data_ouder1[2] + data_ouder1[3];

                // Controleren of de data van ouder overeenkomt met
                // de HashMap van het gezipte bestand
                if (variantHashMap.containsKey(ouder_info)) {
                    ziekte_variant overeenkomst = variantHashMap.get(ouder_info);
//                    System.out.println(overeenkomst);

                    // De informatie van ouder 2
                    String[] data_ouder2 = e.getValue();
                    String ouder_info2 =
                            data_ouder2[1] + data_ouder2[2] + data_ouder2[3];

                    // Controleren of de data van ouder 2 overeenkomt
                    // met de HashMap van het gezipte bestand
                    // Dit klopt niet helemaal, want het zou ook
                    // moeten vergeleken worden met de data van ouder 1
                    if (variantHashMap.containsKey(ouder_info2)) {
                        ziekte_variant overeenkomst2 =
                                variantHashMap.get(hashmap_ouder2);
//                        System.out.println(overeenkomst2);

                    bestand_schrijven.write(data_ouder1[0] + "\t" + (overeenkomst.getReferenceAllele() + overeenkomst.getAlternativeAllele()) + "\t" +
                            overeenkomst.getChromosome() + "\t" + data_ouder1[3] + "\t" + data_ouder2[3] + "\t" +
                            file_ouder1.split("\\.")[0] + "\t" + file_ouder2.split("\\.")[0] + "\n");

                    } else {
                        // Als het niet lukt dan wordt er in het
                        // bestand geschreven dat het niet gelukt is
                        // en stopt het programma
                        System.out.println("Het is niet gelukt om het" +
                                " bestand met de juiste data aan te " +
                                "maken.");
                        bestand_schrijven.write("Het is niet gelukt " +
                                "om de juiste data te verwerken.");
                        System.exit(0);
                    }
                }
            }
            // Er kan niet meer in het bestand geschreven worden en
            // laat hierna weten dat het bestand klaar is
            bestand_schrijven.close();
            System.out.println("Het bestand is aangemaakt. " + file_ouder1
                    + " is vergeleken met " + file_ouder2);

        } catch (IOException | IOError e) {
            e.printStackTrace();
        }
    }

    /**
     * De bestanden van de ouders worden gescand, de hashtags worden
     * eruit gehaald en er wordt gesplitst op de tab
     * @param bestand; Het opgegeven bestand van de ouders
     * @return ouders; De Hashmap van de ouders met deze data
     */
    public static HashMap<String, String[]> bestanden_ouders(String bestand) {
        // Hashmap voor de ouders wordt aangemaakt
        HashMap<String, String[]> ouders = new HashMap<>();
        String line;
        String[] regel;
        try {
            // Het bestand wordt per regel gescand
            Scanner scanner = new Scanner(new File(bestand));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                // Als de regel niet begint met een hashtag dan wordt
                // deze regel gesplit op de tab en toegevoegd aan de
                // ouders HashMap
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

    /**
     * Hier wordt het bestand van ouders uitgekozen, door middel van
     * JFileChooser
     * @return null;
     * @throws IOException; Er is iets fouts gegaan met het bestand
     * inlezen
     * @throws IOError; Er heeft zich ergens een error voorgedaan
     */
    public static String bestanden_kiezen() throws IOException, IOError {
        JFileChooser bestand_kiezen =
                new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int uitgekozen_bestand = bestand_kiezen.showOpenDialog(null);

        if (uitgekozen_bestand == JFileChooser.APPROVE_OPTION) {
            File bestand = bestand_kiezen.getSelectedFile();
            String locatie_bestand = bestand.getAbsolutePath();
            return locatie_bestand;
        } else {
            System.out.println("\n" + "* Het bestand kon niet worden " +
                    "geaccepteerd. Probeer het opnieuw. *");
            bestanden_kiezen();
    }
        return null;
    }
}

/**
 * Deze class zorgt ervoor dat de juiste informatie in ziekte_variant
 * komt te staan
 */
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

    /**
     * De juiste onderdelen worden op zn plek gezet in de
     * ziekte_variant, hoort bij de HashMap van het gezipte bestand
     * @param alleleID; Het ID van het allel
     * @param type; Wat voor een type mutatie het is
     * @param position; Op welke plek deze mutatie voorkomt
     * @param pathogenicity; Hoe schadelijk deze mutatie kan zijn
     * @param geneID; Het ID van het gen waar het op zit
     * @param alternativeAllele; Op welk alternatief allel het zit
     * @param referenceAllele; Op welk referentie allel het zit
     * @param disease; Welke ziekte de mutatie kan veroorzaken
     * @param chromosome; Op welk chromosoom deze mutatie zit
     */
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

    /**
     * Returned alle informatie
     * @return ; Alle informatie
     * */
    public String toString (){
        return "Chromosome: " + chromosome + "\t" + "Position: " + position +
                "\t" + "Type: " + type + "\t" + "Pathogeniciteit: " + pathogenicity
                + "\t" + "geneID: " + geneID + "\t" + "AlternativeAllele: " +
                alternativeAllele + "\t" + "referenceAllele: " + referenceAllele
                + "\t" + "disease: " + disease;
    }
}
