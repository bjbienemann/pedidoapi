package br.com.eiconbrasil.pedidoapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.eiconbrasil.pedidoapi.exception.MessageException;
import br.com.eiconbrasil.pedidoapi.model.dto.ErroDto;

/**
 * 
 * @author Brian Bienemann
 */
@ControllerAdvice
public class PedidoapiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErroDto body = new ErroDto(
				messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale()),
				ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		
		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<ErroDto> erroDto = new ArrayList<ErroDto>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = Objects.nonNull(((FieldError) error).getField())?((FieldError) error).getField() : "";
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			erroDto.add(new ErroDto(nome + " "+ mensagem, ((FieldError) error).toString()));
		}

		return handleExceptionInternal(ex, erroDto, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
	    List<ErroDto> errosDto;
	    
	    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
	    if (!violations.isEmpty()) {
	        errosDto = violations.stream().map(violation -> new ErroDto(violation.getMessage())).collect(Collectors.toList());
	    } else {
	    	ErroDto erroDto = new ErroDto(messageSource.getMessage("mensagem.restricao-violada", null, LocaleContextHolder.getLocale()),
	    			ex.getCause() != null ? ex.getCause().toString() : ex.toString());
	    	
	    	errosDto = Arrays.asList(erroDto);
	    }
	    return handleExceptionInternal(ex, errosDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	 }
	
	@ExceptionHandler(MessageException.class)
	public ResponseEntity<Object> handleMessagesException(MessageException ex, WebRequest request) {
		String message = messageSource.getMessage(ex.getKey(), ex.getArgs(), LocaleContextHolder.getLocale());
		ErroDto body = new ErroDto(message, ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		
		return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
}
