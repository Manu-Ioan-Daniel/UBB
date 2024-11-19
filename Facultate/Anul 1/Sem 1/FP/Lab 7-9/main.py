from teste.teste import Teste
from validare.validare_client import ValidatorClient
from validare.validare_filme import ValidatorFilm
from infrastructura.repo_client import RepoClient
from infrastructura.repo_film import RepoFilm
from prezentare.Consola import Consola
from business.service_film import ServiceFilm
from business.service_client import ServiceClient
teste=Teste()
teste.ruleaza_toate_testele()
validator_client=ValidatorClient()
validator_film=ValidatorFilm()
repo_client=RepoClient()
repo_film=RepoFilm()
service_client=ServiceClient(validator_client, repo_client)
service_film=ServiceFilm(validator_film, repo_film)
consola=Consola(service_film, service_client)
consola.run()
