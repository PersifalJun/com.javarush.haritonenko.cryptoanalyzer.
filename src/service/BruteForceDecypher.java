package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class BruteForceDecypher {
    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я','A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И','К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '}; //Алфавит
    private static final int ALPHABET_SIZE = ALPHABET.length;



    public static void OpenFileForDecryptionByBruteForce(){
        System.out.println("Введите путь к файлу, который должен быть расшифрован: ");
        Scanner console = new Scanner(System.in);
        String Filepath = console.nextLine();
        Path path = Paths.get(Filepath);
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
        console.close();

    }



    public StringBuilder DecryptionByBruteForce(String Data, int key){
        int trueKey = key % ALPHABET_SIZE;              //
        StringBuilder decryptedData = new StringBuilder();
        for (char elem : Data.toCharArray()){
            int index = Data.indexOf(elem);
            if (index!=-1){
                int normalIndex = (index - trueKey + ALPHABET_SIZE) % ALPHABET_SIZE;
                decryptedData.append(ALPHABET[normalIndex]);
            }
            else {
                decryptedData.append(ALPHABET[elem]);
            }
        }

        return decryptedData;

    }
    public static void WriteFileAfterDecryption(String fileName,String data){
        Path pathOfnewFile = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(pathOfnewFile)){
            writer.write(data);
            System.out.println("Данные записаны в файл" + fileName);
        }
        catch(IOException e){
            System.out.println("Ошибка при записи файла: " + e.getMessage());

        }
    }



}




