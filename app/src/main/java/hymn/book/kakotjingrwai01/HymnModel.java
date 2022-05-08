package hymn.book.kakotjingrwai01;

class HymnModel {

   private String number,title,author,lyrics;

   public HymnModel(String number, String title, String author, String lyrics) {
      this.number = number;
      this.title = title;
      this.author = author;
      this.lyrics = lyrics;
   }

   public String getNumber() {
      return number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getAuthor() {
      return author;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public String getLyrics() {
      return lyrics;
   }

   public void setLyrics(String lyrics) {
      this.lyrics = lyrics;
   }
}
