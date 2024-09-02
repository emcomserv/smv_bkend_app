package com.smartbus.aspect;

import com.smartbus.exception.BusBadRequestException;
import com.smartbus.exception.BusInternalServerErrorException;
import com.smartbus.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class BusExceptionHandler {

    public static final String TRACE = "trace";

    @Value("${reflectoring.trace:false}")
    private boolean printStackTrace;

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
//    @ResponseBody
//    public ErrorResponse onhandleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex, HttpServletRequest request) {
//        String path = request.getRequestURI() + "?" + request.getQueryString();
//        ErrorResponse errorResponse = new ErrorResponse(
//                false, LocalDateTime.now(), path, "Kolacut Validation error. Check 'errors' field for details.");
//        ex.getGlobalErrors().stream().forEach(objectError -> {
//            List<String> messageList = new ArrayList<>(
//                    Arrays.asList(objectError.getDefaultMessage().split(",")));
//            messageList.stream().forEach(message -> errorResponse.addValidationError(message));
//        });
//
//        if (CollectionUtils.isEmpty(errorResponse.getErrors())) {
//            ex.getBindingResult()
//                    .getFieldErrors()
//                    .forEach(fieldError -> errorResponse.addFieldValidationError(
//                            fieldError.getField(), fieldError.getDefaultMessage()));
//        }
//
//        log.info("Error Response: {}", errorResponse);
//        return errorResponse;
//    }


    @ExceptionHandler(BusBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequestException(BusBadRequestException busBadRequestException, HttpServletRequest request) {
        return buildErrorResponse(busBadRequestException, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BusInternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleInternalServerException(BusBadRequestException busBadRequestException, HttpServletRequest request) {
        return buildErrorResponse(busBadRequestException, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    private ResponseEntity<Object> buildErrorResponse(
            Exception exception, HttpStatus httpStatus, HttpServletRequest request) {
        return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
    }

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception, String message, HttpStatus httpStatus, HttpServletRequest request) {
        String path = request.getRequestURI() + "?" + request.getQueryString();
        ErrorResponse errorResponse = new ErrorResponse(false, LocalDateTime.now(), path, message);
        if (printStackTrace && isTraceOn(request)) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private boolean isTraceOn(HttpServletRequest request) {
        String[] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value) && value.length > 0 && value[0].contentEquals("true");
    }
}
