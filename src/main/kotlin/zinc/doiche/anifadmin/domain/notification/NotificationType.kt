package zinc.doiche.anifadmin.domain.notification

import net.dv8tion.jda.api.entities.channel.forums.ForumTag

enum class NotificationType(
    val displayName: String
) {
    REPORTING_BUG_OR_ISSUE("버그 및 오류 제보"),
    SUGGESTION("건의사항"),
    REPORTING_USER("유저 신고"),
    INQUIRY("문의사항"),
    OTHERS("기타"),
    SOLVED("해결됨");

    companion object {
        fun fromForumTag(tag: ForumTag): NotificationType {
            for (type in entries) {
                if(type.displayName == tag.name) {
                    return type
                }
            }
            return OTHERS
        }
    }
}