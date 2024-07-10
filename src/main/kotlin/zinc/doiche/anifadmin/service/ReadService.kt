package zinc.doiche.anifadmin.service

import org.springframework.data.web.PagedModel

interface ReadService<M, ID> {
    fun get(id: ID): M?
    fun getPage(page: Int, size: Int): PagedModel<M>
}