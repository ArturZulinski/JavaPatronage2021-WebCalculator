# JavaPatronage2021-WebCalculator

Program wykonujący proste obliczenia matematyczne, których podstawą mogą być liczby rzeczywiste, wektory lub macierze.
Możliwość przeglądania oraz dokonywania obliczeń za pomocą przeglądarki na porcie 8080:
http://localhost:8080/

Link do dokumentacji Swagger: http://localhost:8080/swagger-ui.html<br />

Link do konsoli bazy danych H2: http://localhost:8080/h2-console

## Uruchomienie aplikacji

Do uruchomienia aplikacji potrzebne są:
- Java
- Maven
- InteliJ IDEA Community Edition
- (opcjonalnie) Postman

W programie InteliJ importujemy projekt. Następnie uruchamiamy opcję Run, która również jest odpowiedzialna za build'owanie aplikacji.

## Funkcje matematyczne

Program wykonuje następujące funkcje matematyczne:
- Wybierając 2 liczby rzeczywiste użytkownik może:
    -   Dodać je do siebie
    -   Odjąć o pierwszej liczby drugą
    -   Pomnożyć je ze sobą
    -   Podzielić pierwszą liczbę przez drugą
    -   Podnieść pierwszą liczbę do potęgi drugiej
    -   Spierwiastkować pierwszą liczbę do pierwiastka stopnia liczby drugiej
- Wybierając liczbę i wektor użytkownik może je pomnożyć ze sobą (mnożenie wektora przez skalar)
- Wybierając liczbę i macierz użytkownik może je pomnożyć ze sobą (mnożenie macierzy przez skalar)
- Wybierając 2 wektory użytkownik może:
    -   Dodać je do siebie
    -   Odjąć od pierwszego wektora drugi wektor
- Wybierając 2 macierze użytkownik może:
    -   Dodać je do siebie
    -   Odjąć od perwszej macierzy macierz drugą
    -   Pomnożyć je ze sobą      
- Wybierając macierz oraz wektor użytkownik może pomnożyć je ze sobą

## Funkcje dodatkowe

Program zapisuje również historię wykonywanych operacji w plikach tekstowych (operation_history.txt).
Kiedy ilość operacji przekroczy 5 kilobajtów, program archiwizuje plik z adnotacją numeryczną i nadpisuje wcześniejszy plik.
Użytkownik ma również możliwość:
- Wczytania wszystkich plików historii operacji
- Wczytania aktualnego pliku operacji
- Wczytania wybrango pliku historii operacji
- Usunięcia wszystkich plików historii opeacji

## Baza danych

Program korzysta rówmież z bazy danych H2. Aby z niej skorzystać należy użyć poniższego linku w przeglądarce:
<app_url> = http://localhost:8080/h2-console/<br/>

Do zalogowanie potrzebne są defaultowe dane:
- JDBC URL: jdbc:h2:mem:testdb
- User Name: sa
- Password: <leave blank>

W przypadku problemów z zalogowaniem, potrzeba będzie zainstalować H2 Database Engine z zewnętrznego źródła:
https://www.h2database.com/html/main.html

## Autor

Artur Żuliński