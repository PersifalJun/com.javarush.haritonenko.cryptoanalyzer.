package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Decypher {
    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я','A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И','К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '}; //Алфавит
    private static final int ALPHABET_SIZE = ALPHABET.length;

    public static void OpenFileForDecryption(){                                             //Функция для открытия файла. Здесь использованы функции: DecryptWords, WriteFileAfterDecryption, PrintDataFromDecryptedFile(PathOfDecryptedFile);
        System.out.println("Введите путь к файлу, который должен быть расшифрован: ");
        Scanner console = new Scanner(System.in);
        String Filepath = console.nextLine();
        Path path = Paths.get(Filepath);
        List<String> WordsNeedDecryption = new ArrayList<>();                               //Список зашифрованных слов

        if(Files.exists(path)){                                                             //Проверка на наличие файла

            try{
                if (Files.size(path) == 0){                                                 //Проверка на содержимое файла
                    System.out.println("Ошибка : Файл пустой");
                    return;
                }
            }catch(IOException e){
                System.out.println("Ошибка при проверке файла: " +e.getMessage());
                return;
            }

            try(BufferedReader reader = Files.newBufferedReader(path)){                     //Использование буффера для чтения
                String line;
                System.out.println("File data:");
                while((line=reader.readLine())!=null){
                    System.out.println(line);
                    WordsNeedDecryption.add(line);
                }
            }

            catch(IOException e){
                System.out.println("Ошибка чтения файла");
            }

        }
        else{
            System.out.println("Указанный файл не существует");
            return;
        }
        System.out.println("Введитe ключ : ");
        try{
            int key = console.nextInt();
            while (key<0) {                                   //Проверка на неподходящий ключ

                System.out.println("Вы ввели неправильный ключ, введите ключ снова");
                key = console.nextInt();
            }
            List<String> decryptedWords = DecryptWords(WordsNeedDecryption,key);                //Список с расшифрованными словами
            System.out.println("Введите новый файл для записи расшифрованного текста: ");

            console.nextLine();                                                                //Для корректного получекния пути к файлу

            String PathOfDecryptedFile = console.nextLine();                                   //Путь к файлу с расшифрованным текстом
            WriteFileAfterDecryption(PathOfDecryptedFile,decryptedWords);                      //Запись файла
            System.out.println("Расшифрованные данные в файле: ");
            System.out.println();
            PrintDataFromDecryptedFile(PathOfDecryptedFile);                                    // Вывод расшифрованого содержимого в новом файле
            System.out.println("Текст документа расшифрован!");
        }
        catch(InputMismatchException E){                                                        //Ошибка в случае ввода ключа больше чем Integer.MAX_VALUE;
            System.out.println("Слишком большое значение ключа");
        }




    }



    public static StringBuilder Decryption(String Data, int key){                   // Функция для расшифровки слов
        int trueKey = key %ALPHABET_SIZE;                                           // Правильное значение ключа
        StringBuilder decryptedData = new StringBuilder();
        for (char elem : Data.toCharArray()){
            int index = GetIndexFromALphabet(elem);
            if (index!=-1){
                int normalIndex = (index - trueKey +ALPHABET_SIZE)%ALPHABET_SIZE;
                decryptedData.append(ALPHABET[normalIndex]);
            }
            else {
                decryptedData.append(elem);
            }
        }

        return decryptedData;

    }
    private static int GetIndexFromALphabet(char charElem) {                               //Функция для получения индекса из Алфавита
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ALPHABET[i] == charElem) {
                return i;
            }
        }
        return -1;
    }
    public static List<String> DecryptWords(List<String> words, int key){               // Функция для реализации списка с расшифрованными словами
        List<String> decryptedWords = new ArrayList();
        for(var word : words){
            decryptedWords.add(Decryption(word,key).toString());                        // Использование функции расшифровки слов
        }
        return decryptedWords;
    }

    public static void WriteFileAfterDecryption(String fileName,List<String> data){        // Функция для записи в новый файл зашифрованного текста
        Path pathOfNewFile = Paths.get(fileName);

        if(!Files.exists(pathOfNewFile.getParent()) && pathOfNewFile.getParent()!=null){  // Проверка на отсутствие каталога
            try{
                Files.createDirectories(pathOfNewFile.getParent());                       //Создание каталога
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

            System.out.println("Данные записаны в файл" + fileName);
        }
        catch(IOException e){
            System.out.println("Ошибка при записи файла: " + e.getMessage());

        }

    }
    public static void PrintDataFromDecryptedFile(String PathOfEncryptedFile ){ // Функция для вывода зашифрованного содержимого файла
        Path path = Paths.get(PathOfEncryptedFile);
        if(Files.exists(path)){                                                //Проверка на существование файла
            try(BufferedReader reader = Files.newBufferedReader(path)){        //Использование буффера для чтения
                String line;
                System.out.println("Расшифрованный текст:");
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