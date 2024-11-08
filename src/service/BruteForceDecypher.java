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

    public static void OpenDecryptedFileForBruteForce(){   //Функция для открытия зашифрованного файла. Использует функции : EncryptWordsAddedToArray,ArraysBeingCompared,WriteToFileAfterBruteforce,PrintDataFromFileAfterBruteForce.

        System.out.println("Введите путь к файлу формата txt, который должен быть расшифрован: ");
        Scanner console = new Scanner(System.in);
        String Filepath = console.nextLine();
        Path path = Paths.get(Filepath);
        List<String> encryptedWords = new ArrayList<>();    // Список для разбиения текста в файле на слова

        if(Files.exists(path)){                            //Проверка на существование файла
            try{
                if (Files.size(path) == 0){                //Проверка на содержимое файла
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

        List<String> originalWords = OpenOriginalFile();                    //Список оригинальных слов
        if (originalWords == null){                                         //Проверка на правильную работу функции OpenOriginalFile
            return;
        }
        int key = 0;                                                        //Начальное значение ключа для перебора
        boolean keyIsFound = false;                                         //Начальные режим : ключ не найден
        while(!keyIsFound && key<ALPHABET_SIZE){
            List<String> decryptedWords = EncryptWordsAddedToArray(encryptedWords,key);     // Получение списка зашифрованных слов во время работы цикла while

            if (ArraysBeingCompared(decryptedWords,originalWords)){                         // Проверка равенства списка зишифрованных слов и списка оригинальных слов
                System.out.println("Найден верный ключ: "+ key);
                System.out.println("Введите новый файл для записи расшифрованного текста: ");



                String PathOfDecryptedFile = console.nextLine();                    //Путь к новому файлу, где будет хранититься расшифрованный текст
                WriteToFileAfterBruteforce(PathOfDecryptedFile,decryptedWords);    //Запись в этот файл
                PrintDataFromFileAfterBruteForce(PathOfDecryptedFile);             //Вывод расшифрованного текста на экран
                keyIsFound = true;                                                 //Конечный режим : ключ найден
                System.out.println("Файл расшифрован!");

            }
            else{
                System.out.println("Текущий ключ : "+ key);                                 //Печать всех неподходящих ключей
                key++;
            }
        }

        if(!keyIsFound)
            System.out.println("Ключ не найден");

    }




    public static List<String> OpenOriginalFile(){                                    //Функция для открытия оригинального файла
        System.out.println("Введите путь к оригинальному файлу формата txt: ");
        Scanner console = new Scanner(System.in);
        String Filepath = console.nextLine();
        Path path = Paths.get(Filepath);
        List<String> originalWords = new ArrayList<>();                               // Список для разбиения текста в файле на слова

        if(Files.exists(path)){                                                       //Проверка на существование файла
            try{
                if (Files.size(path) == 0){                                            //Проверка на содержимое файла
                    System.out.println("Ошибка : Файл пустой");

                }
            }catch(IOException e){
                System.out.println("Ошибка при проверке файла: " +e.getMessage());

            }

            try(BufferedReader reader = Files.newBufferedReader(path)){             //Использование буффера для чтения
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




    public static StringBuilder EncryptedWordsToCharArray(String data,int key){         //Функция шифрования каждого слова
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



    public static List<String> EncryptWordsAddedToArray(List<String> words,int key){                // Функция для реализации списка с зашифрованными словами
        List<String> encryptedWords = new ArrayList();
        for(var word : words){
            encryptedWords.add(EncryptedWordsToCharArray(word, key).toString());                    // Использование функции шифрования слов
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



    private static boolean ArraysBeingCompared(List<String> decryptedWords,List<String> originalWords){     //Функция сравнения списка зашифрованных слов и списка оригинальных слов
        if(decryptedWords.size()!= originalWords.size())
            return false;
        for(int i =0 ; i< decryptedWords.size();i++){
            if(!decryptedWords.get(i).equals(originalWords.get(i))){
                return false;
            }
        }
        return true;
    }





    public static void  WriteToFileAfterBruteforce(String fileName,List<String> data){                     // Функция для записи в новый файл зашифрованного текста
        Path pathOfNewFile = Paths.get(fileName);

        if(!Files.exists(pathOfNewFile.getParent()) && pathOfNewFile.getParent()!=null){                   // Проверка на отсутствие каталога
            try{
                Files.createDirectories(pathOfNewFile.getParent());
                System.out.println("Каталог создан: " + pathOfNewFile.getParent());
            }
            catch(IOException e){
                System.out.println("Каталог не создан" + e.getMessage());
            }

        }
        try(BufferedWriter writer = Files.newBufferedWriter(pathOfNewFile)){   //Использование буффера для записи
            for(String line : data){                                           //Запись каждой строки в файл
                writer.write(line);
                writer.newLine();                                              //Добавление перевода строки
            }

            System.out.println("Данные записаны в файл " + fileName);
        }
        catch(IOException e){
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



