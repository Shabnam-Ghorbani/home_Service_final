package ir.maktab.home_service.service.impl;

import ir.maktab.home_service.service.interf.ImagInter;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;

@Component
public class Image implements ImagInter {
    public static File fileWriter(Path path, byte[] photo) throws IOException {
        String randomCode = "/" + String.valueOf((int) (Math.random() * (999 - 100 + 1) + 100)) + ".jpg";
        path = Path.of(path.toString() + randomCode);
        FileOutputStream fos = new FileOutputStream(path.toFile());
        fos.write(photo);
        return new File(path.toUri());

    }

    @Override
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
