package com.algaworks.algafood.core.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedTypes;

    @Override
    public void initialize(FileContentType constraint) {
        this.allowedTypes = Arrays.asList(constraint.allowed());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return file == null ||
                allowedTypes.contains(file.getContentType());
    }
}
