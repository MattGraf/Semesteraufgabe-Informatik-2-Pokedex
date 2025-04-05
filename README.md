Anleitung zum Pokedex
von Samuel Bimmüller, Marc Förster, Matthias Graferger


1. Ausführung
  Programmstart durch Hauptmethode in der Main-Klasse src/UserInterface/Main.java
2. Anmeldung/Registrierung
  Anmeldung
    - Angabe von Benutzername und Passwort, falls Account schon existent
    - Sprachauswahl durch MenuBar
        o Verfügbare Sprachen: Englisch, Deutsch, Japanisch
    - Registrieren, falls Account nicht existent
    - Beispiel:
      o Benutzername: Max
      o Passwort: 1234
  Registrierung
    - Angabe von Benutzername, Passwort, welches wiederholt werden muss
    - Erfolgreich, wenn Benutzername nicht vorhanden und Passwörter übereinstimmen
3. Übersicht
  Pokemonliste
    - Auswahl durch Mausklick
    - Abwahl durch cmd/strg + Mausklick
    - Durchscrollen durch Pfeiltasten
  Teamanzeige
    - Informationsanzeige von Pokemon aus dem Team des Users durch entsprechende Auswahl
  Suche
    - Suchen nach Pokemon durch exakten Namen oder Nr. ohne führende 0en
  Informationsanzeige
    - Anzeige der Informationen/Bild vom ausgewählten Pokemon
  Bearbeitungsauswahl
    - Bildauswahl durch Navigieren in den Dokumenten
  Teamauswahl (Userspezifisch)
    - Hinzufügen
    - Entfernen
      o Notwendige Auswahl von Pokemon in Pokemonliste
      o Notwendige Auswahl von Pokemon in Teamliste
  Sprachauswahl
    - Auswahl der Sprache
      o Anzeige von Namen und Typ jedes Pokemon in der jeweiligen Sprache sofern Information vorhanden
4. Import von CSV-Dateien
    - Auswahl der Sprache
    - Format
      o {Name}, {type=Water}, {type=Water}, {Evolution}
      o Typ muss in den echten Pokemontypen der jeweilige Sprache enthalten sein (siehe Beispiele im csv-Ordner)
      o Dabei ist der zweite Typ optional
      o Evolution muss ein Integer zwischen 0 und 2 sein (einschließlich)
