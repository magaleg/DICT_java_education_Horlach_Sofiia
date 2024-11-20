package CoffeeMachine;

import java.util.Scanner;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private int money;

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
    }

    public void printState() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(coffeeBeans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(money + " of money");
    }

    public void buy(String choice) {
        switch (choice) {
            case "1":
                if (checkResources(250, 0, 16, 1)) {
                    updateResources(-250, 0, -16, -1, 4);
                    System.out.println("I have enough resources, making you a coffee!");
                }
                break;
            case "2":
                if (checkResources(350, 75, 20, 1)) {
                    updateResources(-350, -75, -20, -1, 7);
                    System.out.println("I have enough resources, making you a coffee!");
                }
                break;
            case "3":
                if (checkResources(200, 100, 12, 1)) {
                    updateResources(-200, -100, -12, -1, 6);
                    System.out.println("I have enough resources, making you a coffee!");
                }
                break;
            case "back":
                return;
        }
    }

    public void fill(int addWater, int addMilk, int addCoffeeBeans, int addCups) {
        water += addWater;
        milk += addMilk;
        coffeeBeans += addCoffeeBeans;
        cups += addCups;
    }

    public void take() {
        System.out.println("I gave you " + money);
        money = 0;
    }

    private boolean checkResources(int waterNeeded, int milkNeeded, int coffeeBeansNeeded, int cupsNeeded) {
        if (water < waterNeeded) {
            System.out.println("Sorry, not enough water!");
            return false;
        }
        if (milk < milkNeeded) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
        if (coffeeBeans < coffeeBeansNeeded) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }
        if (cups < cupsNeeded) {
            System.out.println("Sorry, not enough cups!");
            return false;
        }
        return true;
    }

    private void updateResources(int waterChange, int milkChange, int coffeeBeansChange, int cupsChange, int moneyChange) {
        water += waterChange;
        milk += milkChange;
        coffeeBeans += coffeeBeansChange;
        cups += cupsChange;
        money += moneyChange;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);

        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = scanner.next();

            switch (action) {
                case "buy":
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                    String choice = scanner.next();
                    coffeeMachine.buy(choice);
                    break;
                case "fill":
                    System.out.println("Write how many ml of water you want to add:");
                    int addWater = scanner.nextInt();
                    System.out.println("Write how many ml of milk you want to add:");
                    int addMilk = scanner.nextInt();
                    System.out.println("Write how many grams of coffee beans you want to add:");
                    int addCoffeeBeans = scanner.nextInt();
                    System.out.println("Write how many disposable cups you want to add:");
                    int addCups = scanner.nextInt();
                    coffeeMachine.fill(addWater, addMilk, addCoffeeBeans, addCups);
                    break;
                case "take":
                    coffeeMachine.take();
                    break;
                case "remaining":
                    coffeeMachine.printState();
                    break;
                case "exit":
                    scanner.close();
                    return;
            }
        }
    }
}
