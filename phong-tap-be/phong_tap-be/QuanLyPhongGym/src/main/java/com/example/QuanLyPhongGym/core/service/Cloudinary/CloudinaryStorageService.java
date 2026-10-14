package com.example.QuanLyPhongGym.core.service.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.util.Base64;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CloudinaryStorageService {
    private final Cloudinary cloudinary;

    public CloudinaryStorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadBase64Image(
            String imageData,
            String folder) throws Exception {

        if (imageData == null || imageData.isBlank()) {
            return null;
        }

        String base64 = imageData;

        if (imageData.startsWith("data:image")) {
            int commaIndex = imageData.indexOf(",");

            if (commaIndex < 0) {
                throw new IllegalArgumentException(
                        "Dữ liệu ảnh Base64 không hợp lệ");
            }

            base64 = imageData.substring(commaIndex + 1);
        }

        byte[] bytes = Base64.getDecoder().decode(base64);

        Map<?, ?> result = cloudinary.uploader().upload(
                bytes,
                ObjectUtils.asMap(
                        "folder", folder,
                        "resource_type", "image"));

        String secureUrl = (String) result.get("secure_url");

        if (secureUrl == null || secureUrl.isBlank()) {
            throw new IllegalStateException(
                    "Cloudinary không trả về URL ảnh");
        }

        return secureUrl;
    }
}
