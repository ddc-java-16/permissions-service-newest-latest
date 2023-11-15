package edu.cnm.deepdive.passphrase.view;

import com.fasterxml.jackson.databind.util.StdConverter;
import edu.cnm.deepdive.passphrase.service.UUIDStringifier;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UUIDSerializer implements StdConverter<UUID,String>, Converter<UUID, String> {
private final UUIDStringifier stringifier;

  public UUIDSerializer(UUIDStringifier stringifier) {
    this.stringifier = stringifier;
  }

  @Override
  public String convert(UUID uuid) {
    return stringifier.toString(uuid);
  }
}
