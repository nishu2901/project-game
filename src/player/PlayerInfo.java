package player;

public class PlayerInfo {
	String charName;
	int player;
	private int health;

	public PlayerInfo(int player) {
		this.charName = "";
		this.player = player;
		this.health = 100;
	}

	public void resetHealth() {
		health = 100;
	}

	public void giveDamage(int amount) {
		health = health - amount;
	}

	public int getHealth() {
		return health;
	}

	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

}
