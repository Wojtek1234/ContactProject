package pl.wmaciejewski.contactproject.createnewperson.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

import pl.wmaciejewski.contactproject.R;

/**
 * Created by Wojtek on 2014-10-31.
 */
public class ImagePickerDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.pick_image_source)
                .setPositiveButton(R.string.from_gallery, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.from_camera, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }
                );

        final AlertDialog  dialog=builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                // if you do the following it will be left aligned, doesn't look correct
                // button.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play, 0, 0, 0);

                Drawable drawable = getActivity().getResources().getDrawable(R.drawable.gallery);

                // set the bounds to place the drawable a bit right
                drawable.setBounds((int) (drawable.getIntrinsicWidth() * 0.5), 0, (int) (drawable.getIntrinsicWidth() * 1.5), drawable.getIntrinsicHeight());
                button.setCompoundDrawables(drawable, null, null, null);

                Button button2 = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                // if you do the following it will be left aligned, doesn't look correct
                // button.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play, 0, 0, 0);

                Drawable drawable2 = getActivity().getResources().getDrawable(R.drawable.camera);

                // set the bounds to place the drawable a bit right
                drawable2.setBounds((int) (drawable.getIntrinsicWidth() * 0.5), 0, (int) (drawable.getIntrinsicWidth() * 1.5), drawable.getIntrinsicHeight());
                button2.setCompoundDrawables(drawable, null, null, null);
            }
        });
        return dialog;
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }


    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {

            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
