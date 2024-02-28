import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ControlleurServeur {

    public static void TestServeur() {
        Socket MyClient;
        BufferedInputStream input;
        BufferedOutputStream output;
        Board board = new Board();
        Piece serveur = new Piece(0);
        CPUPlayer ai = new CPUPlayer();

        try {
            MyClient = new Socket("localhost", 8888);
            input = new BufferedInputStream(MyClient.getInputStream());
            output = new BufferedOutputStream(MyClient.getOutputStream());
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                char cmd = 0;

                cmd = (char) input.read();
                System.out.println("cmd: " + cmd);
                // Debut de la partie en joueur rouge
                if (cmd == '1') {
                    serveur = new Piece(2);
                    ai.setPiece(new Piece(4));
                    byte[] aBuffer = new byte[1024];

                    int size = input.available();
                    input.read(aBuffer, 0, size);
                    String s = new String(aBuffer).trim();
                    // board.setBoard(s.split(" "));

                    System.out.println("Nouvelle partie! Vous jouer rouge, entrez votre premier coup : ");

                    // Aller chercher le meilleur move possible
                    Case debutAI = new Case('A', 2, ai.getPiece());
                    Case finAI = new Case('A', 8, ai.getPiece());
                    Move moveAI = new Move(debutAI, finAI);
                    String stringMove = moveAI.translateToServer();

                    output.write(stringMove.getBytes(), 0, stringMove.length());
                    output.flush();
                }
                // Debut de la partie en joueur Noir
                if (cmd == '2') {
                    serveur = new Piece(4);
                    ai.setPiece(new Piece(2));
                    System.out.println("Nouvelle partie! Vous jouer noir, attendez le coup des rouges");

                    // Lire le retour de l'adversaire
                    byte[] aBuffer = new byte[1024];
                    int size = input.available();
                    input.read(aBuffer, 0, size);
                    String s = new String(aBuffer).trim(); // Dernier état du tableau (il n'y a pas eu de move de la
                                                           // part des rouges encore)
                }

                // Le serveur demande le prochain coup (FORMAT: A1-B2 ou A1B2 )
                // Le message contient aussi le dernier coup joue.
                if (cmd == '3') {
                    System.out.println("---------------------------");
                    byte[] aBuffer = new byte[16];
                    int size = input.available();
                    input.read(aBuffer, 0, size);

                    String s = new String(aBuffer);
                    System.out.println("Dernier coup :" + s);

                    s = s.replace(" ", "");
                    s = s.replace("-", " ");
                    char[] chars = s.toCharArray();

                    // Dernier move fait par le serveur
                    Case debut = new Case(chars[0], Character.getNumericValue(chars[1]), serveur);
                    Case fin = new Case(chars[2], Character.getNumericValue(chars[3]), serveur);
                    Move lastMove = new Move(debut, fin);
                    board.movePiece(lastMove);

                    // Creer un nouveau move en fonction du meilleur choix

                    Case debutAI = new Case('B', 1, ai.getPiece());
                    Case finAI = new Case('B', 3, ai.getPiece());
                    Move moveAI = new Move(debutAI, finAI);
                    board.movePiece(moveAI);
                    String stringMove = moveAI.translateToServer();
                    output.write(stringMove.getBytes(), 0, stringMove.length());
                    output.flush();
                }
                // Le dernier coup est invalide
                if (cmd == '4') {
                    System.out.println("Coup invalide, entrez un nouveau coup : ");
                    String move = null;
                    move = console.readLine();
                    output.write(move.getBytes(), 0, move.length());
                    output.flush();
                }
                // La partie est terminée
                if (cmd == '5') {
                    byte[] aBuffer = new byte[16];
                    int size = input.available();
                    input.read(aBuffer, 0, size);
                    String s = new String(aBuffer);
                    System.out.println("Partie Terminé. Le dernier coup joué est: " + s);
                    String move = null;
                    move = console.readLine();
                    output.write(move.getBytes(), 0, move.length());
                    output.flush();

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
