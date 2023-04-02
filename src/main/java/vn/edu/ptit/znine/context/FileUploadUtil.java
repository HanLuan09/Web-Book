package vn.edu.ptit.znine.context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static void saveFile(String upload, String fileName, MultipartFile multipartFile ) throws IOException {
		Path path = Paths.get(upload);
		if(!Files.exists(path)) {
			Files.createDirectories(path);
		}
		try(InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = path.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new IOException("Count not save image file"+ fileName);
		}
	}
}
