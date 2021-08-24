package com.alekseenko.lms.validator.chain;

import java.util.List;

/**
 * Базовый класс цепочки.
 */
public abstract class TitleChecker {

  private TitleChecker next;

  public TitleChecker linkWith(TitleChecker next) {
    this.next = next;
    return next;
  }

  public abstract boolean check(List<String> wordTitle);

  protected boolean checkNext(List<String> wordTitle) {
    if (next == null) {
      return true;
    }
    return next.check(wordTitle);
  }
}
