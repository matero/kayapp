package kayapp.clients

import kayak.json.AsJson
import kayak.json.Json
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
  override fun asJson(): Json = Json.of(
    id.asJsonField(),
    email.asJsonField(),
    nickname.asJsonField(),
    name.asJsonField(),
    phone.asJsonField(),
    address.asJsonField(),
    info.asJsonField()
  )

  @JvmInline
  value class Id internal constructor(private val raw: Long) : Field<Long, Id> {
    override fun asJsonField() = "id" to Json.of(raw)

    companion object {
      fun of(value: String): Validated<Id> =
        try {
          Id(value.toLong())
        } catch (e: NumberFormatException) {
          UnsuccessfulValidation.of(e)
        }

      fun of(value: Long): Id = Id(value)

      fun of(requestParameter: Validated<Long>): Validated<Id> =
        if (requestParameter.ok)
          Id(requestParameter.value)
        else
          UnsuccessfulValidation.of(requestParameter.failure)
    }

    override fun describe() = raw.toString()

    override fun toString() = "Client.Id($raw)"
  }

  @JvmInline
  value class Email internal constructor(private val raw: String) : Field<String, Email> {
    val username: String
      get() {
        return raw.substring(0, raw.indexOf('@'))
      }

    val domain: String
      get() = raw.substring(raw.indexOf('@'))

    override fun asJsonField(): Pair<String, Json> = "email" to Json.of(raw)

    override fun describe() = raw

    override fun toString() = "Client.Email($raw)"

    companion object {
      private val emailValidator = EmailValidator.getInstance()

      fun of(value: String): Validated<Email> {
        if (value.isEmpty())
          return UnsuccessfulValidation.of("email can not be empty")
        if (value.isBlank())
          return UnsuccessfulValidation.of("email can not be blank")
        if (!emailValidator.isValid(value))
          return UnsuccessfulValidation.of("email is not a valid email address")

        return Email(value)
      }

      fun at(clientSpec: Json): Validated<Email> {
        val email = clientSpec["email"]
        if (email.isUndefined() || email.isNull())
          return UnsuccessfulValidation.of("email is required")
        if (!email.isString())
          return UnsuccessfulValidation.of("email must be a String")
        return of(email.asString())
      }
    }
  }

  @JvmInline
  value class Nickname internal constructor(private val raw: String) : Field<String, Nickname> {

    override fun asJsonField(): Pair<String, Json> = "nickname" to Json.of(raw)

    override fun describe() = raw

    override fun toString() = "Client.Nickname($raw)"

    companion object {
      fun of(value: String): Validated<Nickname> {
        if (value.isEmpty())
          return UnsuccessfulValidation.of("nickname can not be empty")
        if (value.isBlank())
          return UnsuccessfulValidation.of("nickname can not be blank")
        return Nickname(value)
      }

      fun of(email: Email) = Nickname(email.username)

      fun at(clientSpec: Json): Validated<Nickname> {
        val nickname = clientSpec["nickname"]
        if (nickname.isUndefined() || nickname.isNull())
          return UnsuccessfulValidation.of("nickname is required")
        if (!nickname.isString())
          return UnsuccessfulValidation.of("nickname must be a String")
        return of(nickname.asString())
      }
    }
  }

  @JvmInline
  value class Name internal constructor(private val raw: String) : Field<String, Name> {
    override fun asJsonField(): Pair<String, Json> =
      if (raw.isEmpty())
        NO_NAME_DEFINED
      else
        JSON_FIELD_NAME to Json.of(raw)

    override fun describe() = raw

    override fun toString() = "Client.Name($raw)"

    companion object {
      private const val JSON_FIELD_NAME = "name"
      private val NO_NAME_DEFINED = JSON_FIELD_NAME to Json.Undefined

      internal val NONE = Name("")

      fun of(value: String?): Validated<Name> {
        if (value == null || value.isEmpty() || value.isBlank()) {
          return NONE
        }
        return Name(value)
      }

      fun at(clientSpec: Json): Validated<Name> {
        val name = clientSpec["name"]
        if (name.isUndefined() || name.isNull())
          return NONE
        if (!name.isString())
          return UnsuccessfulValidation.of("name must be a String")
        return of(name.asString())
      }
    }
  }

  @JvmInline
  value class Phone internal constructor(private val raw: String) : Field<String, Phone> {
    override fun asJsonField(): Pair<String, Json> =
      if (raw.isEmpty())
        NO_PHONE_DEFINED
      else
        JSON_FIELD_NAME to Json.of(raw)

    override fun describe() = raw

    override fun toString() = "Client.Phone($raw)"

    companion object {
      private const val JSON_FIELD_NAME = "phone"
      private val NO_PHONE_DEFINED = JSON_FIELD_NAME to Json.Undefined

      internal val NONE = Phone("")

      fun of(value: String?): Validated<Phone> {
        if (value == null || value.isEmpty() || value.isBlank()) {
          return NONE
        }
        return Phone(value)
      }

      fun at(clientSpec: Json): Validated<Phone> {
        val phone = clientSpec["phone"]
        if (phone.isUndefined() || phone.isNull())
          return NONE
        if (!phone.isString())
          return UnsuccessfulValidation.of("phone must be a String")
        return of(phone.asString())
      }
    }
  }

  @JvmInline
  value class Address internal constructor(private val raw: String) : Field<String, Address> {
    override fun asJsonField(): Pair<String, Json> =
      if (raw.isEmpty())
        NO_ADDRESS_DEFINED
      else
        JSON_FIELD_NAME to Json.of(raw)

    override fun describe() = raw

    override fun toString() = "Client.Address($raw)"

    companion object {
      private const val JSON_FIELD_NAME = "address"
      private val NO_ADDRESS_DEFINED = JSON_FIELD_NAME to Json.Undefined

      internal val NONE = Address("")

      fun of(value: String?): Validated<Address> {
        if (value == null || value.isEmpty() || value.isBlank()) {
          return NONE
        }
        return Address(value)
      }

      fun at(clientSpec: Json): Validated<Address> {
        val address = clientSpec["address"]
        if (address.isUndefined() || address.isNull())
          return NONE
        if (!address.isString())
          return UnsuccessfulValidation.of("address must be a String")
        return of(address.asString())
      }
    }
  }

  @JvmInline
  value class Info internal constructor(private val raw: String) : Field<String, Info> {
    override fun asJsonField(): Pair<String, Json> =
      if (raw.isEmpty())
        NO_INFO_DEFINED
      else
        JSON_FIELD_NAME to Json.of(raw)

    override fun describe() = raw

    override fun toString() = "Client.Info($raw)"

    companion object {
      private const val JSON_FIELD_NAME = "info"
      private val NO_INFO_DEFINED = JSON_FIELD_NAME to Json.Undefined

      internal val NONE = Info("")

      fun of(value: String?): Validated<Info> {
        if (value == null || value.isEmpty() || value.isBlank()) {
          return NONE
        }
        return Info(value)
      }

      fun at(clientSpec: Json): Validated<Info> {
        val info = clientSpec["info"]
        if (info.isUndefined() || info.isNull())
          return NONE
        if (!info.isString())
          return UnsuccessfulValidation.of("info must be a String")
        return of(info.asString())
      }
    }
  }
}
