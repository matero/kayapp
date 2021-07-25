package kayapp.clients

interface Field<T, F : Field<T, F>> : kayak.validation.Validated<F> {
  fun asJsonField(): Pair<String, kayak.json.Json>

  fun describe(): String

  override val ok: Boolean
    get() = true

  override val failed: Boolean
    get() = false

  @Suppress("UNCHECKED_CAST")
  override val value: F
    get() = this as F

  override val failure: kayak.validation.Failure
    get() = throw IllegalStateException("Validation was successful, no failure detected")
}
