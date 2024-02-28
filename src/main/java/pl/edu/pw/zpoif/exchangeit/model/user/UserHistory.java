package pl.edu.pw.zpoif.exchangeit.model.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_history")
public class UserHistory extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "share_price_usd", nullable = false)
    private double sharePriceUSD;

    @Column(nullable = false)
    private double quantity;

    @Column(name = "total_usd", nullable = false)
    private double totalUSD; //sharePrice * quantity

    @Column(name = "bought_sold",nullable = false)
    private String boughtSold;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getSymbol() {
        return symbol;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getSharePriceUSD() {
        return sharePriceUSD;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getTotalUSD() {
        return totalUSD;
    }

    public String getBoughtSold() {
        return boughtSold;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setSharePriceUSD(double sharePriceUSD) {
        this.sharePriceUSD = sharePriceUSD;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setTotalUSD(double totalUSD) {
        this.totalUSD = totalUSD;
    }

    public void setBoughtSold(String boughtSold) {
        this.boughtSold = boughtSold;
    }
}
