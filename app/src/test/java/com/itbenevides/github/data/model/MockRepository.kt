
import com.itbenevides.core.data.model.Repository
import io.mockk.every
import io.mockk.mockk

class MockRepository {

    fun create(name: String, authorName: String): Repository {

        // Cria o mock do Repository
        val repositoryMock = mockk<Repository>(relaxed = true)

        // Sobrescreve as variáveis que você deseja no mock
        every { repositoryMock.name } returns name
        every { repositoryMock.owner.login } returns authorName

        // Retorna o mock do Repository
        return repositoryMock
    }
}
