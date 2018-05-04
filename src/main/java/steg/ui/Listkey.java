package steg.ui;

/**
 * The listkey class, used to store keywords and keys to display in ui.
 */
public class Listkey {
  String keyWord = new String();
  String storedKey = new String();

  /**
   * Get the keyword.
   * @return keyWord
   */
  public String getKeyWord() {
    return this.keyWord;
  }

  /**
   * get the stored key.
   * @return storedKey
   */
  public String getStoredKey() {
    return this.storedKey;
  }

  /**
   * Set the stored key value.
   * @param storedKey storedKey
   */
  public void setStoredKey(String storedKey) {
    this.storedKey = storedKey;
  }

  /**
   * Set the keyWord.
   * @param keyWord User keyword.
   */
  public void setKeyWord(String keyWord) {
    this.keyWord = keyWord;
  }
}
