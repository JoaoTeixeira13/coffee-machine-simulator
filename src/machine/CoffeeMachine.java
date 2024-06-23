package machine;

import java.util.Arrays;
import java.util.Scanner;

public class CoffeeMachine {

    static int waterMlInMachine = 400;
    static int milkMlInMachine = 540;
    static int coffeeBeansGrInMachine = 120;
    static int disposableCups = 9;
    static int money = 550;

    static boolean isSwitchedOn = true;
    static State state = State.ACTION;


    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        while (isSwitchedOn) {

            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = scanner.next();

            readInput(action);
        }

    }

    public static void readInput(String input) {

        Scanner scanner = new Scanner(System.in);

        switch (state) {
            case ACTION:
                boolean isValidAction = Arrays.toString(Action.values()).contains(input.toUpperCase());

                if (isValidAction) {
                    switch (Action.valueOf(input.toUpperCase())) {
                        case BUY:
                            buyCoffee(scanner);
                            break;

                        case FILL:
                            fillMachine(scanner);
                            break;

                        case TAKE:
                            takeMoney();
                            break;

                        case REMAINING:
                            displayRemainingQuantities();
                            break;

                        case EXIT:
                            isSwitchedOn = false;
                            break;
                        default:
                            break;
                    }
                }


                break;
            case COFFEE:
                if (disposableCups > 0) {

                    switch (input) {
                        case "1":
                            boolean isEnoughForEspresso = waterMlInMachine >= 250 && coffeeBeansGrInMachine >= 16;
                            prepareEspresso(isEnoughForEspresso);
                            break;

                        case "2":
                            boolean isEnoughForLatte = waterMlInMachine >= 350 && milkMlInMachine >= 75 && coffeeBeansGrInMachine >= 20;
                            prepareLatte(isEnoughForLatte);
                            break;

                        case "3":
                            boolean isEnoughForCappuccino = waterMlInMachine >= 200 && milkMlInMachine >= 100 && coffeeBeansGrInMachine >= 12;
                            prepareCappuccino(isEnoughForCappuccino);
                            break;

                        default:
                            state = State.ACTION;
                            break;
                    }
                } else {
                    System.out.println("Sorry, not enough disposable cups. Please refill.");
                }

                break;
            default:
                break;
        }


    }

    private static void buyCoffee(Scanner scanner) {

        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        state = State.COFFEE;
        String coffeeToBuy = scanner.next();
        readInput(coffeeToBuy);
    }

    private static void fillMachine(Scanner scanner) {

        System.out.println("Write how many ml of water you want to add:");
        waterMlInMachine += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        milkMlInMachine += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        coffeeBeansGrInMachine += scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        disposableCups += scanner.nextInt();
    }

    private static void takeMoney() {

        System.out.println("I give you $" + money);
        money = 0;
    }

    private static void displayRemainingQuantities() {

        System.out.println("The coffee machine has:");
        System.out.println(waterMlInMachine + " ml of water");
        System.out.println(milkMlInMachine + " ml of milk");
        System.out.println(coffeeBeansGrInMachine + " g of coffee beans");
        System.out.println(disposableCups + " disposable cups");
        System.out.println("$" + money + " of money");
    }

    private static void prepareEspresso(boolean isEnoughForEspresso) {

        if (isEnoughForEspresso) {

            printSuccessMessage();
            prepareCoffee(250, 0, 16, 4);

        } else {

            calculateMissingResource(250, 0);
        }
        state = State.ACTION;
    }

    private static void prepareLatte(boolean isEnoughForLatte) {

        if (isEnoughForLatte) {

            printSuccessMessage();
            prepareCoffee(350, 75, 20, 7);
        } else {

            calculateMissingResource(350, 75);
        }

        state = State.ACTION;
    }

    private static void prepareCappuccino(boolean isEnoughForCappuccino) {

        if (isEnoughForCappuccino) {

            printSuccessMessage();
            prepareCoffee(200, 100, 12, 6);
        } else {

            calculateMissingResource(200, 100);
        }

        state = State.ACTION;
    }

    private static void printSuccessMessage() {

        System.out.println("I have enough resources, making you a coffee!");
    }

    private static void prepareCoffee(int waterMl, int milkMl, int coffeeBeansGr, int price) {

        waterMlInMachine -= waterMl;
        milkMlInMachine -= milkMl;
        coffeeBeansGrInMachine -= coffeeBeansGr;
        money += price;
        disposableCups--;
    }

    private static void calculateMissingResource(int requiredWater, int requiredMilk) {

        String missingResource = waterMlInMachine < requiredWater ? "water" :
                milkMlInMachine < requiredMilk ? "milk" : "coffee beans";
        System.out.println("Sorry, not enough " + missingResource);
    }
}

