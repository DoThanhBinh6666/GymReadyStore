package com.project.shopapp_backend.controllers;

import com.project.shopapp_backend.dtos.ProductDTO;
import jakarta.validation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController
{
    private String storeFile(MultipartFile file) throws IOException
    {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //Them UUID vao truoc ten file de dam bao ten file la duy nhat
        String uniqueFilename = UUID.randomUUID().toString() + "_" + fileName;
        //duong dan thu muc ma ban muon save file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        //kiem tra va tao thu muc neu no khong ton tai
        if (!Files.exists(uploadDir))
        {
            Files.createDirectories(uploadDir);
        }
        //duong dan day du den file
        Path destination = Paths.get(uploadDir.toString(),uniqueFilename);
        // copy file vao thu muc dich
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @GetMapping
    public ResponseEntity<String> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    )
    {
        return ResponseEntity.ok("getProducts here");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String productId)
    {
        return ResponseEntity.ok("getProductById with id: " + productId);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductDTO productDTO,
                                           //@RequestPart("file") MultipartFile file,
                                           BindingResult result)
    {
        try {
            if (result.hasErrors())
            {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(f -> f.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
           List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<>() : files;
            for (MultipartFile file : files)
            {
                if(file.isEmpty())
                {
                continue;
                }
                //kiem tra kich thuoc va dinh dang
                if (file.getSize() > 10 * 1024 * 1024)
                {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("kich thuoc file qua lon ,maximun size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/"))//kiem tra xem content co rong hay chua duoi image ko
                {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("file phai la 1 file anh");
                }
                //luu file va cap nhat thumbnail trong DTO
                String fileName = storeFile(file);//thay the ham nay voi code cua ban de luu file
                //luu vao bang product_images
            }

            return ResponseEntity.ok("createProduct successfully");
        } catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable long id)
    {
        return ResponseEntity.ok(String.format("xoa thanh cong product co id = %d thanh cong", id));
    }
}
