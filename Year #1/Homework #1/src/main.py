TotalCost = 0 

BookNameList = [] #-> Store name of the books that gave by user
BookTypeList = [] #-> Store type of the books that gave by user
BookRentTime = [] #-> Store rent time of the books that gave by user
RentalFeeDict = {'Novel':2, "Textbook":4, 'Magazine':1,'Comic Book':1.50}
LateFeeDict = {'Novel':0.5, "Textbook":1, 'Magazine':0.25,'Comic Book':0.75}

print('''
    --- Welcome to book renting service ---
    ''')
Username = input("Username:")
NumberofBooksWillBeRentered = int(input("Book count you want to rent:"))
print('''
      -------------------------------------------
      | Book Type   | Rental Fee    | Late Fee  |
      -------------------------------------------
      | Novel       |   2$          |   0.50$   |
      | Textbook    |   4$          |   1$      |
      | Magazine    |   1$          |   0.25$   |
      | Comic Book  |   1.50$       |   0.75$   |
      -------------------------------------------
        ''')
print("You can rent for 14 days. If you want to rent more than 14 days you must give late fee for each day \n")

for i in range(NumberofBooksWillBeRentered):     
    NameOfBook = input("Name of the book:")
    BookNameList.append(NameOfBook)                          
    TypeOfBook = input("Which type of book you want to rent:")
    if TypeOfBook.capitalize() == "Novel" or TypeOfBook == "Textbook" or TypeOfBook == "Magazine" or TypeOfBook == "Comic Book":
        # Check input if valid
        
        BookTypeList.append(TypeOfBook)
        RentTimeOfBook = int(input("For how long you want to rent the book:"))
        BookRentTime.append(RentTimeOfBook)
    else:
        print("The book you tried to rent is not available on the list. Try again")    

if(len(BookTypeList) != 0):
    # This if statment check if BookTypeList has element(s) in itself. Because if there is no element the system will
    # throw an error

    print("-------------------------------------------")
    print(f'Username: {Username}')
    for i in range(len(BookRentTime)):
        if BookRentTime[i] > 14:
            #If BookRentTime[i] > 14 the late fee will be payed. 

            NewBookRentTime = BookRentTime[i] - 14
            LateBookRentTime = LateFeeDict[BookTypeList[i]] * NewBookRentTime
            #For example i = 0 ,BookTypeList[0] = 'Novel', and LateFeeDict['Novel'] = 0.5.  

            RentalFee = (RentalFeeDict[BookTypeList[i]]) * 14
            #Calculate fee for 14 hours with (Rental fee) * 14.For example BookTypeList[0] = 'Novel, Rental fee = RentalFeeDict['Novel'] = 2.

            print(f'Book name: {BookNameList[i]},Book type: {BookTypeList[i]}, rental fee: {RentalFee}$, late fee cost: {LateBookRentTime}$')
            TotalCost = TotalCost + RentalFee + LateBookRentTime
        else:
            RentalFee = (RentalFeeDict[BookTypeList[i]]) * BookRentTime[i]
            #This part will calculate fee with times that smaller than 14.

            print(f'Book name: {BookNameList[i]}, Book type: {BookTypeList[i]}, rental fee: {RentalFee}$, late fee: 0$')
            TotalCost = TotalCost + RentalFee
    print(f'Total Cost: {TotalCost}$')
    print("-------------------------------------------")


