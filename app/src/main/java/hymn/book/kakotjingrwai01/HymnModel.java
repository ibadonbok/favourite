package hymn.book.kakotjingrwai01;

class HymnModel {

   private String id,title,author,lyric;

   public HymnModel(String id, String title, String author, String lyric) {
      this.id = id;
      this.title = title;
      this.author = author;
      this.lyric = lyric;
      //this.share = share;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
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

   public String getLyric() {
      return lyric;
   }

   public void setLyric(String lyric) {
      this.lyric = lyric;
   }

  // public String getShare() {return share;}

   //public void setShare(String share) { this.share = share; }

}
