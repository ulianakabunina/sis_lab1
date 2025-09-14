import java.io.IOException;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Выберите процессик для запуска:");
            System.out.println("1 - Блокнотичек");
            System.out.println("2 - Калькуляторчик");
            System.out.println("3 - Пэинтик");
            System.out.println("4 - Все процессики сразу!");
            System.out.print("Ваш выбор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            List<String> processesToStart = new ArrayList<>();

            switch (choice) {
                case 1 -> processesToStart.add("notepad.exe");
                case 2 -> processesToStart.add("calc.exe");
                case 3 -> processesToStart.add("mspaint.exe");
                case 4 -> {
                    processesToStart.add("notepad.exe");
                    processesToStart.add("calc.exe");
                    processesToStart.add("mspaint.exe");
                }
                default -> {
                    System.out.println("ААААА НЕПРАВИЛЬНО! ГЛАЗА РАЗУЙ!");
                    return;
                }
            }

            List<Process> runningProcesses = new ArrayList<>();
            Map<String, Process> processMap = new HashMap<>();

            for (String processName : processesToStart) {
                try {
                    Process process = new ProcessBuilder(processName).start();
                    runningProcesses.add(process);
                    processMap.put(processName, process);
                    System.out.println("Процесс " + processName + " запущен с PID: " + process.pid());
                } catch (IOException e) {
                    System.out.println("Ошибка запуска " + processName + ": " + e.getMessage());
                }
            }

            if (runningProcesses.isEmpty()) {
                System.out.println("Ни один процесс не был запущен!");
                return;
            }

            System.out.print("\nЗакрыть все процессы? (д/н): ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("д")) {
                for (Process process : runningProcesses) {
                    process.destroy();
                }
                System.out.println("Все процессы завершены.");

                System.out.println("\nИнформация о всех процессах:");

                for (Map.Entry<String, Process> entry : processMap.entrySet()) {
                    String processName = entry.getKey();
                    Process process = entry.getValue();

                    Map<String, String> processInfo = new HashMap<>();
                    processInfo.put("Имя процессика", processName);
                    processInfo.put("Пиды", String.valueOf(process.pid()));

                    try {
                        processInfo.put("Кодик завершения", String.valueOf(process.exitValue()));
                    } catch (IllegalThreadStateException e) {
                        processInfo.put("Кодик завершения", "Ещё не завершился");
                    }

                    processInfo.put("Статусик выполнения", String.valueOf(process.isAlive()));

                    System.out.println("\n--- " + processName + " ---");
                    for (Map.Entry<String, String> infoEntry : processInfo.entrySet()) {
                        System.out.println(infoEntry.getKey() + ": " + infoEntry.getValue());
                    }
                    System.out.println("----------------------");
                }

                System.out.println("\nСписок всех PID'ов:");
                for (Process process : runningProcesses) {
                    System.out.println("PID: " + process.pid());
                }

            } else {
                System.out.println("Процессы оставлены работать.");

                System.out.println("\nСписок всех запущенных PID'ов:");
                for (Process process : runningProcesses) {
                    System.out.println("PID: " + process.pid() + " (" +
                            getProcessName(processMap, process) + ")");
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static String getProcessName(Map<String, Process> processMap, Process targetProcess) {
        for (Map.Entry<String, Process> entry : processMap.entrySet()) {
            if (entry.getValue().equals(targetProcess)) {
                return entry.getKey();
            }
        }
        return "Неизвестный процесс";
    }
}