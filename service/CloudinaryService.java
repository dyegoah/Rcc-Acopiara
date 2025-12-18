package br.com.higitech.rccAcopiara.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(
            @Value("${cloudinary.cloud_name}") String cloudName,
            @Value("${cloudinary.api_key}") String apiKey,
            @Value("${cloudinary.api_secret}") String apiSecret) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        this.cloudinary = new Cloudinary(config);
    }

    public Map uploadFile(MultipartFile file) throws IOException {
        File uploadedFile = convertMultiPartToFile(file);
        // "resource_type", "auto" permite enviar VÍDEO ou IMAGEM automaticamente
        Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.asMap("resource_type", "auto"));
        uploadedFile.delete();
        return uploadResult;
    }

    public Map deleteFile(String publicId) throws IOException {
        // Tenta deletar como imagem primeiro, se falhar tenta como vídeo
        try {
            return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            return cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "video"));
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}