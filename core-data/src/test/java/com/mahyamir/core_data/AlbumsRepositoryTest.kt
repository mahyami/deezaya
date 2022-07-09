package com.mahyamir.core_data

import androidx.paging.Pager
import androidx.paging.PagingData
import com.mahyamir.core_data.api.DeezayaClient
import com.mahyamir.core_data.converter.AlbumDetailsConverter
import com.mahyamir.core_data.dao.AlbumDetailsApiModel
import com.mahyamir.core_data.model.AlbumDetailsDomainModel
import com.mahyamir.core_data.model.AlbumDomainModel
import com.mahyamir.core_data.paging.AlbumsPagingSource
import com.mahyamir.core_data.paging.PagerGenerator
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test


class AlbumsRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var albumsPagingSource: AlbumsPagingSource

    @MockK
    private lateinit var pagerGenerator: PagerGenerator<Int, AlbumDomainModel>

    @MockK
    private lateinit var deezayaClient: DeezayaClient

    @MockK
    private lateinit var albumDetailsConverter: AlbumDetailsConverter

    @InjectMockKs
    private lateinit var repository: AlbumsRepository

    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `WHEN getAlbums THEN album domain data paging data observable`() {
        val albumDomainModel = AlbumDomainModel(
            id = EXAMPLE_ID,
            title = EXAMPLE_TITLE,
            coverUrl = EXAMPLE_COVER_URL,
            artistName = EXAMPLE_ARTIST_NAME
        )

        // GIVEN
        val pagingData = PagingData.from(listOf(albumDomainModel))
        val pager = mockk<Pager<Int, AlbumDomainModel>> {
            every { flow } returns flowOf(pagingData)
        }

        every {
            pagerGenerator.generate(
                albumsPagingSource,
                AlbumsPagingSource.PAGE_SIZE
            )
        } returns pager

        // WHEN
        val testObserver = repository.getAlbums().test()

        // THEN
        testObserver.assertResult(pagingData)
    }

    @Test
    fun `GIVEN album id WHEN getAlbum THEN album details domain model`() {
        // GIVEN
        val albumDetailsDomainModel = AlbumDetailsDomainModel(
            id = EXAMPLE_ID,
            title = EXAMPLE_TITLE,
            coverUrl = EXAMPLE_COVER_URL,
            releaseDate = EXAMPLE_RELEASE_DATE,
            isExplicit = false,
            share = "",
            artistName = EXAMPLE_ARTIST_NAME,
            tracks = listOf()

        )
        val albumDetailsApiModel = mockk<AlbumDetailsApiModel>()
        every { deezayaClient.getAlbum(EXAMPLE_ID) } returns Single.just(albumDetailsApiModel)
        every { albumDetailsConverter.convert(albumDetailsApiModel) } returns albumDetailsDomainModel
        // WHEN
        val testObserver = repository.getAlbum(EXAMPLE_ID).test()

        // THEN
        testObserver.assertResult(albumDetailsDomainModel)
    }

    companion object {
        private const val EXAMPLE_ID = "albumid"
        private const val EXAMPLE_TITLE = "albumtitle"
        private const val EXAMPLE_COVER_URL = "www.test.com/photo.png"
        private const val EXAMPLE_ARTIST_NAME = "artistName"
        private const val EXAMPLE_RELEASE_DATE = "10/06/1996"
    }

}