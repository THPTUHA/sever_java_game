package com.example.demo.service;

import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("rawtypes")
@Service
public class CloudinaryService {
    private final Cloudinary cloudinary = Singleton.getCloudinary();
    public String uploadImage(MultipartFile file){
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url")+"";
        } catch (Exception ex) {
            System.out.print(ex);
            return null;
        }
    }
}
