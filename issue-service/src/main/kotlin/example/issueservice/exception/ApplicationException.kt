package example.issueservice.exception

sealed class ApplicationException(
    val code: Int,
    override val message: String,
) : RuntimeException()


data class NotFoundException(
    override val message: String,
) : ApplicationException(404, message)


data class UnauthorizedException(
    override val message: String = "인증 정보가 잘못되었습니"
) : ApplicationException(401, message)
