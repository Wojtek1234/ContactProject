package pl.wmaciejewski.contactproject.createnewperson;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pl.wmaciejewski.contactproject.R;

/**
 * Created by Wojtek on 2014-10-30.
 */
public class WrongValueDialog  extends Dialog{
    private String text;



    private boolean result;
    public WrongValueDialog(Context context,String text) {
        super(context);
        this.text=text;
    }
    public boolean isResult() {
        return result;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        result=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrong_value_dlg);
        TextView textView=(TextView)findViewById(R.id.textValueWrong);
        textView.setText(text);
        findViewById(R.id.buttonWrongValueLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result=true;
                dismiss();
            }
        });
        findViewById(R.id.buttonWrongValueRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result=false;
                dismiss();
            }
        });
    }
}
