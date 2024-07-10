package zinc.doiche.anifadmin.service.user;

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import zinc.doiche.anifadmin.domain.user.User
import zinc.doiche.anifadmin.repository.UserRepository
import zinc.doiche.anifadmin.service.ReadService
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
): ReadService<User, UUID> {
    override fun get(id: UUID): User? {
        return userRepository.findByUuid(id)
    }

    override fun getPage(page: Int, size: Int): Page<User> {
        return userRepository.findAll(PageRequest.of(page, size));
    }
}
