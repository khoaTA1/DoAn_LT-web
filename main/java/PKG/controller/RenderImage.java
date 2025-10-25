package PKG.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import PKG.constant.DirectoryPath;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class RenderImage {
	//private static final long serialVersionUID = 1L;

	@GetMapping("image")
	protected void renderImage(@RequestParam("fname") String fileName, HttpServletResponse resp) throws IOException {

		File file = new File(DirectoryPath.dir + "\\" + fileName);

		/*
		 * String mime = getServletContext().getMimeType(file.getName()); if (mime ==
		 * null) mime = "application/octet-stream"; resp.setContentType(mime);
		 */
		if (!file.exists() || file.isDirectory()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
		resp.setContentType("image/jpeg");
		if (file.exists()) {
			IOUtils.copy(new FileInputStream(file), resp.getOutputStream());
		} else {

		}
	}
}
