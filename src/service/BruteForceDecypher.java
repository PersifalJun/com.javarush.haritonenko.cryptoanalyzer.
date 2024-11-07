package service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BruteForceDecypher{
    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я','A', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З',
            'И','К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '}; //Алфавит
    private static final int ALPHABET_SIZE = ALPHABET.length;

    public String decryptByBruteForce(String encryptedText, char[] ALPHABET) {
        StringBuilder decryptedText = new StringBuilder();


        for (int key = 1; key <= ALPHABET_SIZE; key++) {               // Перебираем все возможные сдвиги

            String decrypted = DecryptionByBruteForce(encryptedText, key, ALPHABET);

            writeDecryptionToFileAfterBruteForce(decrypted, key);
        }

        return decryptedText.toString();
    }


    private String DecryptionByBruteForce(String data, int key, char[] ALPHABET) {      // Метод для расшифровки с заданным ключом (сдвигом)
        int trueKey = key % ALPHABET_SIZE;  // Правильное значение ключа
        StringBuilder decryptedData = new StringBuilder();


        for (char elem : data.toCharArray()) {
            int index = GetIndexFromAlphabet(elem, ALPHABET);
            if (index != -1) {
                int normalIndex = (index - trueKey + ALPHABET_SIZE) % ALPHABET_SIZE;
                decryptedData.append(ALPHABET[normalIndex]);
            } else {
                decryptedData.append(elem);  // Если символ не найден, оставляем его как есть
            }
        }

        return decryptedData.toString();
    }


    private int GetIndexFromAlphabet(char charElem, char[] ALPHABET) {                   // Метод для поиска индекса символа в алфавите
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (ALPHABET[i] == charElem) {
                return i;
            }
        }
        return -1;
    }


    private void writeDecryptionToFileAfterBruteForce(String decryptedText, int key) {     // Метод для записи расшифрованных данных в файл

        String fileName = "Decrypted_text_key_" + key + ".txt";

        Path pathOfNewFile = Paths.get(fileName);


        try (BufferedWriter writer = Files.newBufferedWriter(pathOfNewFile)) {
            writer.write(decryptedText);
            writer.newLine();  // Добавляем новую строку
            System.out.println("Записано в файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }
    }



}



