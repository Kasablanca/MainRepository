package com.min.concurrent.chapter10;

public class InduceLockOrder {
	private static final Object tieLock = new Object();

	public void transferMoney(final Account fromAcct, final Account toAcct, final DollarAmount amount) throws InsufficientFundsException {
		class Helper {
			public void transfer() throws InsufficientFundsException {
				if (fromAcct.getBalance().compareTo(amount) < 0)
					throw new InsufficientFundsException();
				else {
					fromAcct.debit(amount);
					toAcct.credit(amount);
				}
			}
		}
		int fromHash = System.identityHashCode(fromAcct);
		int toHash = System.identityHashCode(toAcct);

		if (fromHash < toHash) {
			synchronized (fromAcct) {
				synchronized (toAcct) {
					new Helper().transfer();
				}
			}
		} else if (fromHash > toHash) {
			synchronized (toAcct) {
				synchronized (fromAcct) {
					new Helper().transfer();
				}
			}
		} else {
			synchronized (tieLock) {
				synchronized (fromAcct) {
					synchronized (toAcct) {
						new Helper().transfer();
					}
				}
			}
		}
	}

	interface DollarAmount extends Comparable<DollarAmount> {
	}

	interface Account {
		void debit(DollarAmount d);

		void credit(DollarAmount d);

		DollarAmount getBalance();

		int getAcctNo();
	}

	class InsufficientFundsException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
