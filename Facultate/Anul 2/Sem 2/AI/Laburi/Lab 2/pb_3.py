import nltk
from nltk.tokenize import sent_tokenize, word_tokenize
from unidecode import unidecode
from nltk.corpus import wordnet

with open("texts.txt", "r", encoding = 'utf-8') as f:
    text = f.read()

#3.1

sentences = sent_tokenize(text)
print("Numar propozitii:", len(sentences))

#3.2

words = word_tokenize(text)
print("Numar cuvinte:", len(words))

#3.3

print("Numar cuvinte unice:", len(set(words)))

#3.4

shortest_word = min(words, key=len)
longest_word = max(words, key=len)
print("Cel mai scurt cuvant:", shortest_word)
print("Cel mai lung cuvant:", longest_word)

#3.5

text_fara_diacritice = unidecode(text)
print("Text fara diacritice:", text_fara_diacritice)

#3.6

synonyms = []
for syn in wordnet.synsets(longest_word):
    for lemma in syn.lemmas():
        synonyms.append(lemma.name())

print("Sinonime pentru cuvantul cel mai lung:", set(synonyms))


