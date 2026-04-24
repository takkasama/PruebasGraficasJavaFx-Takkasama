package cr.ac.una.puebasgraficas.service;

import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.Voice;
import java.util.HashMap;
import java.util.Map;



public class FreeTTS {
 static {
        System.setProperty("freetts.voices",
            "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory," +
            "com.sun.speech.freetts.en.us.cmu_time_awb.AlanVoiceDirectory"
        );
 }
    private static FreeTTS instance; 
    private Voice currentVoice;
    private Map<String,Voice> voices;

    public static FreeTTS getInstance(){
        
        if(instance == null){
            synchronized (FreeTTS.class){ 
                if(instance == null) instance = new FreeTTS(); 
            }
        }
        
        return instance;
    }
    
    private FreeTTS(){
        voices = new HashMap<>();
        loadVoices();
    }    

    public void speak(String text){
        try{
            if(text == null || text.isBlank()) return;
            Thread thread = new Thread(()-> currentVoice.speak(text));
            thread.start();
            thread.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    private void loadVoices(){
        VoiceManager voiceManger = VoiceManager.getInstance();        
        
        voices = Map.of(
                "voice1", voiceManger.getVoice("kevin16"),
                "voice2", voiceManger.getVoice("kevin"),
                "voice3", voiceManger.getVoice("alan"));
        
         verifyVoices();
        
         voices.values().forEach(Voice::allocate);
         currentVoice = voices.get("voice1");
        
    }
    
    private void verifyVoices(){
        
        Map<String, String> voiceLabels = Map.of(
            "voice1", "VOICE1 - KEVIN16 -",
            "voice2", "VOICE2 - KEVIN",
             "voice3", "VOICE3 - ALAN"
        );
        
        for (Map.Entry<String, String> entry : voiceLabels.entrySet()) {   
            if(voices.get(entry.getKey()) == null)
                throw new IllegalArgumentException( "The Voice  : " + entry.getValue() + "Is Invalid");
        }}
    
    
    /**
     * @param voiceNumber Refer to number of the voice, could be  "voice1", "voice2" or "voice3"
     */
    public void setCurretVoice(String voiceNumber){
        if(voiceNumber == null || voiceNumber.isBlank()) return;
        Voice newVoice = voices.get(voiceNumber);
        if(newVoice == null || newVoice == currentVoice) return;
        
        currentVoice.deallocate();
        
        currentVoice = newVoice;
        
        currentVoice.allocate();
    }
    
}
