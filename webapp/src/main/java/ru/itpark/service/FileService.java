package ru.itpark.service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileService {
    private final String uploadPath;
    private final String resultPath;
    private String query = "";
    private Path result;

    public FileService() throws IOException {
        uploadPath = System.getenv("UPLOAD_PATH");
        resultPath = System.getenv("RESULT_PATH");
        Files.createDirectories(Paths.get(uploadPath));
    }

    public void readFile(String id, ServletOutputStream os) throws IOException {
        var path = Paths.get(uploadPath).resolve(id);
        Files.copy(path, os);
    }

    public void readAllFiles(String query) throws IOException {
        this.query = query;
            result = Paths.get(this.resultPath).resolve(query);
        if (query != null && !query.isEmpty()) {
                Files.walk(Paths.get(uploadPath))
                        .filter(Files::isRegularFile)
                        .forEach(this::readData);
            }
    }

    public String writeFile(Part part) throws IOException {
        var id = UUID.randomUUID().toString();
        part.write(Paths.get(uploadPath).resolve(id).toString());
        return id;
    }

    public void readData(Path path) {
        try {
            File readFile = path.toFile();
            FileInputStream fis = new FileInputStream(readFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            int number = 1;
            List<String> result = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
//                System.out.println(line); // TODO
//                System.out.println(query); // TODO
//                System.out.println(readFile.getName()); // TODO
                if (line.toLowerCase().contains(query)) {
                    //
                    result.add("[" + readFile.getName()  + "] " + " in line number " +  number + " text: " + line);
                }
                number++;
            }
            for (String s : result) {
                System.out.println(s);
            }
            if (result.size() > 0) {
                writeResult(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Reading data error");
        }
    }

    public void writeResult(List<String> list) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(result.toFile() + ".txt"), StandardCharsets.UTF_8))){
                for (String s : list) {
                    writer.write(s);
                    writer.newLine();
                }
            }catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error write result file");
            }
    }
}
