# PALaboratorul10
UAIC_FII_PA_LAB_10


Setare:
   * se deschid ambele foldere, *Laboratorul10client* si *Laboratorul10Server* ca proiecte intellij
   
1. Compulsory 
    1.  Create the project ServerApplication. This will contain (at least) the classes: GameServer and ClientThread.
        * au fost facute in proiectul **Laboratorul10Server** in 2 pachete. *GameServer* se afla in pachetul server, iar *ClientThread* in pachetul threads
    2. Create the class GameServer. An instance of this class will create a ServerSocket running at a specified port. The server will receive requests (commands) from clients and it will execute them.
        * este exact ca in curs, diferenta: numele variabilelor
    3. Create the class ClientThread. An instance of this class will be responsible with communicating with a client Socket. If the server receives the command stop it will stop and will return to the client the respons "Server stopped", otherwise it return: "Server received the request ... ".
        * copiata din curs, cu urmatoarele diferente:
        * operatiile de I/O (comunicarea cu clientii) sunt realizate intr-un while(true)
        * daca requestul primit este *exit* (clientul s-a inchis pe sine), se afiseaza acest lucru si se iese din while(nu are rost sa asteptam un client care nu va mai veni)
        * daca requestul este *stop*, se trimite inapoi clientului mesajul cerut in cerinta, ~~se inchide in, out, socket, si se da System.exit(0) *(If the server receives the command stop it will stop )*~~ si este inchis threadul
        * in rest este la fel ca in curs
    4. Create the project ClientApplication. This will contain (at least) the class: GameClient.
        * a fost creata in pachetul org.example.client
    5. Create the class GameClient. An instance of this class will read commands from the keyboard and it will send them to the server. The client stops when it reads from the keyboard the string "exit".
        * copiata din curs, cu urmatoarele diferente:
        * I/O se afla intr-un while(true)
        * verificam la inceput daca socketul este inchis, in cazul in care vom intrerupe comunicarea
        * citim de la tastatura intr-un buffered reader
        * daca nu am citit nimic inchidem tot (nu este necesar)
        * daca stdin este exit, inchidem tot si ne oprim
        * daca stdin este stop (si ~~serverul~~ threadul se opreste), nu mai are rost ca sa comunice clientul in gol, deci il oprim si pe el
        
        
2. Optional
    * **Se pot deschide 2 clienti astfel: rulare cu Shift + F10 (sageata de run), si in josul paginii, se deschide si terminalul, se deplaseaza pana in /target/classes si se ruleaza "java org.example.App"**
    1. Implement functionalities of the game, using the classes Game, Board, Player, etc.
        * aceste clase au fost create in pachetul **game** de pe server
        * in clasa ***Game*** avem o lista de jucatori si o tabla de joc, cu getteri si setteri
        * in clasa ***Board*** avem o matrice de joc si metode care verifica existenta unei anumite piese la o anumita pozitie din tabla de joc, adaugarea unei piese de o anumita culoare la o anumita pozitie, getteri, setteri, metode pentru verificarea incheierii jocului, si metode pentru afisarea table de joc
        * in clasa ***Player*** avem un jucator care are doar nume
    2. The clients will send to the server commands such as: create game, join game, submit move, etc.
        * pe partea de client nu se realizeaza nici o modificare fata de *Compulsory*, pentru ca clientul doar primeste input de la tastatura si trimite instructiunea serverului
        * pe partea de server se modifica while(true), adaugandu-se cu ***if*** toate cazurile acceptate de server:
        * serverul face urmatoarele: 
             * se comunica cu clientul prin tcp intr-un while. Se opreste executia threadului atunci cand este primit exit sau stop
             * daca se primeste create game creaza un joc daca nu este deja unul creat
             * daca se primeste join game adauga un player nou la un joc, daca sunt mai putin de 2 (jocul e doar in 2, alb si negru)
             * daca se primeste show board, se afiseaza tabla de joc
             * daca se primeste submit move, se adauga o piesa de culoarea jucatorului pe pozitia respectiva, daca este posibil
             * se tine cont de tura carui jucator este
             * este implementat un sistem de ture, fiecare jucator poate muta doar pe rand
             * in caz de mutare necorespunzatoare, se intoarce un raspuns
         * toate comenzile mentionate in cerinta sunt tratate
    3. The server is responsible with the game management and mediating the players.
        * s-a explicat anterior ca serverul primeste requesturile clientilor, implementeaza turele celor 2 playeri
        * serverul nu are rolul sa anunte ca un jucator si-a terminat tura, ci doar raspunde la requesturi
        * daca un player nu si-a terminat tura, serverul doar va refuza executarea turei celuluilalt player pana cand ii vine si lui randul
    4. Once a game is finished, an HTML or SGF representation of the game should be uploaded to a Web server.
