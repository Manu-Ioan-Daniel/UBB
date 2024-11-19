from datetime import datetime
import sys
import input_output
def tranz_has_bigger_sum(tranzaction_sum,sum):
        return tranzaction_sum>sum

def tranz_before_day(tranzaction_day,day):
    return datetime.strptime(tranzaction_day, "%Y-%m-%d") < day
def same_type(tranzaction_type,type):
    return tranzaction_type.lower() == type.lower()
def terminate():
    sys.exit()
def updated_tranzactions_after_eliminating_certain_type(type,tranzactions):
    updated_tranzactions = []
    for tranzaction in tranzactions:
        if not same_type(input_output.tranzaction_get_type(tranzaction),type):
             updated_tranzactions.append(tranzaction)
    return updated_tranzactions
def is_tranz_before_day_with_bigger_sum(tranzaction,sum,day):
    return tranz_has_bigger_sum(input_output.tranzaction_get_sum(tranzaction),sum) and tranz_before_day(input_output.tranzaction_get_day(tranzaction),day)

def return_tranzaction(tranzaction_day,tranzaction_sum,tranzaction_type):
    tranzaction=[]
    tranzaction.append(tranzaction_day)
    tranzaction.append(tranzaction_sum)
    tranzaction.append(tranzaction_type)
    return tranzaction
def save_state(tranzactions,states):
    tranzactions2 = []
    for tranzaction in tranzactions:
        tranzactions2.append(tranzaction.copy())
    states.append(tranzactions2)
def updated_tranzaction(tranzaction,tranzaction_sum,tranzaction_day,tranzaction_type):
    tranzaction[0]=tranzaction_day
    tranzaction[1]=tranzaction_sum
    tranzaction[2]=tranzaction_type
    return tranzaction
def search_tranzaction_index(tranzactions,tranzaction_sum,tranzaction_day,tranzaction_type):
    index=0
    for tranzaction in tranzactions:
        if input_output.tranzaction_get_type(tranzaction).lower() == tranzaction_type.lower() and str(input_output.tranzaction_get_day(tranzaction))==str(tranzaction_day) and input_output.tranzaction_get_sum(tranzaction)==tranzaction_sum:
            return index
        index+=1
    return None
def updated_tranzactions_after_eliminating_certain_day(day,tranzactions):
    updated_tranzactions = []
    for tranzaction in tranzactions:
        if not input_output.tranzaction_get_day(tranzaction)==day:
             updated_tranzactions.append(tranzaction)
    return updated_tranzactions
def valid_date(date):
    try:
        datetime.strptime(date, "%Y-%m-%d")
        return True
    except ValueError:
        return False
def valid_tranzaction(tranzaction_day,tranzaction_sum,tranzaction_type):
    ok=1
    if(not valid_date(tranzaction_day)):
        print("Invalid date,please enter the command again!")
        ok=0
    if(not tranzaction_sum.isdigit()):
        print("Invalid sum,please enter the command again!")
        ok=0
    if(not tranzaction_type.lower()=="in" and not tranzaction_type.lower()=="out"):
        print("Invalid type,please enter the command again!")
        ok=0
    if(ok==0):
        return False
    return True












