package com.vmware.tanzu.demos.sta.tradingagent.bid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.agent.strategy", havingValue = "pas")
class WarriorsAgent implements BidAgent {
    private final Logger logger = LoggerFactory.getLogger(WarriorsAgent.class);

    @Override
    public List<BidAgentRequest> execute(Context ctx) {
        // Sort input stocks against price.
        final List<Stock> sortedStocks = new ArrayList<>(ctx.stocks());
        sortedStocks.sort(Comparator.comparing(Stock::price));
        final List<Stock> sortedStocks1 = new ArrayList<>(ctx.stocks());
        sortedStocks1.sort(Comparator.comparing(Stock::shares));

        final Stock lowerStock = sortedStocks.get(0);
        final Stock higherStock = sortedStocks1.get(sortedStocks1.size()-1);
        List<BidAgentRequest> bidrequ = new ArrayList<>();
        bidrequ.add((new BidAgentRequest(lowerStock.symbol(), 100)));
        if(higherStock.shares() !=0 )
        bidrequ.add((new BidAgentRequest(higherStock.symbol(), higherStock.shares())));
        logger.info("Found a stock with the lower value: {}", lowerStock.symbol());
        return bidrequ;
    }

    @Override
    public String toString() {
        return "SUPER PAS";
    }
}
