package zinc.doiche.anifadmin.util

import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object FlutterAdaptor {
    fun toCamelCase(string: String): String {
        val compile: Pattern = Pattern.compile("([a-z])_([a-z])")
        val matcher: Matcher = compile.matcher(string)
        return matcher.replaceAll { matchResult ->
            java.lang.String.format(
                "%s%s",
                matchResult.group(1).lowercase(Locale.getDefault()),
                matchResult.group(2).uppercase(Locale.getDefault())
            )
        }
    }
}