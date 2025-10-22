import ru.ifmo.se.pokemon.*;

public final class Growl extends StatusMove {
	//THERE IS A BUG
	//IF power isnt defined overflow occures
	public Growl() {
		super(Type.NORMAL, 1, 100);
	}

	protected void applyOppEffects(Pokemon p) {
		p.setMod(Stat.ATTACK, -1);
	}

	protected String describe() {
		return "Growl";
	}
}
