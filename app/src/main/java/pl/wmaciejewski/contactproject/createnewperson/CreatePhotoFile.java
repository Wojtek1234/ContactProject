package pl.wmaciejewski.contactproject.createnewperson;

import android.widget.EditText;

import java.io.File;

import pl.wmaciejewski.contactproject.exceptions.CouldntCreatePhoto;

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

    public File getFile() throws CouldntCreatePhoto {
        return createPhotoFile();
    }

    private File createPhotoFile() throws CouldntCreatePhoto {
        File photo;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            photo = new File(android.os.Environment
                    .getExternalStorageDirectory(), editText.getText().toString());
        } else {
            photo = new File(this.catcheDir,  this.editText.getText().toString());
        }
        if(photo==null){
            throw new CouldntCreatePhoto("");
        }
        return photo;
    }


}
