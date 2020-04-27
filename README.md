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