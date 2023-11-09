package edu.cnm.deepdive.passphrase.service;

import edu.cnm.deepdive.passphrase.configuration.FileStorageConfiguration;
import edu.cnm.deepdive.passphrase.configuration.FileStorageConfiguration.FileNameProperties;
import edu.cnm.deepdive.passphrase.configuration.FileStorageConfiguration.FileNameProperties.TimestampProperties;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.random.RandomGenerator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.multipart.MultipartFile;
@Service
@Profile("service")
public class LocalFileSystemStorageService implements
    StorageService {
private static final String KEY_PATH_DELIMITER = "/";
private static final String KEY_PATH_FORMAT = "%s";
private static final String INVALID_MEDIA_FORMAT = "%s is not allowed in this storage service.";

private final RandomGenerator rng;
private final Path uploadDirectory;
private final Pattern subdirectoryPattern;
private final Set<String> whitelist;
private final String filenameFormat
private final DateFormat formatter;
private final List<MediaType> contentTypes;

@Autowired
public LocalFileSystemStorageService(FileStorageConfiguration configuration, ApplicationHome home, RandomGenerator rng) {
  this.rng = rng;
  FileNameProperties fileNameProperties = configuration.getFilename();
  TimestampProperties timestampProperties = fileNameProperties.getTimestamp();
  String uploadPath = configuration.getDirectory();
  uploadDirectory = configuration.isApplicationHome()
      ? home.getDir().toPath().resolve(uploadPath)
      : Path.of(uploadPath);
  uploadDirectory.toFile().mkdirs();
  subdirectoryPattern = configuration.getSubdirectoryPattern();
  whitelist = configuration.getWhitelist();
  contentTypes = whitelist.stream().map(MediaType::valueOf).collect(Collectors.toList());
  formatter = new SimpleDateFormat(timestampProperties.getFormat());
  formatter.setTimeZone(TimeZone.getTimeZone(timestampProperties.getTimeZone()));
}
  @Override
  public String store(MultipartFile file) throws IOException, HttpMediaTypeException {
    return null;
  }

  @Override
  public Resource retrieve(String key) throws IOException {
    return null;
  }

  @Override
  public boolean deete(String key)
      throws IOException, UnsupportedOperationException, SecurityException {
    return false;
  }
}
