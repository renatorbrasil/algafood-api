package com.algaworks.algafood.api.exceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ApiError> handleNegocioException(NegocioException e) {
        ApiErrorType errorType = ApiErrorType.ERRO_NEGOCIO;
        if (e instanceof EntidadeNaoEncontradaException) {
            errorType = ApiErrorType.RECURSO_NAO_ENCONTRADO;
        } else if (e instanceof EntidadeEmUsoException) {
            errorType = ApiErrorType.ENTIDADE_EM_USO;
        }

        ApiError apiError = ApiError.fromApiErrorType(errorType, e.getMessage());
        return ResponseEntity.status(errorType.getStatusCode()).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        ApiErrorType errorType = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;
        String detailMessage = "O corpo da requisição é inválido. Verifique erro de sintaxe";;

        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException) {
            detailMessage = getInvalidFormatExceptionMessageDetails((InvalidFormatException) rootCause);
        } else if (rootCause instanceof PropertyBindingException) {
            detailMessage = getPropertyBindingExceptionDetails((PropertyBindingException) rootCause);
        }

        ApiError apiError = ApiError.fromApiErrorType(errorType, detailMessage);
        return ResponseEntity.status(errorType.getStatusCode()).body(apiError);
    }

    private String getPropertyBindingExceptionDetails(PropertyBindingException rootCause) {
        String path = joinPath(rootCause.getPath());
        return String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade", path);
    }


    private String getInvalidFormatExceptionMessageDetails(InvalidFormatException rootCause) {
        String path = joinPath(rootCause.getPath());
        return String.format("A propriedade '%s' recebeu o valor '%s'," +
                        " que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s",
                path,
                rootCause.getValue(),
                rootCause.getTargetType().getSimpleName());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            String detailMessage = getMethodArgumentTypeMismatchDetails((MethodArgumentTypeMismatchException) ex);
            ApiErrorType errorType = ApiErrorType.PARAMETRO_INVALIDO;
            ApiError apiError = ApiError.fromApiErrorType(errorType, detailMessage);
            return ResponseEntity.status(errorType.getStatusCode()).body(apiError);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private String getMethodArgumentTypeMismatchDetails(MethodArgumentTypeMismatchException ex) {
        return String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
    }

    private String joinPath(List<JsonMappingException.Reference> refs) {
        return refs.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorType errorType = ApiErrorType.DADOS_INVALIDOS;
        String detailMessage = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        ApiError apiError = ApiError.fromApiErrorType(errorType, detailMessage);
        apiError.setObjects(getProblemObjects(ex.getBindingResult()));
        return ResponseEntity.status(errorType.getStatusCode()).body(apiError);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ApiError> handleValidacaoException(ValidacaoException e) {
        ApiErrorType errorType = ApiErrorType.DADOS_INVALIDOS;
        String detailMessage = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        ApiError apiError = ApiError.fromApiErrorType(errorType, detailMessage);
        apiError.setObjects(getProblemObjects(e.getBindingResult()));
        return ResponseEntity.status(errorType.getStatusCode()).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorType errorType = ApiErrorType.DADOS_INVALIDOS;
        String detailMessage = "Um ou mais campos de pesquisa estão inválidos. Faça o preenchimento correto e tente novamente.";
        ApiError apiError = ApiError.fromApiErrorType(errorType, detailMessage);
        apiError.setObjects(getProblemObjects(ex.getBindingResult()));
        return ResponseEntity.status(errorType.getStatusCode()).body(apiError);
    }

    private List<ApiError.Object> getProblemObjects(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return ApiError.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorType errorType = ApiErrorType.RECURSO_NAO_ENCONTRADO;
        String detailMessage = String.format("O recurso %s é inexistente.",
                ex.getRequestURL());
        ApiError apiError = ApiError.fromApiErrorType(errorType, detailMessage);
        return ResponseEntity.status(errorType.getStatusCode()).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        ApiErrorType errorType = ApiErrorType.ERRO_DE_SISTEMA;
        String detailMessage = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";

        ex.printStackTrace();

        ApiError apiError = ApiError.fromApiErrorType(errorType, detailMessage);
        return ResponseEntity.status(errorType.getStatusCode()).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        if (body == null) {
            body = ApiError.fromHttpStatus(status, status.getReasonPhrase());
        } else if (body instanceof String) {
            body = ApiError.fromHttpStatus(status, (String) body);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

}
