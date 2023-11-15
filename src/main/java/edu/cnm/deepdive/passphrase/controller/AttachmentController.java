package edu.cnm.deepdive.passphrase.controller;

import edu.cnm.deepdive.passphrase.model.entity.Attachment;
import edu.cnm.deepdive.passphrase.service.AbstractAttachmentService;
import edu.cnm.deepdive.passphrase.service.AbstractUserService;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/passphrases/{passphrasesKey}/attachements")
public class AttachmentController {

  private final AbstractUserService userSerivce;
  private final AbstractAttachmentService attachmentService;

  @Autowired
  public AttachmentController(AbstractUserService userSerivce,
      AbstractAttachmentService attachmentService) {
    this.userSerivce = userSerivce;
    this.attachmentService = attachmentService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  Iterable<Attachment> get(@PathVariable UUID passphraseKey) {
    return attachmentService.readAll(userSerivce.getCurrentUser(), passphraseKey);
  }

  @GetMapping(path = "/{attachmentKey}", produces = MediaType.APPLICATION_JSON_VALUE)
  Attachment get(@PathVariable UUID passphrasesKey, @PathVariable UUID attachmentKey) {
    return attachmentService.read(userSerivce.getCurrentUser(), passphrasesKey, attachmentKey);
  }

  @GetMapping(path = "/{attachmentKey}/content")
  public ResponseEntity<Resource> getContent(@PathVariable UUID passphrasesKey,
      @PathVariable UUID attachmentKey) {
    Attachment attachment = attachmentService.read(userSerivce.getCurrentUser(), passphrasesKey,
        attachmentKey);
    Resource resource = attachmentService.readContent(userSerivce.getCurrentUser(), passphrasesKey,
        attachmentKey);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            String.format("attachment: filename=\"%s\"", attachment.getFilename()))
        .header(HttpHeaders.CONTENT_TYPE, attachment.getContentType())
        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(attachment.getSize()))
        .body(resource);

  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Attachment> post(@PathVariable UUID passphrasesKey, @RequestParam
  MultipartFile file) throws IOException {
    Attachment attachment = attachmentService.store(userSerivce.getCurrentUser(), passphrasesKey,
        file);
    URI location = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(AttachmentController.class)
            .get(passphrasesKey, attachment.getKey())
    ).toUri();
    return ResponseEntity.created(location)
        .body(attachment);
  }
  @DeleteMapping(path = "/{attachmentkey}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID passphrasesKey, @PathVariable UUID attachmentKey) {
    attachmentService.delete(userSerivce.getCurrentUser(), passphrasesKey, attachmentKey);

  }

}
