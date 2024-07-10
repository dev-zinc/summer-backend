package zinc.doiche.anifadmin.service

import org.springframework.data.domain.Page

interface ReadService<M, ID> {
    fun get(id: ID): M?
    fun getPage(page: Int, size: Int): Page<M>
}