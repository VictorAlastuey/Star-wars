
import java.util.Random;
import java.util.Scanner;

    public class Main {
        private static final int TAMANO = 10;
        private static final char VACIO = 'L';
        private static final char YODA = 'Y';
        private static final char VADER = 'V';
        private static final char DARTH_MAUL = 'D';
        private static final char R2D2 = 'R';
        private static final char MURO = 'M';
        private static final char FINAL = 'F';
        private static final char POCION = 'P';
        private static final int VIDAS = 3;

        private static char[][] tableroYoda = new char[TAMANO][TAMANO];
        private static char[][] tableroVader = new char[TAMANO][TAMANO];
        private static int yodaX, yodaY, vaderX, vaderY;
        private static int vidasYoda = VIDAS, vidasVader = VIDAS;
        private static boolean yodaTienePocion = false, vaderTienePocion = false;

        public static void main(String[] args) {
            inicializarTablero(tableroYoda);
            inicializarTablero(tableroVader);
            colocarJugador(YODA);
            colocarJugador(VADER);
            colocarObstaculos(tableroYoda, DARTH_MAUL);
            colocarObstaculos(tableroVader, R2D2);
            colocarMuros(tableroYoda);
            colocarMuros(tableroVader);
            colocarPociones(tableroYoda);
            colocarPociones(tableroVader);
            colocarFinal();

            Scanner scanner = new Scanner(System.in);
            while (vidasYoda > 0 && vidasVader > 0) {
                mostrarTableros();
                System.out.println("Turno de Yoda:");
                moverJugador(scanner, tableroYoda, YODA);

                if (yodaX == TAMANO - 1 && yodaY == TAMANO - 1) {
                    System.out.println("¡Yoda ha llegado a la casilla final! ¡Yoda gana!");
                    break;
                }

                System.out.println("Turno de Darth Vader:");
                moverJugador(scanner, tableroVader, VADER);

                if (vaderX == TAMANO - 1 && vaderY == TAMANO - 1) {
                    System.out.println("¡Darth Vader ha llegado a la casilla final! ¡Darth Vader gana!");
                    break;
                }
            }

            if (vidasYoda == 0) {
                System.out.println("¡Darth Vader gana! Yoda ha perdido todas las vidas.");
            } else if (vidasVader == 0) {
                System.out.println("¡Yoda gana! Darth Vader ha perdido todas las vidas.");
            }

            scanner.close();
        }

        private static void inicializarTablero(char[][] tablero) {
            for (int i = 0; i < TAMANO; i++) {
                for (int j = 0; j < TAMANO; j++) {
                    tablero[i][j] = VACIO;
                }
            }
        }

        private static void colocarJugador(char jugador) {
            Random rand = new Random();
            int x = rand.nextInt(TAMANO);
            int y = rand.nextInt(TAMANO);

            if (jugador == YODA) {
                yodaX = x;
                yodaY = y;
                tableroYoda[x][y] = YODA;
            } else if (jugador == VADER) {
                vaderX = x;
                vaderY = y;
                tableroVader[x][y] = VADER;
            }
        }

        private static void colocarObstaculos(char[][] tablero, char obstaculo) {
            Random rand = new Random();
            int count = 0;
            while (count < 5) {
                int x = rand.nextInt(TAMANO);
                int y = rand.nextInt(TAMANO);
                if (tablero[x][y] == VACIO) {
                    tablero[x][y] = obstaculo;
                    count++;
                }
            }
        }

        private static void colocarMuros(char[][] tablero) {
            Random rand = new Random();
            int count = 0;
            while (count < 5) {
                int x = rand.nextInt(TAMANO);
                int y = rand.nextInt(TAMANO);
                if (tablero[x][y] == VACIO) {
                    tablero[x][y] = MURO;
                    count++;
                }
            }
        }

        private static void colocarPociones(char[][] tablero) {
            Random rand = new Random();
            int count = 0;
            while (count < 5) {
                int x = rand.nextInt(TAMANO);
                int y = rand.nextInt(TAMANO);
                if (tablero[x][y] == VACIO) {
                    tablero[x][y] = POCION;
                    count++;
                }
            }
        }

        private static void colocarFinal() {
            tableroYoda[TAMANO - 1][TAMANO - 1] = FINAL;
            tableroVader[TAMANO - 1][TAMANO - 1] = FINAL;
        }

        private static void mostrarTableros() {
            System.out.println("Tablero de Yoda:");
            mostrarTablero(tableroYoda);
            System.out.println("\nTablero de Darth Vader:");
            mostrarTablero(tableroVader);
        }

        private static void mostrarTablero(char[][] tablero) {
            for (char[] fila : tablero) {
                for (char celda : fila) {
                    System.out.print(celda + " ");
                }
                System.out.println();
            }
        }

        private static void moverJugador(Scanner scanner, char[][] tablero, char jugador) {
            boolean tienePocion = (jugador == YODA) ? yodaTienePocion : vaderTienePocion;

            if (tienePocion) {
                System.out.println("Tienes una poción. Puedes intercambiar tu posición con cualquier casilla 'L'. Ingresa las coordenadas (fila columna): ");
                int nuevaX = scanner.nextInt();
                int nuevaY = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer de entrada

                if (tablero[nuevaX][nuevaY] == VACIO) {
                    if (jugador == YODA) {
                        tablero[yodaX][yodaY] = VACIO;
                        yodaX = nuevaX;
                        yodaY = nuevaY;
                        tablero[yodaX][yodaY] = YODA;
                        yodaTienePocion = false;
                    } else {
                        tablero[vaderX][vaderY] = VACIO;
                        vaderX = nuevaX;
                        vaderY = nuevaY;
                        tablero[vaderX][vaderY] = VADER;
                        vaderTienePocion = false;
                    }
                    return;
                } else {
                    System.out.println("La casilla no es válida para el intercambio.");
                }
            }

            System.out.println("Ingresa tu movimiento (por ejemplo, 2D para 2 pasos a la derecha): ");
            String entrada = scanner.nextLine();
            int pasos = Character.getNumericValue(entrada.charAt(0));
            char direccion = entrada.charAt(1);

            int dx = 0, dy = 0;
            switch (direccion) {
                case 'D':
                    dy = pasos;
                    break;
                case 'A':
                    dy = -pasos;
                    break;
                case 'W':
                    dx = -pasos;
                    break;
                case 'S':
                    dx = pasos;
                    break;
                default:
                    System.out.println("¡Movimiento inválido!");
                    return;
            }

            int nuevoX = (jugador == YODA ? yodaX : vaderX) + dx;
            int nuevoY = (jugador == YODA ? yodaY : vaderY) + dy;

            nuevoX = (nuevoX + TAMANO) % TAMANO;
            nuevoY = (nuevoY + TAMANO) % TAMANO;

            if (tablero[nuevoX][nuevoY] == MURO) {
                System.out.println("¡Te encontraste con un muro! No puedes avanzar.");
                return;
            }

            if (jugador == YODA) {
                tablero[yodaX][yodaY] = VACIO;
                yodaX = nuevoX;
                yodaY = nuevoY;
                if (tableroYoda[yodaX][yodaY] == DARTH_MAUL) vidasYoda--;
                if (tableroYoda[yodaX][yodaY] == POCION) yodaTienePocion = true;
                tableroYoda[yodaX][yodaY] = YODA;
            } else {
                tableroVader[vaderX][vaderY] = VACIO;
                vaderX = nuevoX;
                vaderY = nuevoY;
                if (tableroVader[vaderX][vaderY] == R2D2) vidasVader--;
                if (tableroVader[vaderX][vaderY] == POCION) vaderTienePocion = true;
                tableroVader[vaderX][vaderY] = VADER;
            }
        }
    }

