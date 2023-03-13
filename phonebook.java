import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class phoneBook {
    public static void main(String[] args) throws IOException {

        menu();

    }

    public static void menu() {
        HashMap<String, ArrayList<String>> phoneBook = new HashMap<String, ArrayList<String>>();
        String userChoice = "";
        Boolean run = true;
        while (run) {
            ClearConsole();
            System.out.println("Введите номер действия:");
            System.out.println("1 - вывод всех контактов");
            System.out.println("2 - поиск контакта по логину");
            System.out.println("3 - добавление контакта / телефона к конаткту");
            System.out.println("4 - отредактировать контакт (удалить телефон)");
            System.out.println("5 - сохранить книгу на диск в txt файл");
            System.out.println("6 - сохранить книгу на диск в бинарный файл");
            System.out.println("7 - загрузить книгу из txt файла");
            System.out.println("8 - загрузить книгу из бинарного файла");
            System.out.println("9 - создать новый справочник");
            System.out.println("0 - Выход");
            System.out.println();
            System.out.print("введите номер действия: ");
            userChoice = getUserInput();

            if (userChoice.equals("1")) { // вывод всех контактов
                showAll(phoneBook);
                System.out.println("нажмите Enter, чтобы вернуться в меню");
                System.console().readLine();
            
            } else if (userChoice.equals("2")) { // поиск контакта по логину
                findContact(phoneBook);
                System.out.println("нажмите Enter, чтобы вернуться в меню");
                System.console().readLine();

            } else if (userChoice.equals("3")) { // добавление контакта/номера к контакту
                addInfo(phoneBook);
                System.out.println("нажмите Enter, чтобы вернуться в меню");
                System.console().readLine();

            } else if (userChoice.equals("4")) { // отредактировать контакт (удалить телефон)
                editContact(phoneBook);
                System.out.println("нажмите Enter, чтобы вернуться в меню");
                System.console().readLine();

            } else if (userChoice.equals("5")) { // сохранить в txt
                saveToTxtFile(phoneBook);
                System.out.println("нажмите Enter, чтобы вернуться в меню");
                System.console().readLine();

            } else if (userChoice.equals("6")) { // сохранить в бинарник
                try {
                    saveToBinFile(phoneBook);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("нажмите Enter, чтобы вернуться в меню");
                System.console().readLine();

            } else if (userChoice.equals("7")) { // чтение из txt файла
                phoneBook = readFromTxtFile();
                System.out.println("нажмите Enter, чтобы вернуться в меню");
                System.console().readLine();

            } else if (userChoice.equals("8")) { // чтение из бинарного файла
                try {
                    phoneBook = readFromBinFile();
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                System.out.println("нажмите Enter, чтобы вернуться в меню");
                System.console().readLine();
            } else if (userChoice.equals("9")) { // новая книга
                phoneBook = createNewPhoneBook();
                System.out.println("нажмите Enter, чтобы вернуться в меню");
                System.console().readLine();

            } else if (userChoice.equals("0")) { // новая книга
                run = false;
                System.out.println("работа закончена");


            } else {
                ClearConsole();
                System.out.println("нет такого пункта меню..");
                System.out.println("нажмите Enter, чтобы вернуться в меню");
                System.console().readLine();
            }

        }
    }

    public static void findContact(HashMap<String, ArrayList<String>> map) {
        ClearConsole();
        System.out.print("введите логин контакта: ");
        String name = getUserInput();
        if (map.get(name) == null) {
            System.out.println("такого контакта в книге нет");
            ;
        } else {
            System.out.println("на контакт записаны номера:");
            for (int i = 0; i < map.get(name).size(); i++) {
                System.out.println((i + 1) + ". " + map.get(name).get(i));
            }
        }
    }

    public static Map<String, ArrayList<String>> editContact(HashMap<String, ArrayList<String>> map) {
        ClearConsole();
        System.out.print("какой контакт надо отредактировать: ");
        String name = getUserInput();
        if (map.get(name) == null) {
            System.out.println("такого контакта в книге нет");
            ;
        } else {
            System.out.println("на контакт записаны номера:");
            for (int i = 0; i < map.get(name).size(); i++) {
                System.out.println((i + 1) + ". " + map.get(name).get(i));
            }
            System.out.print("введите номер пункта, который нужно удалить: ");
            try {
                int indexToDel = Integer.parseInt(getUserInput()) - 1;
                String removedTel = map.get(name).remove(indexToDel);
                System.out.println("телефон " + removedTel + " удален");
            } catch (Exception e) {
                System.out.println("ошибка записи: " + e);
                System.out.println("введите пункт");
            }

        }

        return map;
    }

    public static void showAll(HashMap<String, ArrayList<String>> map) {
        ClearConsole();
        if (map.isEmpty()) {
            System.out.println("справочник пуст");
        } else {
            map.entrySet().forEach(entery -> {
                // ArrayList<String> telArrayList = entery.getValue();
                System.out.println(entery.getKey() + ":");
                for (int i = 0; i < entery.getValue().size(); i++) {
                    System.out.println((i + 1) + ". " + entery.getValue().get(i));
                }
            });

        }
    }

    public static void saveToTxtFile(HashMap<String, ArrayList<String>> map) {
        ClearConsole();
        StringBuilder str = new StringBuilder();
        try (BufferedWriter br = new BufferedWriter(new FileWriter("phones.txt"))) {
            map.entrySet().forEach(entery -> {
                // ArrayList<String> telArrayList = entery.getValue();
                str.append(entery.getKey() + ":");
                for (int i = 0; i < entery.getValue().size(); i++) {
                    str.append(entery.getValue().get(i));
                    if (i < entery.getValue().size() - 1) {
                        str.append(",");
                    } else {
                        str.append("\n");
                    }
                }
            });
            br.write(str.toString());
            System.out.println("книга сохранена в файле phones.txt");
        } catch (Exception e) {
            System.out.println("ошибка записи: " + e);
        }
    }

    public static void saveToBinFile(HashMap<String, ArrayList<String>> map) throws IOException {
        ClearConsole();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("phones_bin"))) {
            oos.writeObject(map);
            System.out.println("книга сохранена в файле phones_bin");
        } catch (Exception e) {
            System.out.println("ошибка записи: " + e);
        }
    }

    public static HashMap<String, ArrayList<String>> readFromBinFile() throws IOException, ClassNotFoundException {
        ClearConsole();
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("phones_bin"))) {
            map = (HashMap<String, ArrayList<String>>) ois.readObject();
            System.out.println("справочник загружен");
        } catch (FileNotFoundException e) {
            System.err.println("ошибка: файл не найден.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("ошибка: не возможно прочесть файл.");
            e.printStackTrace();
        }
        return map;
    }

    public static HashMap<String, ArrayList<String>> readFromTxtFile() {
        ClearConsole();
        HashMap<String, ArrayList<String>> resultMap = new HashMap<String, ArrayList<String>>();

        try (BufferedReader br = new BufferedReader(new FileReader("phones.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // System.out.println(line);
                String[] splitted = line.split(":");
                resultMap.put(splitted[0], new ArrayList<String>(Arrays.asList(splitted[1].split(","))));
            }
            System.out.println("справочник загружен");
        } catch (Exception e) {
            System.out.println("ошибка записи: " + e);
        }
        return resultMap;
    }

    public static Map<String, ArrayList<String>> addInfo(HashMap<String, ArrayList<String>> bookName) {
        ClearConsole();
        System.out.print("Введите логин контакта: ");
        String name = getUserInput();
        if (bookName.get(name) == null) {
            bookName.put(name, new ArrayList<String>());
        } else {
            System.out.println("такой логин уже есть");
        }

        System.out.print("введите телефон для добавления: ");
        String tel = getUserInput();
        bookName.get(name).add(tel);
        System.out.println("контакт добавлен");
        return bookName;

    }

    public static HashMap<String, ArrayList<String>> createNewPhoneBook() {
        ClearConsole();
        HashMap<String, ArrayList<String>> newPhoneBook = new HashMap<String, ArrayList<String>>();
        System.out.println("новая книга создана.");
        return newPhoneBook;
    }

    public static void ClearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name"); // Check the current operating system

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getUserInput() {
        // try (Scanner scanner = new Scanner(System.in)) {
        // // String userInput = "";
        // String userInput = scanner.next();
        // return userInput;
        // }

        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // String result = "";
        // try {
        // String userInput = br.readLine();
        // byte bytes[] = userInput.getBytes("ibm866");
        // result = new String(bytes, "UTF-8");
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        String result = System.console().readLine();
        return result;
    }

}