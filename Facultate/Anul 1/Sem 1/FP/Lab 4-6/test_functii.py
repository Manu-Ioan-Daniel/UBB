from functii import*
from datetime import datetime
def test_tranz_has_bigger_sum():
    assert tranz_has_bigger_sum(15,14) == True
    assert tranz_has_bigger_sum(29, 11) == True
    assert tranz_has_bigger_sum(5000, 3000) == True
    assert tranz_has_bigger_sum(150, 1499) == False
    assert tranz_has_bigger_sum(69, 420) == False
def test_tranz_before_day():
    assert tranz_before_day("2023-11-10",datetime(2023,11,7))==False
    assert tranz_before_day("2020-5-7", datetime(2023, 9, 8)) == True
    assert tranz_before_day("2024-11-10", datetime(2024, 11, 10)) == False
def test_same_type():
    assert same_type("IN","IN") == True
    assert same_type("IN", "in") == True
    assert same_type("in", "IN") == True
    assert same_type("out", "out") == True
    assert same_type("OUT", "out") == True
    assert same_type("out", "IN") == False
    assert same_type("OUT", "IN") == False
    assert same_type("IN", "out") == False
    assert same_type("in", "out") == False
def test_updated_tranzactions_after_eliminating_certain_type():
    tranzactions = [["2022-10-01",69,"in"],["2022-10-01",70,"in"],["2022-10-01",71,"in"],["2022-10-01",69,"out"]]
    type = "in"
    assert [["2022-10-01",69,"out"]]==updated_tranzactions_after_eliminating_certain_type(type,tranzactions)
def test_is_tranz_before_day_with_bigger_sum():
    tranzaction = ["2022-10-01",69,"in"]
    sum = 70
    day = datetime(2023,11,7)
    assert is_tranz_before_day_with_bigger_sum(tranzaction,sum,day) == False
    sum=68
    assert is_tranz_before_day_with_bigger_sum(tranzaction, sum, day) == True


def test_all():
    test_tranz_has_bigger_sum()
    test_tranz_before_day()
    test_same_type()
    test_updated_tranzactions_after_eliminating_certain_type()
    test_is_tranz_before_day_with_bigger_sum()
