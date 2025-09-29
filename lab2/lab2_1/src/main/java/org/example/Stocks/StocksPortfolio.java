package org.example.Stocks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StocksPortfolio {
    private List<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double totalValue() {
        double total = 0;
        for (Stock stock : stocks) {
            total += stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
        return total;
    }

    /**
     * Returns the top N most valuable stocks in the portfolio
     * @param topN the number of most valuable stocks to return
     * @return a list with the topN most valuable stocks in the portfolio
     */
    public List<Stock> mostValuableStocks(int topN) {
        return stocks.stream()
                .sorted((s1, s2) -> Double.compare(
                    stockmarket.lookUpPrice(s2.getLabel()) * s2.getQuantity(),
                    stockmarket.lookUpPrice(s1.getLabel()) * s1.getQuantity()))
                .limit(topN)
                .collect(Collectors.toList());
    }
}