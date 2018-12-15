package Objects;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class ErrorSender {
    public Activity a;
  public  ErrorSender(Activity a)
    {
        this.a = a;
    }
    public  void SendLogcatMail(){

        // save logcat in file
        File outputFile = new File(Environment.getExternalStorageDirectory(),
                "logcat.txt");
        try {
            Runtime.getRuntime().exec(
                    "logcat -f " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //send file using email
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // Set type to "email"
        emailIntent.setType("vnd.android.cursor.dir/email");
        String to[] = {"leonidf1991@gmail.com"};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
        // the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, outputFile.getAbsolutePath());
        // the mail subject
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Subject");
        a.startActivity(Intent.createChooser(emailIntent , "Send email..."));
    }
}
