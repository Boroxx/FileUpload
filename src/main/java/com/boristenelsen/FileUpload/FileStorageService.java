package com.boristenelsen.FileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class FileStorageService {


    private String fileLocation;
    private List<String> allImagesBase64Encoded;


    @Autowired
    private FileModelRepository fileModelRepository;


    /*Filtert alle Bilddateien aus der Datenbank und hängt diese Base64 encodiert an eine List<String>.
    * Diese Liste aus Strings wird benötigt um die bilder in Thymeleaf zu rendern
    * */
    public List<String> produceBase64EncodedFileList(){
        allImagesBase64Encoded = new ArrayList<String>();
        for(FileModel file : fileModelRepository.findAll()){
            if(file.getFileType().contains("jpeg"))
            allImagesBase64Encoded.add(convertBinImageToString(file.getData()));
        }
        return allImagesBase64Encoded;

    }

    /* Es wird das multipartFile welches durch den POST-Request übergeben wird, in einer Datenbank gespeichert mithilfe
     * des FileModelRepositorys
     */
    public FileModel storeFile(MultipartFile multipartFile){
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {

            if(filename.contains("..")) throw new FileStorageException("Die Datei hat einen Falschen Pfad " + filename);
            FileModel fm = new FileModel(filename, multipartFile.getContentType(), multipartFile.getBytes());

            return fileModelRepository.save(fm);

        } catch (IOException e) {
            throw new FileStorageException("Kann die Datei: " + filename + "nicht speichern", e);
        }

    }
    public FileModel getFileModel(Long id){
        return fileModelRepository.findById(id).orElseThrow(()-> new FileSystemNotFoundException("Datei nicht gefunden: " + id));
    }

    public List<FileModel> getAllFileModels(){
        return fileModelRepository.findAll();
    }


    /* Konvertiert eine Byte-Array zu einem Base64 encoded String
     */
    public static String convertBinImageToString(byte[] binImage) {
        if(binImage!=null && binImage.length>0) {
            return Base64.getEncoder().encodeToString(binImage);
        }
        else
            return "";
    }


}
