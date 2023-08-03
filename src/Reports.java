import java.util.Scanner;

public class Reports {

	public static void prompt() {
		Scanner input = new Scanner(System.in);

		final int option1 = 1;
		final int exitReport = 2;

		String reportsPrompt = String.format(
			"******* Run a report *******\n"
			+ "%2d - option1\n"
			+ "%2d - Exit reports",
			option1, exitReport);


		int choice = option1;
		while (choice != exitReport) {

			System.out.println(reportsPrompt);
            System.out.print(": ");
            choice = input.nextInt();
            input.nextLine();
		}
	}
}
