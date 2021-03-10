package jetbrains.buildServer.runner.kotlinBuildStep

import jetbrains.buildServer.BaseTestCase
import jetbrains.buildServer.util.ArchiveExtractorManager
import jetbrains.buildServer.util.ArchiveFileSelector
import jetbrains.buildServer.util.ssl.SSLTrustStoreProvider
import jetbrains.buildServer.web.openapi.PluginDescriptor
import org.assertj.core.api.BDDAssertions.then
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.io.File
import java.security.KeyStore

@Test
class KotlinServerToolProviderTest: BaseTestCase() {

    private lateinit var provider: KotlinServerToolProvider
    private lateinit var archiveManager: MockArchiveManager

    @BeforeMethod
    @Throws(Exception::class)
    override protected fun setUp() {
        super.setUp()
        archiveManager = MockArchiveManager()
        provider = KotlinServerToolProvider(MockPluginDescriptor(), archiveManager, MockSSLStoreProvider())
    }


    public fun `test tool versions`() {
        then(provider.defaultBundledVersionId).isNull()
        then(provider.bundledToolVersions).isEmpty()
        then(provider.availableToolVersions).isNotEmpty()
    }

    public fun `fail to parse prefix`() = failToParseVersion("something-1.4.31.zip")

    public fun `fail to parse extension`() = failToParseVersion("kotlin-compiler-1.4.31.txt")

    public fun `fail to parse absent version`() = failToParseVersion("kotlin-compiler.zip")

    public fun `fail to parse unknown version`() = failToParseVersion("kotlin-compiler-43.42.41.zip")

    private fun failToParseVersion(fileName: String) {
        val result = provider.tryGetPackageVersion(File(fileName));
        then(result.toolVersion).isNull();
        then(result.details).containsIgnoringCase("fail")
    }

    public fun `parse version`() {
        val result = provider.tryGetPackageVersion(File("kotlin-compiler-1.4.31.zip"))
        then(result.toolVersion).isNotNull()
        then(result.toolVersion!!.id).isEqualTo("kotlin.1.4.31")
        then(result.toolVersion!!.displayName).isEqualTo("Kotlin 1.4.31")
        then(result.toolVersion!!.type).isEqualTo(KotlinToolType.INSTANCE)
    }

    private class MockArchiveManager: ArchiveExtractorManager {

        val extractedFiles = mutableListOf<File>()

        override fun extractFiles(archive: File, selector: ArchiveFileSelector) {
            extractedFiles.add(archive)
        }
    }

    private class MockPluginDescriptor : PluginDescriptor {
        override fun getParameterValue(key: String): String? = null
        override fun getPluginName() = ""
        override fun getPluginResourcesPath() = ""
        override fun getPluginResourcesPath(relativePath: String) = relativePath
        override fun getPluginVersion(): String? = null
        override fun getPluginRoot() = File(".")
    }

    private class MockSSLStoreProvider: SSLTrustStoreProvider {
        override fun getTrustStore(): KeyStore? = null
    }
}