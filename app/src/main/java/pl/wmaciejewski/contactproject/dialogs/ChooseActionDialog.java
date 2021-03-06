package pl.wmaciejewski.contactproject.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import pl.wmaciejewski.contactproject.R;

/**
 * Created by w.maciejewski on 2014-11-04.
 */
public class ChooseActionDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.choose_action));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.action_picker_layout, null);
        builder.setView(view);
        view.findViewById(R.id.sendEmailButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMailButtonClick(ChooseActionDialog.this);
                finish();
            }
        });
        view.findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCallButtonClick(ChooseActionDialog.this);
                finish();
            }
        });
        view.findViewById(R.id.sendSMSButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSMSButtonClick(ChooseActionDialog.this);
                finish();
            }
        });

        Drawable d = new ColorDrawable(Color.GRAY);
        d.setAlpha(130);
        dialog.getWindow().setBackgroundDrawable(d);
        dialog.getWindow().setContentView(view);

        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }



    public interface onChooseActionListener{
        public void onMailButtonClick(DialogFragment dialog);
        public void onSMSButtonClick(DialogFragment dialog);
        public void onCallButtonClick(DialogFragment dialog);

    }
    onChooseActionListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (onChooseActionListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement onChooseActionListener");
        }
    }

    private void finish(){
        this.dismiss();
    }
}
