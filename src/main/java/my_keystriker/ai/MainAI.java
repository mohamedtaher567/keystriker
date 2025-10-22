package my_keystriker.ai;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import javax.xml.transform.stream.StreamSource;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.google.gson.Gson;

public class MainAI {

	private static final String PROMPT_TEMPLATE = "Generate the sequence of Java AWT Robot key codes (integers from java.awt.event.KeyEvent) required to type the following text: \"[YOUR DESIRED STRING HERE]\".\r\n"
			+ "\r\n"
			+ "The output MUST be a single JSON string with the key \"key_sequence\" containing an array of integers.\r\n"
			+ "\r\n" + "**Crucial Logic:**\r\n"
			+ "1.  Represent each action as a separate integer in the array (e.g., keyPress, keyRelease, ShiftPress, ShiftRelease).\r\n"
			+ "2.  For uppercase letters, precede the main letter key code with the key code for 'Shift' (VK_SHIFT=16), and follow the letter key code with the key code for 'Shift' release.\r\n"
			+ "3.  For standard keys (lowercase, numbers, space), provide only the key code for the character.\r\n"
			+ "4.  Assume a QWERTY English keyboard layout. \r\n"
			+ "5.  Handle key releases using negative values of same keycode \r\n"
			+ "6.  Give me resposne that is json parsable directly. \r\n";

	public static void main(String[] args) throws Exception {
		Robot robot = new Robot();
		Iterator<String> iterator = Files.lines(Path.of(args[0])).iterator();
		while (iterator.hasNext()) {
			String line = iterator.next();
			System.out.println("Line: " + line);
			List<Integer> arr = getKeyCodes(line);

			for (int keyCode : arr) {
				robot.delay(100);
				if (keyCode < 0) {
					robot.keyRelease(-keyCode);
				} else {
					robot.keyPress(keyCode);
				}
			}
			if (iterator.hasNext()) {
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.delay(100);
				robot.keyRelease(KeyEvent.VK_ENTER);
			}
		}
	}

	private static List<Integer> getKeyCodes(String line) throws Exception {
		// Use the builder and setApiKey() method
		String apiKey = System.getenv("GEMINI_API_KEY");
		if(apiKey == null) {
			throw new Exception("You need to set GEMINI_API_KEY enviroment variable with your Gemini API key");
		}
		Client client = Client.builder().apiKey(apiKey).build();
		String linePrompt = PROMPT_TEMPLATE.replace("[YOUR DESIRED STRING HERE]", line);
		System.out.println("lineprompt: " + linePrompt);
		GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash",
				linePrompt, null);
		System.out.println("response is ready");
		System.out.println(response.text());
		Gson gson = new Gson();
        KeyEventsJson keyEventsObject = gson.fromJson(response.text().replace("```json\n", "").replace("\n```", ""), KeyEventsJson.class);

		return keyEventsObject.getKeySequence();
	}
}
