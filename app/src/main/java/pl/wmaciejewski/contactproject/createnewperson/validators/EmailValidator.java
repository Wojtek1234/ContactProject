package pl.wmaciejewski.contactproject.createnewperson.validators;

/**
 * Created by w.maciejewski on 2014-10-30.
 */
public class EmailValidator implements Validator{

    @Override
    public boolean validate(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
