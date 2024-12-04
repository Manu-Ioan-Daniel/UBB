from tinycss2 import serialize_identifier

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
from infrastructura.repo_inchiriere_fisier import FileRepoInchiriere
from infrastructura.repo_film_fisier import FileRepoFilm
from infrastructura.repo_client_fisier import FileRepoClient
validator_client=ValidatorClient()
validator_film=ValidatorFilm()
validator_inchiriere=ValidatorInchiriere()
repo_film_fisier=FileRepoFilm(r"C:\Users\Deny\Documents\GitHub\UBB\Facultate\Anul 1\Sem 1\FP\Lab 7-9\date\filme.txt")
repo_client_fisier=FileRepoClient(r"C:\Users\Deny\Documents\GitHub\UBB\Facultate\Anul 1\Sem 1\FP\Lab 7-9\date\clienti.txt")
repo_inchiriere_fisier=FileRepoInchiriere(r"C:\Users\Deny\Documents\GitHub\UBB\Facultate\Anul 1\Sem 1\FP\Lab 7-9\date\inchirieri.txt")
repo_client=RepoClient()
repo_film=RepoFilm()
repo_inchiriere=RepoInchiriere()
service_client=ServiceClient(validator_client, repo_client,repo_client_fisier)
service_film=ServiceFilm(validator_film, repo_film,repo_film_fisier)
service_inchiriere=ServiceInchiriere(repo_inchiriere,validator_inchiriere,service_film,service_client,repo_inchiriere_fisier)
consola=Consola(service_film, service_client,service_inchiriere)
consola.run()