package PKG.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import PKG.entity.Category;
import PKG.service.CategoryService;
import PKG.service.ItemService;

@ControllerAdvice
public class GlobalInjection {
	@Autowired
	CategoryService cateserv;
	
	@Autowired
	ItemService itemserv;
	
	@ModelAttribute("cateList")
	public List<Category> getAllCategories() {
	    return cateserv.findAll();
	}
	
	@ModelAttribute("fieldNames")
	public Map<String, String> getFieldNames() {
		Map<String, String> fieldNames = new HashMap<>();
		
        fieldNames.put("name", "Tên sản phẩm");
        fieldNames.put("price", "Giá bán");
        fieldNames.put("brand", "Thương hiệu");
        fieldNames.put("origin", "Xuất xứ");
        fieldNames.put("dungTich", "Dung tích (lít)");
        fieldNames.put("inverter", "Có Inverter?");
        fieldNames.put("congSuatLamLanh", "Công suất làm lạnh (HP)");
        fieldNames.put("congSuat", "Công suất (W)");
        fieldNames.put("loaiQuat", "Loại quạt");
        fieldNames.put("congSuatMucGio", "Công suất - mức gió");
        fieldNames.put("canhQuat", "Cánh quạt");
        fieldNames.put("kieuTu", "Kiểu tủ");
        fieldNames.put("loaiBep", "Loại bếp");
        fieldNames.put("tongCongSuat", "Tổng công suất (W)");
        fieldNames.put("kichThuocVungNau", "Kích thước vùng nấu");
        fieldNames.put("manHinh", "Màn hình");
        fieldNames.put("doPhanGiai", "Độ phân giải");
        fieldNames.put("tanSoQuet", "Tần số quét màn hình (Hz)");
        fieldNames.put("loaiMay", "Loại máy");
        fieldNames.put("congSuatHut", "Công suất hút (Pa)");
        fieldNames.put("doOnCaoNhat", "Độ ồn cao nhất (dB)");
        fieldNames.put("loaiMayGiat", "Loại máy giặt");
        fieldNames.put("hieuSuat", "Hiệu suất");
        fieldNames.put("khoiLuongGiat", "Khối lượng giặt (Kg)");
        
        return fieldNames;
	}
	
	@ModelAttribute("trueOrFalseRewrite")
    public Map<String, String> getTrueOrFalseRewrite() {
        Map<String, String> map = new HashMap<>();
        
        map.put("true", "Có");
        map.put("false", "Không");
        
        return map;
    }
}
