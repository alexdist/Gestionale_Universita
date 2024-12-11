# Progetto Gestionale Università - Applicazione Client/Server per la Gestione degli Esami

Applicazione Java client/server parallelo progettata per gestire gli esami universitari. 

---

## Funzionalità del Progetto

### **Segreteria**
- Inserisce nuovi esami sul server universitario (salvando i dati su file).
- Inoltra le richieste di prenotazione degli studenti al server universitario.
- Fornisce agli studenti le date degli esami disponibili per un determinato corso.

### **Studente**
- Richiede alla segreteria le date degli esami disponibili per un corso specifico.
- Invia una richiesta di prenotazione di un esame alla segreteria.

### **Server Universitario**
- Riceve l'aggiunta di nuovi esami dalla segreteria.
- Gestisce le prenotazioni degli esami ricevute dalla segreteria.
- Assegna un numero di prenotazione progressivo per ogni richiesta e lo restituisce alla segreteria, che a sua volta lo comunica allo studente.

---

## Requisiti

1. **Java Development Kit (JDK)**  
   - Versione 11 o successiva.  
   - Verifica l'installazione con:
     ```bash
     java -version
     javac -version
     ```
   - Se il JDK non è installato, scaricalo da:
     - [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html)
     - [OpenJDK](https://openjdk.org)

2. **Git**  
   - Per clonare la repository GitHub. Verifica l'installazione con:
     ```bash
     git --version
     ```

---

## Installazione

1. **Clona il Repository**
   - Usa il comando:
     ```bash
     git clone <https://github.com/alexdist/Gestionale_Universita>
     cd Gestionale_Universita
     ```

2. **Naviga nella Directory del Codice**
   - Spostati nella directory `src` dove si trovano i file Java:
     ```bash
     cd src
     ```

---

## Compilazione ed Esecuzione

Ogni componente deve essere compilato ed eseguito in un terminale separato. Segui i passaggi indicati per ciascun file.

### **Terminale 1: UniversityServer**
1. Compila:
   ```bash
   javac UniversityServer.java

2. Esegui:
   ```bash
   java UniversityServer
   ```

### **Terminale 2: SegreteriaServer**
1. Compila:
   ```bash
   javac SegreteriaServer.java

2. Esegui:
   ```bash
   java SegreteriaServer
   ```
### **Terminale 3: Segreteria**
1. Compila:
   ```bash
   javac Segreteria.java

2. Esegui:
   ```bash
   java Segreteria
   ```
### **Terminale 4: Studente1**
1. Compila:
   ```bash
   javac Studente1.java

2. Esegui:
   ```bash
   java Studente1
   ```

### **Terminale 5: Studente2**
1. Compila:
   ```bash
   javac Studente2.java

2. Esegui:
   ```bash
   java Studente2
   ```
   ### **Terminale 6: Studente3**
1. Compila:
   ```bash
   javac Studente3.java

2. Esegui:
   ```bash
   java Studente3
   ```


## Ordine di Avvvio
1. Avviare prima i server:
   - UniversityServer
   - SegreteriaServer

2. Avviaer la segreteria:
   - Segreteria

3. Avviare poi:
   - Studente1
   - Studente2
   - Studente3
