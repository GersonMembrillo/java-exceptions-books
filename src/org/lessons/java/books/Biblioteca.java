package org.lessons.java.books;

import java.io.*;
import java.util.Scanner;

public class Biblioteca {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Quanti libri vuoi inserire nel catalogo? ");
        int numLibri = sc.nextInt();
        sc.nextLine();

        Libro[] catalogo = new Libro[numLibri];

        for (int i = 0; i < numLibri; i++) {
            System.out.println("\nInserisci i dettagli del libro " + (i + 1) + ":");
            try {
                catalogo[i] = inputLibro(sc);
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: " + e.getMessage());
                i--; // Ripeti l'inserimento in caso di errore
            }
        }

        System.out.println("\nCatalogo dei libri:");
        for (int i = 0; i < numLibri; i++) {
            mostraLibro(catalogo[i], i + 1);
        }

        // Inserisci libri dentro file
        scriviTitoliSuFile(catalogo, "titoli.txt");
        System.out.println("--------------------- \nTitoli dei libri sono stati scritti su 'titoli.txt'");

        // Lettura file e mostra video
        leggiTitoliDaFile("titoli.txt");
    }

    public static Libro inputLibro(Scanner sc) throws IllegalArgumentException {
        String titolo, autore, editore;
        int numeroPagine;

        // Dati libro/i
        System.out.print("\nTitolo: ");
        titolo = sc.nextLine();
        if (titolo.isEmpty()) {
            throw new IllegalArgumentException("Titolo non valido.");
        }

        System.out.print("Autore: ");
        autore = sc.nextLine();
        if (autore.isEmpty()) {
            throw new IllegalArgumentException("Autore non valido.");
        }

        System.out.print("Editore: ");
        editore = sc.nextLine();
        if (editore.isEmpty()) {
            throw new IllegalArgumentException("Editore non valido.");
        }

        do {
            System.out.print("Numero di pagine: ");
            numeroPagine = sc.nextInt();
            sc.nextLine();
            if (numeroPagine <= 0) {
                System.out.println("Il numero di pagine deve essere maggiore di 0.");
            }
        } while (numeroPagine <= 0);

        return new Libro(titolo, numeroPagine, autore, editore);
    }

    public static void mostraLibro(Libro libro, int numeroLibro) {
        System.out.println("\nLibro " + numeroLibro + ":");
        System.out.println("Titolo: " + libro.getTitolo());
        System.out.println("Autore: " + libro.getAutore());
        System.out.println("Editore: " + libro.getEditore());
        System.out.println("Numero di pagine: " + libro.getNumeroPagine());
        System.out.println();
    }

    public static void scriviTitoliSuFile(Libro[] catalogo, String nomeFile) {
        try (FileWriter fileWriter = new FileWriter(nomeFile);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (Libro libro : catalogo) {
                bufferedWriter.write(libro.getTitolo());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void leggiTitoliDaFile(String nomeFile) {
        try (FileReader fileReader = new FileReader(nomeFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            System.out.println("\nTitoli dei libri dal file:\n");

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


