import ru.ifmo.se.pokemon.*;

public final class Confide extends StatusMove {
	public Confide()
	{
		super();
		this.type = Type.NORMAL;
	}

	protected void applyOppEffects(Pokemon p) {
		p.setMod(Stat.SPECIAL_ATTACK, -1);
	}

	protected String describe() {
		return "CONFIDE";
	}
}
