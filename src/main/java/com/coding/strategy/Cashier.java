package com.coding.strategy;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Cashier {

    /**
     * 会员,策略对象
     */
    private Buyer buyer;

    public Cashier(Buyer buyer) {
        this.buyer = buyer;
    }

    public BigDecimal quote(BigDecimal orderPrice) {
        return this.buyer.calPrice(orderPrice);
    }

    public static void main(String[] args) {
        //选择并创建需要使用的策略对象
        Buyer strategy = new VipBuyer();
        //创建上下文
        Cashier cashier = new Cashier(strategy);
        //计算价格
        BigDecimal quote = cashier.quote(new BigDecimal(300));
        System.out.println("普通会员商品的最终价格为：" + quote.doubleValue());

        strategy = new SuperVipBuyer();
        cashier = new Cashier(strategy);
        quote = cashier.quote(new BigDecimal(300));
        System.out.println("超级会员商品的最终价格为：" + quote.doubleValue());
    }
}
