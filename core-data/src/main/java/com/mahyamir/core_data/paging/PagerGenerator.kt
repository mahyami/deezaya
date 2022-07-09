package com.mahyamir.core_data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import javax.inject.Inject

class PagerGenerator<KEY : Any, VALUE : Any> @Inject constructor() {

    fun generate(source: PagingSource<KEY, VALUE>, pageSize: Int) = Pager(
        config = PagingConfig(pageSize = pageSize),
        pagingSourceFactory = { source }
    )
}