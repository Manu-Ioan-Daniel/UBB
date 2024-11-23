
from teste.teste import Teste
from validare.validare_client import ValidatorClient
from validare.validare_filme import ValidatorFilm
from infrastructura.repo_client import RepoClient
from infrastructura.repo_film import RepoFilm
from prezentare.Consola import Consola
from business.service_film import ServiceFilm
from business.service_client import ServiceClient
from validare.validare_inchiriere import ValidatorInchiriere
from business.service_inchiriere import ServiceInchiriere
from infrastructura.repo_inchiriere import RepoInchiriere
teste=Teste()
teste.ruleaza_toate_testele()
validator_client=ValidatorClient()
validator_film=ValidatorFilm()
validator_inchiriere=ValidatorInchiriere()
repo_client=RepoClient()
repo_film=RepoFilm()
repo_inchiriere=RepoInchiriere()
service_client=ServiceClient(validator_client, repo_client)
service_film=ServiceFilm(validator_film, repo_film)
service_inchiriere=ServiceInchiriere(repo_inchiriere,validator_inchiriere,service_film,service_client)
consola=Consola(service_film, service_client,service_inchiriere)
consola.run()