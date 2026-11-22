package by.gstu.sales;

import by.gstu.sales.model.Transaction;
import by.gstu.sales.service.SalesService;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("app.properties")) {
            props.load(in);
        } catch (IOException e) {
            System.err.println("Ошибка чтения app.properties. Убедитесь, что файл существует.");
            return;
        }

        String csvPath = props.getProperty("csv.input.path");
        String filterProductId = props.getProperty("filter.product.id");
        int topCount = Integer.parseInt(props.getProperty("report.top.sales.count", "10"));
        String separator = props.getProperty("csv.separator", ",");

        try {
            List<String> lines = Files.readAllLines(Paths.get(csvPath));
            List<Transaction> transactions = lines.stream()
                    .skip(1) // Пропускаем заголовок
                    .filter(line -> !line.trim().isEmpty())
                    .map(line -> {
                        String[] parts = line.split(separator);
                        return new Transaction(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]);
                    })
                    .collect(Collectors.toList());

            SalesService service = new SalesService();

            System.out.println("--- Sales by Region ---");
            Map<String, Double> byRegion = service.getSalesByRegion(transactions);
            byRegion.forEach((region, sum) -> System.out.printf("%s: %.2f%n", region, sum));

            System.out.println("\n--- Top " + topCount + " Sales (ID) ---");
            service.getTopSalesIds(transactions, topCount).forEach(System.out::println);

            System.out.println("\n--- Filtered by Product " + filterProductId + " ---");
            service.filterByProductId(transactions, filterProductId).forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Ошибка обработки CSV: " + e.getMessage());
        }
    }
}