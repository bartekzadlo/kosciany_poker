# Kościany Poker - Dokumentacja projektu

## Opis ogólny
Kościany Poker to gra hazardowa dla dwóch graczy, w której celem jest uzyskanie jak najlepszego układu kości. Projekt został zaimplementowany w języku Java i oferuje zarówno interfejs konsolowy, jak i graficzny (GUI).

## Funkcjonalności
- Gra dla dwóch graczy (ludzi)
- System oceny układów kości (od najwyższej do najniższej):
  - Poker (pięć kości o tej samej liczbie oczek)
  - Kareta (cztery kości o tej samej liczbie oczek)
  - Full ( jedna para i trójka)
  - Duży strit (kości pokazujące wartości od 2 do 6, po kolei)
  - Mały strit (kości pokazujące wartości od 1 do 5, po kolei)
  - Trójka (trzy kości o tej samej liczbie oczek)
  - Dwie pary (dwie pary kości, o tej samej liczbie oczek)
  - Para (dwie kości o tej samej liczbie oczek)
  - Najwyższa kość (pięć nie tworzących żadnego układu oczek)
- System zakładów i puli nagród
- Statystyki graczy (stan konta)

## Struktura projektu

### Klasy główne
1. **Main** - Punkt wejścia do programu, menu główne
2. **Game** - Logika gry dla trybu dwóch graczy
3. **PokerGUI_Stol_Styled** - Interfejs graficzny gry

### Klasy graczy
1. **Player** - Interfejs definiujący zachowanie gracza
2. **AbstractPlayer** - Klasa abstrakcyjna implementująca podstawowe funkcje gracza
3. **HumanPlayer** - Implementacja gracza ludzkiego

### Klasy pomocnicze
1. **FirstThrow** - Obsługa pierwszego rzutu kośćmi
2. **SecondThrow** - Obsługa drugiego rzutu (przerzutu)
3. **ScoreEvaluator** - System oceny i porównywania układów kości

## Mechanika gry
1. Gracze rozpoczynają z określoną kwotą pieniędzy
2. Każda runda zaczyna się od ustalenia stawki
3. Gracze wykonują kolejno:
   - Pierwszy rzut 5 kośćmi
   - Drugi rzut (z możliwością przerzutu wybranych kości)
4. Po obu rzutach następuje porównanie układów i rozdanie nagrody

## Interfejs graficzny (GUI)
- Zielone tło stołu pokerowego
- Emotikony kości (⚀ ⚁ ⚂ ⚃ ⚄ ⚅)
- Panel informacyjny dla każdego gracza
- Log gry wyświetlający akcje i wyniki
- Przyciski sterowania:
  - "Rzuć kośćmi"
  - "Zakończ turę"
  - "Nowa gra"

## Wymagania
- Java 8 lub nowsza
- Dla GUI: biblioteki Swing

## Instrukcja uruchomienia
1. Skompiluj projekt: `javac main/java/*.java`
2. Uruchom: `java main.java.Main` (wersja konsolowa) lub `java main.java.PokerGUI_Stol_Styled` (wersja GUI)

## Algorytmy
### 1. Ocena układu kości
Klasa `ScoreEvaluator` implementuje system oceny układów w następującej hierarchii (od najsilniejszego):
1. Poker (5 jednakowych) - siła 8
2. Kareta (4 jednakowe) - siła 7
3. Full (trójka + para) - siła 6
4. Duży strit (2-3-4-5-6) - siła 5
5. Mały strit (1-2-3-4-5) - siła 4
6. Trójka - siła 3
7. Dwie pary - siła 2
8. Para - siła 1
9. Najwyższa kość - siła 0
### 2. Mechanizm tiebreaker'ów
W przypadku takich samych układów kości u obu graczy naraz wyższe wartości kości przeważają, np. jeśli masz dwie trójki, a twój przeciwnik dwie czwórki, on wygrywa. Pozostałe kości mogą być brane pod uwagę, jeżeli obaj gracze mają takie same układy z takimi samymi kośćmi, np. ty i przeciwnik macie po cztery szóstki, jednak ty masz trójkę jako piątą kość a twój przeciwnik jedynkę — ty wygrywasz. Tak więc remisy występują w bardzo rzadkich przypadkach, tylko wtedy, gdy obaj gracze mają pięć identycznych kości.