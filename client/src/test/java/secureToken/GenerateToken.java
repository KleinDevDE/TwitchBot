package secureToken;

public class GenerateToken {
    public static void main(String[] args){
        /*
        SecurityToken: 25 Stellig
        Prüfziffer: 1 Buchstaben, 2 Ziffern

        SecurityToken besteht aus
        - Random String
          - Buchstaben
          - Zahlen

        Prüfziffer berechnet aus
        - Teile der ClientID
          - Casesar-Verschlüsslung X Stellen (Anzahl Charakter der Email Adresse)
        - SecurityToken (Wert der Buchstaben addieren
        -
         */
    }
}
