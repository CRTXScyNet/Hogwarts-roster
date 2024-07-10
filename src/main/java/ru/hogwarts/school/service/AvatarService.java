package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;


@Service
@Transactional
public class AvatarService {

    @Value("${student.avatar.dir.path}")
    private String avatarsDir;
    private AvatarRepository avatarRepository;
    private StudentService studentService;
    Logger logger = Logger.getLogger(AvatarService.class.getName());

    public AvatarService(AvatarRepository avatarRepository, StudentService service) {
        this.avatarRepository = avatarRepository;
        this.studentService = service;
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.getStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath)) {
            is.transferTo(os);
        }
        Avatar avatar = getAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(null);
    }

    private Avatar getAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private String getExtension(String s) {
        int dot = s.lastIndexOf('.');
        return s.substring(dot + 1);
    }

//    private byte[] generateDataForDB(Path filePath) throws IOException {
//        try (InputStream is = Files.newInputStream(filePath);
//             BufferedInputStream bis = new BufferedInputStream(is, 1024);
//             ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
//            BufferedImage image = ImageIO.read(bis);
//
//            int height = image.getHeight() / (image.getWidth() / 100);
//            BufferedImage newImage = new BufferedImage(100, height, image.getType());
//            Graphics2D graphics2D = image.createGraphics();
//            graphics2D.drawImage(image, 100, height, null);
//            graphics2D.dispose();
//            ImageIO.write(newImage, getExtension(filePath.getFileName().toString()), baos);
//            return baos.toByteArray();
//        }
//    }
}
