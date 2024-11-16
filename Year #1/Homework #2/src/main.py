def is_prime(value):
    isprime = 0
    if isinstance(value, int):
        for i in range(1, value, 1):
            if value // i != 0:
                isprime += 1
            else:
                pass
        if isprime == value-1:
            return True
        else:
            return False
    else:
        print("This is not valid")
def accuracy_test_is_prime():
    pass
def speed_test_is_prime():
    pass

