TotalCost = 0 

BookNameList = [] #-> Store name of the books that gave by user
BookTypeList = [] #-> Store type of the books that gave by user
BookRentTime = [] #-> Store rent time of the books that gave by user
RentalFeeDict = {'NOVEL':2, "TEXTBOOK":4, 'MAGAZINE':1,'COMIC BOOK':1.50}
LateFeeDict = {'NOVEL':0.5, "TEXTBOOK":1, 'MAGAZINE':0.25,'COMIC BOOK':0.75}

print('''
    --- Welcome to book renting service ---
    ''')
Username = input("Username:").upper()
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
print("You can rent for 14 days. If you want to rent more than 14 days you must pay late fee for each day \n")

NumberofBooksWillBeRentered = int(input("Book count you want to rent:"))

for i in range(NumberofBooksWillBeRentered):     
    NameOfBook = input("Name of the book:")
    BookNameList.append(NameOfBook)                          
    TypeOfBook = input("Which type of book you want to rent:")
    if TypeOfBook.upper() == "NOVEL" or TypeOfBook.upper() == "TEXTBOOK" or TypeOfBook.upper() == "MAGAZINE" or TypeOfBook.upper() == "COMIC BOOK":
        # Check input if valid

        BookTypeList.append(TypeOfBook.upper())
        RentTimeOfBook = int(input("For how long you want to rent the book:"))
        BookRentTime.append(RentTimeOfBook)
        if NumberofBooksWillBeRentered>1:
            print("-------------------------------------------")
    else:
        print("The book(s) which you tried to add is not valid book type")

if(len(BookTypeList) != 0):
    # This if statment check if BookTypeList has element(s) in itself. Because if there is no element the system will
    # throw an error

    print("-------------------------------------------")
    print(f'USERNAME: {Username}')
    for i in range(len(BookRentTime)):
        BookNameUpper = BookNameList[i].upper()
        RentalFee = RentalFeeDict[BookTypeList[i]]

        if BookRentTime[i] > 14:
            #If BookRentTime[i] > 14 the late fee will be payed. 

            NewBookRentTime = BookRentTime[i] - 14
            LateBookRentTime = (LateFeeDict[BookTypeList[i]] + RentalFeeDict[BookTypeList[i]]) * NewBookRentTime
            #For example i = 0 ,BookTypeList[0] = 'Novel', and LateFeeDict['Novel'] = 0.5.  

            print(f'BOOK NAME: {BookNameUpper} || BOOK TYPE: {BookTypeList[i]} || RENTAL FEE: {RentalFee}$ || LATE FEE COST: {LateBookRentTime}$')
            TotalCost = TotalCost + RentalFee + LateBookRentTime
        else:
            #This part will calculate fee with times that smaller than 14.

            print(f'BOOK NAME: {BookNameUpper} || BOOK TYPE: {BookTypeList[i]} || RENTAL FEE: {RentalFee}$ || LATE FEE COST: 0$')
            TotalCost = TotalCost + RentalFee
    print(f'TOTAL COST: {TotalCost}$')
    print("-------------------------------------------")


