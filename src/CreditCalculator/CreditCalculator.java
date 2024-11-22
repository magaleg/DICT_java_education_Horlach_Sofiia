public class CreditCalculator {
    public static void main(String[] args) {
        String type = System.getProperty("type");
        String principalStr = System.getProperty("principal");
        String periodsStr = System.getProperty("periods");
        String interestStr = System.getProperty("interest");
        String paymentStr = System.getProperty("payment");

        Double principal = (principalStr != null) ? Double.parseDouble(principalStr) : null;
        Double periods = (periodsStr != null) ? Double.parseDouble(periodsStr) : null;
        Double interest = (interestStr != null) ? Double.parseDouble(interestStr) : null;
        Double payment = (paymentStr != null) ? Double.parseDouble(paymentStr) : null;

        if (type == null || interest == null || (principal == null && periods == null && payment == null)) {
            System.out.println("Incorrect parameters");
            return;
        }

        calculate(type, principal, periods, interest, payment);
    }

    private static void calculate(String type, Double principal, Double periods, Double interest, Double payment) {
        if (type.equals("diff")) {
            calculateDifferentiatedPayments(principal, periods, interest);
        } else if (type.equals("annuity")) {
            if (payment == null) {
                calculateAnnuityPayment(principal, periods, interest);
            } else if (principal == null) {
                calculateLoanPrincipal(payment, periods, interest);
            } else if (periods == null) {
                calculateNumberOfPayments(principal, payment, interest);
            } else {
                System.out.println("Incorrect parameters");
            }
        } else {
            System.out.println("Incorrect parameters");
        }
    }

    private static void calculateDifferentiatedPayments(Double principal, Double periods, Double interest) {
        if (principal == null || periods == null || interest == null) {
            System.out.println("Incorrect parameters");
            return;
        }
        if (principal <= 0 || periods <= 0 || interest <= 0) {
            System.out.println("Incorrect parameters");
            return;
        }

        double i = interest / (12 * 100);
        double totalPayment = 0;

        for (int m = 1; m <= periods; m++) {
            double diffPayment = (principal / periods) + i * (principal - (principal * (m - 1) / periods));
            totalPayment += Math.ceil(diffPayment);
            System.out.printf("Month %d: payment is %.0f\n", m, Math.ceil(diffPayment));
        }

        System.out.printf("Overpayment = %.0f\n", totalPayment - principal);
    }

    private static void calculateAnnuityPayment(Double principal, Double periods, Double interest) {
        double i = interest / (12 * 100);
        double annuity = principal * (i * Math.pow(1 + i, periods)) / (Math.pow(1 + i, periods) - 1);
        System.out.printf("Your annuity payment = %.0f!\n", Math.ceil(annuity));
        System.out.printf("Overpayment = %.0f\n", (Math.ceil(annuity) * periods) - principal);
    }

    private static void calculateLoanPrincipal(Double payment, Double periods, Double interest) {
        double i = interest / (12 * 100);
        double principal = payment / ((i * Math.pow(1 + i, periods)) / (Math.pow(1 + i, periods) - 1));
        System.out.printf("Your loan principal = %.0f!\n", Math.floor(principal));
        System.out.printf("Overpayment = %.0f\n", (payment * periods) - Math.floor(principal));
    }

    private static void calculateNumberOfPayments(Double principal, Double payment, Double interest) {
        double i = interest / (12 * 100);
        double n = Math.log(payment / (payment - i * principal)) / Math.log(1 + i);
        int months = (int) Math.ceil(n);

        int years = months / 12;
        months = months % 12;

        if (years > 0 && months > 0) {
            System.out.printf("It will take %d years and %d months to repay this loan!\n", years, months);
        } else if (years > 0) {
            System.out.printf("It will take %d years to repay this loan!\n", years);
        } else {
            System.out.printf("It will take %d months to repay this loan!\n", months);
        }
        System.out.printf("Overpayment = %.0f\n", (payment * Math.ceil(n)) - principal);
    }
}
