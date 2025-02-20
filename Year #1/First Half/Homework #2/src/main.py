from timeit import default_timer as timer

def is_prime(value):

    if isinstance(value, int):
        #Check if value is int or not

        isnotprime = False
        SquareRootofValue = value**(1/2)
        
        for i in range(2, int(SquareRootofValue)+1, 1):
            #2<= i <= value because of the nature of for loop

            if value % i != 0:
                pass

            elif value %i == 0:
                # Check if value is divide by i. If i can divide value it means value is not a prime number

                #print(i) -> if you want to learn which numbers that can divide value you can delete 
                # the comment signature behind print statement

                isnotprime = True
                break
                #After deleting comment signature behind print you should add comment signature beh ind break
                #Thanks to break statement code's performance is increased

        if isnotprime == True:
            return False
        else:
            return True
    else:
        print("This is not valid")
def accuracy_test_is_prime(test_case, expected_value):
    if expected_value == is_prime(test_case):
        #if expected_valule is true than this line will start

        print(f"Test for {test_case} is passed")
    else:
        print(f"Test for {test_case} is failed")

def speed_test_is_prime(value):
    timer()
    is_prime(value)
    print(f"Time for {value} ", timer())



accuracy_test_is_prime(23909, True)
accuracy_test_is_prime(43177, True)
accuracy_test_is_prime(31091, True)
accuracy_test_is_prime(44532, False)
accuracy_test_is_prime(13860, False)
accuracy_test_is_prime(38243, False)

speed_test_is_prime(43177)
speed_test_is_prime(784687)
speed_test_is_prime(12009367)
speed_test_is_prime(231002771)
speed_test_is_prime(3484798799)







