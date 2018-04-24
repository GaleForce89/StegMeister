package steg.ui;

public class Listkey {
  String keyWord = new String();
  String storedKey = new String();

  public String getKeyWord() {
    return this.keyWord;
  }

  public String getStoredKey() {
    return this.storedKey;
  }

  public void setStoredKey(String storedKey) {
    this.storedKey = storedKey;
  }

  public void setKeyWord(String keyWord) {
    this.keyWord = keyWord;
  }
}
