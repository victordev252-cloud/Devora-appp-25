package com.devora.community

object Moderation {

    private val blocked = listOf(
        "gus",
        "siil",
        "naas",
        "dawo",
        "wasmo",
        "hoyada",
        "silkeed",
        "h.w",
        "e.w",
        "idhuuq",
        "dhuuq",
        "is was"
    )

    private val urlRegex =
        Regex("""(https?://|www\.|t\.me/|telegram\.me/)""")

    fun check(text: String): Result {

        val lower = text.lowercase()

        if (urlRegex.containsMatchIn(lower)) {
            return Result.BLOCK_LINK
        }

        for (word in blocked) {
            if (lower.contains(word)) {
                return Result.BLOCK_WORD
            }
        }

        return Result.ALLOW
    }

    enum class Result {
        ALLOW,
        BLOCK_WORD,
        BLOCK_LINK
    }
}
