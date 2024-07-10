package zinc.doiche.anifadmin.domain.user

data class PersonalSettings(
    val pickUp: Boolean,
    val drop: Boolean,
    val whisper: Boolean,
    val stem: Boolean,
    val lightBlock: Boolean,
    val superJump: Boolean,
    val tpa: Boolean,
)