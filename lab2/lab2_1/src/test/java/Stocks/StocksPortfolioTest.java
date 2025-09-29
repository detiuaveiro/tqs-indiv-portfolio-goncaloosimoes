package Stocks;

import org.example.Stocks.IStockmarketService;
import org.example.Stocks.Stock;
import org.example.Stocks.StocksPortfolio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Hamcrest imports
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
// AssertJ imports
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {

    @Mock
    private IStockmarketService stockmarket;

    @InjectMocks
    private StocksPortfolio portfolio;

    @Test
    void testTotalValueHamcrest() {
        // Setup
        when(stockmarket.lookUpPrice("MSFT")).thenReturn(300.0);
        when(stockmarket.lookUpPrice("AAPL")).thenReturn(200.0);

        portfolio.addStock(new Stock("MSFT", 2));
        portfolio.addStock(new Stock("AAPL", 4));

        // Exercise
        double result = portfolio.totalValue();

        // Verify - Hamcrest style
        assertThat(result, is(closeTo(1400.0, 0.01)));
        assertThat(result, is(both(greaterThan(1399.0)).and(lessThan(1401.0))));

        // Verify mock interactions
        verify(stockmarket).lookUpPrice("MSFT");
        verify(stockmarket).lookUpPrice("AAPL");
        verify(stockmarket, never()).lookUpPrice("GOOG");
    }

    @Test
    void testTotalValueAssertJ() {
        // Setup
        when(stockmarket.lookUpPrice("MSFT")).thenReturn(300.0);
        when(stockmarket.lookUpPrice("AAPL")).thenReturn(200.0);

        portfolio.addStock(new Stock("MSFT", 2));
        portfolio.addStock(new Stock("AAPL", 4));

        // Exercise
        double result = portfolio.totalValue();

        // Verify - AssertJ style
        assertThat(result)
                .isEqualTo(1400.0)
                .isCloseTo(1400.0, offset(0.01))
                .isBetween(1399.0, 1401.0)
                .isPositive();

        // Verify mock interactions
        verify(stockmarket).lookUpPrice("MSFT");
        verify(stockmarket).lookUpPrice("AAPL");
        verify(stockmarket, never()).lookUpPrice("GOOG");
    }

    @Test
    void testMostValuableStocksHamcrest() {
        // Setup
        when(stockmarket.lookUpPrice("MSFT")).thenReturn(300.0);
        when(stockmarket.lookUpPrice("AAPL")).thenReturn(200.0);
        when(stockmarket.lookUpPrice("GOOG")).thenReturn(150.0);

        portfolio.addStock(new Stock("MSFT", 2));  // Total value: 600
        portfolio.addStock(new Stock("AAPL", 4));  // Total value: 800
        portfolio.addStock(new Stock("GOOG", 3));  // Total value: 450

        // Exercise
        List<Stock> topStocks = portfolio.mostValuableStocks(2);

        // Verify - Hamcrest style
        assertThat(topStocks, hasSize(2));
        assertThat(topStocks.get(0).getLabel(), is("AAPL"));
        assertThat(topStocks.get(1).getLabel(), is("MSFT"));
    }

    @Test
    void testMostValuableStocksAssertJ() {
        // Setup
        when(stockmarket.lookUpPrice("MSFT")).thenReturn(300.0);
        when(stockmarket.lookUpPrice("AAPL")).thenReturn(200.0);
        when(stockmarket.lookUpPrice("GOOG")).thenReturn(150.0);

        portfolio.addStock(new Stock("MSFT", 2));  // Total value: 600
        portfolio.addStock(new Stock("AAPL", 4));  // Total value: 800
        portfolio.addStock(new Stock("GOOG", 3));  // Total value: 450

        // Exercise
        List<Stock> topStocks = portfolio.mostValuableStocks(2);

        // Verify - AssertJ style
        assertThat(topStocks)
                .hasSize(2)
                .extracting(Stock::getLabel)
                .containsExactly("AAPL", "MSFT");

        // Verify mock interactions
        verify(stockmarket, times(2)).lookUpPrice("MSFT");
        verify(stockmarket, times(2)).lookUpPrice("AAPL");
        verify(stockmarket, times(2)).lookUpPrice("GOOG");
    }
}