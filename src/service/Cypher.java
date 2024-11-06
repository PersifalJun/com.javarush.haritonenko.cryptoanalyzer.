package service;

import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;




public class Cypher {
    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
    private static final int ALPHABET_SIZE = ALPHABET.length;

    public static void OpenFileForEncryption(){
        System.out.println("Введите путь к файлу формата txt, который должен быть зашифрован: ");
        Scanner console = new Scanner(System.in);
        String Filepath = console.nextLine();
        Path path = Paths.get(Filepath);
        if(Files.exists(path)){
            try(BufferedReader reader = Files.newBufferedReader(path)){
                List<String> words = new ArrayList<>(); // Массив для разбиения текста в файле на слова
                String line;
                System.out.println("File data:");
                while((line=reader.readLine())!=null){
                    System.out.println(line);
                    words.add(line);

                }
                System.out.println("Введитe ключ : ");
                int key = console.nextInt();
                while (key<0 || key > Integer.MAX_VALUE){

                    System.out.println("Вы ввели неправильный ключ, введите ключ снова");
                    key = console.nextInt();
                }
                List<String> encryptedWords = encryptWords(words,key); // Список зашифрованных слов

                System.out.println("Введите новый файл для записи зашифрованного текста: ");

                console.nextLine(); //Для корректного получекния пути к файлу

                String PathOfEncryptedFile = console.nextLine();

                WriteFileAfterEncryption(PathOfEncryptedFile,encryptedWords); // запись в новый файл зашифрованного текста

                System.out.println("Данные в зашифрованном файле: ");
                System.out.println();
                PrintDataFromEncryptedFile(PathOfEncryptedFile);

            }
            catch(IOException e){
                System.out.println("Error of reading the file");
            }

        }
        else{
            System.out.println("The specified file does not exist");
        }




    }
    public static List<String> encryptWords(List<String> words,int key){ //Функция для реализации списка с зашифрованными словами
        List<String> encryptedWords = new ArrayList();
        for(var word : words){
            encryptedWords.add(Encryption(word,key).toString());
        }
        return encryptedWords;
    }

    public static StringBuilder Encryption(String data, int key){ // Функция для шифрования каждого слова

        int trueKey = key % ALPHABET_SIZE;                            //Правильный ключ
        StringBuilder encryptedData = new StringBuilder();
        for (char elem : data.toCharArray()){
            int index = data.indexOf(elem);
            if (index !=-1){
                int shiftIndex = (index + trueKey) % ALPHABET_SIZE;
                encryptedData.append(ALPHABET[shiftIndex]);

            }
            else{
                encryptedData.append(elem);
            }
        }

        return encryptedData;

    }
    public static void WriteFileAfterEncryption(String fileName,List<String> data){ // Функция для записи в новый файл зашифрованного текста
        Path pathOfNewFile = Paths.get(fileName);

        if(!Files.exists(pathOfNewFile.getParent()) && pathOfNewFile.getParent()!=null){
            try{
                Files.createDirectories(pathOfNewFile.getParent());
                System.out.println("Каталог создан: " + pathOfNewFile.getParent());
            }
            catch(IOException e){
                System.out.println("Каталог не создан" + e.getMessage());
            }

        }
        try(BufferedWriter writer = Files.newBufferedWriter(pathOfNewFile)){
            for(String line : data){ //Запись каждой строки в файл
                writer.write(line);
                writer.newLine(); //Добавление перевода строки
            }

            System.out.println("Данные записаны в файл" + fileName);
        }
        catch(IOException e){
            System.out.println("Ошибка при записи файла: " + e.getMessage());

        }

    }
    public static void PrintDataFromEncryptedFile(String PathOfEncryptedFile ){
        Path path = Paths.get(PathOfEncryptedFile);
        if(Files.exists(path)){
            try(BufferedReader reader = Files.newBufferedReader(path)){
                String line;
                System.out.println("File data:");
                while((line=reader.readLine())!=null){
                    System.out.println(line);

                }

            }
            catch(IOException e){
                System.out.println("Error of reading the file");
            }

        }
        else{
            System.out.println("The specified file does not exist");
        }



    }

}
