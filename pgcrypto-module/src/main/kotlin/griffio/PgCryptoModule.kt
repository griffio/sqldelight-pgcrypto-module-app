package griffio

import app.cash.sqldelight.dialect.api.IntermediateType
import app.cash.sqldelight.dialect.api.PrimitiveType.BLOB
import app.cash.sqldelight.dialect.api.PrimitiveType.TEXT
import app.cash.sqldelight.dialect.api.SqlDelightModule
import app.cash.sqldelight.dialect.api.TypeResolver
import app.cash.sqldelight.dialects.postgresql.PostgreSqlType
import app.cash.sqldelight.dialects.postgresql.PostgreSqlTypeResolver
import com.alecstrong.sql.psi.core.psi.SqlFunctionExpr

class PgCryptoModule : SqlDelightModule {
    override fun typeResolver(parentResolver: TypeResolver): TypeResolver = PgCryptoResolver(parentResolver)

    override fun setup() {
    }
}

open class PgCryptoResolver(private val parentResolver: TypeResolver) : PostgreSqlTypeResolver(parentResolver) {

    override fun functionType(functionExpr: SqlFunctionExpr): IntermediateType? =
        when (functionExpr.functionName.text.lowercase()) {
            "armor" -> IntermediateType(TEXT).asNullable()
            "crypt" -> IntermediateType(TEXT).asNullable()
            "dearmor" -> IntermediateType(BLOB).asNullable()
            "decrypt" -> IntermediateType(BLOB).asNullable()
            "decrypt_iv" -> IntermediateType(BLOB).asNullable()
            "digest" -> IntermediateType(BLOB).asNullable()
            "encrypt" -> IntermediateType(BLOB).asNullable()
            "encrypt_iv" -> IntermediateType(TEXT).asNullable()
            "gen_random_bytes" -> IntermediateType(BLOB).asNullable()
            "gen_salt" -> IntermediateType(TEXT).asNullable()
            "hmac" -> IntermediateType(BLOB).asNullable()
            "pgp_key_id" -> IntermediateType(TEXT).asNullable()
            "pgp_pub_decrypt" -> IntermediateType(TEXT).asNullable()
            "pgp_pub_decrypt_bytea" -> IntermediateType(BLOB).asNullable()
            "pgp_pub_encrypt" -> IntermediateType(BLOB).asNullable()
            "pgp_pub_encrypt_bytea" -> IntermediateType(BLOB).asNullable()
            "pgp_sym_decrypt" -> IntermediateType(TEXT).asNullable()
            "pgp_sym_decrypt_bytea" -> IntermediateType(BLOB).asNullable()
            "pgp_sym_encrypt" -> IntermediateType(BLOB).asNullable()
            "pgp_sym_encrypt_bytea" -> IntermediateType(BLOB).asNullable()
            // also add binary string functions
            "bit_count" -> IntermediateType(PostgreSqlType.BIG_INT).asNullable()
            "decode" -> IntermediateType(BLOB).asNullable()
            "encode" -> IntermediateType(TEXT).asNullable()
            "get_bit" -> IntermediateType(PostgreSqlType.INTEGER).asNullable()
            "get_byte" -> IntermediateType(PostgreSqlType.INTEGER).asNullable()
            "set_bit" -> IntermediateType(BLOB).asNullable()
            "set_byte" -> IntermediateType(BLOB).asNullable()
            "sha224" -> IntermediateType(BLOB).asNullable()
            "sha256" -> IntermediateType(BLOB).asNullable()
            "sha384" -> IntermediateType(BLOB).asNullable()
            "sha512" -> IntermediateType(BLOB).asNullable()
            else -> super.functionType(functionExpr)
        }
}
