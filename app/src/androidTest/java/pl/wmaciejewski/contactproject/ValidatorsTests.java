package pl.wmaciejewski.contactproject;

import android.test.AndroidTestCase;

import pl.wmaciejewski.contactproject.createnewperson.validators.EmailValidator;
import pl.wmaciejewski.contactproject.createnewperson.validators.PhoneNumberValidator;

/**
 * Created by Wojtek on 2014-10-30.
 */
public class ValidatorsTests extends AndroidTestCase {


    public void testemailValidator(){
        EmailValidator emailValidator=new EmailValidator();

        assertTrue(emailValidator.validate("wojciech@o2.pl"));
        assertFalse(emailValidator.validate("askfmasdfijn"));
    }

    public void testPhoneValidator(){
        PhoneNumberValidator phoneNumberValidator=new PhoneNumberValidator();
        assertFalse(phoneNumberValidator.validate("1929383487299374986516843216adsafg8"));
        assertTrue(phoneNumberValidator.validate("+48 606 225 225"));
        assertTrue(phoneNumberValidator.validate("525525525"));
        assertFalse(phoneNumberValidator.validate("++525525525"));

    }
}
