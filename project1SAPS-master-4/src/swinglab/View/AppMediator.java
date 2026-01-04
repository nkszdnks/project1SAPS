package swinglab.View;

import Entities.Accounts.BusinessAcount;
import Entities.Users.Customer;
import Entities.Users.User;
import Entities.Users.UserRole;

import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

import javax.swing.JPanel;

import static Entities.Users.UserRole.ADMIN;
import static Entities.Users.UserRole.BUSINESS;

public class AppMediator {

		private static User CurrentUser;
		
		private static BankFrame bank;
        private static LocalDate today =  LocalDate.now();
		private static CardLayout cardLayout;
		private static JPanel cards;  
		private static BusinessAcount BankOfTucAccount = new BusinessAcount("BankOfTucACCID","GR200202503131719410",0.0,100000,LocalDate.now(),0,0);
        public static final NumberFormat euroFormat =
            NumberFormat.getCurrencyInstance(Locale.GERMANY);

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



	public static void goToHomeDashboard() {
		if (CurrentUser == null) return;

		switch (CurrentUser.getRole()) {
			case BUSINESS ->
					cardLayout.show(cards, "businessDashboard");
			case ADMIN ->
					cardLayout.show(cards, "adminDashboard");
			default ->
					cardLayout.show(cards, "dashboard");
		}
	}




	public static void setUser(User user) {

            AppMediator.CurrentUser = user;
            if(user==null){
                bank.user.setText("  User: -");
                return;
            }
            bank.user.setText("  User: " +(user.getRole().equals(ADMIN)?user.getUsername():((Customer)user).getFullName()));

        }

		public static BusinessAcount getBankOfTucAccount() {
			return BankOfTucAccount;
		}

        public static void setToday(LocalDate today) {
           AppMediator.today = today;
           bank.date.setText("Date: " + today.toString());
        }


    public static void setAllCardsColor(Color color) {
        if (cards == null) return;

        for (Component comp : cards.getComponents()) {
            if (comp instanceof JPanel panel) {
                panel.setOpaque(true);
                panel.setBackground(color);
            }
        }

        cards.revalidate();
        cards.repaint();
    }





		public static void goToIssueBill() {
			if (CurrentUser != null && CurrentUser.getRole() == BUSINESS) {///////////////////////////////
				cardLayout.show(cards, "issueBill");
			}
		}



        public static LocalDate getToday() {
           return today;
        }
}
