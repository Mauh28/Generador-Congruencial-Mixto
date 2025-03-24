import java.util.Scanner;
public class GeneradorCongruencialMixto {

    //Formula del generador:
    //Xn + 1 = (a * Xn + x ) mod m

    //Condiciones para el periodo completo
    //1.- c y m son primos relativos
    //2.- a - 1 es multiplo de todos los factores primos de m

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a, c, m, x0;

        System.out.println("Ingresa los parametros iniciales:");
        System.out.print("Multiplicador (a): ");
        a = scanner.nextInt();
        System.out.print("Incremento (c): ");
        c = scanner.nextInt();
        System.out.print("Modulo (m): ");
        m = scanner.nextInt();
        System.out.print("Semilla (X0): ");
        x0 = scanner.nextInt();

        // Validación de condiciones para período completo
        if (!esPeriodoCompleto(a, c, m)) {
            System.out.println("==============================================================================");
            System.out.println("Advertencia: Los valores ingresados pueden no garantizar un período completo.");
            System.out.println("==============================================================================");
        }

        generarNumerosCongruenciales(a, c, m, x0);
    }

    private static void generarNumerosCongruenciales(int a, int c, int m, int x0) {
        System.out.println("\nIteracion \tValor generado (Xn+1)");
        int inicio = x0;  // Para detectar cuando se repite el primer valor
        for (int i = 0; i < m; i++) {
            x0 = calcularIteraciones(a, c, m, x0);
            System.out.println(i + "\t\t" + x0);

            // Si se repite el valor inicial antes de completar m iteraciones, se detiene.
            if (x0 == inicio) {
                System.out.println("Ciclo detectado, fin de la generacion.");
                break;
            }
        }
    }

    private static int calcularIteraciones(int a, int c, int m, int x0){
        return (a * x0 + c) % m;
    }

    private static boolean esPeriodoCompleto(int a, int c, int m) {
        // Verifica que c y m sean primos relativos
        System.out.println("==============================================================================");
        if (mcd(c, m) != 1) {
            System.out.println("Los valores (c, m) no cumplen con la condicion para el periodo completo");
            return false;
        }

        // Verifica que (a - 1) sea múltiplo de todos los factores primos de m
        int factoresPrimosDeM = factoresPrimos(m);
        if ((a - 1) % factoresPrimosDeM != 0) {
            System.out.println("El valor (a) no cumple con la condicion para el periodo completo");
            return false;
        }

        // Si m es potencia de 2, (a - 1) debe ser múltiplo de 4
        if ((m & (m - 1)) == 0 && (a - 1) % 4 != 0) {
            return false;
        }

        System.out.println("Los valores cumplen con las condiciones para el periodo completo");
        System.out.println("==============================================================================");
        return true;
    }

    private static int mcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static int factoresPrimos(int n) {
        int factor = 1;
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factor = i;
                n /= i;
            }
        }
        return factor;
    }
}
