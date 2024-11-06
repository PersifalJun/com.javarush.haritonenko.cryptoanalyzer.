

/*Рекомендации к использованию программы :
1).Для работы с шифрованием создайте два файла формата txt (например, в файле FileBeforeBeingEncrypted
будет содержаться текст до шифрования, а во второй файл EncryptedFile будет записан этот зашифрованный текст.
*/


import java.util.Scanner;
import service.Cypher;


public class Application {

    public static void main(String[] args){
        // Логика меню
        // 1. Шифрование
        // 2. Расшифровка с ключом
        // 3. Brute force
        // 4. Статистический анализ
        // 0. Выход
        System.out.println("Шифровальщик методом цезаря");
        System.out.println("Выберите пункт меню");
        System.out.println("1. Шифрование с ключом");
        System.out.println("2. Расшифровка с ключом");
        System.out.println("3. Brute force");
        System.out.println("0. Выход");
        System.out.println();
        Scanner console = new Scanner(System.in);
        int choice = Integer.parseInt(console.nextLine());
        switch(choice){
            case 1 :

                System.out.println("Выполняется шифрование ключом");

                Cypher.OpenFileForEncryption();
                System.out.println();
                System.out.println("Файл зашифрован!");

                break;
            case 2 :
                System.out.println("Выполняется расшифровка с ключом");
                System.out.println("Введитe ключ : ");

                break;
            case 3 :
                System.out.println("Выполняется расшифровка текста с помощью brute force");
                break;
            default:
                System.out.println("Выход");



        }

    }
}
