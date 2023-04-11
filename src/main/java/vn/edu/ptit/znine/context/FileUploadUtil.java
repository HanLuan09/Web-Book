package vn.edu.ptit.znine.context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path path = Paths.get(uploadDir);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = path.resolve(fileName);
			Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			// TODO: handle exception
			throw new IOException("Count not save image file"+fileName);
		}
	}
}