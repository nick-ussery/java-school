package com.lambdaschool.schools.services;

import com.lambdaschool.schools.models.ValidationError;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "helperFunctions")
public class HelperFunctionsImpl implements HelperFunctions
{
    @Override
    public List<ValidationError> getConstraintViolation(Throwable cause) {
        //cycles through errors until finds ConstraintViolationException or to the end
        while ((cause != null) && !(cause instanceof ConstraintViolationException))
        {
            cause= cause.getCause();
        }
        List<ValidationError> listVe = new ArrayList<>();
        //check to make sure list isnt null, but list should be of ConstrainViolationExceptions now
        if(cause != null)
        {
            ConstraintViolationException ex = (ConstraintViolationException) cause;
            //set type of cause to constraintviolations, then loop through
            for(ConstraintViolation cv : ex.getConstraintViolations())
            {
                ValidationError newVe = new ValidationError();
                newVe.setCode(cv.getInvalidValue().toString());
                newVe.setMessage(cv.getMessage());
                listVe.add(newVe);
            }
        }
        return listVe;
    }
}
