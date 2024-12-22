def AddUser():
    User = []

    with open(r"input\users.txt", "r") as file: 
        #Open file for read

        lines = file.readlines()
        print("Existin users:")
        for i in range(len(lines)):
        #subtract "\n" from elements of lines and print it out
            if(i!=0):
            #Thanks to it the first line won't write on the screen

                
                print(lines[i][0].strip())
                #Because of the fact that I added users as a list element I can use list[i][0]

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
 
def Login():

    UserName = input("Username(for login):")

    with open(r"input\users.txt", "r") as file:

        lines = file.readlines()

        if UserName+"\n" in lines:
        #Check if UserName + "|n" is in the list

            print(f"Login succesfull. Welcome {UserName}")

