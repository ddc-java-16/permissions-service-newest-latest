package edu.cnm.deepdive.passphrase.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "word")
public class Word {

  @NonNull
  @Id
  @Column(name = "word_id", updatable = false)
  @JsonIgnore
  private Long id;

private int order;
  @NonNull
  @Column(nullable = false, updatable = false)
  @JsonValue
  private String value;

  @NonNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "passphrase_id", nullable = false)
  @JsonIgnore
  private Passphrase passphrase;

  @NonNull
  public Long getId() {
    return id;
  }

  @NonNull
  public String getValue() {
    return value;
  }

  public void setValue(@NonNull String value) {
    this.value = value;
  }

  @NonNull
  public Passphrase getPassphrase() {
    return passphrase;
  }

  public void setPassphrase(@NonNull Passphrase passphrase) {
    this.passphrase = passphrase;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }
}
