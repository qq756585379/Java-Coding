package com.coding.strategy;

import java.math.BigDecimal;

public interface Buyer {

    /**
     * 计算应付价格
     */
    BigDecimal calPrice(BigDecimal orderPrice);
}
