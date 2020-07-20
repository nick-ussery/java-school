package com.lambdaschool.schools.services;

import com.lambdaschool.schools.models.ValidationError;

import java.util.List;

public interface HelperFunctions
{
    public List<ValidationError> getConstraintViolation(Throwable cause);
}
