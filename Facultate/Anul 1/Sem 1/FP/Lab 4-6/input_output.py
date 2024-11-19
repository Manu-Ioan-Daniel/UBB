from csv import excel_tab
from datetime import datetime
from functii import*
"""
 tranzactions = [
        {'day': '2022-03-15', 'sum': 105, 'type': 'in'},
        {'day': '2023-09-02', 'sum': 420, 'type': 'out'},
        {'day': '2024-06-24', 'sum': 69, 'type': 'in'},
        {'day': '2021-11-10', 'sum': 1, 'type': 'in'},
        {'day': '2024-08-19', 'sum': 477770, 'type': 'out'},
        {'day': '2025-01-28', 'sum': 3005, 'type': 'in'},
        {'day': '2009-07-11', 'sum': 299, 'type': 'out'}
    ]
"""
tranzactions = [["2022-03-15",105,"in"],["2023-09-02",420,"out"],["2024-06-24",69,"in"],["2021-11-10",1,"in"],["2024-08-19",477770,"out"],["2025-01-28",3005,"in"],["2009-07-11",299,"out"]]
states=[]
def tranzaction_get_sum(tranzaction):
    return int(tranzaction[1])

def tranzaction_get_day(tranzaction):
    return str(tranzaction[0])

def tranzaction_get_type(tranzaction):
    return tranzaction[2]

def get_sum():
    while(True):
        try:
            sum = int(input("Enter a sum: "))
            if(sum>0):
                return sum
            else:
                print("Please enter a positive integer!")
        except ValueError:
            print("Please enter a positive integer!")

def get_day():
    while(True):
        day = input("Enter a date(YYYY-MM-DD): ")
        try:
            date = datetime.strptime(day, "%Y-%m-%d").date()
            return str(date)
        except ValueError:
            print("Please enter a valid date!(YYYY-MM-DD)")

def get_type():
    while(True):
        type = input("Enter a type (IN or OUT): ")
        if type=="IN" or type=="OUT" or type=="in" or type=="out":
            return type
        else:
            print("Please enter a valid type!")

def print_tranzaction(tranzaction):
    print("Tranzaction of type: "+tranzaction_get_type(tranzaction)+ " from day: "+tranzaction_get_day(tranzaction)+" with a value of: "+str(tranzaction_get_sum(tranzaction)))

def print_tranzactions_with_bigger_sum(tranzactions,sum):
    #sum=get_sum()
    for tranzaction in tranzactions:
        if tranz_has_bigger_sum(tranzaction_get_sum(tranzaction),sum):
           print_tranzaction(tranzaction)


def print_tranz_before_day_with_bigger_sum(tranzactions,day,sum):
    #sum=get_sum()
    #day=get_day()
    for tranzaction in tranzactions:
        if is_tranz_before_day_with_bigger_sum(tranzaction,sum,day):
            print_tranzaction(tranzaction)

def print_tranz_of_type(tranzactions):
    type=get_type()
    for tranzaction in tranzactions:
        if same_type(tranzaction_get_type(tranzaction),type):
            print_tranzaction(tranzaction)

def actually_run():
    start2(tranzactions)
def start2(tranzactions):
    while(True):
        get_request2(tranzactions)

"""def start(tranzactions):
    while(True):
        print("What do you want to do?: ")
        print("1.Print the tranzactions with a sum bigger than a given sum")
        print("2.Print the tranzactions with a sum bigger than a given sum and a date before a given date")
        print("3.Print the tranzactions of a certain type")
        print("4.Eliminate all tranzactions of a certain type")
        print("5.Add a tranzaction")
        print("6.Undo an operation(4 or 5 or 7)")
        print("7.Modifiy a tranzaction")
        print("8.Eliminate tranzactions from a certain date")
        get_request(tranzactions)
"""
"""def get_request(tranzactions):
    number=get_answer()
    if number==1:
        print_tranzactions_with_bigger_sum(tranzactions)
    elif number==2:
        print_tranz_before_day_with_bigger_sum(tranzactions)
    elif number==3:
        print_tranz_of_type(tranzactions)
    elif number==4:
        type=get_type()
        save_state(tranzactions,states)
        updated_tranzactions=updated_tranzactions_after_eliminating_certain_type(type,tranzactions)
        print_all_tranzactions(updated_tranzactions)
        start(updated_tranzactions)
    elif number==5:
        save_state(tranzactions,states)
        tranzaction = return_tranzaction(get_day(),get_sum(),get_type())
        tranzactions.append(tranzaction)
        start(tranzactions)
    elif number==6:
        if states:
           updated_tranzactions=states.pop()
           start(updated_tranzactions)
        else:
            print("There are no operations to undo")
    elif number==7:
        save_state(tranzactions,states)
        index = search_tranzaction_index(tranzactions,get_sum(),get_day(),get_type())
        if index == None:
            print("Tranzaction does not exist!")
        else:
            tranzactions[index] = return_tranzaction(get_day(), get_sum(), get_type())
        start(tranzactions)
    elif number==8:
        save_state(tranzactions,states)
        updated_tranzactions=updated_tranzactions_after_eliminating_certain_day(get_day(),tranzactions)
        start(updated_tranzactions)
"""
def get_request2(tranzactions):
    user_input = input("Enter a command(/help if you dont know any commands)")
    inputs =[]
    inputs=user_input.split()
    match inputs[0]:
        case "/help":
            print("print >sum sum --- prints all tranzactions with a higher sum than the sum you input")
            print("print <day >sum day sum ---prints all tranzactions before a certain day with a sum bigger than the sum you input")
            print("add day sum type --- adds a tranzaction of the type 'type' that happened in the day 'day' with a sum of 'sum'" )
            print("update day sum type day2 sum2 type2 updates the tranzaction of the type 'type' that happened in the day 'day' with a sum of 'sum' to a tranzaction of the type 'type2' that happened in the day 'day2' with a sum of 'sum2'")
            print("delete type (type) ---deletes all tranzactions of a certain type")
            print("delete day (day) ---deletes all tranzactions that heppened in a certain day")
            print("undo ---undos the most recent change in the tranzactions list")
        case "print":
            match inputs[1]:
                case ">sum":
                     if(not inputs[2].isdigit()):
                         print("Invalid sum,please enter the command again")
                         start2(tranzactions)
                     print_tranzactions_with_bigger_sum(tranzactions,int(inputs[2]))
                case "<day":
                    if not valid_date(inputs[3]):
                        print("Invalid day,please enter the command again")
                        start2(tranzactions)
                    print_tranz_before_day_with_bigger_sum(tranzactions,datetime.strptime(inputs[3], "%Y-%m-%d"),int(inputs[4]))
                case Default:
                    print("No such command exists")
        case "add":
            if not valid_tranzaction(inputs[1],inputs[2],inputs[3]):
                start2(tranzactions)
            tranzaction=return_tranzaction(inputs[1],inputs[2],inputs[3])
            save_state(tranzactions,states)
            tranzactions.append(tranzaction)
        case "update":
            if not valid_tranzaction(inputs[1],inputs[2],inputs[3]) or not valid_tranzaction(inputs[4],inputs[5],inputs[6]):
                start2(tranzactions)
            index=search_tranzaction_index(tranzactions,int(inputs[2]),inputs[1],inputs[3])
            if index==None:
                print("Tranzaction does not exist!")
                start2(tranzactions)
            save_state(tranzactions, states)
            tranzactions[index]=return_tranzaction(inputs[4],inputs[5],inputs[6])
        case "delete":
            match inputs[1]:
                case "day":
                    if(not valid_date(inputs[2])):
                        print("Invalid day,please enter the command again")
                        start2(tranzactions)
                    save_state(tranzactions, states)
                    updated_tranzactions = updated_tranzactions_after_eliminating_certain_day(inputs[2], tranzactions)
                    start2(updated_tranzactions)
                case "type":
                    if not inputs[2].lower()=="in" and not inputs[2].lower()=="out":
                        print("Invalid type,please enter the command again")
                        start2(tranzactions)
                    save_state(tranzactions, states)
                    updated_tranzactions=updated_tranzactions_after_eliminating_certain_type(inputs[2],tranzactions)
                    start2(updated_tranzactions)
                case Default:
                    print("No such command exists")
        case "undo":
            if states:

                updated_tranzactions=states.pop()
                start2(updated_tranzactions)
            print("There are no operations to undo")
            start2(tranzactions)



def print_all_tranzactions(tranzactions):
    for tranzaction in tranzactions:
        print_tranzaction(tranzaction)

def get_answer():
    try:
        answer = int(input("Enter a number from 1 to 8: "))
        if(answer<1 or answer>8):
            print("Please enter a number between 1 and 8!")
        return answer
    except ValueError:
        print("Please enter a number!")
def run():
    start(tranzactions)
