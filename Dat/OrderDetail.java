package PKG.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "hoten", columnDefinition = "nvarchar(255)", nullable = false)
    private String hoTenNguoiNhan;

    @Column(name = "sdt", columnDefinition = "nvarchar(20)", nullable = false)
    private String soDienThoai;

    @Column(name = "diachi", columnDefinition = "nvarchar(500)", nullable = false)
    private String diaChiGiaoHang;

    @Column(name = "phuongthuc", columnDefinition = "nvarchar(100)", nullable = false)
    private String phuongThucThanhToan;

    @Column(name="tongtien", columnDefinition = "decimal(18,2)", nullable = true)
    private Double tongTien;
}
