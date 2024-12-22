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

            for k in range( len(UserList[i])):

                if k%3 == 0:
                #It will append items that can be divised to 3 

                    print(UserList[i][k].split(',')[0])
                    #Print the items before first comma

    Username = input("Name(yourname_surname):")

    Age = 20

    Mail = Username + "@" + "example.com"

    User.append(Username,","" ")
    User.append(Age,"+"," ")
    User.append(Mail)
    User.append("\n")

    
    with open(r"input\users.txt", "a") as file:
    #Open file in append mod. It will add item as last sentence

        file.writelines(User)
        #Add "\n" 

    UserName = input("Username(for login):")

    with open(r"input\users.txt", "r") as file:

        lines = file.readlines()

        if UserName+"\n" in lines:
        #Check if UserName + "|n" is in the list

            print(f"Login succesfull. Welcome {UserName}")

def ProductManagement():
    with open(r"input\products.txt", "r") as file:
        list = file.readlines()

        for i in range(len(list)):

            if(i!=0):
                print(f"{i}. item, {list[i][0]}, {list[i][1]}, {list[i][2]}" )

