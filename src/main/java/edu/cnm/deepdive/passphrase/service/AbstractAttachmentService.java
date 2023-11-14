package edu.cnm.deepdive.passphrase.service;

import edu.cnm.deepdive.passphrase.model.entity.Attachment;
import edu.cnm.deepdive.passphrase.model.entity.User;
import java.io.IOException;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface AbstractAttachmentService {
  Iterable<Attachment> readAll(@NonNull User user, @NonNull UUID passphraseKey);
  Attachment read(@NonNull User user, @NonNull UUID passphraseKey, @NonNull UUID attachmentKey);

  Resource readContent(@NonNull User user, @NonNull UUID passphraseKey, @NonNull UUID attachmentKey)
      throws IOException;

  Attachment store(@NonNull User user, @NonNull UUID passphraseKey, @NonNull MultipartFile file);
  void delete(@NonNull User user, @NonNull UUID passphraseKey, @NonNull UUID attachmentKey);

}
