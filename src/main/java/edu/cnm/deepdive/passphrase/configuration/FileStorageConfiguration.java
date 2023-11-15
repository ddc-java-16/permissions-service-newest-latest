package edu.cnm.deepdive.passphrase.configuration;

import java.security.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "file-storage")
@Component

public class FileStorageConfiguration {

  /** Flag Controlling whethter the application home directory will be used as the file storage directory. */

  private boolean applicationHome = true;

  private String directory = "uploads";
    private Pattern subdirectoryPattern = Pattern.compile("^(.{4})(.{2})(.{2}).*$");
    private Set<String> whitelist = new LinkedHashSet<>();
    private FileNameProperties filename;
  public boolean isApplicationHome() {
    return applicationHome;
  }

  public void setApplicationHome(boolean applicationHome) {
    this.applicationHome = applicationHome;
  }

  public String getDirectory() {
    return directory;
  }

  public void setDirectory(String directory) {
    this.directory = directory;
  }

  public Pattern getSubdirectoryPattern() {
    return subdirectoryPattern;
  }

  public void setSubdirectoryPattern(Pattern subdirectoryPattern) {
    this.subdirectoryPattern = subdirectoryPattern;
  }

  public Set<String> getWhitelist() {
    return whitelist;
  }

  public void setWhitelist(Set<String> whitelist) {
    this.whitelist = whitelist;
  }

  public FileNameProperties getFilename() {
    return filename;
  }

  public void setFilename(
      FileNameProperties filename) {
    this.filename = filename;
  }

  public static class FileNameProperties {
    private String unknown;
    private String format;
    private TimestampProperties timestamp;
    private int randomizerLimit;
    // TODO: 11/9/23

    public String getUnknown() {
      return unknown;
    }

    public void setUnknown(String unknown) {
      this.unknown = unknown;
    }

    public String getFormat() {
      return format;
    }

    public void setFormat(String format) {
      this.format = format;
    }

    public int getRandomizerLimit() {
      return randomizerLimit;
    }

    public void setRandomizerLimit(int randomizerLimit) {
      this.randomizerLimit = randomizerLimit;
    }

    public TimestampProperties getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(TimestampProperties timestamp) {
      this.timestamp = timestamp;
    }

    public static class TimestampProperties {
      private String format;
      private String timeZone;

      public String getFormat() {
        return format;
      }

      public void setFormat(String format) {
        this.format = format;
      }

      public String getTimeZone() {
        return timeZone;
      }

      public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
      }
    }
  }
}
