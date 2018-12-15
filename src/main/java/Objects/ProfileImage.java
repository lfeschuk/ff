package Objects;

import android.graphics.Bitmap;

public class ProfileImage {
    Bitmap image;
    String indexString = "";

    public ProfileImage(Bitmap image, String indexString) {
        this.image = image;
        this.indexString = indexString;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getIndexString() {
        return indexString;
    }

    public void setIndexString(String indexString) {
        this.indexString = indexString;
    }
}
