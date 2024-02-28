package pl.edu.pw.zpoif.exchangeit.model.user;


import jakarta.persistence.*;


@Entity
@Table(name = "users_assets")
public class UserAssets extends UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private double quantity;

    @Column(name = "share_price_usd", nullable = false)
    private double sharePriceUSD;

    @Column(name = "total_usd", nullable = false)
    private double totalUSD; //sharePrice * quantity


    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getSharePriceUSD() {
        return sharePriceUSD;
    }

    public double getTotalUSD() {
        return totalUSD;
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

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setSharePriceUSD(double sharePriceUSD) {
        this.sharePriceUSD = sharePriceUSD;
    }

    public void setTotalUSD(double totalUSD) {
        this.totalUSD = totalUSD;
    }
}
