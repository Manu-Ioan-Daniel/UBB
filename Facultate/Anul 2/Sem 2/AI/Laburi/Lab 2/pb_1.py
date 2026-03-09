import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


df = pd.read_csv("surveyDataSience.csv",header = 1)
print("Numar resp: ", df.shape[0])
print("Nr atribute: ", df.shape[1])
print(df.dtypes)
complete = df.dropna()
print("Respondenti cu date complete: ",complete.shape[0])

education_col = "What is the highest level of formal education that you have attained or plan to attain within the next 2 years?"

education_map = {
    "Bachelor’s degree": 3,
    "Master’s degree": 5,
    "Doctoral degree": 8,
}

df["YearsEducation"] = df[education_col].map(education_map)
medie = df["YearsEducation"].mean()
print("Durata medie estimata pt respondenti la studii", medie)

medie_romania = df[df["In which country do you currently reside?"] == "Romania"]["YearsEducation"].mean()
print("Durata medie estimata pt respondenti romani la studii", medie_romania)

medie_romania_femei = df[(df["In which country do you currently reside?"] == "Romania") &
                         (df["What is your gender? - Selected Choice"] == "Woman")
]["YearsEducation"].mean()
print("Durata medie estimata pt respondenti romani femei la studii", medie_romania_femei)

femei_ro_complete = complete[
    (complete["In which country do you currently reside?"] == "Romania") &
    (complete["What is your gender? - Selected Choice"] == "Woman")
]

print("Femei RO cu date complete:", femei_ro_complete.shape[0])

python_col = "What programming languages do you use on a regular basis? (Select all that apply) - Selected Choice - Python"
cpp_col = "What programming languages do you use on a regular basis? (Select all that apply) - Selected Choice - C++"

femei_ro = df[
    (df["In which country do you currently reside?"] == "Romania") &
    (df["What is your gender? - Selected Choice"] == "Woman")
]

python_femei = femei_ro[femei_ro[python_col].notna()]
cpp_femei = femei_ro[femei_ro[cpp_col].notna()]

print("Femei RO Python:", python_femei.shape[0])
print("Femei RO C++:", cpp_femei.shape[0])

age_col = "What is your age (# years)?"

print("Interval dominant Python:", python_femei[age_col].value_counts().idxmax())
print("Interval dominant C++:", cpp_femei[age_col].value_counts().idxmax())

for col in df.select_dtypes(include="object"):
    print(col)
    print("Valori unice:", df[col].nunique())

exp_col = "For how many years have you been writing code and/or programming?"

def mid_interval(interval):
    if pd.isna(interval):
        return np.nan
    interval = interval.replace("years","")
    if "+" in interval:
        return float(interval.replace("+", ""))
    if "<" in interval:
        return float(interval.replace("<",""))
    if "never" in interval:
        return 0.0
    parts = interval.split("-")
    return (float(parts[0]) + float(parts[1])) / 2

df["ExperienceYears"] = df[exp_col].apply(mid_interval)
print("Min:", df["ExperienceYears"].min())
print("Max:", df["ExperienceYears"].max())
print("Media:", df["ExperienceYears"].mean())
print("Std:", df["ExperienceYears"].std())
print("Mediana:", df["ExperienceYears"].median())


#1.b

python_all = df[df[python_col].notna()]

plt.hist(python_all[age_col])
plt.title("Distribuyie Python - global")
plt.xlabel("Varsta")
plt.ylabel("Frecventa")
plt.show()

python_ro = df[
    (df["In which country do you currently reside?"] == "Romania") &
    (df[python_col].notna())
]

plt.hist(python_ro[age_col])
plt.title("Distributie Python - Romania")
plt.show()

python_ro_f = df[
    (df["In which country do you currently reside?"] == "Romania") &
    (df["What is your gender? - Selected Choice"] == "Woman") &
    (df[python_col].notna())
]

plt.hist(python_ro_f[age_col])
plt.title("Distributie Python - femei Romania")
plt.show()

plt.boxplot(df["ExperienceYears"].dropna())
plt.title("Boxplot - Vechime programare")
plt.show()