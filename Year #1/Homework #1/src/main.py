TotalCost = 0 

BookNameList = []
BookTypeList = []
BookRentTime = []
RentalFeeDict = {'Novel':2, "Textbook":4, 'Magazine':1,'Comic Book':1.50}
LateFeeDict = {'Novel':0.5, "Textbook":1, 'Magazine':0.25,'Comic Book':0.75}

print('''
    --- Welcome to book renting service ---
    ''')
Username = input("Username:")
NumberofBooksWillBeRentered = int(input("Book count you want to rent:"))
print('''
      -------------------------------------------
      | Book Type   | Rental Fee    | Late fee  |
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
        BookTypeList.append(TypeOfBook)
        RentTimeOfBook = int(input("For how long you want to rent the book:"))
        BookRentTime.append(RentTimeOfBook)
    else:
        print("The book you tried to rent is not available on the list. Try again")    

if(len(BookTypeList) != 0): #Check for BookList list if there is an element or not 
    print("-------------------------------------------")
    print(f'Username: {Username}')
    for i in range(len(BookRentTime)):
        if BookRentTime[i] > 14:
            NewBookRentTime = BookRentTime[i] - 14
            LateBookRentTime = LateFeeDict[BookTypeList[i]] * NewBookRentTime
            RentalFee = (RentalFeeDict[BookTypeList[i]]) * 14
            print(f'Book name: {BookNameList[i]},Book type: {BookTypeList[i]}, rental fee: {RentalFee}$, late fee cost: {LateBookRentTime}$')
            TotalCost = TotalCost + RentalFee + LateBookRentTime
        else:
            RentalFee = (RentalFeeDict[BookTypeList[i]]) * BookRentTime[i]
            print(f'Book name: {BookNameList[i]}, Book type: {BookTypeList[i]}, rental fee: {RentalFee}$, late fee: 0$')
            TotalCost = TotalCost + RentalFee
    print(f'Total Cost: {TotalCost}$')
    print("-------------------------------------------")


