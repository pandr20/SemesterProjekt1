package GameLauncher;

import MusicPlayer.PlayMusic;

public class Start {
    public static void main (String[] args) {

        String filepath = "worldofzuul/MusicFileVictor.wav";

        PlayMusic musicObject = new PlayMusic();
        musicObject.playMusic(filepath);

        Game game = new Game();
        game.play();
    }
}
