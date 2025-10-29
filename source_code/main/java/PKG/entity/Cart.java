package PKG.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Người dùng sở hữu giỏ hàng
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Sản phẩm trong giỏ
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "discount")
    private Double discount;

    public double getTotalPrice() {
        double price = item.getPrice();
        double finalPrice = price * (1 - (discount != null ? discount/100 : 0));
        return finalPrice * quantity;
    }
}
