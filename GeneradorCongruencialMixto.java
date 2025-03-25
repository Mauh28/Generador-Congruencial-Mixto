import java.util.Scanner;
//Realizado por Mauro Eduardo Cruz Mendez y Jorge Herrera Hipolito

/**
 * Clase que implementa un Generador Congruencial Mixto (GCM).
 * Este generador se basa en la fórmula:
 * Xn+1 = (a * Xn + c) mod m
 * 
 * Condiciones para garantizar un período completo:
 * 1.- c y m deben ser primos relativos.
 * 2.- (a - 1) debe ser múltiplo de todos los factores primos de m.
 * 3.- Si m es una potencia de 2, (a - 1) debe ser múltiplo de 4.
 */
public class GeneradorCongruencialMixto {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a, c, m, x0;

        // Solicitud de parámetros al usuario
        System.out.println("Ingresa los parametros iniciales:");
        System.out.print("Multiplicador (a): ");
        a = scanner.nextInt();
        System.out.print("Incremento (c): ");
        c = scanner.nextInt();
        System.out.print("Modulo (m): ");
        m = scanner.nextInt();
        System.out.print("Semilla (X0): ");
        x0 = scanner.nextInt();
        scanner.close();

        // Validación de condiciones para período completo
        if (!esPeriodoCompleto(a, c, m)) {
            System.out.println("==============================================================================");
            System.out.println("Advertencia: Los valores ingresados pueden no garantizar un periodo completo.");
            System.out.println("==============================================================================");
        }

        // Generación de números pseudoaleatorios
        generarNumerosCongruenciales(a, c, m, x0);
    }

    /**
     * Método que genera una secuencia de números pseudoaleatorios usando el GCM.
     * 
     * a  Multiplicador
     * c  Incremento
     * m  Módulo
     * x0 Semilla inicial
     */
    private static void generarNumerosCongruenciales(int a, int c, int m, int x0) {
        System.out.println("\nIteracion \tValor generado (Xn+1)");
        int inicio = x0;  // Para detectar la repetición del primer valor
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

    /**
     * Calcula la siguiente iteración del generador congruencial mixto.
     * 
     * a  Multiplicador
     * c  Incremento
     * m  Módulo
     * x0 Valor actual de la secuencia
     * return retorna el siguiente valor generado
     */
    private static int calcularIteraciones(int a, int c, int m, int x0) {
        return (a * x0 + c) % m;
    }

    /**
     * Verifica si los parámetros cumplen las condiciones para garantizar un período completo.
     * 
     * a Multiplicador
     * c Incremento
     * m Módulo
     * return retorna true si se cumplen las condiciones, false en caso contrario
     */
    private static boolean esPeriodoCompleto(int a, int c, int m) {
        System.out.println("==============================================================================");
        // Verifica que c y m sean primos relativos
        if (mcd(c, m) != 1) {
            System.out.println("Los valores (c, m) no cumplen con la condición para el período completo");
            return false;
        }

        // Verifica que (a - 1) sea múltiplo de todos los factores primos de m
        int factoresPrimosDeM = factoresPrimos(m);
        if ((a - 1) % factoresPrimosDeM != 0) {
            System.out.println("El valor (a) no cumple con la condición para el período completo");
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

    /**
     * Calcula el máximo común divisor (MCD) entre dos números.
     * 
     * a Primer número
     * b Segundo número
     * return retorna el El MCD (Maximo Comun Divisor) de a y b
     */
    private static int mcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Obtiene el mayor factor primo de un número dado.
     * 
     * n Número a factorizar
     * return retornara el mayor factor primo de n
     */
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
