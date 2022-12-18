package pet.juniors_dev.elibrary.utility;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pet.juniors_dev.elibrary.dto.FileDto;
import pet.juniors_dev.elibrary.exception.FileException;
import pet.juniors_dev.elibrary.exception.FileWriteException;
import pet.juniors_dev.elibrary.exception.GCPFileUploadException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class CloudStorageUtil {
    @Value("${gcp.config.file}")
    private String gopConfigFile;

    @Value("${gcp.project.id}")
    private String gopProjectId;

    @Value("${gcp.bucket.id}")
    private String gopBucketId;

    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;

    public FileDto uploadFile(MultipartFile file) throws FileException, FileWriteException, GCPFileUploadException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new FileException("File name is null");
        try {
            String contentType = file.getContentType();
            byte[] fileData = file.getBytes();
            return uploadToStorage(fileName, fileData, contentType);
        } catch (IOException e) {
            throw new GCPFileUploadException("An error occurred while storing data to GCS");
        }
    }

    private FileDto uploadToStorage(String fileName, byte[] fileData, String contentType) throws IOException, GCPFileUploadException {
        InputStream inputStream = new ClassPathResource(gopConfigFile).getInputStream();
        StorageOptions options = StorageOptions.newBuilder().setProjectId(gopProjectId)
                .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
        Storage storage = options.getService();
        Bucket bucket = storage.get(gopBucketId, Storage.BucketGetOption.fields());
        String randomId = UUID.randomUUID().toString();
        Blob blob = bucket.create(gcpDirectoryName + "/" +
                randomId + "." + FilenameUtils.getExtension(fileName), fileData, contentType);
        if (blob != null) {
            return new FileDto(storage.getOptions().getHost() + "/" +
                    blob.getBucket() + "/" +
                    blob.getName(), blob.getMediaLink());
        }
        throw new GCPFileUploadException("An error occurred while storing data to GCS");
    }
}
