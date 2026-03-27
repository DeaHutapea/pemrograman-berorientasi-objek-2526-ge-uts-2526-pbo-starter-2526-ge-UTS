package fintech.driver;

import fintech.model.*;

import java.util.*;

/**
 * @author 12S24053 Dea Hutapea
 */

public class Driver1 {
//    container to store all accounts
    private static ArrayList<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // string to hold user input
        String input = new String();

        // infinite loop
        while (true) {
            // input request
            input = scanner.nextLine();

            // break loop after terminate command
            if (input.equals("---"))
                break;

            // store split input
            String[] data = input.split("#");

            // for event flow branching
            String command = data[0];

            // remove the first element which already
            // assigned to var `command`
            data = Arrays.copyOfRange(data, 1, data.length);

            // Account object holder
            Account tmp = null;

            switch (command) {
                case "create-account":
                    // if given name has not been registered
                    // as an account
                    if (findAccount(data[1]) == null) {
                        // construct a brand-new account
                        Account account = new Account(data[0], data[1]);

                        // display/log the new account
                        System.out.println(account);

                        // add new account to account list
                        accounts.add(account);
                    }
                    break;

                case "create-transaction":
                    // find account with the given name
                    tmp = findAccount(data[0]);

                    // if account with the given name is found
                    if (tmp != null) {
                        // cast `data[1]` into double
                        double amount = Double.parseDouble(data[1]);

                        // construct a brand-new transaction
                        Transaction transaction = new Transaction(tmp, amount, data[2], data[3]);

                        // add new transaction to transaction list
                        tmp.addTransaction(transaction);
                    }
                    break;

                case "show-accounts":
                    // clone the account list in order to
                    // preserve original account list state
                    ArrayList<Account> population = new ArrayList<>(accounts);

                    // sort the new transaction lists
                    // by case-insensitively comparing `name` attribute
                    population.sort(Comparator.comparing(left -> left.getName().toLowerCase()));

                    // iterate through account list
                    for (Account account: population)
                        // display each account's detail
                        System.out.println(account.detail());
                    break;

                case "show-account":
                    tmp = findAccount(data[0]);

                    // if account with given name is found
                    if (tmp != null)
                        // display said account's detail
                        System.out.println(tmp.detail());
                    break;

                case "find-account":
                    tmp = findAccount(data[0]);

                    // if account with given name is found
                    if (tmp != null)
                        // display said account
                        System.out.println(tmp);
                    break;

                case "remove-account":
                    tmp = findAccount(data[0]);

                    // if account with given name is found
                    if (tmp != null)
                        // remove account from account list
                        accounts.remove(tmp);
                    break;

                default:
                    break;
            }
        }

        scanner.close();
    }

    public static Account findAccount(String name) {
        // iterate through account list
        for (Account account: accounts)
            // if account with given name is found
            if (account.getName().equalsIgnoreCase(name))
                // return the object
                return account;

        // if the flow reach this point it means
        // that account with given name is not found
        return null;
    }
}
