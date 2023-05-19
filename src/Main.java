/*
Name: Hasnain
Date: 2023-24-01
Course: ICS3U
Program description: This program is a game where the user rolls dice
to deal damage to bosses from 20 different categories
 */
import java.util.Random;
import java.util.Scanner;
import java.lang.Math;
public class Main {
    public static void main(String[] args) {
        int[] categoryUseCount = new int[20];
        Instructions();
        System.out.println("Player HP: 100\n");
        //Defined the HP of the bosses and player. Boss HP is stored in an array
        int categoryUsed = 0;
        int playerHP = 100;
        int EldenBeastHP = 1000;
        int EsgarHP = 500;
        int AstelHP = 700;
        int SiluriaHP = 400;
        int[] BossHP = {EldenBeastHP, EsgarHP, AstelHP, SiluriaHP};
        //While loop that runs until the player or the boss dies
        while ((BossHP[0] > 0 && BossHP[1] > 0 && BossHP[2] > 0 && BossHP[3] > 0) || playerHP > 0) {
            int[] dice = sixRandomDice();
            int boss = bossSelection(BossHP);
            playerHP = damage(boss, dice, BossHP, playerHP, categoryUseCount,categoryUsed);
        }
        //Prints the result of the game
        if (playerHP == 0) {
            System.out.println("You have been defeated");
        }
        else {
            System.out.println("You have defeated all the bosses");
        }
        System.out.print("hi");
    }

    /**
     * Checks if the inputted string is only a character
     * @param str (A string that is inputted by the user) Can be numbers, characters or a combination of both
     * @return boolean
     */
    private static boolean IsItChar(String str) {
        if (str.length() > 1) {
            return true;
        }
        else {
            return str.charAt(0) != 'n' && str.charAt(0) != 'y';
        }
    }
    /**
     * Allows the user to select which boss they want to fight
     * takes bossHP as input
     * Returns the boss selected-
     *
     */
    private static int bossSelection(int[] bossHP) {
        int bossSelected;
        int maxRange = 5;
        int minRange = 0;
        do {
            //Prints the Bosses and their HP and takes a user input for the boss they selected
            Scanner input = new Scanner(System.in);
            System.out.println("Please select a boss to fight");
            System.out.println("1. Elden Beast (HP: " + bossHP[0] + ")");
            System.out.println("2. Esgar, The Priest of Blood (HP: " + bossHP[1] + ")");
            System.out.println("3. Astel, Naturalborn of the Void (HP: " + bossHP[2] + ")");
            System.out.println("4. Crucible Knight Siluria (HP: " + bossHP[3] + ")");
            bossSelected = stringToint(input.next());

            // Range Validation
            if (IsInRange(bossSelected, minRange, maxRange)) {
                System.out.println("Invalid Input");
            }
        } while (bossSelected >= maxRange || bossSelected <= minRange);
        return bossSelected;
    }

    /**
     * Converts any string into an integer. Allows for the input to be verified if it is an integer or not.
     * @param str (A string that is inputted by the user) Can be numbers, characters or a combination of both
     * @return int returns the equivalent string. ex. "1" returns 1
     */
    public static int stringToint( String str){
        //checks if the number is negative
        int i = 0, number = 0;
        boolean isNegative = false;
        int length = str.length();
        if( str.charAt(0) == '-' ){
            isNegative = true;
            i = 1;
        }
        //loops through the string and converts it into an integer
        while( i < length ){
            number *= 10;
            number += ( str.charAt(i++) - '0' );
        }
        //if the number was negative it returns the number that was converted as a negative number
        if( isNegative ) {
            number = -number;
        }
        return number;
    }
    /**
     * Checks if the inputted number is within the range of the min and max range
     * @param checkValue (The number that is inputted by the user)
     * @param minRange (The minimum range that the number can be not inclusive)
     * @param maxRange (The maximum range that the number can be not inclusive)
     * @return boolean
     */
    private static boolean IsInRange(int checkValue, int minRange, int maxRange) {
        return (checkValue <= minRange || checkValue >= maxRange);
    }
    /**
     * prints the boss name and the HP of the boss after the damage has been dealt
     * @param damage (The damage that the boss takes)
     * @param bossHP (The HP of the boss)
     * @param bossSelected (The boss that is selected)
     * @return int (The new HP of the boss)
     */
    private static int Bosses(int damage, int bossHP, int bossSelected) {
        String boss;
        //amount of HP the boss has and removes the damage from the HP
        int HP = bossHP;
        HP = HP - damage;
        //checks which boss is selected and gives a variable the name of the boss
        if (bossSelected == 1) {
            boss = "Elden Beast";
        }
        else if (bossSelected == 2) {
            boss = "Esgar, The Priest of Blood";
        }
        else if (bossSelected == 3) {
            boss = "Astel, Naturalborn of the Void";
        }
        else if (bossSelected == 4) {
            boss = "Crucible Knight Siluria";
        }
        else {
            boss = "Error";
        }
        //checks if the boss is dead and prints the result
        //if it is not dead then it prints the boss name and the HP left plus the damage it took. Returns the final HP
        if (HP <= 0) {
            System.out.println(boss + " has been defeated");
            return 0;
        }
        else {
            System.out.println(boss + " has " + HP + "HP left (-" + damage +")");
        }
        return HP;
    }
    
    /**
     * Calculates the damage that the player does to the boss
     *
     * @param boss             (The boss that is selected)
     * @param dice             (The dice that the player rolled)
     * @param bossHP           (The HP of the boss)
     * @param playerHP         (The HP of the player)
     * @param categoryUseCount (The amount of times the category has been used)
     * @param categoryUsed    (How many categories have been used)
     * @return int (The damage that the player does to the boss)
     */
    private static int damage(int boss, int[] dice, int[] bossHP, int playerHP, int[] categoryUseCount, int categoryUsed) {
        //variables used throughout this method
        int category;
        int minValue = 0;
        int maxValue = 21;
        int damage = 0;
        //player hp
        System.out.println("\nYou have " + playerHP + "HP left");
        //Printing all the categories for the player to choose from
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("\nSelect the category you would like to use for determining " +
                    "the amount of damage you do. Remember, each one can only be used once. The categories marked with 1, have already been used, marked with 0 have not been used yet");
            System.out.println("1. Ones" + " (" + categoryUseCount[0] + ")");
            System.out.println("2. Twos" + " (" + categoryUseCount[1] + ")");
            System.out.println("3. Threes" + " (" + categoryUseCount[2] + ")");
            System.out.println("4. Fours" + " (" + categoryUseCount[3] + ")");
            System.out.println("5. Fives" + " (" + categoryUseCount[4] + ")");
            System.out.println("6. Sixes" + " (" + categoryUseCount[5] + ")");
            System.out.println("7. Elden's Three Rings" + " (" + categoryUseCount[6] + ")");
            System.out.println("8. Elden's Four Rings" + " (" + categoryUseCount[7] + ")");
            System.out.println("9. Elden's Five Rings" + " (" + categoryUseCount[8] + ")");
            System.out.println("10. Small Elden's Straight" + " (" + categoryUseCount[9] + ")");
            System.out.println("11. Large Elden's Straight" + " (" + categoryUseCount[10] + ")");
            System.out.println("12. Elden's Ring" + " (" + categoryUseCount[11] + ")");
            System.out.println("13. Elden's Chance" + " (" + categoryUseCount[12] + ")");
            System.out.println("14. Elden's Blessing" + " (" + categoryUseCount[13] + ")");
            System.out.println("15. Elden's Curse" + " (" + categoryUseCount[14] + ")");
            System.out.println("16. Elden's Wrath" + " (" + categoryUseCount[15] + ")");
            System.out.println("17. Elden's Mercy" + " (" + categoryUseCount[16] + ")");
            System.out.println("18. Elden's Rage" + " (" + categoryUseCount[17] + ")");
            System.out.println("19. Elden's Fury" + " (" + categoryUseCount[18] + ")");
            System.out.println("20. Elden's Destruction" + " (" + categoryUseCount[19] + ")");
            category = stringToint(input.next());
            //checks if the player has entered a valid input
            if (IsInRange(category, minValue, maxValue)) {
                System.out.println("Invalid Input");
            }
        } while (category < minValue || category > maxValue);
        //checks if the category has already been used
        for (int i = 0; i < categoryUseCount.length; i++) {
            if (categoryUseCount[i] == 1 && category == i + 1) {
                System.out.println("You have already used this category");
                System.out.println("Please select another category");
                damage(boss, dice, bossHP, playerHP, categoryUseCount, categoryUsed);
            }
        }
        //checks which category the player has chosen and calculates the damage
        categoryUseCount[category - 1] = 1;
        categoryUsed++;
        if (categoryUsed == 20) {
            System.out.println("You have used all the categories");
            playerHP = 0;
        }
        if (playerHP >= 0) {
            switch (category) {
                case 1, 5, 2, 4, 6, 3 -> damage = onesToSixes(dice, category);
                case 7 -> damage = eldensThreeRings(dice);
                case 8 -> damage = eldensFourRings(dice);
                case 9 -> damage = eldensFiveRings(dice);
                case 10, 11 -> damage = smallLargeEldenStraights(dice, category);
                case 12 -> damage = eldenRing(dice);
                case 13 -> damage = eldenChance(dice);
                case 14 -> playerHP = eldenBlessing(dice, playerHP);
                case 15 -> damage = eldenCurse(dice);
                case 16 -> damage = eldenWrath(dice);
                case 17 -> damage = eldenMercy(dice);
                case 18 -> damage = eldenRage(dice);
                case 19 -> damage = eldenFury(dice);
                case 20 -> damage = eldenDestruction(dice);
                default -> System.out.println("Error");
            }
        }
            //checks which boss the player is fighting and calculates the damage
        do {
            if (boss == 1) {
                bossHP[0] = Bosses(damage, bossHP[0], boss);
            }
            else if (boss == 2) {
                bossHP[1] = Bosses(damage, bossHP[1], boss);
            }
            else if (boss == 3) {
                bossHP[2] = Bosses(damage, bossHP[2], boss);
            }
            else if (boss == 4) {
                bossHP[3] = Bosses(damage, bossHP[3], boss);
            }
            else {
                System.out.println("Error");
            }
            //checks if the player did no damage
            if (damage == 0 && playerHP > 0) {
                System.out.println("You did no damage");
                //generates a random number from 1 to 6 and if the number is 1, the player will lose 10 HP
                Random random = new Random();
                int randomNum = 1 + random.nextInt(6) ;
                if (randomNum == 1) {
                    playerHP = playerHP - 10;
                    System.out.println("You lost 10 HP because you did no damage and were unlucky");
                }
                //if the player has Elden's Chance, got unlucky and ended up doing no damage, the player will lose 50 HP
                else if (category == 13) {
                    System.out.println("You lost 50 HP because you did no damage and used Elden's Chance");
                    playerHP = playerHP - 50;
                }

            }
            //if the player did damage, prints out how much damage and as well as the player's HP
            else {
                System.out.println("You did " + damage + " damage");
            }
            System.out.println("\nPlayer HP: " + playerHP + "\n");
            //checks if the player has died
            //if the player has died, the game ends
            if (playerHP <= 0) {
                System.out.println("You have been defeated");
                System.out.println("Game Over");
                return 0;
            }
        } while (IsInRange(category, minValue, maxValue));
        return playerHP;
    }

    /**
     * This method calculates the damage for Elden's Destruction
     * Elden's Destruction: If the player rolls all even numbers or all odd numbers, the player does 100 damage
     * @param dice (the dice the player rolled)
     * @return (the damage the player does)
     */
    private static int eldenDestruction(int[] dice) {
        //checks how many even and odd numbers there are in the dice
        int evenCount = 0;
        int oddCount = 0;
        for (int i = 0; i < dice.length; i++) {
            if (dice[i] % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
        //checks if all the dice are even or odd and returns damage accordingly
        }
        if (evenCount == 6 || oddCount == 6) {
            return 100;
        }
        return 0;
    }

    /**
     * This method calculates the damage for Elden's Fury
     * Elden's Fury: If the player rolls three pairs of dice that each add up to 7, the player does 100 damage
     * @param dice (the dice the player rolled)
     * @return (the damage the player does)
     */
    private static int eldenFury(int[] dice) {
        //checks if all three pairs of dice add up to 7 and returns damage accordingly
        if ((dice[0] + dice[1] == 7) && (dice[2] + dice[3] == 7) && (dice[4] + dice[5] == 7)) {
            return 100;
        } else {
            return 0;
        }
    }

    /**
     * This method calculates the damage for Elden's Rage
     * Elden's Rage: If the player rolls two triples of dice that each add up to 11, the player does 100 damage
     * @param dice (the dice the player rolled)
     * @return (the damage the player does)
     */
    private static int eldenRage(int[] dice) {
        //checks if all two triples of dice add up to 11 and returns damage accordingly
        if ((dice[0] + dice[1] + dice[2] == 11) && (dice[3] + dice[4] + dice[5] == 11)) {
            return 400;
        } else {
            return 0;
        }
    }

    /**
     * This method calculates the damage for Elden's Mercy
     * Elden's Mercy: If the player rolls any pair of dice that add up to 7, the player does 100 damage
     * @param dice (the dice the player rolled)
     * @return (the damage the player does)
     */
    private static int eldenMercy(int[] dice) {
        //checks if any pair of dice add up to 7 and returns damage accordingly
        for (int i = 0; i < dice.length; i++) {
            for (int j = i + 1; j < dice.length; j++) {
                if (dice[i] + dice[j] == 7) {
                    return 300;
                }
            }
        }
        return 0;
    }

    /**
     * This method calculates the damage for Elden's Wrath
     * Elden's Wrath: If the player rolls 1, 2, 3, 4, 5, 6 in any order, the player does 100 damage
     * @param dice (the dice the player rolled)
     * @return (the damage the player does)
     */
    private static int eldenWrath(int[] dice) {
        //checks if the player rolled 1, 2, 3, 4, 5, 6 in any order
        int[] diceCount = new int[6];
        for (int i = 0; i < dice.length; i++) {
            if (dice[i] == 1 || dice[i] == 2 || dice[i] == 3 || dice[i] == 4 || dice[i] == 5 || dice[i] == 6) {
                diceCount[dice[i] - 1]++;
            }
        }
        //checks if there is one of each number and returns damage accordingly 
        for (int i = 0; i < diceCount.length; i++) {
            if (diceCount[i] >= 2) {
                return 0;
            }
        }
        return 700;
    }

    /**
     * This method calculates the damage for Elden's Curse
     * Elden's Curse: If the player rolls any triple of dice that add up to 11, the player does 100 damage
     * @param dice (the dice the player rolled)
     * @return (the damage the player does)
     */
    private static int eldenCurse(int[] dice) {
        //checks if any triple of dice add up to 11 and returns damage accordingly
        for (int i = 0; i < dice.length; i++) {
            for (int j = i + 1; j < dice.length; j++) {
                for (int k = j + 1; k < dice.length; k++) {
                    if (dice[i] + dice[j] + dice[k] == 11) {
                        return 300;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * This method calculates the damage for Elden's Blessing
     * Elden's Blessing: If the player rolls 3 pairs of dice where each pair is the same number, the player is healed 100 HP
     * @param dice (the dice the player rolled)
     * @param playerHP (the player's HP)
     * @return (the player's HP)
     */
    private static int eldenBlessing(int[] dice, int playerHP) {
        //checks if the player rolled 3 pairs of dice where each pair is the same number
        int count = 0;
        for (int i = 0; i < dice.length; i++) {
            for (int j = i + 1; j < dice.length; j++) {
                if (dice[i] == dice[j]) {
                    count++;
                }
            }
        }

        //checks if there are three pairs of equivalent numbers and returns health accordingly
        if (count == 3) {
            playerHP = playerHP + 100;
            System.out.println("You have been healed 100 HP! You might lose 10");
        }

        return playerHP;
    }

    /**
     * This method calculates the damage for Elden's Chance
     * Elden's Chance: adds up all the dice and multiplies it by 10 
     * a random number generator generates a number between 1 and 6
     * if the random number is 1, then the player does 0 damage
     * if the random number is not 1, then the player does the damage calculated by Elden's Chance
     * @param dice (the dice the player rolled)
     * @return (the damage the player does)
     */
    private static int eldenChance(int[] dice) {
        //adds up all the dice
        int damage = 0;
        for (int i = 0; i < 6; i++) {
            damage = damage + dice[i];
        }
        //generates a random number between 1 and 6
        //if the random number is 1, return 0 as the damage
        //if the random number is not 1, return the damage from above and multiplies it by 10
        Random random = new Random();
        int randomNum = 1 + random.nextInt(6) ;
        if (randomNum == 1) {
            return 0;
        }
        return damage * 10;

    }
    /**
     * This method calculates the damage for Elden's Ring
     * Elden's Ring: If the player rolls a Yahtzee, the player does 100 damage
     * @param dice (the dice the player rolled)
     * @return (the damage the player does)
     */
    private static int eldenRing(int[] dice) {
        //checks if the player rolled a Yahtzee and returns damage accordingly
        if (dice[0] == dice[1] && dice[1] == dice[2] && dice[2] == dice[3] && dice[3] == dice[4]) {
            return 690;
        }
            return 0;
    }
    private static int smallLargeEldenStraights(int[] dice, int category) {
        //sorts the array from least to greatest
        //this method of sorting is called bubble sorting
        //this method takes the first element and compares it to the second element
        //if the first element is greater than the second element, then the two elements are swapped
        //this process is repeated until the array is sorted
        int[] sortedDice = new int[dice.length];
        for (int i = 0; i < dice.length; i++) {
            sortedDice[i] = dice[i];
        }
        for (int i = 0; i < sortedDice.length; i++) {
            for (int j = i + 1; j < sortedDice.length; j++) {
                if (sortedDice[i] > sortedDice[j]) {
                    int temp = sortedDice[i];
                    sortedDice[i] = sortedDice[j];
                    sortedDice[j] = temp;
                }
            }
        }
        //checks if the sorted array is a small straight or large straight (how many consecutive numbers there are)
        for (int i = 0; i < sortedDice.length - 1; i++) {
            if (i == sortedDice.length - 2) {
                if (category == 11) {
                    return 100;
                } else {
                    return 200;
                }
            }
        }
        return 0;
    }

    /**
     * This method calculates the damage for Elden's Five Rings
     * Elden's Five Rings: if the player rolls a three of a kind and a two of a kind, the player does 100 damage
     * @param dice (the dice the player rolled)
     * @return (the damage the player does)
     */
    private static int eldensFiveRings(int[] dice) {
        //tells diceCount how many of each number there is in the dice roll
        int twoOfAKindCount = 0;
        int[] diceCount = new int[6];
        int damage = 0;
        for (int i = 0; i < dice.length; i++) {
            diceCount[dice[i] - 1]++;
        }
        //adds up the damage for five rings by adding up all the numbers in the dice and multiplying it by 10
        //In any dice roll, that matches the criteria for Elden's Five Rings, there will be 4 pairs of equivalent numbers
        for (int i = 0; i < diceCount.length; i++) {
            if (diceCount[i] >= 3 || diceCount[i] == 2) {
                for (int j = 0; j < dice.length; j++) {
                        damage += dice[j];
                }
            }
        }
        //finds a pair of two identical numbers
        for (int i = 0; i < dice.length; i++) {
            for (int j = i + 1; j < dice.length; j++) {
                if (dice[i] == dice[j]) {
                    twoOfAKindCount++;
                }
            }
        }
        if (twoOfAKindCount == 4) {
            damage = (damage * 5) + 50;
            return damage;
        }
        return 0;
    }

    /**
     * This method calculates the damage for Elden's Four Rings
     * Elden's Four Rings: if the player rolls a four of a kind, the player does 100 damage
     * @param dice (the dice the player rolled)
     * @return (the damage the player does)
     */
    private static int eldensFourRings(int[] dice) {
        int[] diceCount = new int[6];
        int damage = 0;
        //tells diceCount how many of each number there is in the dice roll
        for (int i = 0; i < dice.length; i++) {
            diceCount[dice[i] - 1]++;
        }
        //checks if there is a four of a kind and adds up the damage by summing all the dice
        for (int l = 0; l < diceCount.length; l++) {
            if (diceCount[l] >= 4) {
                for (int j = 0; j < dice.length; j++) {
                    if (diceCount[l] >= 4) {
                        damage += dice[j];
                    }
                }
            }
        }
        damage = (damage * 2) * 5;
        return damage;
    }

    /**
     * This method calculates the damage for Elden's Three Rings
     * Elden's Three Rings: if the player rolls a three of a kind, the player does 100 damage. if there are two three of a kinds, the player does 200 damage
     * @param dice (the dice the player rolled)
     * @return (the damage the player does)
     */
    private static int eldensThreeRings(int[] dice) {
       //tells diceCount how many of each number there is in the dice roll
        int[] diceCount = new int[6];
        int damage = 0;
        for (int i = 0; i < dice.length; i++) {
            diceCount[dice[i] - 1]++;
        }
        //checks if there is a three of a kind and how many
        int threeOfAKindCount = 0;
        for (int i = 0; i < diceCount.length; i++) {
            if (diceCount[i] >= 3) {
                threeOfAKindCount++;
            }
        }
        //adds up the damage for the three of a kind by adding up the values of the dice
        for (int l = 0; l < diceCount.length; l++) {
            if (diceCount[l] >= 3) {
                for (int j = 0; j < dice.length; j++) {
                    damage += dice[j];
                }
            }
        }
        if  (threeOfAKindCount == 1) {
            damage *= 2;
        }
        //if there is one three of a kind, the damage is 100, if there are two, the damage is 200
        //but if there is a four of a kind, then the damage is halved for choosing an incorrect ring
        for (int i = 0; i < diceCount.length; i++) {
            if (diceCount[i] >= 4) {
                damage = damage/2;
                System.out.println("You chose the wrong ring! Your damage is halved!");
            }
        }
        damage = damage * 5;
        if (threeOfAKindCount == 1) {
            return damage;
        }
        else if (threeOfAKindCount == 2) {
            damage = damage * 2;
            return damage;
        }
        return 0;
    }

    /**
     * This method calculates the damage for Elden's ones to sixes
     * Elden's ones to sixes: depending on the category selected, the number of the equivalent dice rolled is multiplied by 10 or 5
     * @param dice (the dice the player rolled)
     * @param category (the category the player chose)
     * @return (the damage the player does)
     */
    private static int onesToSixes(int[] dice, int category) {
        int counter = 0;
        int damage = counter;
        //counts how many of the specific number equivalent to the category the player rolled
        for (int i = 0; i < dice.length; i++) {
            if (dice[i] == category) {
                counter++;
            }
        }
        //if the player rolled no dice of the category, the damage is 0
        if (counter == 0) {
            return 0;
        }
        //if the player rolled a die of the category, the damage is 10 or 5 depending on the category
        else {
            if (category >= 4) {
                damage = (category * counter) * 5;
            }
            else {
                damage = (category * counter) * 10;
            }
            return damage;
        }
    }
    /**
     * This method generates the six random dice for the game
     * no parameters
     * @return (the dice the player rolled)
     */
    private static int[] sixRandomDice () {
        int [] dice = new int [6];
        //generates the six random dice
        for (int i = 0; i < 6; i++) {
            dice[i] = (int) (Math.random() * 6) + 1;
        }
        //prints the dice
        for (int i = 0; i < 6; i++) {
            System.out.print(dice[i] + " ");
        }
        //returns the dice
        return rerollDice(dice);
    }

    /**
     * This method asks the player if they want to reroll any dice and if they do, it rerolls the dice
     * @param dice (the dice the player rolled)
     * @return (the new dice the player rolled if they rerolled otherwise the old dice)
     */
    private static int [] rerollDice(int [] dice) {
        //asks the player if they want to reroll any dice
            Scanner input = new Scanner(System.in);
            String option;
            //for loop runs all this twice so the player can reroll twice
            for (int i = 0; i < 2; i++) {
                do {
                    System.out.println("\nWould you like to reroll any dice? (y/n)");
                    //input validation
                    option = input.next().toLowerCase();
                    if (IsItChar(option)) {
                        System.out.println("Please enter a valid option");
                    }
                } while (IsItChar(option));
                //if the player wants to reroll, it asks how many dice they want to reroll and which dice they want to reroll
                if (option.equals("y")) {
                    int reroll;
                    int minValue = 0;
                    int maxValue = 4;
                    do {
                        System.out.println("How many dice would you like to reroll? (1, 2 or 3)");
                        //input validation
                        reroll = stringToint(input.next());
                        if (IsInRange(reroll, minValue, maxValue)) {
                            System.out.println("Invalid Input");
                        }
                    } while (reroll < 1 || reroll > 3);
                    for (int j = 0; j < reroll; j++) {
                        System.out.println("Select the dice you want to reroll by inputting the corresponding dice number");
                        int diceNumber = input.nextInt();
                        dice[diceNumber - 1] = (int) (Math.random() * 6) + 1;
                    }
                    for (int j = 0; j < 6; j++) {
                        System.out.print(dice[j] + " ");
                    }
                } else {
                    return dice;
                }
            }
        return dice;
    }

    /**
     * This method prints the instructions and rules of the game
     * no parameters
     * no return
     */
    private static void Instructions() {
        System.out.println("""
                Welcome to Elden Ring Dice!
                In this game you will roll 6 dice and you will have the option to reroll 1, 2, or 3 of the dice twice.
                You will then be able to choose a category to calculate how much damage you do based off you dice.
                Each category can only be used once.
                If you use all 20 categories before the bosses are defeated, you will automatically be killed and the game will end.
                You will be able to score in the following categories:
                
                Category                 | Requirements                 | Damage
                -------------------------|------------------------------|------------------
                1. Ones                  | 1 or more ones               | 10 x number of ones
                2. Twos                  | 1 or more twos               | 10 x number of twos
                3. Threes                | 1 or more threes             | 10 x number of threes
                4. Fours                 | 1 or more fours              | 5  x number of fours
                5. Fives                 | 1 or more fives              | 5  x number of fives
                6. Sixes                 | 1 or more sixes              | 5  x number of sixes
                7. Elden's Three Rings   | 3 or more of the same number | (Sum of dice x 5) x 2 (if two 3 of a rings are present)
                8. Elden's Four Rings    | 4 or more of the same number | Sum of dice x 10
                9. Elden's Five Rings    | 5 or more of the same number | Sum of dice x 5 + 50
                10. Small Elden Straight | 4 Consecutive numbers        | 100
                11. Large Elden Straight | 5 Consecutive numbers        | 200
                12. Elden Ring           | All 6 dice are the same      | 690
                13. Elden's Chance       | Any combination of dice      | Sum of all dice x 10
                14. Elden's Blessing     | 3 dice pairs, each pair same | + 100 player HP
                15. Elden's Curse        | Any triple sums to 11        | 300
                16. Elden's Wrath        | All 6 numbers in any order   | 700
                17. Elden's Mercy        | Any pair sums to 7           | 300
                18. Elden's Rage         | Both halves of dice sum to 11| 400
                19. Elden's Fury         | 3 pairs, each pairs sums to 7| 100
                20. Elden's Destruction  | If all odd or all even       | 100
                
                if you do no damage you have the chance to lose 10 HP for not scoring.
                Using Elden's Chance, will have the chance to do no damage and lose 50 HP.
                if you lose all your HP you will die and the game will end.
                Few things to keep in mind:
                1. When using Elden's Five Rings, it must be a 3 of a kind and a 2 of a kind. if you have a four of a kind,
                you will do 0 damage.
                2. If you use Elden's Blessing, and gain 100 HP, there is a chance to lose 10 HP. lets hope your odds are in your favour.
                
                Good luck and may the Elden Ring be with you! May the odds be ever in your favour!\n""");
    }
}