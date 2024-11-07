package service;

import java.util.ArrayList;
import java.util.InputMismatchException;
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
            'ъ', 'ы', 'ь', 'э', 'я','A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И','К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '}; //Алфавит
    private static final int ALPHABET_SIZE = ALPHABET.length;

    public static void OpenFileForEncryption(){ //Функция для открытия файла. В ней также вызываются функции : encryptWords , getIndexFromAlphabet ,WriteFileAfterEncryption ,PrintDataFromEncryptedFile

        System.out.println("Введите путь к файлу формата txt, который должен быть зашифрован: ");
        Scanner console = new Scanner(System.in);
        String Filepath = console.nextLine();
        Path path = Paths.get(Filepath);

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
                List<String> words = new ArrayList<>(); // Массив для разбиения текста в файле на слова
                String line;
                System.out.println("File data:");
                while((line=reader.readLine())!=null){
                    System.out.println(line);
                    words.add(line);

                }
                System.out.println("Введитe ключ : ");
                try{
                    int key = console.nextInt();
                    while (key<0){       //Проверка на неподходящий ключ

                        System.out.println("Вы ввели неправильный ключ, введите ключ снова");
                        key = console.nextInt();
                    }
                    List<String> encryptedWords = encryptWords(words,key); // Получение списка зашифрованных слов после вызова функции encryptWords

                    System.out.println("Введите новый файл для записи зашифрованного текста: ");

                    console.nextLine(); //Для корректного получекния пути к файлу

                    String PathOfEncryptedFile = console.nextLine();

                    WriteFileAfterEncryption(PathOfEncryptedFile,encryptedWords); // Запись в новый файл зашифрованного текста

                    System.out.println("Данные в зашифрованном файле: ");
                    System.out.println();
                    PrintDataFromEncryptedFile(PathOfEncryptedFile); // Вывод зашифрованого содержимого в новом файле
                    System.out.println("Файл зашифрован!");

                }
                catch(InputMismatchException E){
                    System.out.println("Слишком большое значение ключа");
                }

            }
            catch(IOException e){                       //Ошибка в случае ввода ключа больше чем Integer.MAX_VALUE;
                System.out.println("Ошибка чтения файла");
            }

        }
        else{
            System.out.println("Указанный файл не существует");
        }




    }
    public static List<String> encryptWords(List<String> words,int key){  // Функция для реализации списка с зашифрованными словами
        List<String> encryptedWords = new ArrayList();
        for(var word : words){
            encryptedWords.add(Encryption(word,key).toString()); // Использование функции шифрования слов
        }
        return encryptedWords;
    }

    public static StringBuilder Encryption(String data, int key){      // Функция для шифрования каждого слова по методу цезаря

        int trueKey = key % ALPHABET_SIZE;                            //Правильный ключ (Учет условия о том, что ключ вновь проходит по алфавиту , если его знаечние больше размера алфавита
        StringBuilder encryptedData = new StringBuilder();
        for (char elem : data.toCharArray()){
            int index = getIndexFromAlphabet(elem);                   //Вызов функции для поиска индекса элемента в алфавите
            if (index !=-1){
                int shiftIndex = (index + trueKey) % ALPHABET_SIZE;   //Индекс элемента после смешения на значение ключа
                encryptedData.append(ALPHABET[shiftIndex]);

            }
            else{
                encryptedData.append(elem);
            }
        }

        return encryptedData;

    }
    private static int getIndexFromAlphabet(char charElem){ //Функция для получения индекса элемента из алфавита
        for(int i = 0; i< ALPHABET.length;i++){
            if(ALPHABET[i] == charElem){
                return i;
            }
        }
        return -1;
    }
    public static void WriteFileAfterEncryption(String fileName,List<String> data){        // Функция для записи в новый файл зашифрованного текста
        Path pathOfNewFile = Paths.get(fileName);

        if(!Files.exists(pathOfNewFile.getParent()) && pathOfNewFile.getParent()!=null){  // Проверка на отсутствие каталога
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

            System.out.println("Данные записаны в файл" + fileName);
        }
        catch(IOException e){
            System.out.println("Ошибка при записи файла: " + e.getMessage());

        }

    }
    public static void PrintDataFromEncryptedFile(String PathOfEncryptedFile ){ // Функция для вывода зашифрованного содержимого файла
        Path path = Paths.get(PathOfEncryptedFile);
        if(Files.exists(path)){                                                //Проверка на существование файла
            try(BufferedReader reader = Files.newBufferedReader(path)){        //Использование буффера для чтения
                String line;
                System.out.println("Зашифрованный текст:");
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
