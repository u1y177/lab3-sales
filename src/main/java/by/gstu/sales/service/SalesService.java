package by.gstu.sales.service;

import by.gstu.sales.model.Transaction;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис для агрегации и фильтрации данных о продажах.
 */
public class SalesService {

    /**
     * Подсчитывает общую сумму продаж, сгруппированную по региону.
     * @param transactions список транзакций
     * @return Map, где ключ - регион, значение - общая сумма
     */
    public Map<String, Double> getSalesByRegion(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getRegion,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }

    /**
     * Находит ID самых дорогих продаж.
     * @param transactions список транзакций
     * @param limit максимальное количество записей
     * @return список TransactionID, отсортированных по убыванию суммы
     */
    public List<String> getTopSalesIds(List<Transaction> transactions, int limit) {
        return transactions.stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .limit(limit)
                .map(Transaction::getTransactionId)
                .collect(Collectors.toList());
    }

    /**
     * Фильтрует транзакции по ID продукта.
     * @param transactions список транзакций
     * @param productId ID продукта для фильтрации
     * @return список отфильтрованных транзакций
     */
    public List<Transaction> filterByProductId(List<Transaction> transactions, String productId) {
        return transactions.stream()
                .filter(t -> t.getProductId().equals(productId))
                .collect(Collectors.toList());
    }
}