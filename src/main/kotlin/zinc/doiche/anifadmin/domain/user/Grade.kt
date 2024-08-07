package zinc.doiche.anifadmin.domain.user

data class Grade(
    val tier: Tier,
    val level: Int,
    val isVisible: Boolean,
)

enum class Tier {
    BRONZE,
    SILVER,
    GOLD,
    PURPLE,
    RUBY,
    DIAMOND,
}