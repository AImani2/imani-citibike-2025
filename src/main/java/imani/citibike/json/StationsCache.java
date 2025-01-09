package imani.citibike.json;

import com.google.gson.Gson;
import imani.citibike.aws.CitibikeRequestHandler;
import imani.citibike.service.CitibikeService;
import imani.citibike.service.CitibikeServiceFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import software.amazon.awssdk.regions.Region;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;

public class StationsCache {

    S3Client s3Client;
    private Instant lastModified;
    private final Gson gson = new Gson();
    private final String BUCKET = "imani.citibike";
    private final String KEY = "request.json";
    private Stations stations;
    private final CitibikeService citibikeService;

    public StationsCache() {
        Region region = Region.US_EAST_2;
        this.s3Client = S3Client.builder()
                .region(region)
                .build();
        this.citibikeService = new CitibikeServiceFactory().getService();
    }

    // proper update
    public Stations getStations() {
        boolean moreThanOneHour = getAgeS3();
        if (!moreThanOneHour && stations != null){
            return stations;
        } else if (stations != null && moreThanOneHour) {
            stations = citibikeService.getStationInfoResponse().blockingGet();
            lastModified = Instant.now();
            writeS3();

        } else if (stations == null && !moreThanOneHour) {
            readS3();
            // update last modified to last modified from s3
        } else if (stations == null && moreThanOneHour) {
            stations = citibikeService.getStationInfoResponse().blockingGet();
            lastModified = Instant.now();
            writeS3();
        }
        return stations;
    }

    public void readS3() {
        GetObjectRequest getObjectRequest = GetObjectRequest
                .builder()
                .bucket(BUCKET)
                .key(KEY)
                .build();

        InputStream in = s3Client.getObject(getObjectRequest);
        stations = gson.fromJson(new InputStreamReader(in), Stations.class);
    }

    public void writeS3() {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(KEY)
                .build();

        String content = gson.toJson(stations);
        s3Client.putObject(putObjectRequest, RequestBody.fromString(content));
    }

    // returns true if the object is older than one hour
    public boolean getAgeS3() {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(BUCKET)
                .key(KEY)
                .build();

        try {
            HeadObjectResponse headObjectResponse = s3Client.headObject(headObjectRequest);
            Instant lastModified = headObjectResponse.lastModified();
            return Duration.between(lastModified, Instant.now()).toHours() > 0;
        } catch (Exception e) {
            // either the file doesn't exist in S3 or you don't have access to it.
            return false;
        }
    }

}

// where and how am I going to call this class?
