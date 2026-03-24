package griffio

import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import griffio.queries.Sample
import org.postgresql.ds.PGSimpleDataSource
import java.time.OffsetDateTime

private fun getSqlDriver() = PGSimpleDataSource().apply {
    setURL("jdbc:postgresql://localhost:5432/postgres")
    applicationName = "App Main"
    user = "postgres"
    password = "postgres"
}.asJdbcDriver()

fun stringIdentifier(n: Int) = (1..n).map { ('A'..'Z').random() }.joinToString("")
fun longIdentifier(n: Int) = (1..n).map { (1..10).random() }.joinToString("").toLong()

fun main() {
    val driver = getSqlDriver()
    val sample = Sample(driver)

    val userEmail = (stringIdentifier(7) + "@" + stringIdentifier(5) + ".org").lowercase()

    val user = sample.pgcryptoQueries.insertUser(userEmail, "p@sS_W0rd" ).executeAsOne().also { println(it) }
    sample.pgcryptoQueries.verifyUserPassword(userEmail, "p@sS_W0rd").executeAsOne().also { println(it) }
    val doc = sample.pgcryptoQueries.insertDocument(user.id, "Document Title", "Document Content").executeAsOne().also { println(it) }
    sample.pgcryptoQueries.selectDocumentChecksum(doc.id).executeAsOne().also { println(it) }
    sample.pgcryptoQueries.insertApiToken(user.id, OffsetDateTime.now()).executeAsOne().also { println(it) }

    sample.pgcryptoQueries.hashTextSha256("This is a test".toByteArray()).executeAsOne().also { println(it) }
    sample.pgcryptoQueries.hmacTextSha256("This is a test".toByteArray(), "secret".toByteArray()).executeAsOne().also { println(it) }
}
