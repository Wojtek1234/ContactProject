package pl.wmaciejewski.contactproject.createnewperson.validators;

import android.telephony.PhoneNumberUtils;

/**
 * Created by w.maciejewski on 2014-10-30.
 */
public class PhoneNumberValidator implements Validator {
    @Override
    public boolean validate(CharSequence target) {
        return PhoneNumberUtils.isGlobalPhoneNumber((String) target);
    }
}
