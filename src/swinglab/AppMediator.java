package swinglab;

import Entities.Users.User;

import java.awt.CardLayout;

//import javax.swing.JMenu;
import javax.swing.JPanel;

public class AppMediator {

		private static User CurrentUser;
		
		private static BankFrame bank;
		private static CardLayout cardLayout;
		private static JPanel cards;  
		private static String acount_IBAN;

		public static JPanel getCards() {
			return cards;
		}

		public static void setCards(JPanel cards) {
			AppMediator.cards = cards;
		}

		public static  CardLayout getCardLayout() {
			return cardLayout;
		}

		public static void setCardLayout(CardLayout cardLayout) {
			AppMediator.cardLayout = cardLayout;
		}

		public static BankFrame getBank() {
			return bank;
		}

		public static void setBank(BankFrame bank) {
			AppMediator.bank = bank;
		}

		public static User getUser() {
			return CurrentUser;
		}

		public static void setUser(User user) {
			AppMediator.CurrentUser = user;
		}

		public static String getAcount_IBAN() {
			return acount_IBAN;
		}

		public static void setAcount_IBAN(String acount_IBAN) {
			AppMediator.acount_IBAN = acount_IBAN;
		}
}
