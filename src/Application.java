

/*Рекомендации к использованию программы :
1).Для работы с шифрованием создайте два файла формата txt (например, в файле FileBeforeBeingEncrypted
будет содержаться текст до шифрования, а во второй файл EncryptedFile будет записан этот зашифрованный текст).
2).Для работы с шифрованием можно использовать файл EncryptedFile из пунтка 1. Нужно создать файл формата txt для записи (например, DecryptedFile)
, туда запишется информация после расшифровки.
*/


import java.util.Scanner;
import service.Cypher;
import service.Decypher;


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



                break;
            case 2 :
                System.out.println("Выполняется расшифровка с ключом");
                Decypher.OpenFileforDecryption();



                break;
            case 3 :
                System.out.println("Выполняется расшифровка текста с помощью brute force");
                break;
            case 0:
                System.out.println("Выход");
            default:
                System.out.println("Выберите один из предложенных пунктов");



        }

    }
}
