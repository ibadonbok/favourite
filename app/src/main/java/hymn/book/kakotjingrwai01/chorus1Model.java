package hymn.book.kakotjingrwai01;

public class chorus1Model {
    // variables for our chorus
    // id and lyric.
    String id,lyric,rmvfav;

    // creating constructor for our variables.
    public chorus1Model(String id, String lyric,String rmvfav) {
        this.id = id;
        this.lyric = lyric;
        this.rmvfav = rmvfav;
    }
    // creating getter and setter methods.
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getRmvfav() {
        return rmvfav;
    }

    public void setRmvfav(String rmvfav) {
        this.rmvfav = rmvfav;
    }
}

