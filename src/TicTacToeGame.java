import java.util.*;

public class TicTacToeGame {
        static ArrayList<Integer> playerPositions = new ArrayList<>();
        static ArrayList<Integer> cpuPositions = new ArrayList<>();
        static char[][] gameBoard = {
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}
        };
        static ArrayList<List<Integer>> winningConditions = new ArrayList<>();
        static int count = 0;

    public static void main(String[] args) {
        winningConditions.addAll(getWinningConditions());

        System.out.println();
        System.out.println("Welcome to the Tic Tac Toe Game!");
        System.out.println("Your opponent will be a computer and the first to get three in a row is the winner!");
        System.out.println("You will be the 'X's and the computer will be the 'O's");
        System.out.println("There are a total of 9 locations available to place your piece");
        System.out.println("The locations are numbered 1-9 going from top to bottom, left to right");
        System.out.println("This is represented by the example below:\n" + exampleBoard());
        System.out.println("Let the game begin!");
        String result;
        boolean validInput = false;
        Scanner scan = new Scanner(System.in);
        printGameBoard(gameBoard);

        while(true) {
            int input = 0;
            while (!validInput)
            {
                System.out.print("Where would you like to place your 'X'?: ");
                try {
                    input = scan.nextInt();
                    while(input > 9 || input < 1)//make sure they enter a number between 1-9
                    {
                        System.out.print("That's not a number between 1-9. Please enter a valid input: ");
                        input = scan.nextInt();
                    }
                    //make sure the position isn't already taken.
                    while (playerPositions.contains(input) || cpuPositions.contains(input)) {
                        System.out.print("Position taken! Enter a position that isn't already taken: ");
                        input = scan.nextInt();
                    }
                    validInput = true;
                } catch (InputMismatchException e) {//make sure user enters valid input (integer)
                    System.out.println("The entry was an invalid input. Please enter an integer.");
                    scan.next();
                }
            }

            placePiece(input, "user", gameBoard);
            System.out.println("You have placed an 'X' at location: " + input);
            count++;
            result = checkForWinner();
            if (!result.isEmpty()) // if there is a winner after player plays
            {
                if(count < 9)//the board isn't full yet
                {
                    printGameBoard(gameBoard);
                    System.out.println(result);//then congratulate User and dip
                    break;
                }
                else //This mean the game ended in a tie, we will then clear the board and restart
                {
                    printGameBoard(gameBoard);
                    System.out.println(result);
                    System.out.println("We can't have it end like that can we? Let's start over!");
                    System.out.println("This time the CPU gets to go first! Pieces remain the same!");
                    playerPositions.clear();
                    cpuPositions.clear();
                    gameBoard[0][0] = ' ';
                    gameBoard[0][2] = ' ';
                    gameBoard[0][4] = ' ';
                    gameBoard[2][0] = ' ';
                    gameBoard[2][2] = ' ';
                    gameBoard[2][4] = ' ';
                    gameBoard[4][0] = ' ';
                    gameBoard[4][2] = ' ';
                    gameBoard[4][4] = ' ';
                    count = 0;
                }

            }

            int cpuPos = cpuBrain();//decides where to go to win
            placePiece(cpuPos, "cpu", gameBoard);
            System.out.println("The CPU has placed an 'O' at location: " + cpuPos);
            count++;
            result = checkForWinner();
            if (!result.isEmpty()) {//If there is a winner after CPU plays
                if(count < 9)//the board isn't full yet
                {
                    printGameBoard(gameBoard);
                    System.out.println(result);//then congratulate CPU and dip
                    break;
                }
                else //This mean the game ended in a tie, we will then clear the board and restart
                {
                    printGameBoard(gameBoard);
                    System.out.println(result);
                    System.out.println("We can't have it end like that can we? Let's start over!");
                    System.out.println("This time the User gets to go first! Pieces remain the same!");
                    playerPositions.clear();
                    cpuPositions.clear();
                    gameBoard[0][0] = ' ';
                    gameBoard[0][2] = ' ';
                    gameBoard[0][4] = ' ';
                    gameBoard[2][0] = ' ';
                    gameBoard[2][2] = ' ';
                    gameBoard[2][4] = ' ';
                    gameBoard[4][0] = ' ';
                    gameBoard[4][2] = ' ';
                    gameBoard[4][4] = ' ';
                    count = 0;
                }
            }

            printGameBoard(gameBoard);
            validInput = false;
        }
    }

    private static String exampleBoard() {
        return  "1 | 2 | 3\n" +
                "- + - + -\n" +
                "4 | 5 | 6\n" +
                "- + - + -\n" +
                "7 | 8 | 9\n";
    }

    private static int cpuBrain() {
        Integer cpuPos = 0;

        //if this is the first move don't matter just place somewhere that isn't taken
        if(cpuPositions.isEmpty())
        {
            if(!playerPositions.contains(5))
            {
                cpuPos = 5;//middle is best space to have first off
            }
            else//if not available just take one
            {
                Random num = new Random();
                do {
                    cpuPos = num.nextInt(9) + 1;
                } while (playerPositions.contains(cpuPos));
            }
        }
        else
        {
            for(List<Integer> l : winningConditions) {
                //If user has any winning combinations, STOP THE USER! TAKE THE SPOT!
                if (playerPositions.contains(l.get(0)) && playerPositions.contains(l.get(1))
                        && !cpuPositions.contains(l.get(2)))
                {
                    cpuPos = l.get(2);
                    break;
                }
                else if (playerPositions.contains(l.get(1)) && playerPositions.contains(l.get(2))
                        && !cpuPositions.contains(l.get(0)))
                {
                    cpuPos = l.get(0);
                    break;
                }
                else if (playerPositions.contains(l.get(0)) && playerPositions.contains(l.get(2))
                        && !cpuPositions.contains(l.get(1)))
                {
                    cpuPos = l.get(1);
                    break;
                }
                //If the CPU can win, WIN IT ALL BABY!
                else if (cpuPositions.contains(l.get(0)) && cpuPositions.contains(l.get(2))
                        && !playerPositions.contains(l.get(1)))
                {
                    cpuPos = l.get(1);
                    break;
                }
                else if (cpuPositions.contains(l.get(1)) && cpuPositions.contains(l.get(2))
                        && !playerPositions.contains(l.get(0)))
                {
                    cpuPos = l.get(0);
                    break;
                }
                else if (cpuPositions.contains(l.get(0)) && cpuPositions.contains(l.get(1))
                        && !playerPositions.contains(l.get(2)))
                {
                    cpuPos = l.get(2);
                    break;
                }
                else//otherwise just place somewhere he ain't winning anyway
                {
                    Random num = new Random();
                    do {
                        cpuPos = num.nextInt(9) + 1;
                    } while (playerPositions.contains(cpuPos) || cpuPositions.contains(cpuPos));
                }
            }
        }
        return cpuPos;
    }

    public static void placePiece(int place, String player, char[][] gameBoard){
        if(player.equals("user")) {
            playerPositions.add(place);
            switch(place){
                case 1:
                    gameBoard[0][0] = 'X';
                    break;
                case 2:
                    gameBoard[0][2] = 'X';
                    break;
                case 3:
                    gameBoard[0][4] = 'X';
                    break;
                case 4:
                    gameBoard[2][0] = 'X';
                    break;
                case 5:
                    gameBoard[2][2] = 'X';
                    break;
                case 6:
                    gameBoard[2][4] = 'X';
                    break;
                case 7:
                    gameBoard[4][0] = 'X';
                    break;
                case 8:
                    gameBoard[4][2] = 'X';
                    break;
                default:
                    gameBoard[4][4] = 'X';
                    break;
            }
        } else {
            cpuPositions.add(place);
            switch(place){
                case 1:
                    gameBoard[0][0] = 'O';
                    break;
                case 2:
                    gameBoard[0][2] = 'O';
                    break;
                case 3:
                    gameBoard[0][4] = 'O';
                    break;
                case 4:
                    gameBoard[2][0] = 'O';
                    break;
                case 5:
                    gameBoard[2][2] = 'O';
                    break;
                case 6:
                    gameBoard[2][4] = 'O';
                    break;
                case 7:
                    gameBoard[4][0] = 'O';
                    break;
                case 8:
                    gameBoard[4][2] = 'O';
                    break;
                default:
                    gameBoard[4][4] = 'O';
                    break;
            }
        }
    }

    public static void printGameBoard(char[][] gameBoard){
        for(char[] row : gameBoard){
            for(char c : row){
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static String checkForWinner(){
        String result = "";

        ArrayList<List<Integer>> winningCondition = getWinningConditions();

        for(List<Integer> l : winningCondition){
            if (playerPositions.containsAll(l))
            {
                result = "Congratulations! You beat the CPU!";
                break;
            }
            else if (cpuPositions.containsAll(l))
            {
                result = "You were beat by the CPU. Better luck next time!";
                break;
            }
            else if (count == 9)
            {
                if (playerPositions.containsAll(l))
                {
                    result = "Congratulations! You beat the CPU!";
                    break;
                }
                else if (cpuPositions.containsAll(l))
                {
                    result = "You were beat by the CPU. Better luck next time!";
                    break;
                }
                else
                {
                    result =  "No winner! This game ends in a tie!";
                    break;
                }
            }
        }
        return result;
    }

    private static ArrayList<List<Integer>> getWinningConditions() {
        List<Integer> topRow = Arrays.asList(1,2,3);
        List<Integer> midRow = Arrays.asList(4,5,6);
        List<Integer> botRow = Arrays.asList(7,8,9);
        List<Integer> leftCol = Arrays.asList(1,4,7);
        List<Integer> midCol = Arrays.asList(2,5,8);
        List<Integer> rightCol = Arrays.asList(3,6,9);
        List<Integer> cross1 = Arrays.asList(1,5,9);
        List<Integer> cross2 = Arrays.asList(3,5,7);

        ArrayList<List<Integer>> winningCondition = new ArrayList<>();
        winningCondition.add(topRow);
        winningCondition.add(midRow);
        winningCondition.add(botRow);
        winningCondition.add(leftCol);
        winningCondition.add(midCol);
        winningCondition.add(rightCol);
        winningCondition.add(cross1);
        winningCondition.add(cross2);

        return winningCondition;
    }
}