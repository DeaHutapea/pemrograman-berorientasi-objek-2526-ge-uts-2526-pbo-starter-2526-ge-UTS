package fintech.driver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import fintech.model.Account;
import fintech.model.Transaction;
import fintech.model.TransferTransaction;

/**
 * @author 12S24053 Dea Hutapea
 */

public class Driver4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] tokens = line.split("#");
            String command = tokens[0];

            if (command.equals("create-account")) {
            String accountId = tokens[1];
            String accountName = tokens[2];

            Account account = new Account(accountId, accountName);
            accounts.add(account);
            } 
        else if (command.equals("create-transaction")) {
                String accountId = tokens[1];
                String targetId = tokens[2];
                double amount = Double.parseDouble(tokens[3]);
                LocalDateTime timestamp = LocalDateTime.parse(tokens[4]);
                String note = tokens[5];

                Account account = getAccountById(accounts, accountId);
                Account targetAccount = getAccountById(accounts, targetId);

                if (account != null && targetAccount != null && account.getBalance() >= amount) {
                    Transaction transaction = new Transaction(TransactionType.DEBIT, amount, timestamp, note, targetAccount, account);
                    account.addTransaction(transaction);
                    transactions.add(transaction);

                    Transaction reverseTransaction = new Transaction(TransactionType.CREDIT, amount, timestamp, "REVERT: " + note, account, targetAccount);
                    targetAccount.addTransaction(reverseTransaction);
                    transactions.add(reverseTransaction);
                }
            } else if (command.equals("revert-transaction")) {
                String accountId = tokens[1];
                int transactionId = Integer.parseInt(tokens[2]);
                LocalDateTime timestamp = LocalDateTime.parse(tokens[3]);

                Account account = getAccountById(accounts, accountId);

                if (account != null) {
                    Transaction transaction = account.getTransactionById(transactionId);

                    if (transaction != null && transaction.getType() == TransactionType.DEBIT) {
                        Transaction reverseTransaction = new Transaction(TransactionType.CREDIT, transaction.getAmount(), timestamp, "REVERT: " + transaction.getNote(), transaction.getTarget(), transaction.getSource());
                        transaction.getTarget().addTransaction(reverseTransaction);
                        transactions.add(reverseTransaction);
                    }
                }
            } else if (command.equals("show-account")) {
                String accountId = tokens[1];
                Account account = getAccountById(accounts, accountId);

                if (account != null) {
                    System.out.println(account);
                    for (Transaction transaction : account.getTransactionHistory()) {
                        System.out.println(transaction);
                    }
                }
            }
        }

        sc.close();
    }

    private static Account getAccountById(ArrayList<Account> accounts, String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }
}


