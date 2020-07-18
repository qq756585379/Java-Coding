package com.coding.strategy;

import java.math.BigDecimal;

/**
 * 超级会员
 */
public class SuperVipBuyer implements Buyer {

    @Override
    public BigDecimal calPrice(BigDecimal orderPrice) {
        return orderPrice.multiply(new BigDecimal(0.8));
    }
}

