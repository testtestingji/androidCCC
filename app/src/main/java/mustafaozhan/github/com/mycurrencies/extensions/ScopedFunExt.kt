package mustafaozhan.github.com.mycurrencies.extensions

fun <T> T.whether(
    method: (condition: T) -> Boolean
) = if (this != null && method(this)) {
    this
} else {
    null
}

fun <T> T.unless(
    method: (condition: T) -> Boolean
) = if (this != null && !method(this)) {
    this
} else {
    null
}

fun <T> T.whetherThis(
    method: T.(condition: T) -> Boolean
) = if (this != null && method(this)) {
    this
} else {
    null
}

fun <T> T.unlessThis(
    method: T.(condition: T) -> Boolean
) = if (this != null && !method(this)) {
    this
} else {
    null
}

fun <T, R> T.mapTo(
    transform: T.(T) -> R
): R = transform(this)
