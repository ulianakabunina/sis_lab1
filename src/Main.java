import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Выберите процессик для запуска:");
            System.out.println("1 - Блокнотичек");
            System.out.println("2 - Калькуляторчик");
            System.out.println("3 - Пэинтик");
            System.out.print("Ваш выбор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            String processName = null;

            switch (choice) {
                case 1 -> processName = "notepad.exe";
                case 2 -> processName = "calc.exe";
                case 3 -> processName = "mspaint.exe";
                default -> {
                    System.out.println("ААААА НЕПРАВИЛЬНО! ГЛАЗА РАЗУЙ!");
                    return;
                }
            }

            Process process = new ProcessBuilder(processName).start();
            System.out.println("Процесс " + processName + " запущен с PID: " + process.pid());

            System.out.print("Закрыть процесс? (д/н): ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("д")) {
                process.destroy();
                System.out.println("Процесс завершён.");
            } else {
                System.out.println("Процесс оставлен работать.");
            }

        } catch (IOException e) {
            System.out.println("Ошибка запуска: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
