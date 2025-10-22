import ru.ifmo.se.pokemon.*;

public final class Will_O_Wisp extends StatusMove {
	public Will_O_Wisp() {
		super(Type.FIRE, 1, 85);
	}

	protected void applyOppEffects(Pokemon p)
	{
		Effect effect = new Effect();
		effect.burn(p);
	}

	protected String describe() {
		return "Will O Wisp";
	}
}
