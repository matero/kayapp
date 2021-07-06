package kayapp.clients

import kayak.validation.SuccessfulValidation
import kayak.validation.UnsuccessfulValidation
import kayak.validation.Validated

class Repository {
  private val db = mutableMapOf<Client.Id, Client>(
    Client.Id(1) to Client(Client.Id(1), Client.Email("user@email.com")),
    Client.Id(2) to Client(Client.Id(2), Client.Email("jj@email.com")),
    Client.Id(3) to Client(Client.Id(3), Client.Email("guille@email.com")),
    Client.Id(4) to Client(Client.Id(4), Client.Email("adrian@email.com")),
    Client.Id(5) to Client(Client.Id(5), Client.Email("laureano@email.com")),
    Client.Id(6) to Client(Client.Id(6), Client.Email("pepe@email.com")),
    Client.Id(7) to Client(Client.Id(7), Client.Email("jc@email.com")),
    Client.Id(8) to Client(Client.Id(8), Client.Email("cuki@email.com")),
    Client.Id(9) to Client(Client.Id(9), Client.Email("sonrin@email.com")),
    Client.Id(10) to Client(Client.Id(10), Client.Email("anacleta@email.com")),
    Client.Id(11) to Client(Client.Id(11), Client.Email("user2@email.com")),
    Client.Id(12) to Client(Client.Id(12), Client.Email("jj2@email.com")),
    Client.Id(13) to Client(Client.Id(13), Client.Email("guille2@email.com")),
    Client.Id(14) to Client(Client.Id(14), Client.Email("adrian2@email.com")),
    Client.Id(15) to Client(Client.Id(15), Client.Email("laureano2@email.com")),
    Client.Id(16) to Client(Client.Id(16), Client.Email("pepe2@email.com")),
    Client.Id(17) to Client(Client.Id(17), Client.Email("jc2@email.com")),
    Client.Id(18) to Client(Client.Id(18), Client.Email("cuki2@email.com")),
    Client.Id(19) to Client(Client.Id(19), Client.Email("sonrin2@email.com")),
    Client.Id(20) to Client(Client.Id(20), Client.Email("anacleta2@email.com")),
    Client.Id(21) to Client(Client.Id(21), Client.Email("user3@email.com")),
    Client.Id(22) to Client(Client.Id(22), Client.Email("jj3@email.com")),
    Client.Id(23) to Client(Client.Id(23), Client.Email("guille3@email.com")),
    Client.Id(24) to Client(Client.Id(24), Client.Email("adrian3@email.com")),
    Client.Id(25) to Client(Client.Id(25), Client.Email("laureano3@email.com")),
    Client.Id(26) to Client(Client.Id(26), Client.Email("pepe3@email.com")),
    Client.Id(27) to Client(Client.Id(27), Client.Email("jc3@email.com")),
    Client.Id(28) to Client(Client.Id(28), Client.Email("cuki3@email.com")),
    Client.Id(29) to Client(Client.Id(29), Client.Email("sonrin3@email.com")),
    Client.Id(30) to Client(Client.Id(30), Client.Email("anacleta2@email.com")),
    Client.Id(31) to Client(Client.Id(31), Client.Email("user3@email.com")),
    Client.Id(32) to Client(Client.Id(32), Client.Email("jj3@email.com")),
    Client.Id(33) to Client(Client.Id(33), Client.Email("guille3@email.com")),
    Client.Id(34) to Client(Client.Id(34), Client.Email("adrian3@email.com")),
    Client.Id(35) to Client(Client.Id(35), Client.Email("laureano3@email.com")),
    Client.Id(36) to Client(Client.Id(36), Client.Email("pepe3@email.com")),
    Client.Id(37) to Client(Client.Id(37), Client.Email("jc3@email.com")),
    Client.Id(38) to Client(Client.Id(38), Client.Email("cuki3@email.com")),
    Client.Id(39) to Client(Client.Id(39), Client.Email("sonrin3@email.com")),
    Client.Id(40) to Client(Client.Id(40), Client.Email("anacleta3@email.com")),
    Client.Id(41) to Client(Client.Id(41), Client.Email("user4@email.com")),
    Client.Id(42) to Client(Client.Id(42), Client.Email("jj4@email.com")),
    Client.Id(43) to Client(Client.Id(43), Client.Email("guille4@email.com")),
    Client.Id(44) to Client(Client.Id(44), Client.Email("adrian4@email.com")),
    Client.Id(45) to Client(Client.Id(45), Client.Email("laureano4@email.com")),
    Client.Id(46) to Client(Client.Id(46), Client.Email("pepe4@email.com")),
    Client.Id(47) to Client(Client.Id(47), Client.Email("jc4@email.com")),
    Client.Id(48) to Client(Client.Id(48), Client.Email("cuki4@email.com")),
    Client.Id(49) to Client(Client.Id(49), Client.Email("sonrin4@email.com")),
    Client.Id(50) to Client(Client.Id(50), Client.Email("anacleta4@email.com")),
    Client.Id(51) to Client(Client.Id(51), Client.Email("user5@email.com")),
    Client.Id(52) to Client(Client.Id(52), Client.Email("jj5@email.com")),
    Client.Id(53) to Client(Client.Id(53), Client.Email("guille5@email.com")),
    Client.Id(54) to Client(Client.Id(54), Client.Email("adrian5@email.com")),
    Client.Id(55) to Client(Client.Id(55), Client.Email("laureano5@email.com")),
    Client.Id(56) to Client(Client.Id(56), Client.Email("pepe5@email.com")),
    Client.Id(57) to Client(Client.Id(57), Client.Email("jc5@email.com")),
    Client.Id(58) to Client(Client.Id(58), Client.Email("cuki5@email.com")),
    Client.Id(59) to Client(Client.Id(59), Client.Email("sonrin5@email.com")),
    Client.Id(60) to Client(Client.Id(60), Client.Email("anacleta5@email.com")),
  )

  fun all(): List<Client> = db.values.toList()

  fun exist(id: Client.Id) = db.containsKey(id)

  fun get(id: Client.Id) : Validated<Client> =
    if (db.containsKey(id))
      SuccessfulValidation.of(db[id]!!)
    else
      UnsuccessfulValidation.of("client not found")

  fun find(id: Client.Id) : Client? = db[id]
}
