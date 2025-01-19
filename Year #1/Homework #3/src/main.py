def UserManagement():
    User = []
    UserList = []
    NewUser = []

    with open(r"input\users.txt", "r") as file: 
        #Open file for read

        lines = file.readlines()
        print("Existing users:")

        for i in range(len(lines)):
        #Open a for loop for add items of the file to a 2d array

            NewUser = [lines[i]]
            UserList.append(NewUser)

        for i in range(len(UserList)):

            if i!= 0:

                for k in range( len(UserList[i])):

                    if k%3 == 0 :
                    #It will append items that can be divised to 3 

                        print(UserList[i][k].split(',')[0])
                        #Print the items before first comma

    Username = "Test_User"

    Age = "18"

    Mail = "Test_User@gmail.com"

    User.append(Username + "," +" ")
    User.append(Age+","+" ")
    User.append(Mail)
    User.append("\n")

    
    with open(r"input\users.txt", "a") as file:
    #Open file in append mod. It will add item as last sentence

        file.writelines(User)

    UserName = input("Username(for login):")

    with open(r"input\users.txt", "r") as file:

        lines = file.readlines()
        
        for i in lines:

            if UserName in i:
            #Check if UserName + "|n" is in the list
                print(f"Login succesfull. Welcome {UserName}")
def AddProduct():

    print("Original products:")
    with open(r"input\products.txt", "r") as file:

        NewItem = []
        ItemList = []

        lines = file.readlines()

        for i in range(len(lines)):

            if(i!=0):

                NewItem = [lines[i].strip()]
                ItemList.append(NewItem)

        for i in range(len(ItemList)):

            print(ItemList[i][0])
    print("\n")

    with open(r"input\products.txt", "a") as file:

        ItemList = [["21, Smart Watch, 2000, 15 \n"],["22, Gaming Mouse, 1500, 20 \n"],
                    ["23, Mechanicak Keyboard, 3500, 10 \n"],["24, Wireless Earbuds, 2500, 30 \n"],
                    ["25, Monitor, 5000, 8 \n" ]] 

        print("Added products")
        for i in ItemList:

            for j in i:

                file.write(j)
                print(j.strip())
    print('\n')                       
def ShoppingCart():
    IDList = ["1","2","3","5","7","9","12","14","16","18","20","21","22","23","25"]
    ShoppinCart = []
    TotalCost = 0;

    with open(r"input\products.txt", "r") as file:

        NewItem = []
        ItemList = []

        lines = file.readlines()

        for i in range(len(lines)):

            if(i!=0):
            #Thanks to this line we will bypass "ID, Name, Price, Stock" part

                NewItem = [lines[i].strip()]
                ItemList.append(NewItem)

        for i in range(len(ItemList)):

            if ItemList[i][0].split(',')[0] in IDList:  
            #This part will add the items to ShoppingCart that have ID in the IDList

                print(ItemList[i][0])
                ShoppinCart.append(ItemList[i][0].strip()),

                TotalCost = TotalCost + int(ItemList[i][0].split(',')[2])

        print(f'Total cost: {TotalCost} \n')
        TotalCost = 0    

        ElementsToRemove = ["1","3","7","9","16","21","23"]

        for i in ShoppinCart[:]:
        #I added [:] for create copy of the list for manipulate its elements with split(',')

            for j in i.split(','):
                
                if j in ElementsToRemove:
                    
                    ShoppinCart.remove(i)
                    
        print("\n")
        print("Elements after removed")

        for i in ShoppinCart:

            print(i)
            TotalCost = TotalCost + int(i.split(',')[2])

        print(f'Total cost: {TotalCost} \n')

    return ShoppinCart 
    #Thanks to this line you can use ShoppinCart variable for Discount() function
def Discount(cart):
    #I suggest you the use ShoppingCart() function as the input of this function

    TotalCost = 0

    for i in range(len(cart)):

        if cart[i].split(",")[1] == " Laptop":
            
            print(f'After 15% discount on laptop: {int(cart[i].split(",")[2])*85/100}')

            cart[i] = cart[i].split(",")[0]+','+cart[i].split(",")[1]+','+str(int(int(cart[i].split(",")[2])*17/20))+ ','+cart[i].split(",")[3]
            #This line only changes 3rd element of the array and this is the price of the element

        elif cart[i].split(",")[1] == " Monitor":

            print(f'After 15% discount on monitor: {int(cart[i].split(",")[2])*85/100}')

            cart[i] = cart[i].split(",")[0]+','+cart[i].split(",")[1]+','+str(int(int(cart[i].split(",")[2])*17/20))+ ','+cart[i].split(",")[3]
            #This line only changes 3rd element of the array and this is the price of the element

        TotalCost = TotalCost + int(cart[i].split(",")[2])
 
    if TotalCost > 1000:
        TotalCost = TotalCost*90/100
   
    print(f'Total cost is {TotalCost}')

    return TotalCost     
def WriteData(cart, cost):

    with open(r"output\transaction_summary.txt", 'a', encoding='utf-8') as file:

        file.write("User: Test_User \n")

        for i in cart:

            file.write(f'{i.split(",")[1]} x 1 : {i.split(",")[2]} \n')

        file.write(f'Total cost: {cost}') 

    print("Succes")       
def UpdateProduct(list,startingpoint):

    if startingpoint == len(list):
        return None
    
    NewItem = []
    ItemList = [] 

    with open(r"input\products.txt", "r") as file:

        lines = file.readlines()

        for i in range(len(lines)):

            if(i!=0):

                NewItem = [lines[i].strip()]
                ItemList.append(NewItem)

        for i in range(len(ItemList)):

            if ItemList[i][0].split(',')[0] == list[startingpoint]:

                print(f'Before {ItemList[i][0]}')

                IncreasePrice = float(input("Price multiplier(0.9/1.10):"))
                IncreaseStock = float(input("Stock multiplier(0.8/1.20):"))

                ItemList[i][0] = ItemList[i][0].split(',')[0] + ','+ItemList[i][0].split(',')[1]+','+str(int(ItemList[i][0].split(',')[2]) * IncreasePrice) + ',' + str(int(ItemList[i][0].split(',')[3]) * IncreaseStock)
                #This line changes price and stock value depend on the multiplier

                print(f'After {ItemList[i][0]}')

    with open(r"input\products.txt", "w") as file:
        #This segment will erase file and re-write file with the updated ItemList array
        
        file.write("ID, Name, Price, Stock \n")

        for i in range(len(ItemList)):

            file.writelines(f'{ItemList[i][0]} \n')
    
    return UpdateProduct(list, startingpoint+1)
def Filter():

    with open(r"input\products.txt", "r") as file:

        PriceRange = input("Price range(Format: x-y):")

        #Split the price range with "-" 
        SmallPrice = PriceRange.split("-")[0] 
        BigPrice = PriceRange.split("-")[1]

        NewItem = []
        ItemList = []

        lines = file.readlines()

        for i in range(len(lines)):

            if(i!=0):

                NewItem = [lines[i].strip()]
                ItemList.append(NewItem)

        for i in range(len(ItemList)):

            if int(SmallPrice) < int(ItemList[i][0].split(',')[2]) < int(BigPrice):
                
                print(ItemList[i][0])

        print('\n')
        print("Filter by stock availability > 10")

        for i in range(len(ItemList)):

            if int(ItemList[i][0].split(',')[3]) > 10:
                
                print(ItemList[i][0])   

        print('\n')
        print('Products that contaion word Mouse')   

        for i in range(len(ItemList)):

            if 'Mouse' in ItemList[i][0].split(',')[1] :
                
                print(ItemList[i][0])             

def main():
    #UserManagement()
    #AddProduct()
    #ShoppingCart()                     #You dont need to use this lane if you will wun WriteData() or Discount()
    #Discount(ShoppingCart())           #You dont need to use this lane if you will wun WriteData()
    #WriteData(ShoppingCart(),Discount(ShoppingCart()) )
    #UpdateList = ["1","4","14","21","24"]
    #UpdateProduct(UpdateList, 0)
    #Filter()

main()