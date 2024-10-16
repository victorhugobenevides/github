import com.itbenevides.core.data.model.Repository
import io.mockk.every
import io.mockk.mockk

class MockRepository {

    fun create(name: String, authorName: String): Repository {

        val repositoryMock = mockk<Repository>(relaxed = true)

        every { repositoryMock.name } returns name
        every { repositoryMock.owner.login } returns authorName

        return repositoryMock
    }
}
