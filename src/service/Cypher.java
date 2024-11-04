package service;

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.BufferedWriter;




public class Cypher {
    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};
    private static final int ALPHABET_SIZE = ALPHABET.length;

    public static void OpenFileforEncryption(){
        System.out.println("Введите путь к файлу формата txt, который должен быть зашифрован: ");
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
    public static StringBuilder Encryption(String data, int key){

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
    public static void writeFileaAfterEncryption(String fileName,String data){
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
