# Gestione Clienti

## Descrizione
Questo progetto implementa un'applicazione per la gestione dei clienti utilizzando Java e l'interfaccia grafica Swing. L'applicazione consente di aggiungere nuovi clienti, visualizzare il massimo stipendio tra i clienti presenti, salvare e aprire elenchi di clienti da file.

## Requisiti di sistema
- Java Development Kit (JDK) versione 8 o superiore
- Ambiente di sviluppo Java compatibile (ad esempio Visual Studio Code, IntelliJ IDEA)

## Come eseguire l'applicazione
1. Assicurarsi di avere il JDK installato sul proprio sistema.
2. Scaricare il codice sorgente del progetto.
3. Aprire il progetto nell'ambiente di sviluppo Java.
4. Compilare e eseguire il file `Application.java`.

## Funzionalità principali
- Aggiunta di nuovi clienti con nome, cognome, stipendio e email.
- Visualizzazione del massimo stipendio tra i clienti presenti.
- Salvataggio dell'elenco dei clienti in un file.
- Caricamento di un elenco di clienti da un file.

## Utilizzo dell'applicazione
1. Avviare l'applicazione eseguendo il file `Application.java`.
2. Nella barra del menu, selezionare l'opzione `File`.
3. Per aggiungere un nuovo cliente, selezionare l'opzione `add Client` dal menu `File`. Inserire i dettagli del cliente nella finestra di dialogo e fare clic su "Aggiungi".
4. Per verificare il massimo stipendio tra i clienti, selezionare l'opzione `Check salary` dal menu `File`.
5. Per salvare l'elenco dei clienti in un file, selezionare l'opzione `Save` dal menu `File`.
6. Per aprire un elenco di clienti da un file, selezionare l'opzione `Open` dal menu `File`.

## Struttura del codice
Il progetto è suddiviso in due classi principali:
- `Application.java`: Questa classe rappresenta l'applicazione GUI e gestisce l'interfaccia utente, l'aggiunta di clienti, il salvataggio e il caricamento da file.
- `Clienti.java`: Questa classe definisce il modello di dati per i clienti, inclusi nome, cognome, stipendio ed email.

## Autore
- [@Giuseppe-Falliti](https://www.github.com/GiuseppeFalliti)
