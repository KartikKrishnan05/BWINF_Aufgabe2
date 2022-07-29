import java.io.BufferedReader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;

public class altemain {
    public static void main(String[] args) {
        String dateiPfad = "C:\\Informatik\\BWinf_40\\src\\Hotelliste1.txt";
        int übrigeTage = 5;
        int maxAmTag = 360;
        int gesamtDistanz = 0;
        int übrigeDistanz = 0;
        int abgelegteStrecke = 0;
        int anzahlHotels = 0;
        int min;
        int max;
        int Zeile = 1;
        int Zeit;
        double Bewertung;
        HOTEL Hotel;
        HOTEL ausgewähltesHotel = null;



        ArrayList<HOTEL> Möglichkeiten = new ArrayList<HOTEL>();
        ArrayList<HOTEL> Hotelliste = new ArrayList<HOTEL>();

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

        for (int a = 0; a < 4; a++) {

            min = (übrigeDistanz / übrigeTage) + abgelegteStrecke;
            //System.out.println("min = " + min);
            max = abgelegteStrecke + maxAmTag;
            //System.out.println("max = " + max);


            for (int b = 0; b < anzahlHotels; b++) {
                if (Hotelliste.get(b).Zeit >= min && Hotelliste.get(b).Zeit <= max) {
                    //System.out.println("Hotel " + (b + 1) + " ist im Bereich der ersten Pause");
                    Möglichkeiten.add(Hotelliste.get(b));
                }
            }

            if (Möglichkeiten.size() == 0){

                for (int b = 0; b < anzahlHotels; b++)
                    if (Hotelliste.get(b).Zeit >= min - 50 && Hotelliste.get(b).Zeit <= max) {
                        //System.out.println("Hotel " + (b + 1) + " ist im Bereich der ersten Pause");
                        Möglichkeiten.add(Hotelliste.get(b));
                    }
            }

            ausgewähltesHotel = Möglichkeiten.stream().max(Comparator.comparing(HOTEL::BewertungAusgeben)).get();
            //System.out.println("Das ausgewählte Hotel ist: " + ausgewähltesHotel.Zeit);

            System.out.println("Übernachtung bei: Zeit: " + ausgewähltesHotel.ZeitAusgeben() + ", Bewertung: " + ausgewähltesHotel.BewertungAusgeben());
            abgelegteStrecke = ausgewähltesHotel.Zeit;
            //System.out.println("abgelegte Strecke = " + abgelegteStrecke);
            übrigeDistanz = gesamtDistanz - ausgewähltesHotel.Zeit;
            //System.out.println("übrige Distanz: " + übrigeDistanz);
            übrigeTage--;
            Möglichkeiten.clear();

        }
    }
}

