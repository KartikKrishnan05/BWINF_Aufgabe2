
public class HOTEL {

        //Attribute
        public int Zeit;
        public double Bewertung;

        public HOTEL(int Zeit, double Bewertung){
                this.Zeit = Zeit;
                this.Bewertung = Bewertung;
        }

        public int ZeitAusgeben(){
                return Zeit;
        }

        public double BewertungAusgeben(){
                return Bewertung;
        }

}
