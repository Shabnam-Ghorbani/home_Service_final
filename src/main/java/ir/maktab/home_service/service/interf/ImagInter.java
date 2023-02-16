package ir.maktab.home_service.service.interf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface ImagInter {

    static File fileWriter(Path path, byte[] photo) throws IOException {
        return null;
    }

    byte[] fileToBytes(String filename) throws IOException;
}
