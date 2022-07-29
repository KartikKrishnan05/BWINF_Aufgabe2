import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

public class mainBsp5_final {

    public static int übrigeDistanz = 0;
    public static int übrigeTage = 5;
    public static int maxAmTag = 360;
    public static int gesamtDistanz = 0;
    public static int abgelegteStrecke = 0;
    public static int anzahlHotels = 0;
    public static int min;
    public static int max;
    public static int Zeile = 1;
    public static int Zeit;
    public static double Bewertung;
    public static HOTEL Hotel;
    public static HOTEL ausgewähltesHotel = null;

    public static ArrayList<HOTEL> EndgültigeHotelsOp1 = new ArrayList<HOTEL>();
    public static ArrayList<HOTEL> EndgültigeHotelsOp2 = new ArrayList<HOTEL>();
    public static ArrayList<HOTEL> EndgültigeHotels = new ArrayList<HOTEL>();
    public static ArrayList<HOTEL> Hotelliste = new ArrayList<HOTEL>();
    public static ArrayList<HOTEL> Möglichkeiten = new ArrayList<HOTEL>();

    public static void main(String[] args) {
        String dateiPfad = "C:\\Informatik\\BWinf_40\\src\\Hotelliste5.txt";
        double SummeOp1;
        double SummeOp2;
        try {

            //BufferedReader bufferreader = new BufferedReader(new InputStreamReader(new FileInputStream(dateiPfad), Charset.forName("UTF-8")));
            BufferedReader bufferreader = Files.newBufferedReader(Path.of(dateiPfad));
            String line;
            while ((line = bufferreader.readLine()) != null) {
                String[] werte = line.split(" ");
                if (Zeile == 1) {
                    anzahlHotels = Integer.parseInt(werte[0]);
                } else if (Zeile == 2) {
                    gesamtDistanz = Integer.parseInt(werte[0]);
                    übrigeDistanz = gesamtDistanz;
                } else {
                    Zeit = Integer.parseInt(werte[0]);
                    Bewertung = Double.parseDouble(werte[1]);
                    //Hotel = new HOTEL(distance, rating);
                    Hotelliste.add(new HOTEL(Zeit, Bewertung));

                }

                Zeile += 1;
            }
        } catch (Exception ignored) {
            System.out.println("fehler vorhanden beim auslesen der Datei");
        }

            try{
            EndgültigeHotelsOp1 = MoeglicheHotels(0, übrigeDistanz);
             SummeOp1 = EndgültigeHotelsOp1.stream().mapToDouble(HOTEL::BewertungAusgeben).sum();
            //System.out.println(EndgültigeHotelsOp1.size());
            } catch(Exception ignored){
                 SummeOp1 = 0.0;
            }

            try{
                EndgültigeHotelsOp2 = MoeglicheHotels(15, übrigeDistanz);
                 SummeOp2 = EndgültigeHotelsOp2.stream().mapToDouble(HOTEL::BewertungAusgeben).sum();
                //System.out.println(EndgültigeHotelsOp2.size());
            } catch(Exception ignored){
                 SummeOp2 = 0.0;
            }
            if (SummeOp1 > SummeOp2){
                EndgültigeHotels= EndgültigeHotelsOp1;
            }else{
                //System.out.println("Else");
                EndgültigeHotels = EndgültigeHotelsOp2;
            }

        for(int i = 0; i < EndgültigeHotels.size(); i++) {
            System.out.println("Übernachtung bei: Zeit: " + EndgültigeHotels.get(i).Zeit + ", Bewertung: " + EndgültigeHotels.get(i).Bewertung);
        }
    }


     public static ArrayList<HOTEL> MoeglicheHotels(int abweichung, int übrigeDistanz){

        übrigeTage = 5;
        abgelegteStrecke = 0;
        ArrayList<HOTEL> gewählteHotels = new ArrayList<HOTEL>();

        //System.out.println(abweichung);

         for (int a = 0; a < 4; a++) {
             min = ((übrigeDistanz / übrigeTage) + abgelegteStrecke) - abweichung;
             //System.out.println("min = " + min);
             max = abgelegteStrecke + maxAmTag;
             //System.out.println("max = " + max);

             for (int b = 0; b < anzahlHotels; b++) {
                 if (Hotelliste.get(b).Zeit >= min  && Hotelliste.get(b).Zeit <= max) {
                     //System.out.println("Hotel " + (b + 1) + " ist im Bereich der ersten Pause");
                     Möglichkeiten.add(Hotelliste.get(b));
                 }
             }

             if (Möglichkeiten.size() == 0) {

                 for (int b = 0; b < anzahlHotels; b++)
                     if (Hotelliste.get(b).Zeit >= min - 50 && Hotelliste.get(b).Zeit <= max) {
                         //System.out.println("Hotel " + (b + 1) + " ist im Bereich der ersten Pause");
                         Möglichkeiten.add(Hotelliste.get(b));
                     }
             }
             ausgewähltesHotel = Möglichkeiten.stream().max(Comparator.comparing(HOTEL::BewertungAusgeben)).get();
             //System.out.println("Das ausgewählte Hotel ist: " + ausgewähltesHotel.Zeit);


             //System.out.println("Übernachtung bei: Zeit: " + ausgewähltesHotel.ZeitAusgeben() + ", Bewertung: " + ausgewähltesHotel.BewertungAusgeben());
             gewählteHotels.add(ausgewähltesHotel);

             abgelegteStrecke = ausgewähltesHotel.Zeit;
             //System.out.println("abgelegte Strecke = " + abgelegteStrecke);
             übrigeDistanz = gesamtDistanz - ausgewähltesHotel.Zeit;
             //System.out.println("übrige Distanz: " + übrigeDistanz);
             übrigeTage--;
             Möglichkeiten.clear();
         }
         return gewählteHotels;

    }

}
