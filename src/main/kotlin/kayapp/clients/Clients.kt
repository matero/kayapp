package kayapp.clients

import kayak.json.Json
import kayak.validation.UnsuccessfulValidation
import kayak.validation.Validated
import kayak.validation.validate

class Clients(private val repository: Repository = Repository()) {
  fun all() = repository.all()

  fun get(id: Client.Id): Validated<Client> = repository.get(id)

  fun createClientUsing(clientSpec: Json): Validated<Client> {
    if (!clientSpec.isObject())
      return UnsuccessfulValidation.of("json doesn't represent a client")

    if (hasId(clientSpec))
      return UnsuccessfulValidation.of("no id must be defined for creation")

    val email = Client.Email.at(clientSpec)
    val nickname = Client.Nickname.at(clientSpec)
    val name = Client.Name.at(clientSpec)
    val phone = Client.Phone.at(clientSpec)
    val address = Client.Address.at(clientSpec)
    val info = Client.Info.at(clientSpec)

    val specValidation: Validated<Client> = validate(
      "email" to email,
      "nickname" to nickname,
      "name" to name,
      "phone" to phone,
      "address" to address,
      "info" to info)

    if (specValidation.ok) {
      return repository.insert(email.value, nickname.value, name.value, phone.value, address.value, info.value)
    } else {
      return specValidation;
    }
  }

  private fun hasId(clientSpec: Json): Boolean {
    val id = clientSpec["id"]
    return id.isDefined() && id.isNotNull()
  }

}
