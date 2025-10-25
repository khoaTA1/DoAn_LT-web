package PKG.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class inputUpdatePassw {
	@NotBlank(message = "Không được để trống!")
	private String currentPassw;
	
	@NotBlank(message = "Không được để trống!")
	private String newPassw;
	
	@NotBlank(message = "Không được để trống!")
	private String confirmNewPass;
}
