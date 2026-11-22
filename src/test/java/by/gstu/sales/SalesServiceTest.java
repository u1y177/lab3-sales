package by.gstu.sales;

import by.gstu.sales.model.Transaction;
import by.gstu.sales.service.SalesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SalesServiceTest {
    private SalesService service;
    private List<Transaction> testData;

    @Before
    public void setUp() {
        service = new SalesService();
        testData = Arrays.asList(
            new Transaction("T1", "2025-01-01", "P1", 100.0, "North"),
            new Transaction("T2", "2025-01-02", "P2", 50.0, "South"),
            new Transaction("T3", "2025-01-03", "P1", 200.0, "North")
        );
    }

    @Test
    public void testGetSalesByRegion() {
        Map<String, Double> result = service.getSalesByRegion(testData);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(300.0, result.get("North"), 0.01);
        Assert.assertEquals(50.0, result.get("South"), 0.01);
    }

    @Test
    public void testGetTopSalesIds() {
        List<String> result = service.getTopSalesIds(testData, 2);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("T3", result.get(0));
        Assert.assertEquals("T1", result.get(1));
    }

    @Test
    public void testFilterByProductId() {
        List<Transaction> result = service.filterByProductId(testData, "P1");
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("T1", result.get(0).getTransactionId());
        Assert.assertEquals("T3", result.get(1).getTransactionId());
    }
}