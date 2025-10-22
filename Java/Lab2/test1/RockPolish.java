import ru.ifmo.se.pokemon.*;

public final class RockPolish extends StatusMove {
	public RockPolish() {
		super();
		this.type = Type.ROCK;
	}

	protected void applyOppEffects(Pokemon p) {
		p.setMod(Stat.SPEED, 2);
	}

	protected String describe() {
		return "ROCK POLISH";
	}
}
