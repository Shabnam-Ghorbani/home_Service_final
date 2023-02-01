package ir.maktab.home_service.service;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class Image {
    public byte[] fileToBytes(String filename) throws IOException {
        final byte[] buffer = new byte[256];
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (InputStream in = new FileInputStream(new File(filename))) {
                int bytesRead;
                while ((bytesRead = in.read(buffer)) > 0)
                    out.write(buffer, 0, bytesRead);
            }
            return out.toByteArray();
        }
    }
}
