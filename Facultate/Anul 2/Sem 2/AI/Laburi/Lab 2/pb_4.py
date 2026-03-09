import pandas as pd
import matplotlib.pyplot as plt
from math import log, sqrt

def plotData(data1, data2, title1, title2):
    fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(15,5))
    ax1.hist(data1, 20)
    ax1.set_title(title1)
    ax2.hist([log(x) for x in data2 if pd.notna(x)], 20)
    ax2.set_title(title2)
    plt.show()

def logNormalize(data):
    return [log(x) for x in data if pd.notna(x)]

def minMaxNormalize(data):
    min_val = min(x for x in data if pd.notna(x))
    max_val = max(x for x in data if pd.notna(x))
    return [(x - min_val) / (max_val - min_val) for x in data if pd.notna(x)]

#Normalizare pb 1 durata aniilor de studii universitare

df = pd.read_csv("surveyDataSience.csv",header = 1)
education_col = "What is the highest level of formal education that you have attained or plan to attain within the next 2 years?"
education_map = {
    "Bachelor’s degree": 3,
    "Master’s degree": 5,
    "Doctoral degree": 8

}

df["YearsEducation"] = df[education_col].map(education_map)
plotData(df["YearsEducation"], logNormalize(df["YearsEducation"]), "Durata studiilor universitare", "Logaritm durata studiilor universitare")
plotData(df["YearsEducation"], minMaxNormalize(df["YearsEducation"]), "Durata studiilor universitare", "Min-Max durata studiilor universitare")







