package my_keystriker.ai;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.List;

// 1. Define a class to match the JSON structure
class KeyEventsJson {
    // The field name must match the JSON key "key_sequence"
    @SerializedName("key_sequence")
    private List<Integer> keySequence;

    // Getter for accessing the list of key codes
    public List<Integer> getKeySequence() {
        return keySequence;
    }
}
