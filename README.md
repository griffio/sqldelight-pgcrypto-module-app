# SqlDelight 2.3.x Postgresql PgCrypto module support 

https://github.com/cashapp/sqldelight

**Experimental**

Use with SqlDelight `2.3.x` or higher   

---

SqlDelight Pgcrypto Module

https://www.postgresql.org/docs/current/pgcrypto.html

## Usage

Instead of a new dialect or adding PostgreSql extensions into the core PostgreSql grammar e.g. https://postgis.net/ and https://github.com/pgvector/pgvector

Use a custom SqlDelight module to implement the type resolver for PgCrypto operations

```kotlin
sqldelight {
    databases {
        create("Sample") {
            deriveSchemaFromMigrations.set(true)
            migrationOutputDirectory = file("$buildDir/generated/migrations")
            migrationOutputFileFormat = ".sql"
            packageName.set("griffio.queries")
            dialect(libs.sqldelight.postgresql.dialect)
            module(project(":pgcrypto-module")) // module can be local project
            // or external dependency module("io.github.griffio:sqldelight-pgcrypto-module:0.0.1")
        }
    }
}
```

`pgcrypto-module` published in Maven Central https://central.sonatype.com/artifact/io.github.griffio/sqldelight-pgcrypto/versions

`io.github.griffio:sqldelight-pgcrypto:0.0.1`

---

```shell
./gradlew build &&
./gradlew flywayMigrate
```
