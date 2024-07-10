package zinc.doiche.anifadmin.domain.user

data class JobStat(
    val levels: Map<String, Int>,
    val relicIds: List<String>
)
