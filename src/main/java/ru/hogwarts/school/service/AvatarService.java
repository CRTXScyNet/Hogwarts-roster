package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@Service
@Transactional
public class AvatarService {
    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);
    @Value("${student.avatar.dir.path}")
    private String avatarsDir;
    private AvatarRepository avatarRepository;
    private StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService service) {
        this.avatarRepository = avatarRepository;
        this.studentService = service;
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Был вызван метод 'uploadAvatar'");
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
        logger.info("Был вызван метод 'findAvatar'");
        return avatarRepository.findByStudentId(studentId).orElse(null);
    }

    private Avatar getAvatar(Long studentId) {
        logger.info("Был вызван метод 'getAvatar'");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private String getExtension(String s) {
        logger.info("Был вызван метод 'getExtension'");
        int dot = s.lastIndexOf('.');
        return s.substring(dot + 1);
    }

    public Collection<Avatar> getAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("Был вызван метод 'getAvatars'");
        PageRequest request = PageRequest.of(pageNumber, pageSize);
        return avatarRepository.findAll(request).getContent();
    }
}
