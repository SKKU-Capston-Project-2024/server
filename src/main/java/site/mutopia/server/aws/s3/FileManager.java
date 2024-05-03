package site.mutopia.server.aws.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileManager {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public String uploadFile(MultipartFile multipartFile, String replaceName) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalArgumentException("file name is null");
        }
        String[] parts = fileName.split("\\.");
        String ext = parts[parts.length - 1];

        if (replaceName != null && !replaceName.isEmpty()) {
            fileName = replaceName + "." + ext;
        }

        String contentType = switch (ext) {
            case "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "txt" -> "text/plain";
            case "csv" -> "text/csv";
            default -> "";
        };

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);

            amazonS3.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException e) {
            log.error("AmazonServiceException: {}", e.getMessage());
            throw new IllegalArgumentException("Failed to upload file");
        } catch (SdkClientException e) {
            log.error("SdkClientException: {}", e.getMessage());
            throw new IllegalArgumentException("Failed to upload file");
        }

        // Fetching object information
        List<S3ObjectSummary> objects = amazonS3.listObjectsV2(bucket).getObjectSummaries();
        for (Object object : objects) {
            System.out.println("object = " + object);
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
