package example.issueservice.exception

import com.auth0.jwt.exceptions.TokenExpiredException
import mu.KotlinLogging
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(ApplicationException::class)
    fun handlerApplicationException(ex: ApplicationException): ErrorResponse {
        logger.error { ex.message }

        return ErrorResponse(code = ex.code, message = ex.message)
    }

    @ExceptionHandler(TokenExpiredException::class)
    fun handlerTokenExpiredException(ex: TokenExpiredException): ErrorResponse {
        logger.error { ex.message }

        return ErrorResponse(code = 401, message = "Token Expired Error")
    }

    @ExceptionHandler(Exception::class)
    fun handlerException(ex: Exception): ErrorResponse {
        logger.error { ex.message }

        return ErrorResponse(code = 500, message = "Internal Server Error")
    }

}
