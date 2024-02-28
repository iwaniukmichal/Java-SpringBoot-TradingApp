package pl.edu.pw.zpoif.exchangeit.model.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users_trading")
public class UserTrading extends UserEntity {

    @Id
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(name = "cash_usd", nullable = false)
    private double cashUSD;

    @Column(name = "stock_usd", nullable = false)
    private double stockUSD;

    @Column(name = "total_usd", nullable = false)
    private double totalUSD; //cash + stock

    @Column(name = "initial_usd", nullable = false)
    private double initialUSD;

    @Column(name = "profit_usd", nullable = false)
    private double profitUSD; // total - initial

    public Long getUserId() {
        return userId;
    }

    public double getCashUSD() {
        return cashUSD;
    }

    public double getStockUSD() {
        return stockUSD;
    }

    public double getTotalUSD() {
        return totalUSD;
    }

    public double getInitialUSD() {
        return initialUSD;
    }

    public double getProfitUSD() {
        return profitUSD;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCashUSD(double cashUSD) {
        this.cashUSD = cashUSD;
    }

    public void setStockUSD(double stockUSD) {
        this.stockUSD = stockUSD;
    }

    public void setTotalUSD(double totalUSD) {
        this.totalUSD = totalUSD;
    }

    public void setInitialUSD(double initialUSD) {
        this.initialUSD = initialUSD;
    }

    public void setProfitUSD(double profitUSD) {
        this.profitUSD = profitUSD;
    }
}
