package edu.cnm.deepdive.passphrase.service;

import edu.cnm.deepdive.passphrase.model.dao.PassphraseRepository;
import edu.cnm.deepdive.passphrase.model.entity.Passphrase;
import edu.cnm.deepdive.passphrase.model.entity.User;
import edu.cnm.deepdive.passphrase.model.entity.Word;
import java.util.List;
import java.util.UUID;
import java.util.random.RandomGenerator;
import org.springframework.stereotype.Service;

@Service
public class PassphraseService implements
    AbstractPassphraseService {

  private final PassphraseRepository repository;
  private final PassphraseProvider provider;

  public PassphraseService(PassphraseRepository repository, RandomGenerator rng,
      PassphraseProvider provider) {
    this.repository = repository;
    this.provider = provider;
  }

  @Override
  public List<Passphrase> readAll(User user) {
    return user.getPassphrases();
  }

  @Override
  public Passphrase read(User user, UUID key) {

    return repository.findByUserAndKey(user, key).orElseThrow();
  }

  @Override
  public List<Passphrase> search(User user, String fragment) {
    return repository.findAllByUserAndNameContainsIgnoreCaseOrderByNameAsc(user, fragment);
  }

  @Override
  public Passphrase create(User user, Passphrase passphrase) {
    List<Word> words = passphrase.getWords();
    if (words.isEmpty()) {
      if(passphrase.getLength() <= 0) {
        throw new IllegalArgumentException(String.format("Invalid length: %d; most be positive.", passphrase.getLength()));
      }
provider.generate(passphrase.getLength())
    .stream()
    .map((str) -> {
      Word word = new Word();
      word.setValue(str);
      return word;
    })
    .forEach(words::add);
    };

    int counter = 0;
    for (Word word : words) {
      word.setOrder(counter++);
      word.setPassphrase(passphrase);
    }
    passphrase.setUser(user);
    return repository.save(passphrase);
  }

  @Override
  public void delete(User user, UUID key) {
    repository
        .findByUserAndKey(user, key)
        .ifPresent((passphrase) -> repository.delete(passphrase));

  }

  @Override
  public Passphrase update(User user, UUID key, Passphrase recieved) {

    return repository
        .findByUserAndKey(user, key)
        .map((passphrase) -> {
          if (recieved.getName() != null) {
            passphrase.setName(recieved.getName());
          }
          if (!recieved.getWords().isEmpty()) {
            passphrase.getWords().forEach((word) -> word.setPassphrase(passphrase));
            passphrase.getWords().clear();
            int counter = 0;
            for(Word word : recieved.getWords()) {

              word.setPassphrase(passphrase);
              word.setOrder(counter++);
            }
            passphrase.getWords().addAll(recieved.getWords());

          }
            return repository.save(passphrase);
        })
        .orElseThrow();
  }

  @Override
  public String updateName(User user, UUID key, String recieved) {
    return repository
        .findByUserAndKey(user, key)
        .map((passphrase) -> {
      passphrase.setName(recieved);
      return repository.save(passphrase);
    })
        .map(Passphrase::getName)
        .orElseThrow();
  }

  @Override
  public List<String> updateWords(User user, UUID key, List<String> received) {
    return repository
        .findByUserAndKey(user, key)
        .map((passphrase) -> {
          int[] order = new int[]{0};
          passphrase.getWords().clear();
          passphrase.getWords().addAll(received.stream().map((item) -> {
            Word word = new Word();
            word.setValue(item);
            word.setPassphrase(passphrase);
            word.setOrder(order[0]++);
            return word;
          }).toList()
        );
          return repository.save(passphrase);
        }).map(Passphrase::getWords)
        .map((words) -> words.stream().map(Word::getValue).toList())
        .orElseThrow();

  }

  @Override
  public List<String> generate(int length) {
    return provider.generate(length);
  }


}
