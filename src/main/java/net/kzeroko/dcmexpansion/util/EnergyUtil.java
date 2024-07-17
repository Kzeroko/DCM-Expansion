package net.kzeroko.dcmexpansion.util;

public class EnergyUtil {
    private static final double FE_TO_J = 2.5;
    private static final double FE_TO_EU = 0.25;
    private static final double J_TO_FE = 0.4;
    private static final double J_TO_EU = 0.1;
    private static final double EU_TO_FE = 4;
    private static final double EU_TO_J = 10;

    public static double feToJoules(double fe) {
        return fe * FE_TO_J;
    }

    public static double feToEu(double fe) {
        return fe * FE_TO_EU;
    }

    public static double joulesToFe(double joules) {
        return joules * J_TO_FE;
    }

    public static double joulesToEu(double joules) {
        return joules * J_TO_EU;
    }

    public static double euToFe(double eu) {
        return eu * EU_TO_FE;
    }

    public static double euToJoules(double eu) {
        return eu * EU_TO_J;
    }
}
