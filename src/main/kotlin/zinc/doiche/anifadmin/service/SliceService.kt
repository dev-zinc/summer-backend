package zinc.doiche.anifadmin.service

import org.springframework.data.domain.Slice

interface SliceService<M> {
    fun getSlice(page: Int, size: Int): Slice<M>
}