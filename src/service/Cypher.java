package service;

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;



public class Cypher {

    public static void OpenFileforEncryption(){
        System.out.println("Введите путь к файлу, который должен быть зашифрован: ");
        Scanner console = new Scanner(System.in);
        String Filepath = console.nextLine();
        Path path = Paths.get(Filepath);
        if(Files.exists(path)){
            try{
                String data = Files.readString(path);
                System.out.println("File data: \n" + data );
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
}
