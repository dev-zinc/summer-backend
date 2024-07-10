package zinc.doiche.anifadmin.service.user;

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service
import zinc.doiche.anifadmin.domain.user.User
import zinc.doiche.anifadmin.repository.UserRepository
import zinc.doiche.anifadmin.service.ReadService

@Service
class UserService(
    private val userRepository: UserRepository
): ReadService<User, String> {
    override fun get(id: String): User? {
        return userRepository.findByUuid(id)
    }

    override fun getPage(page: Int, size: Int): PagedModel<User> {
        return PagedModel(userRepository.findAll(PageRequest.of(page, size)));
    }
}
