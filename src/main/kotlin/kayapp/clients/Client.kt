package kayapp.clients

import kayak.json.*
import kayak.validation.SuccessfulValidation
import kayak.validation.UnsuccessfulValidation
import kayak.validation.Validated
import org.apache.commons.validator.routines.EmailValidator

class Client internal constructor(
  val id: Id,
  val email: Email,
  val nickname: Nickname = Nickname.of(email),
  val name: Name = Name.NONE,
  val phone: Phone = Phone.NONE,
  val address: Address = Address.NONE,
  val info: Info = Info.NONE
) : AsJson {
  override fun asJson(): Json = JsonObject.of(
    id.asJsonField(),
    email.asJsonField(),
    nickname.asJsonField(),
    name.asJsonField(),
    phone.asJsonField(),
    address.asJsonField(),
    info.asJsonField()
  )

  @JvmInline
  value class Id internal constructor(private val value: Long) {
    fun asJsonField() = Json.field(JSON_FIELD_NAME, JsonNumber.of(value))

    companion object {
      private val JSON_FIELD_NAME: JsonString = JsonString.of("id")

      fun of(value: String?): Validated<Id> =
        if (value == null)
          UnsuccessfulValidation.of("id can not be null")
        else
          try {
            SuccessfulValidation.of(Id(value.toLong()))
          } catch (e: NumberFormatException) {
            UnsuccessfulValidation.of(e)
          }

      fun of(value: Long?): Validated<Id> =
        if (value == null)
          UnsuccessfulValidation.of("id can not be null")
        else
          SuccessfulValidation.of(Id(value))

      fun of(requestParameter: Validated<Long>): Validated<Id> =
        if (requestParameter.ok)
          SuccessfulValidation.of(Id(requestParameter.value))
        else
          UnsuccessfulValidation.of(requestParameter.failure)
    }
  }

  @JvmInline
  value class Email internal constructor(private val value: String) {
    val username: String
      get() {
        val i = value.indexOf('@')
        val s = value.substring(0, i)
        return s
      }
    val domain: String
      get() = value.substring(value.indexOf('@'))

    fun asJsonField(): Pair<JsonString, Json> = Json.field(JSON_FIELD_NAME, JsonString.of(value))

    companion object {
      private val JSON_FIELD_NAME = JsonString.of("email")
      private val emailValidator = EmailValidator.getInstance()

      fun of(value: String?): Validated<Email> {
        if (value == null)
          return UnsuccessfulValidation.of("email can not be null")
        if (value.isEmpty())
          return UnsuccessfulValidation.of("email can not be empty")
        if (value.isBlank())
          return UnsuccessfulValidation.of("email can not be blank")
        if (!emailValidator.isValid(value))
          return UnsuccessfulValidation.of("email is not a valid email address")

        return SuccessfulValidation.of(Email(value))
      }
    }
  }

  @JvmInline
  value class Nickname internal constructor(private val value: String) {

    fun asJsonField(): Pair<JsonString, Json> = Json.field(JSON_FIELD_NAME, JsonString.of(value))

    companion object {
      private val JSON_FIELD_NAME = JsonString.of("nickname")

      fun of(value: String?): Validated<Nickname> {
        if (value == null)
          return UnsuccessfulValidation.of("nickname can not be null")
        if (value.isEmpty())
          return UnsuccessfulValidation.of("nickname can not be empty")
        if (value.isBlank())
          return UnsuccessfulValidation.of("nickname can not be blank")
        return SuccessfulValidation.of(Nickname(value))
      }

      fun of(email: Email) = Nickname(email.username)
    }
  }

  @JvmInline
  value class Name internal constructor(private val value: String) {
    fun asJsonField(): Pair<JsonString, Json> =
      if (value.isEmpty())
        NONE_JSON_FIELD
      else
        Json.field(JSON_FIELD_NAME, JsonString.of(value))

    companion object {
      private val JSON_FIELD_NAME = JsonString.of("name")
      private val NONE_JSON_FIELD = Json.field(JSON_FIELD_NAME, JsonUndefined)

      internal val NONE = Name("")
      private val VALID_NONE = SuccessfulValidation.of(NONE)

      fun of(value: String?): Validated<Name> {
        if (value == null || value.isEmpty() || value.isBlank()) {
          return VALID_NONE
        }
        return SuccessfulValidation.of(Name(value))
      }
    }
  }

  @JvmInline
  value class Phone internal constructor(private val value: String) {
    fun asJsonField(): Pair<JsonString, Json> =
      if (value.isEmpty())
        NONE_JSON_FIELD
      else
        Json.field(JSON_FIELD_NAME, JsonString.of(value))

    companion object {
      private val JSON_FIELD_NAME = JsonString.of("phone")
      private val NONE_JSON_FIELD = Json.field(JSON_FIELD_NAME, JsonUndefined)

      internal val NONE = Phone("")
      private val VALID_NONE = SuccessfulValidation.of(NONE)

      fun of(value: String?): Validated<Phone> {
        if (value == null || value.isEmpty() || value.isBlank()) {
          return VALID_NONE
        }
        return SuccessfulValidation.of(Phone(value))
      }
    }
  }

  @JvmInline
  value class Address internal constructor(private val value: String) {
    fun asJsonField(): Pair<JsonString, Json> =
      if (value.isEmpty())
        NONE_JSON_FIELD
      else
        Json.field(JSON_FIELD_NAME, JsonString.of(value))

    companion object {
      private val JSON_FIELD_NAME = JsonString.of("address")
      private val NONE_JSON_FIELD = Json.field(JSON_FIELD_NAME, JsonUndefined)

      internal val NONE = Address("")
      private val VALID_NONE = SuccessfulValidation.of(NONE)

      fun of(value: String?): Validated<Address> {
        if (value == null || value.isEmpty() || value.isBlank()) {
          return VALID_NONE
        }
        return SuccessfulValidation.of(Address(value))
      }
    }
  }

  @JvmInline
  value class Info internal constructor(private val value: String) {
    fun asJsonField(): Pair<JsonString, Json> =
      if (value.isEmpty())
        NONE_JSON_FIELD
      else
        Json.field(JSON_FIELD_NAME, JsonString.of(value))

    companion object {
      private val JSON_FIELD_NAME = JsonString.of("info")
      private val NONE_JSON_FIELD = Json.field(JSON_FIELD_NAME, JsonUndefined)

      internal val NONE = Info("")
      private val VALID_NONE = SuccessfulValidation.of(NONE)

      fun of(value: String?): Validated<Info> {
        if (value == null || value.isEmpty() || value.isBlank()) {
          return VALID_NONE
        }
        return SuccessfulValidation.of(Info(value))
      }
    }
  }
}
