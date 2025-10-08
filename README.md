# DATA ACCESS OBJECT

* Tấn Phát

* Gồm những entity sau:
  * User (dựa trên code của Khoa đã test trong mục tham khảo).
  * Password (dựa trên code của Khoa đã test trong mục tham khảo).
  * Category (gồm các trường: id (tạo tự động), categoryName, icon).
  * Item (lớp trừu tượng, gồm các trường: id (tạo tự động), name, price, image, discount, noiSanXuat, brand).
  * washer (kế thừa từ Item, gồm các trường: loaiMayGiat, hieuSuat, khoiLuongGiat).
  * Fridge (kế thừa từ Item, gồm các trường: kieuTu, dungTich, congSuat).
  * Tivi (kế thừa từ Item, gồm các trường: manHinh, doPhanGiai, tanSoQuet).
  * airCon (kế thừa từ Item, gồm các trường: loaiMay, inverter (boolean), congSuatLamLanh).
  * stove (kế thừa từ Item, gồm các trường: loaiBep, tongCongSuat, kichThuocVungNau).
  * Cooker (kế thừa từ Item, gồm các trường: dungTich, congSuat).
  * vacuum (kế thừa từ Item, gồm các trường: loaiMay, congSuatHut, doOnCaoNhat).
  * fan (kế thừa từ Item, gồm các trường: loaiQuat, congSuatMucGio, canhQuat).
