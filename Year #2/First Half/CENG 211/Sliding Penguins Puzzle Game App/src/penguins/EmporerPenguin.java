package penguins;

import java.util.Scanner

public class EmporerPenguin extends Penguin{

    public EmporerPenguin(String name){

        super(name);
    }

    @Override
    public void useSpecialAbility(String direction) throws Exception{

        Scanner input = new Scanner(System.in);

        System.out.println("How many squares you want to go(maximum of 3:)");
        int squareValue = input.nextInt();

        int[] coordinates = new int[2];
        coordinates = getPosition();

        if(direction.equals("Left")){

            coordinates[0] = coordinates[0] - squareValue;
            this.setPosition(coordinates[0], coordinates[1]);
            
        }
        else if(direction.equals("Right")){

            coordinates[0] = coordinates[0] + squareValue;
            this.setPosition(coordinates[0], coordinates[1]);
        }
        else if(direction.equals("Up")){

            coordinates[1] = coordinates[1] + squareValue;
            this.setPosition(coordinates[0], coordinates[1]);
        }
        else if(direction.equals("Down")){

            coordinates[1] = coordinates[1] - squareValue;
            this.setPosition(coordinates[0], coordinates[1]);
        }
        else{
            throw new Exception("Direction must be Left, Right, Up and Down not different");
        }


    }
}
