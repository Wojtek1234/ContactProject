package pl.wmaciejewski.contactproject.createnewperson;

import android.widget.EditText;

import java.io.File;

/**
 * Created by w.maciejewski on 2014-11-03.
 */
public class CreatePhotoFile {

    private final EditText editText;
    private final File catcheDir ;
    public CreatePhotoFile(EditText editText,File catcheDir) {
        this.editText = editText;
        this.catcheDir = catcheDir;
    }

    public File getFile(){
        return createPhotoFile();
    }

    private File createPhotoFile() {
        File photo;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            photo = new File(android.os.Environment
                    .getExternalStorageDirectory(), editText.getText().toString());
        } else {
            photo = new File(this.catcheDir,  this.editText.getText().toString());
        }
        return photo;
    }


}
