package edu.cnm.deepdive.passphrase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class DicewareProvider implements PassphraseProvider{

  private final RandomGenerator rng;
  List<String> words;

  @Autowired
  public DicewareProvider(WordProvider provider, RandomGenerator rng) {
    this.rng = rng;
    words = new ArrayList<>(provider.words());
  }
  @Override
  public List<String> generate(int length) {
    int size = words.size();
    return Stream.generate(() -> words.get(rng.nextInt(size))).limit(length).collect(
        Collectors.toList());
  }
}
