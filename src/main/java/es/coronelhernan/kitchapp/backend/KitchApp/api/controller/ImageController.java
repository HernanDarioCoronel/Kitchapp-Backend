package es.coronelhernan.kitchapp.backend.KitchApp.api.controller;

import es.coronelhernan.kitchapp.backend.KitchApp.infrastructure.storage.LocalFileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final LocalFileStorageService storageService;

    public ImageController(LocalFileStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {
        String filename = storageService.store(file);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/images/")
                .path(filename)
                .build()
                .toUri();
        Map<String, Object> body = new HashMap<>();
        body.put("filename", filename);
        body.put("url", uri.toString());
        body.put("contentType", StringUtils.hasText(file.getContentType()) ? file.getContentType() : "application/octet-stream");
        body.put("size", file.getSize());
        return ResponseEntity.created(uri).body(body);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serve(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        String contentType = guessContentType(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

    private String guessContentType(String filename) {
        try {
            Path p = Paths.get(filename);
            String probed = Files.probeContentType(p);
            if (probed != null) return probed;
        } catch (IOException ignored) {
        }
        if (filename.endsWith(".png")) return "image/png";
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) return "image/jpeg";
        if (filename.endsWith(".gif")) return "image/gif";
        return "application/octet-stream";
    }
}
