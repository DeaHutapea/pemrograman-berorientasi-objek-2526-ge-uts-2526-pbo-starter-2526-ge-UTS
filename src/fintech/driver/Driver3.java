package fintech.driver;

import fintech.model;

import java.util;

/**
 * @author 12S24053 Dea Hutapea
 */

public class Driver3 {

    private static ArrayList<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("---"))
                break;

            String[] data = input.split("#");
            String command = data[0];

            switch (command) {
                case "create-account":
                    String name = data[1];
                    String id = data[2];
                    if (findAccount(id) == null) {
                        Account account = new Account(id, name);
                        accounts.add(account);
                        System.out.println(account.getOwner() + "|" + account.getName() + "|0.0");
                    }
                    break;

                case "create-transaction":
                    Account sender = findAccount(data[1]);
                    Account recipient = findAccount(data[2]);
                    if (sender != null && recipient != null) {
                        double amount = Double.parseDouble(data[3]);
                        String date = data[4];
                        String description = data[5];
                        Transaction transaction = new Transaction(sender, amount, date, description);
                        if (transaction.getAmount() != -1) {
                            recipient.addTransaction(transaction);
                            sender.addTransaction(transaction);
                            sender.debit(amount);
                            recipient.credit(amount);
                            System.out.println(transaction.getId() + "|" + transaction.getAccount().getName() + "|" + transaction.getAmount() + "|" + transaction.getPostedAt() + "|" + transaction.getNote());
                        }
                    }
                    break;


                case "show-accounts":
                accounts.sort(Comparator.comparing(Account::getName));
                for (Account account : accounts)
                    System.out.println(account.getName() + "|" + account.getOwner() + "|" + account.getBalance());
                break;

                case "show-account":
                    Account account = findAccount(data[1]);
                    if (account != null) {
                        System.out.println(account.getName() + "|" + account.getOwner() + "|" + account.getBalance());
                        for (Transaction transaction : account.getTransactions())
                            System.out.println(transaction.getId() + "|" + transaction.getAccount().getName() + "|" + transaction.getAmount() + "|" + transaction.getPostedAt() + "|" + transaction.getNote());
                    }
                    break;

                case "find-account":
                    Account acc = findAccount(data[1]);
                    if (acc != null) { 
                        System.out.println(acc.getName() + "|" + acc.getOwner() + "|" + acc.getBalance());
                        for (Transaction transaction : acc.getTransactions())
                            System.out.println(transaction.getId() + "|" + transaction.getAccount().getName() + "|" + transaction.getAmount() + "|" + transaction.getPostedAt() + "|" + transaction.getNote());
                    }
                    break;

                case "remove-account":
                    Account toRemove = findAccount(data[1]);
                    if (toRemove != null) {
                        accounts.remove(toRemove);
                        System.out.println("Account " + toRemove.getName() + " removed.");
                    }
                    break;

                default:
                    break;
            }
        }

        scanner.close();
    }

    public static Account findAccount(String id) {
        for (Account account : accounts)
            if (account.getOwner().equals(id))
                return account;
        return null;
    }
}
