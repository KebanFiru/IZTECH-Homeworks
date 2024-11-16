def is_prime(value):
    isprime = 0
    #This integer for check if value is prime or not. If value is prime, isprime is going to be equel value -1

    if isinstance(value, int):
        #Check if value is int or not

        for i in range(1, value, 1):
            #1<= i <= value-1 because of the nature of for loop

            if value // i != 0:
                # Increase isprime by 1 if value has remainder with the integer that smaller than value

                isprime += 1
            else:
                pass
        if isprime == value-1:
            #Check if isprime is equel to value-1. That means all the numbers that between 1 
            #and value-1 cant divide value without remainder and that means it's a prime number

            return True
        else:
            return False
    else:
        print("This is not valid")
def accuracy_test_is_prime():
    pass
def speed_test_is_prime():
    pass

