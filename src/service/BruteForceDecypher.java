package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class BruteForceDecypher{
    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я','A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И','К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '}; //Алфавит
    private static final int ALPHABET_SIZE = ALPHABET.length;

    public static void OpenDecryptedFileForBruteForce(){  //Функция для открытия зашифрованного файла

        System.out.println("Введите путь к файлу формата txt, который должен быть зашифрован: ");
        Scanner console = new Scanner(System.in);
        String Filepath = console.nextLine();
        Path path = Paths.get(Filepath);
        List<String> encryptedWords = new ArrayList<>();   // Массив для разбиения текста в файле на слова

        if(Files.exists(path)){                           //Проверка на существование файла
            try{
                if (Files.size(path) == 0){              //Проверка на содержимое файла
                    System.out.println("Ошибка : Файл пустой");
                    return;
                }
            }catch(IOException e){
                System.out.println("Ошибка при проверке файла: " +e.getMessage());
                return;
            }

            try(BufferedReader reader = Files.newBufferedReader(path)){     //Использование буффера для чтения

                String line;
                System.out.println("File data:");
                while((line=reader.readLine())!=null){
                    System.out.println(line);
                    encryptedWords.add(line);
                }

            }
            catch(IOException e){
                System.out.println("Ошибка чтения файла");
            }
        }
        else{
            System.out.println("Указанный файл не существует");
        }

        List<String> originalWords = OpenOriginalFile();
        if (originalWords == null){
            return;
        }
        int key = 0;
        boolean keyIsFound = false;
        while(!keyIsFound && key<ALPHABET_SIZE){
            List<String> decryptedWords = EncryptWordsAddedToArray(encryptedWords,key);

            if (ArraysBeingCompared(decryptedWords,originalWords)){
                System.out.println("Найден верный ключ: "+ key);
                WriteToFileAfterBruteforce(String.join(" ",decryptedWords),key);
                keyIsFound = true;
                System.out.println("Файл расшифрован!");

            }
            else{
                key++;
            }
        }

        if(!keyIsFound)
            System.out.println("Ключ не найден");

    }




    public static List<String> OpenOriginalFile(){  //Функция для открытия оригинального файла
        System.out.println("Введите путь к оригинальному файлу формата txt: ");
        Scanner console = new Scanner(System.in);
        String Filepath = console.nextLine();
        Path path = Paths.get(Filepath);
        List<String> originalWords = new ArrayList<>();             // Массив для разбиения текста в файле на слова

        if(Files.exists(path)){                           //Проверка на существование файла
            try{
                if (Files.size(path) == 0){              //Проверка на содержимое файла
                    System.out.println("Ошибка : Файл пустой");

                }
            }catch(IOException e){
                System.out.println("Ошибка при проверке файла: " +e.getMessage());

            }

            try(BufferedReader reader = Files.newBufferedReader(path)){     //Использование буффера для чтения
                String line;
                System.out.println("File data:");
                while((line=reader.readLine())!=null){
                    System.out.println(line);
                    originalWords.add(line);
                }
                return originalWords;

            }
            catch(IOException e){
                System.out.println("Ошибка чтения файла");
            }
        }
        else{
            System.out.println("Указанный файл не существует");
            return null;
        }

        return originalWords;
    }




    public static StringBuilder EncryptedWordsToCharArray(String data,int key){
        StringBuilder decryptedWords = new StringBuilder();
        for (char elem : data.toCharArray()){
            int index = GetIndexFromAlphabet(elem,ALPHABET);
            if (index !=-1){
                int indexToCompare = (index - key + ALPHABET_SIZE) % ALPHABET_SIZE;
                decryptedWords.append(ALPHABET[indexToCompare]);

            }
            else{
                decryptedWords.append(elem);
            }
        }

        return decryptedWords;

    }



    public static List<String> EncryptWordsAddedToArray(List<String> words,int key){  // Функция для реализации списка с зашифрованными словами
        List<String> encryptedWords = new ArrayList();
        for(var word : words){
            encryptedWords.add(EncryptedWordsToCharArray(word, key).toString()); // Использование функции шифрования слов
        }
        return encryptedWords;
    }




    private static int GetIndexFromAlphabet(char charElem, char[] ALPHABET) {                   // Метод для поиска индекса символа в алфавите
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (ALPHABET[i] == charElem) {
                return i;
            }
        }
        return -1;
    }



    private static boolean ArraysBeingCompared(List<String> decryptedWords,List<String> originalWords){
        if(decryptedWords.size()!= originalWords.size())
            return false;
        for(int i =0 ; i< decryptedWords.size();i++){
            if(!decryptedWords.get(i).equals(originalWords.get(i))){
                return false;
            }
        }
        return true;
    }





    private static void WriteToFileAfterBruteforce(String decryptedText, int key) {     // Метод для записи расшифрованных данных в файл

        String fileName = "Decrypted_text_key_" + key + ".txt";

        Path pathOfNewFile = Paths.get(fileName);


        try (BufferedWriter writer = Files.newBufferedWriter(pathOfNewFile)) {
            writer.write(decryptedText);
            writer.newLine();  // Добавляем новую строку
            System.out.println("Записано в файл: " + fileName);
            PrintDataFromFileAfterBruteForce(fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }
    }

    public static void PrintDataFromFileAfterBruteForce(String PathOfEncryptedFile ){ // Функция для вывода зашифрованного содержимого файла
        System.out.println("Расшифрованный текст:");
        Path path = Paths.get(PathOfEncryptedFile);
        if(Files.exists(path)){                                                //Проверка на существование файла
            try(BufferedReader reader = Files.newBufferedReader(path)){        //Использование буффера для чтения
                String line;
                while((line=reader.readLine())!=null){
                    System.out.println(line);

                }

            }
            catch(IOException e){
                System.out.println("Ошибка чтения файла");
            }

        }
        else{
            System.out.println("Указанный файл не существует");
        }

    }

}



