package my_keystriker;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws AWTException, IOException {
        Robot robot = new Robot();
        robot.delay(2000); // Give user time to focus on target window
        System.out.println("Starting...");
        Files.lines(Path.of(args[0])).forEach(input -> typeString(robot, input));
        robot.waitForIdle();
    }

    private static void typeString(Robot robot, String text) {
        for (char c : text.toCharArray()) {
            typeChar(robot, c);
        }
        robot.waitForIdle();
    }

    private static void typeChar(Robot robot, char c) {
        boolean shiftNeeded = false;
        int keyCode = -1;

        switch (c) {
            // Letters
            case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
            case 'g': case 'h': case 'i': case 'j': case 'k': case 'l':
            case 'm': case 'n': case 'o': case 'p': case 'q': case 'r':
            case 's': case 't': case 'u': case 'v': case 'w': case 'x':
            case 'y': case 'z':
                keyCode = KeyEvent.getExtendedKeyCodeForChar(Character.toUpperCase(c));
                break;
            case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
            case 'G': case 'H': case 'I': case 'J': case 'K': case 'L':
            case 'M': case 'N': case 'O': case 'P': case 'Q': case 'R':
            case 'S': case 'T': case 'U': case 'V': case 'W': case 'X':
            case 'Y': case 'Z':
                keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                shiftNeeded = true;
                break;

            // Digits and shifted symbols
            case '0': keyCode = KeyEvent.VK_0; break;
            case '1': keyCode = KeyEvent.VK_1; break;
            case '2': keyCode = KeyEvent.VK_2; break;
            case '3': keyCode = KeyEvent.VK_3; break;
            case '4': keyCode = KeyEvent.VK_4; break;
            case '5': keyCode = KeyEvent.VK_5; break;
            case '6': keyCode = KeyEvent.VK_6; break;
            case '7': keyCode = KeyEvent.VK_7; break;
            case '8': keyCode = KeyEvent.VK_8; break;
            case '9': keyCode = KeyEvent.VK_9; break;
            case '!': keyCode = KeyEvent.VK_1; shiftNeeded = true; break;
            case '@': keyCode = KeyEvent.VK_2; shiftNeeded = true; break;
            case '#': keyCode = KeyEvent.VK_3; shiftNeeded = true; break;
            case '$': keyCode = KeyEvent.VK_4; shiftNeeded = true; break;
            case '%': keyCode = KeyEvent.VK_5; shiftNeeded = true; break;
            case '^': keyCode = KeyEvent.VK_6; shiftNeeded = true; break;
            case '&': keyCode = KeyEvent.VK_7; shiftNeeded = true; break;
            case '*': keyCode = KeyEvent.VK_8; shiftNeeded = true; break;
            case '(': keyCode = KeyEvent.VK_9; shiftNeeded = true; break;
            case ')': keyCode = KeyEvent.VK_0; shiftNeeded = true; break;

            // Punctuation and special symbols
            case '-': keyCode = KeyEvent.VK_MINUS; break;
            case '_': keyCode = KeyEvent.VK_MINUS; shiftNeeded = true; break;
            case '=': keyCode = KeyEvent.VK_EQUALS; break;
            case '+': keyCode = KeyEvent.VK_EQUALS; shiftNeeded = true; break;
            case '[': keyCode = KeyEvent.VK_OPEN_BRACKET; break;
            case '{': keyCode = KeyEvent.VK_OPEN_BRACKET; shiftNeeded = true; break;
            case ']': keyCode = KeyEvent.VK_CLOSE_BRACKET; break;
            case '}': keyCode = KeyEvent.VK_CLOSE_BRACKET; shiftNeeded = true; break;
            case '\\': keyCode = KeyEvent.VK_BACK_SLASH; break;
            case '|': keyCode = KeyEvent.VK_BACK_SLASH; shiftNeeded = true; break;
            case ';': keyCode = KeyEvent.VK_SEMICOLON; break;
            case ':': keyCode = KeyEvent.VK_SEMICOLON; shiftNeeded = true; break;
            case '\'': keyCode = KeyEvent.VK_QUOTE; break;
            case '"': keyCode = KeyEvent.VK_QUOTE; shiftNeeded = true; break;
            case ',': keyCode = KeyEvent.VK_COMMA; break;
            case '<': keyCode = KeyEvent.VK_COMMA; shiftNeeded = true; break;
            case '.': keyCode = KeyEvent.VK_PERIOD; break;
            case '>': keyCode = KeyEvent.VK_PERIOD; shiftNeeded = true; break;
            case '/': keyCode = KeyEvent.VK_SLASH; break;
            case '?': keyCode = KeyEvent.VK_SLASH; shiftNeeded = true; break;
            case '`': keyCode = KeyEvent.VK_BACK_QUOTE; break;
            case '~': keyCode = KeyEvent.VK_BACK_QUOTE; shiftNeeded = true; break;

            // Space and tabs
            case ' ': keyCode = KeyEvent.VK_SPACE; break;
            case '\t': keyCode = KeyEvent.VK_TAB; break;
            case '\n': keyCode = KeyEvent.VK_ENTER; break;

            default:
                System.out.println("Unsupported character: " + c);
                return;
        }

        if (keyCode == -1) {
            System.out.println("Cannot type char: " + c);
            return;
        }

        if (shiftNeeded) {
            robot.keyPress(KeyEvent.VK_SHIFT);
        }

        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);

        if (shiftNeeded) {
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }

        robot.delay(50); // adjustable typing speed
    }
}
